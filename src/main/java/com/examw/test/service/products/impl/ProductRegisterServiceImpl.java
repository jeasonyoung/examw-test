package com.examw.test.service.products.impl;

import org.apache.log4j.Logger;

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
	 * 设置 注册码服务接口
	 * @param registrationService
	 * 
	 */
	public void setRegistrationService(IRegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	/**
	 * 设置 软件类型数据接口
	 * @param softwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}

	/**
	 * 设置 注册码绑定数据接口
	 * @param registrationBindingDao
	 * 
	 */
	public void setRegistrationBindingDao(
			IRegistrationBindingDao registrationBindingDao) {
		this.registrationBindingDao = registrationBindingDao;
	}

	public boolean verify(ProductRegister data) throws Exception {
		if(data == null)
			return false;
		if(data.check())
		{
			if(logger.isDebugEnabled()) logger.debug(String.format("注册码验证:%s",data.toString()));
			String code = this.registrationService.cleanCodeFormat(data.getCode());
			if(this.registrationService.verificationFormat(code))
			{
				Registration registration = this.registrationService.loadRegistration(code);
				if(registration == null) throw new Exception("注册码未登记！");
				if(!data.getProductId().equals(registration.getProduct().getId()))
				{
						throw new Exception("注册码与该产品不匹配");
				}
				if(registration.getStatus() == RegistrationStatus.LOGOFF.getValue() || registration.getStatus() == RegistrationStatus.DISABLE.getValue()){
					throw new Exception(String.format("注册码：%s!",  this.registrationService.loadStatusName(registration.getStatus())));
				}
				SoftwareType type = this.softwareTypeDao.load(data.getTerminalCode());
				if(type == null) throw new Exception("终端代码未登记!");
				RegistrationBinding dataBinding = this.registrationBindingDao.loadBinding(code, type.getId(), data.getMachine());
				if(dataBinding == null)
				{
					throw new Exception("没有该注册码的绑定记录!");
				}
				return true;
			}
		}
		return false;
	}
}
