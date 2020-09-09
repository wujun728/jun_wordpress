package cn.jeeweb.web.modules.email.dao;

import cn.jeeweb.web.modules.email.entity.EmailSendLog;
import cn.jeeweb.web.modules.email.service.IEmailSendLogService;
import cn.jeeweb.common.email.data.EmailResult;
import cn.jeeweb.common.email.disruptor.EmailDao;
import cn.jeeweb.common.email.disruptor.EmailData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 *
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @title: EmailDaoIml.java
 * @package cn.gov.gzst.upms.sms.dao
 * @description: 短信信息返回
 * @author: 王存见
 * @date: 2017年6月8日 上午11:20:04
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */
public class EmailDaoImpl implements EmailDao {

    @Autowired
    private IEmailSendLogService emailSendLogService;

    @Override
    public void doResult(String eventId, EmailData emailData, EmailResult smsResult) {
        try {
            EmailSendLog sendLog = emailSendLogService.selectById(eventId);
            sendLog.setMsg(smsResult.getMsg());
            if (smsResult.isSuccess()) {
                sendLog.setStatus(EmailSendLog.EMAIL_SEND_STATUS_SUCCESS);
            } else {
                sendLog.setStatus(EmailSendLog.EMAIL_SEND_STATUS_FAIL);
            }
            sendLog.setResponseDate(new Date());
            emailSendLogService.insertOrUpdate(sendLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}