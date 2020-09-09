package cn.jeeweb.web.modules.sys.controller;

import cn.jeeweb.web.modules.oa.entity.OaNotification;
import cn.jeeweb.web.modules.oa.service.IOaNotificationService;
import cn.jeeweb.common.mybatis.mvc.wrapper.EntityWrapper;
import cn.jeeweb.common.utils.CookieUtils;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.utils.ThemeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
	@Autowired
	private IOaNotificationService oaNotificationService;

	/**
	 * 跳转到后台首页
	 * @return
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("redirect:/admin");
	}

	@RequestMapping(value = "${jeeweb.admin.url.prefix}",method = RequestMethod.GET)
	public String index(Model model) {
		// 加载通知公告
		int oaNotificationCount = oaNotificationService
				.selectCount(new EntityWrapper<OaNotification>(OaNotification.class).eq("status", "1"));
		List<OaNotification> oaNotifications = oaNotificationService
				.selectList(new EntityWrapper<OaNotification>(OaNotification.class).eq("status", "1"));
		model.addAttribute("oaNotificationCount", oaNotificationCount);
		model.addAttribute("oaNotifications", oaNotifications);
		// 加载模版
		String theme = ThemeUtils.getTheme();
		return "modules/sys/index/index-" + theme;
	}

	/**
	 * Coookie设置
	 */
	@RequestMapping(value = "${jeeweb.admin.url.prefix}/theme/{theme}")
	public String themeCookie(@PathVariable String theme, HttpServletRequest request) {
		if (StringUtils.isNotBlank(theme)) {
			ThemeUtils.setTheme(theme);
		}
		return "redirect:" + request.getParameter("url");
	}

	@RequestMapping("${jeeweb.admin.url.prefix}/main")
	public String main() {
		return "modules/sys/index/main";
	}

}
