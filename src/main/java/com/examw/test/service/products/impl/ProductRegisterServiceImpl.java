package com.examw.test.service.products.impl;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IRegistrationBindingDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.RegistrationBinding;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.service.products.IProductRegisterService;
import com.examw.test.service.products.IRegistrationService;
import com.examw.test.service.products.RegistrationStatus;

/**
 * 产品注册服务实现类
 * @author fengwei.
 * @since 2015年1月13日 下午3:24:34.
 */
public class ProductRegisterServiceImpl implements IProductRegisterService{
	private static final Logger logger = Logger.getLogger(ProductRegisterServiceImpl.class);
	private IRegistrationService registrationService;
	private ISoftwareTypeDao softwareTypeDao;
	private IRegistrationBindingDao registrationBindingDao;
	/**
	 * 设置注册码服务接口.
	 * @param registrationService
	 * 注册码服务接口.
	 */
	public void setRegistrationService(IRegistrationService registrationService) {
		if(logger.isDebugEnabled())logger.debug("注入注册码服务接口...");
		this.registrationService = registrationService;
	}
	/**
	 * 设置 软件类型数据接口.
	 * @param softwareTypeDao
	 * 软件类型数据接口.
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		if(logger.isDebugEnabled())logger.debug("注入软件类型数据接口...");
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置 注册码绑定数据接口.
	 * @param registrationBindingDao
	 * 注册码绑定数据接口.
	 */
	public void setRegistrationBindingDao(IRegistrationBindingDao registrationBindingDao) {
		if(logger.isDebugEnabled())logger.debug("注入注册码绑定数据接口...");
		this.registrationBindingDao = registrationBindingDao;
	}
	/*
	 * 注册码验证。
	 * @see com.examw.test.service.products.IProductRegisterService#verify(com.examw.test.service.products.IProductRegisterService.ProductRegister)
	 */
	@Override
	public boolean verify(ProductRegister data) throws Exception {
		if(data == null) throw new IllegalArgumentException("产品注册对象不存在！");
		if(StringUtils.isEmpty(data.getCode())) throw new Exception("注册码为空！");
		if(StringUtils.isEmpty(data.getProductId())) throw new Exception("产品为空！");
		if(StringUtils.isEmpty(data.getMachine())) throw new Exception("机器标示为空！");
		if(!this.registrationService.verificationFormat(data.getCode())){
			throw new Exception("注册码错误！");
		}
		//加载注册码
		Registration registration = this.registrationService.loadRegistration(data.getCode());
		if(registration == null){
			logger.error(String.format("非法注册码：%s,警告：注册码算法可能已被破解！", data.getCode()));
			throw new Exception("非法注册码！");
		}
		//检查注册码状态
		if(registration.getStatus() == RegistrationStatus.LOGOFF.getValue() || registration.getStatus() == RegistrationStatus.DISABLE.getValue()){
			throw new Exception(String.format("注册码：%s!",  this.registrationService.loadStatusName(registration.getStatus())));
		}
		//检查注册码产品是否匹配
		if(!data.getProductId().equals(registration.getProduct().getId())){
			throw new Exception("注册码与该产品不匹配！");
		}
		//软件类型
		SoftwareType type = this.softwareTypeDao.load(data.getTerminalCode());
		if(type == null) throw new Exception(String.format("终端代码[%d]未登记!",data.getTerminalCode()));
		RegistrationBinding dataBinding = this.registrationBindingDao.loadBinding(registration.getId(), type.getId(), data.getMachine());
		if(dataBinding == null) throw new Exception("没有该注册码的绑定记录!");
		return true;
	}
}