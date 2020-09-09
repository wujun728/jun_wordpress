package cn.jeeweb.web.security.shiro.realm;

import java.io.Serializable;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.jeeweb.common.utils.AddressUtils;
import cn.jeeweb.common.utils.IpUtils;
import cn.jeeweb.common.utils.ServletUtils;
import cn.jeeweb.web.security.shiro.filter.authc.UsernamePasswordToken;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import eu.bitwalker.useragentutils.UserAgent;
import io.swagger.annotations.ApiModelProperty;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.modules.sys.service.IUserService;
import cn.jeeweb.web.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * http://blog.csdn.net/babys/article/details/50151407
 * 
 * @author 王存见
 *
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	/**
	 * 不返回授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(UserUtils.getRoleStringList());
		authorizationInfo.setStringPermissions(UserUtils.getPermissionsList());
		return authorizationInfo;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}
	/**
	 * 认证的回调方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken authcToken = (UsernamePasswordToken) token;
		String username = authcToken.getUsername();
		User user = userService.findByUsername(username);
		if (user == null) {
			user = userService.findByEmail(username);
			if (user == null) {
				user = userService.findByPhone(username);
			}
		}
		if (user == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (User.STATUS_LOCKED.equals(user.getStatus())) {
			throw new LockedAccountException(); // 帐号锁定
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				new Principal(user), // 用户名
				user.getPassword(), // 密码
				ByteSource.Util.bytes(user.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private String id; // 编号
		private String username; // 登录名
		private String realname; // 姓名
		@JSONField(format="yyyy-MM-dd HH:mm:ss")
		private Date startTime;
		@JSONField(format="yyyy-MM-dd HH:mm:ss")
		private Date stopTime;
		@JSONField(format="yyyy-MM-dd HH:mm:ss")
		private Date lastAccessTime;
		/**登录IP*/
		private String loginIp;
		/**登录地点*/
		private String loginLocation;
		/**浏览器类型*/
		private String browser;
		/**操作系统*/
		private String os;

		public Principal(User user) {
			this.id = user.getId();
			this.username = user.getUsername();
			this.realname = user.getRealname();
			init();
		}

		public Principal(String id,String username,String realname) {
			this.id = id;
			this.username = username;
			this.realname = realname;
			init();
		}

		private void init(){
			UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
			String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
			// 获取客户端操作系统
			String os = userAgent.getOperatingSystem().getName();
			// 获取客户端浏览器
			String browser = userAgent.getBrowser().getName();
			this.startTime = new Date();
			this.browser = browser;
			this.os = os;
			this.loginIp = ip;
			this.loginLocation = AddressUtils.getRealAddressByIP(ip);
		}

		public String getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getRealname() {
			return realname;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setRealname(String realname) {
			this.realname = realname;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getStopTime() {
			return stopTime;
		}

		public void setStopTime(Date stopTime) {
			this.stopTime = stopTime;
		}

		public Date getLastAccessTime() {
			return lastAccessTime;
		}

		public void setLastAccessTime(Date lastAccessTime) {
			this.lastAccessTime = lastAccessTime;
		}

		public String getLoginIp() {
			return loginIp;
		}

		public void setLoginIp(String loginIp) {
			this.loginIp = loginIp;
		}

		public String getLoginLocation() {
			return loginLocation;
		}

		public void setLoginLocation(String loginLocation) {
			this.loginLocation = loginLocation;
		}

		public String getBrowser() {
			return browser;
		}

		public void setBrowser(String browser) {
			this.browser = browser;
		}

		public String getOs() {
			return os;
		}

		public void setOs(String os) {
			this.os = os;
		}

		@Override
		public String toString() {
			return id;
		}

	}
}
