package com.examw.test.service.security;

import com.examw.test.model.security.RightInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 基础权限服务接口。
 * @author yangyong.
 * @since 2014-05-03.
 */
public interface IRightService extends IBaseDataService<RightInfo> {
	/**
	 *  加载权限名称。
	 * @param right
	 * 权限值。
	 * @return
	 * 权限名称。
	 */
	String loadRightName(Integer right);
	/**
	 * 初始化数据。
	 * @throws Exception
	 */
	void init() throws Exception;
}