package cn.jeeweb.web.modules.email.service;

import cn.jeeweb.web.modules.email.entity.EmailSendLog;
import cn.jeeweb.common.mybatis.mvc.service.ICommonService;

import java.io.Serializable;
import java.util.List;

/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.email.service
* @title: 邮件发送日志服务接口
* @description: 邮件发送日志服务接口
* @author: 王存见
* @date: 2018-09-12 10:58:46
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
public interface IEmailSendLogService extends ICommonService<EmailSendLog> {

    /**
     * <p>
     *  邮件重发
     * </p>
     *
     * @param idList 主键ID列表
     * @return boolean
     */
    boolean retrySend(List<? extends Serializable> idList);
}