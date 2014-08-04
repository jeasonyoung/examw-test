package com.examw.test.service.shiro;

import org.apache.log4j.Logger;
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

import com.examw.test.domain.security.User;
import com.examw.test.service.security.IUserService;
import com.examw.test.support.PasswordHelper;

/**
 * 用户认证。
 * @author yangyong.
 * @since 2014-05-13.
 */
public class UserRealm extends AuthorizingRealm implements IShiroCacheClear {
	private static Logger logger = Logger.getLogger(UserRealm.class);
	private IUserService userService;
	private PasswordHelper passwordHelper;
	/**
	 * 设置用户服务。
	 * @param userService
	 */
	public void setUserService(IUserService userService) {
		if(logger.isDebugEnabled()) logger.debug("设置用户服务接口...");
		this.userService = userService;
	}
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		if(logger.isDebugEnabled()) logger.debug("设置密码工具对象...");
		this.passwordHelper = passwordHelper;
	}
	/*
	 * 执行获取授权信息。
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if(logger.isDebugEnabled()) logger.debug("执行获取授权信息...");
		String account = (String)principals.getPrimaryPrincipal();
		if(logger.isDebugEnabled()) logger.debug("account=" + account);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(this.userService.findRoles(account));
		authorizationInfo.setStringPermissions(this.userService.findPermissions(account));
		return authorizationInfo;
	}
	/*
	 * 获得认证信息。
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		if(logger.isDebugEnabled()) logger.debug("获取认证信息...");
		String account = (String)token.getPrincipal();
		if(logger.isDebugEnabled()) logger.debug("account=" + account);
		User user = this.userService.findByAccount(account);
		if(user == null) throw new UnknownAccountException();//没找到账号。
		if(user.getStatus() == User.STATUS_DISABLE){
			throw new LockedAccountException();//账号锁定。
		}
		String pwd = this.passwordHelper.encryptPassword(user);
		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配。
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
					user.getAccount(),//账号
					pwd,
					ByteSource.Util.bytes(user.getId()),
					this.getName()
				);
		
		return authenticationInfo;
	}
	/*
	 * 清除缓存授权信息。
	 * @see org.apache.shiro.realm.AuthorizingRealm#clearCachedAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		if(logger.isDebugEnabled()) logger.debug("清除授权缓存...");
        super.clearCachedAuthorizationInfo(principals);
    }
	/*
	 * 清除缓存认证信息。
	 * @see org.apache.shiro.realm.AuthenticatingRealm#clearCachedAuthenticationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
    	if(logger.isDebugEnabled()) logger.debug("清除认证缓存...");
        super.clearCachedAuthenticationInfo(principals);
    }
    /*
     * 清除缓存。
     * @see org.apache.shiro.realm.CachingRealm#clearCache(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
    	if(logger.isDebugEnabled()) logger.debug("清空缓存...");
        super.clearCache(principals);
    }
    /*
     * 清空授权信息缓存。
     * @see com.examw.oa.service.shiro.IShiroCacheClear#clearAllCachedAuthorizationInfo()
     */
    @Override
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }
    /*
     * 清空认证信息缓存。
     * @see com.examw.oa.service.shiro.IShiroCacheClear#clearAllCachedAuthenticationInfo()
     */
    @Override
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
    /*
     * 清空所有缓存。
     * @see com.examw.oa.service.shiro.IShiroCacheClear#clearAllCache()
     */
   @Override
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}