package com.examw.test.service.products;

import com.examw.test.domain.products.Registration;
import com.examw.test.model.products.RegistrationBindingInfo;
import com.examw.test.service.IBaseDataService;
import com.examw.test.service.products.IProductRegisterService.ProductRegister;

/**
 * 注册码绑定服务接口。
 * @author fengwei.
 * @since 2014年8月15日 上午9:52:51.
 */
public interface IRegistrationBindingService extends IBaseDataService<RegistrationBindingInfo>{
	/**
	 * 添加注册码绑定。
	 * @param registerCode
	 * 注册码。
	 * @param softwareTypeCode
	 * 软件类型代码。
	 * @param machine
	 * 设备机器标示。
	 * @param userId
	 * 产品用户。
	 * @throws Exception
	 */
	boolean addBinding(String registerCode,String softwareTypeCode,String machine,String userId) throws Exception;
	/**
	 * 添加注册码绑定
	 * @param data
	 * 注册码。
	 * @param softwareTypeCode
	 *  软件类型代码。
	 * @param machine
	 * 设备机器标示。
	 * @param userId
	 * 产品用户。
	 * @return
	 * @throws Exception
	 */
	boolean addBinding(Registration data,String softwareTypeCode,String machine,String userId) throws Exception;
	/**
	 * 添加注册码绑定
	 * @param data 
	 * @return
	 * @throws Exception
	 */
	boolean addBinding(ProductRegister data) throws Exception;
}