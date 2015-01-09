package com.examw.test.service.shiro;
/**
 *  Shiro用户缓存。
 * 
 * @author yangyong
 * @since 2014年11月11日
 */
public interface IUserCache {
	/**
	 *  移除用户缓存。
	 * @param account
	 */
	void removeUserCache(String account);
	/**
	 * 移除全部授权信息缓存。
	 */
	void removeAuthorizationCache();
}