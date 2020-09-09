package cn.jeeweb.web.modules.sms.task;

import cn.jeeweb.web.modules.sms.entity.SmsSendLog;
import cn.jeeweb.web.modules.sms.service.ISmsSendLogService;
import cn.jeeweb.web.modules.sms.service.ISmsSendService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.web.modules.email.task
 * @title:
 * @description: 短息任务，主要检查发送失败的
 * @author: 王存见
 * @date: 2018/9/17 16:52
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Component("smsTask")
public class SmsTask implements ApplicationListener<ContextRefreshedEvent> {
    public final Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private ISmsSendLogService smsSendLogService;
    @Autowired
    private ISmsSendService smsSendService;
    private Boolean isRun=Boolean.FALSE;

    /**
     * 这里把发送中的短信恢复发送
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //找到发送中的短信重新发送
        List<SmsSendLog> smsSendLogList = smsSendLogService
                .selectList(new EntityWrapper<>(SmsSendLog.class)
                        .eq("status", SmsSendLog.SMS_SEND_STATUS_IN));
        //恢复处理
        for (SmsSendLog smsSendLog : smsSendLogList) {
            smsSendService.send(smsSendLog.getId(),smsSendLog.getPhone(),
                    smsSendLog.getSendCode(), JSON.parseObject(smsSendLog.getSendData(), Map.class));
        }
    }

    /**
     * 检查回复并写入到日志中
     */
    public void run() {
        log.info(" 短信重发日志运行开始......................................" + (new Date()));
        if (!isRun) {
            isRun=Boolean.TRUE;
            try{
                //查询出询问短信
                //找到未发送成功的短信
                List<SmsSendLog> smsSendLogList = smsSendLogService
                        .selectList(new EntityWrapper<SmsSendLog>(SmsSendLog.class)
                                .eq("status", SmsSendLog.SMS_SEND_STATUS_FAIL).lt("tryNum", 3)
                        );
                //恢复处理
                for (SmsSendLog smsSendLog : smsSendLogList) {
                    sendByLog(smsSendLog);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                isRun=Boolean.FALSE;
            }
        }
    }

    public void sendByLog(SmsSendLog smsSendLog){
        try {
            //更新类型
            smsSendLog.setStatus(SmsSendLog.SMS_SEND_STATUS_IN);
            //表示响应成功！
            if (smsSendLog.getTryNum() == null) {
                smsSendLog.setTryNum(0);
            }
            smsSendLog.setTryNum(smsSendLog.getTryNum() + 1);
            smsSendLogService.insertOrUpdate(smsSendLog);
            smsSendService.send(smsSendLog.getId(),smsSendLog.getPhone(), smsSendLog.getSendCode(),
                    JSON.parseObject(smsSendLog.getSendData(), Map.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
