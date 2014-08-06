package com.examw.test.service.shiro;
/**
 * 清空缓存接口。
 * @author yangyong.
 * @since 2014-07-20.
 */
public interface IShiroCacheClear {
	/**
	 * 清空所有授权缓存。
	 */
	void clearAllCachedAuthorizationInfo();
	/**
	 * 清空所有认证缓存。
	 */
	void clearAllCachedAuthenticationInfo();
	/**
	 * 清空所有缓存。
	 */
	void clearAllCache();
}