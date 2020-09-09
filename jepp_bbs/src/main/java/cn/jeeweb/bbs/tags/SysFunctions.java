package cn.jeeweb.bbs.tags;

import javax.servlet.http.Cookie;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.common.utils.SpringContextHolder;
import cn.jeeweb.common.utils.StringUtils;
import org.springframework.core.env.Environment;

/**
 * 
 * All rights Reserved, Designed By www.jeeweb.cn
 * 
 * @title: SysFunctions.java
 * @package cn.jeeweb.web.tags
 * @description: 提供一些公用的函数
 * @author: 王存见
 * @date: 2017年3月28日 下午10:04:07
 * @version V1.0
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 *
 */
public class SysFunctions {

	/**
	 * 获得后台地址
	 * 
	 * @title: getAdminUrlPrefix
	 * @description: 获得后台地址
	 * @return
	 * @return: String
	 */
	public static String getAdminUrlPrefix() {
		Environment env= SpringContextHolder.getBean(Environment.class);
		String adminUrlPrefix = env.getProperty("jeeweb.admin.url.prefix");
		return adminUrlPrefix;
	}

	/**
	 * 加载风格
	 * 
	 * @title: getTheme
	 * @return
	 * @return: String
	 */
	public static String getTheme() {
		// 默认风格
		Environment env= SpringContextHolder.getBean(Environment.class);
		String theme = env.getProperty("jeeweb.admin.default.theme");
		if (StringUtils.isEmpty(theme)) {
			theme = "uadmin";
		}
		// cookies配置中的模版
		Cookie[] cookies = ServletUtils.getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("theme")) {
				theme = cookie.getValue();
			}
		}
		return theme;
	}
}
