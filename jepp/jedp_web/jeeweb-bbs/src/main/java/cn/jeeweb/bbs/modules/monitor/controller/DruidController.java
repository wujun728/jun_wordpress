package cn.jeeweb.bbs.modules.monitor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.jeeweb.bbs.aspectj.annotation.Log;
import cn.jeeweb.common.mvc.annotation.ViewPrefix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.jeeweb.common.mvc.controller.BaseController;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresMethodPermissions;
import cn.jeeweb.common.security.shiro.authz.annotation.RequiresPathPermission;

@Controller
@RequestMapping("${jeeweb.admin.url.prefix}/monitor/druid")
@ViewPrefix("modules/monitor/druid")
@RequiresPathPermission("monitor:druid")
public class DruidController extends BaseController {

	@RequiresMethodPermissions("index")
	@RequestMapping(method = RequestMethod.GET)
	@Log(title = "Druid监控查看")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {
		return display("index");
	}
}
