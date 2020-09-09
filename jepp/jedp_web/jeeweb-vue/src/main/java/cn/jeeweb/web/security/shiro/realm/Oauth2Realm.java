package cn.jeeweb.web.security.shiro.realm;

import cn.jeeweb.web.modules.sys.entity.User;
import cn.jeeweb.web.security.shiro.filter.authc.Oauth2Token;
import cn.jeeweb.web.utils.JWTHelper;
import cn.jeeweb.web.utils.UserUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.Serializable;

/**
 * 
 * @author 王存见
 *
 */
public class Oauth2Realm extends org.apache.shiro.realm.AuthorizingRealm {

	/**
	 * 授权的回调方法
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
		return token instanceof Oauth2Token;
	}

	/**
	 * 认证的回调方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String accessToken = (String) token.getPrincipal();
		String id= JWTHelper.getClaims(accessToken,"id");
		String username=JWTHelper.getClaims(accessToken,"username");
		String realname=JWTHelper.getClaims(accessToken,"realname");
		UserRealm.Principal principal = new UserRealm.Principal(id,username,realname);
		return new SimpleAuthenticationInfo(principal, Boolean.TRUE, getName());
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
}
