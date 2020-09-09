package cn.jeeweb.bbs.config;

import cn.jeeweb.bbs.modules.email.dao.EmailDaoImpl;
import cn.jeeweb.common.email.disruptor.EmailDao;
import cn.jeeweb.common.email.disruptor.EmailHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.dataact.jeeweb.modules.config
 * @title:
 * @description: 邮件发送配置
 * @author: 王存见
 * @date: 2018/3/1 16:06
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Configuration
public class EmailConfig {

    @Bean
    public EmailDao emailDao(){
        EmailDao emailDao=  new EmailDaoImpl();
        return emailDao;
    }

    @Bean
    public EmailHelper emailHelper(EmailDao emailDao){
        EmailHelper emailHelper=  new EmailHelper();
        emailHelper.setHandlerCount(1);
        emailHelper.setBufferSize(1024);
        emailHelper.setEmailDao(emailDao);
        return emailHelper;
    }

}
