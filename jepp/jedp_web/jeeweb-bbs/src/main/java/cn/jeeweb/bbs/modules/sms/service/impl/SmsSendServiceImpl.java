package cn.jeeweb.bbs.modules.sms.service.impl;

import cn.jeeweb.bbs.modules.sms.entity.SmsSendLog;
import cn.jeeweb.bbs.modules.sms.entity.SmsTemplate;
import cn.jeeweb.bbs.modules.sms.service.ISmsSendLogService;
import cn.jeeweb.bbs.modules.sms.service.ISmsSendService;
import cn.jeeweb.bbs.modules.sms.service.ISmsTemplateService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.sms.disruptor.SmsHelper;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.common.utils.mapper.JsonMapper;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.bbs.modules.sms.service.impl
 * @title:
 * @description: 短信发送
 * @author: 王存见
 * @date: 2018/9/12 11:15
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@Service("smsSendService")
public class SmsSendServiceImpl implements ISmsSendService {

	@Autowired
	private ISmsTemplateService smsTemplateService;
	@Autowired
	private ISmsSendLogService smsSendLogService;
	@Autowired
	private SmsHelper smsHelper; //自动注入的Bean

	@Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public void send(String phone, String code, Map<String, Object> datas) {
		String[] phones = { phone };
		send(phones,code,datas);
	}

	@Override
	@Transactional(propagation= Propagation.NOT_SUPPORTED)
	public void send(String[] phones, String code, Map<String, Object> datas) {
		SmsTemplate template = smsTemplateService.selectOne(new EntityWrapper<SmsTemplate>().eq("code", code));
		if (datas == null) {
			datas = new HashMap<>();
		}
		if (template == null){
			return ;
		}
		for (String phone: phones) {
			SmsSendLog smsSendLog = new SmsSendLog();
			smsSendLog.setPhone(phone);
			smsSendLog.setTemplateName(template.getName());
			smsSendLog.setMsg("发送成功");
			smsSendLog.setSendCode(code);
			smsSendLog.setResponseDate(new Date());
			smsSendLog.setSendData(JsonMapper.toJsonString(datas));
			smsSendLog.setStatus(SmsSendLog.SMS_SEND_STATUS_IN);
			smsSendLog.setTryNum(0);
			smsSendLog.setDelFlag("0");
			smsSendLogService.insert(smsSendLog);
			// 发送邮件,这里以后需要判断类型
			smsHelper.sendAsyncSms(smsSendLog.getId(),phone,template.getTemplateContent(),null,datas);
		}
	}

	@Override
	public void send(String eventId, String phone, String code, Map<String, Object> datas) {
		String[] phones = { phone };
		send(eventId,phones,code,datas);
	}

	@Override
	public void send(String eventId, String[] phones, String code, Map<String, Object> datas) {
		SmsTemplate template = smsTemplateService.selectOne(new EntityWrapper<SmsTemplate>().eq("code", code));
		if (datas == null) {
			datas = new HashMap<>();
		}
		for (String phone: phones) {
			// 发送邮件,这里以后需要判断类型
			smsHelper.sendAsyncSms(eventId, phone, template.getTemplateContent(), null, datas);
		}
	}
}