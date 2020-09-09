package cn.jeeweb.web.modules.email.service.impl;

import cn.jeeweb.web.modules.email.entity.EmailSendLog;
import cn.jeeweb.web.modules.email.mapper.EmailSendLogMapper;
import cn.jeeweb.web.modules.email.service.IEmailSendLogService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.email.service.impl
* @title: 邮件发送日志服务实现
* @description: 邮件发送日志服务实现
* @author: 王存见
* @date: 2018-09-12 10:58:46
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("emailsendlogService")
public class EmailSendLogServiceImpl  extends CommonServiceImpl<EmailSendLogMapper,EmailSendLog> implements  IEmailSendLogService {

}