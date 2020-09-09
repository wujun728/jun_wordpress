package cn.jeeweb.web.modules.sms.service.impl;

import cn.jeeweb.web.modules.sms.entity.SmsTemplate;
import cn.jeeweb.web.modules.sms.mapper.SmsTemplateMapper;
import cn.jeeweb.web.modules.sms.service.ISmsTemplateService;
import cn.jeeweb.common.mybatis.mvc.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* All rights Reserved, Designed By www.jeeweb.cn
*
* @version V1.0
* @package cn.jeeweb.web.modules.sms.service.impl
* @title: 短信模板服务实现
* @description: 短信模板服务实现
* @author: 王存见
* @date: 2018-09-14 09:47:35
* @copyright: 2018 www.jeeweb.cn Inc. All rights reserved.
*/
@Transactional
@Service("smstemplateService")
public class SmsTemplateServiceImpl  extends CommonServiceImpl<SmsTemplateMapper,SmsTemplate> implements  ISmsTemplateService {

}