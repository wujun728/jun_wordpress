
package cn.jeeweb.web.security.shiro.filter.online;

import cn.jeeweb.common.security.shiro.session.SessionDAO;
import cn.jeeweb.common.utils.StringUtils;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.security.shiro.ShiroConstants;
import cn.jeeweb.web.security.shiro.session.mgt.OnlineSession;

import cn.jeeweb.web.utils.UserUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 
 * All rights Reserved, Designed By www.jeeweb.cn
 * @title:  OnlineSessionFilter.java   
 * @package cn.jeeweb.web.modules.sys.security.shiro.web.filter.online
 * @description:   在线
 * @author: 王存见   
 * @date:   2017年6月26日 下午5:55:19   
 * @version V1.0 
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved. 
 *
 */
public class OnlineSessionFilter extends AccessControlFilter {

	/**
	 * 强制退出后重定向的地址
	 */
	private String forceLogoutUrl;

	private SessionDAO sessionDAO;

	public String getForceLogoutUrl() {
		return forceLogoutUrl;
	}

	public void setForceLogoutUrl(String forceLogoutUrl) {
		this.forceLogoutUrl = forceLogoutUrl;
	}

	public void setSessionDAO(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		Subject subject = getSubject(request, response);
		if (subject == null || subject.getSession() == null) {
			return true;
		}
		Session session = sessionDAO.readSession(subject.getSession().getId());
		if (session != null && session instanceof OnlineSession) {
			OnlineSession onlineSession = (OnlineSession) session;
			request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
			// 把user id设置进去
			//boolean isGuest = onlineSession.getUserId() == null  ;
			if (StringUtils.isEmpty(onlineSession.getUserId())) {
				User user = UserUtils.getUser();
				if (user != null) {
					onlineSession.setUserId(user.getId());
					onlineSession.setUsername(user.getUsername());
					onlineSession.markAttributeChanged();
				}
			}
			// sessionDAO.update(onlineSession);
			if (onlineSession.getStatus() == OnlineSession.OnlineStatus.force_logout) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		Subject subject = getSubject(request, response);
		if (subject != null) {
			subject.logout();
		}
		saveRequestAndRedirectToLogin(request, response);
		return true;
	}

	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		WebUtils.issueRedirect(request, response, getForceLogoutUrl());
	}

}
