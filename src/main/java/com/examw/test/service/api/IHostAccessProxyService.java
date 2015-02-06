package com.examw.test.service.api;

import com.examw.test.model.api.LoginUser;
import com.examw.test.model.api.RegisterUser;

/**
 * 访问中华考试网代理服务接口。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
public interface IHostAccessProxyService {
	/**
	 * 注册新用户。
	 * @param registerUser
	 * 注册用户信息。
	 * @return
	 */
	public void registerUser(RegisterUser registerUser) throws Exception;
	/**
	 * 登录用户。
	 * @param loginUser
	 * 登录用户信息。
	 * @return
	 * @throws Exception
	 */
	public String login(LoginUser loginUser) throws Exception;
}