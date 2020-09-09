package cn.jeeweb.web.modules.sms.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.sms.data.SmsResult;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.sms.service.ISmsSendService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @title 短信发送
 * @description 短信发送
 * @author 王存见
 * @date 2017-06-08 12:56:37
 * @version V1.0
 *
 */
@RestController
@RequestMapping("/sms/send")
@Log(title = "短信发送")
public class SmsSendController extends BaseController {
	@Autowired
	private ISmsSendService smsSendService;

	@PostMapping(value = "/sendSmsByCode")
	@Log(logType = LogType.OTHER,title = "短信发送")
	public Response sendSmsByCode(HttpServletRequest request,
								  @RequestParam("phone") String phone,
								  @RequestParam("code") String code,
								  @RequestParam("data") String data) {
		try {
			String[] phones=phone.split(",");
			if (!StringUtils.isEmpty(data)) {
				smsSendService.send(phones, code, JSON.parseObject(StringEscapeUtils.unescapeHtml4(data),Map.class));
			} else {
				smsSendService.send(phones, code, Maps.newHashMap());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("短信发送失败");
		}
		return Response.ok("短信发送成功");
	}

	@PostMapping(value = "/massSendSmsByCode")
	public Response massSendSmsByCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			String phone = request.getParameter("phone");
			String code = request.getParameter("code");
			String data = request.getParameter("data");
			String[] phones=phone.split(",");
			if (!StringUtils.isEmpty(data)) {
				smsSendService.send(phones, code, JSON.parseObject(StringEscapeUtils.unescapeHtml4(data),Map.class));
			} else {
				smsSendService.send(phones, code, Maps.newHashMap());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("短信发送失败");
		}
		return Response.ok("短信发送成功");
	}
}