package com.examw.test.service.api;

import com.examw.test.model.api.AppRegister;

/**
 * 服务端注册服务接口。
 * 
 * @author yangyong
 * @since 2015年2月16日
 */
public interface IHostRegisterService {
	/**
	 * 校验应用注册码。
	 * @param appRegister
	 * 注册信息。
	 * @return
	 * @throws Exception
	 */
	boolean verifyAppRegister(AppRegister appRegister) throws Exception;
}