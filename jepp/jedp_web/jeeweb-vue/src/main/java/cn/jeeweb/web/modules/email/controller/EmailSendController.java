package cn.jeeweb.web.modules.email.controller;

import cn.jeeweb.common.http.Response;
import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.web.aspectj.annotation.Log;
import cn.jeeweb.web.aspectj.enums.LogType;
import cn.jeeweb.web.modules.email.service.IEmailSendService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringEscapeUtils;
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
@RequestMapping("/email/send")
@Log(title = "邮件发送")
public class EmailSendController extends BaseController {
	@Autowired
	private IEmailSendService emailSendService;

	@PostMapping(value = "/massSendEmailByCode")
	@Log(logType = LogType.OTHER,title = "发送邮件")
	public Response massSendEmailByCode(HttpServletRequest request,
										@RequestParam("email") String email,
										@RequestParam("code") String code,
										@RequestParam("data") String data) {
		try {
			String[] emails=email.split(",");
			for (String emailItem:emails) {
				if (!org.springframework.util.StringUtils.isEmpty(data)) {
					Map<String,Object> datas = JSON.parseObject(StringEscapeUtils.unescapeHtml4(data),Map.class);
					emailSendService.send(emailItem, code,datas);
				} else {
					emailSendService.send(emailItem, code, Maps.newHashMap());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.error("邮件发送失败");
		}
		return Response.ok("删除成功");
	}
}