package com.examw.test.service.api.impl;

import org.apache.log4j.Logger;

import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.api.AppRegister;
import com.examw.test.service.api.IHostRegisterService;
import com.examw.test.service.products.IRegistrationBindingService;
import com.examw.test.service.products.IRegistrationCodeService;
/**
 * 服务端注册服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年2月16日
 */
public class HostRegisterServiceImpl implements IHostRegisterService {
	private final  static Logger logger = Logger.getLogger(HostRegisterServiceImpl.class);
	private ISoftwareTypeLimitDao softwareTypeLimitDao;
	private IRegistrationCodeService registrationCodeService;
	private IRegistrationBindingService registrationBindingService;
	/**
	 * 设置软件类型限制数据接口。
	 * @param softwareTypeLimitDao 
	 *	  软件类型限制数据接口。
	 */
	public void setSoftwareTypeLimitDao(ISoftwareTypeLimitDao softwareTypeLimitDao) {
		if(logger.isDebugEnabled())
		this.softwareTypeLimitDao = softwareTypeLimitDao;
	}
	/**
	 * 设置注册码服务接口。
	 * @param registrationCodeService 
	 *	  注册码服务接口。
	 */
	public void setRegistrationCodeService(IRegistrationCodeService registrationCodeService) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码服务接口...");
		this.registrationCodeService = registrationCodeService;
	}
	/**
	 * 设置注册码绑定服务接口。
	 * @param registrationBindingService 
	 *	  注册码绑定服务接口。
	 */
	public void setRegistrationBindingService(IRegistrationBindingService registrationBindingService) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码绑定服务接口...");
		this.registrationBindingService = registrationBindingService;
	}
	/*
	 * 验证应用注册码。
	 * @see com.examw.test.service.api.IHostRegisterService#verifyAppRegister(com.examw.test.model.api.AppRegister)
	 */
	@Override
	public boolean verifyAppRegister(AppRegister appRegister) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("验证应用注册码:%s", appRegister));
		if(appRegister == null) throw new IllegalArgumentException("注册信息为空！");
		if(this.registrationCodeService.validation(appRegister.getCode())){//检测注册码是否合法
			Registration registration = this.registrationCodeService.loadRegistration(appRegister.getCode());
			Product product = null;
			if(registration != null && (product =  registration.getProduct()) != null){
				if(!product.getId().equalsIgnoreCase(appRegister.getProductId())){//检查注册码所属产品是否一致
					throw new Exception(String.format("注册码［%1$s］所属于产品：%2$s", appRegister.getCode(),  product.getName()));
				}
				//获取限制
				Integer limits =  this.softwareTypeLimitDao.limits(appRegister.getClientTypeCode(), appRegister.getCode());
				if(limits == null) throw new Exception("未设置注册码限制！");
				//判断限制
				int count = 0;
				if(limits > 0 && (count = registration.getBindings().size()) >=  limits){
					throw new Exception(String.format("注册码绑定［%1$d］已超过限制［%2$d］", count, limits));
				}
				//添加绑定
				return this.registrationBindingService.addBinding(appRegister.getCode(), 
						appRegister.getClientTypeCode(),
						appRegister.getClientMachine(),
						appRegister.getUserId());
			}
		}
		return false;
	}

}