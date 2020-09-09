package cn.jeeweb.web.modules.sms.task;

import org.springframework.stereotype.Component;

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
public class SmsTask {

    public void run(){
        System.out.println("SmsTask 运行啦！");
    }
}
