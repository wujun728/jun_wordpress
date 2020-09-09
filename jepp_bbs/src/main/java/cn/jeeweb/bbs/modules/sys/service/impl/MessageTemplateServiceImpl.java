package cn.jeeweb.bbs.modules.sys.service.impl;

import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import cn.jeeweb.bbs.modules.sys.service.IMessageTemplateService;
import cn.jeeweb.bbs.modules.sys.entity.MessageTemplate;
import cn.jeeweb.bbs.modules.sys.mapper.MessageTemplateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.bbs.modules.sys.service.impl
* @title: 消息模版服务实现
* @description: 消息模版服务实现
* @author: 王存见
* @date: 2018-09-03 15:10:10
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("messagetemplateService")
public class MessageTemplateServiceImpl  extends CommonServiceImpl<MessageTemplateMapper,MessageTemplate> implements  IMessageTemplateService {

}