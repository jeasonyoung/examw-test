package com.examw.test.service.api.impl;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.api.AppRegister;
import com.examw.test.service.api.IHostRegisterService;
import com.examw.test.service.products.IRegistrationBindingService;
import com.examw.test.service.products.IRegistrationCodeService;
import com.examw.test.service.products.RegistrationStatus;
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
	 * 注册码没有激活的话,激活注册码  2015.03.19 修改
	 * @see com.examw.test.service.api.IHostRegisterService#verifyAppRegister(com.examw.test.model.api.AppRegister)
	 */
	@Override
	public boolean verifyAppRegister(AppRegister appRegister) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("验证应用注册码:%s", appRegister));
		if(appRegister == null) throw new IllegalArgumentException("注册信息为空！");
		String reg_code = this.registrationCodeService.cleanCodeFormat(appRegister.getCode());
		if(StringUtils.isEmpty(reg_code)) throw new Exception("注册码为空！");
		boolean result = this.registrationCodeService.verificationFormat(reg_code);
		if(!result) throw new Exception("注册码错误！");
		Registration data = this.registrationCodeService.loadRegistration(reg_code);
		if(data == null) throw new Exception(String.format("注册码未登记！"));
		//注册码没有激活
		if(data.getStatus() == RegistrationStatus.NONE.getValue()){//激活注册码
			 this.registrationCodeService.activeRegistration(reg_code);
			 data = this.registrationCodeService.loadRegistration(reg_code);
		}
		else if(data.getStatus() != RegistrationStatus.ACTIVE.getValue()){
			throw new Exception("注册码不是有效状态!");
		}
		//验证注册码有效期
		if(data.getStartTime() != null && data.getEndTime() != null){//验证有效期
			long current_time = System.currentTimeMillis(),//当前时间
					start_time = data.getStartTime().getTime(),//有效期开始时间
					end_time = data.getEndTime().getTime();//有效期结束时间
			if(current_time < start_time || current_time > end_time){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				throw new Exception(String.format("注册码不在有效时间范围［%1$s - %2$s］内！", sdf.format(data.getStartTime()), sdf.format(data.getEndTime())));
			}
		}
		//验证产品限制
		Product product = null;
		if((product = data.getProduct()) == null){
			throw new Exception("注册码未关联产品！");
		}
		//验证注册码关联的产品和软件关联的产品是否一致
		if(!product.getId().equalsIgnoreCase(appRegister.getProductId())){
			throw new Exception(String.format("注册码［%1$s］所属于产品：%2$s", reg_code,  product.getName()));
		}
		//验证终端限制
		Integer limits =  this.softwareTypeLimitDao.limits(appRegister.getClientTypeCode(), reg_code);
		int count = 0;
		if(limits > 0 && (count = data.getBindings().size()) >=  limits){
			throw new Exception(String.format("注册码绑定［%1$d］已超过限制［%2$d］", count, limits));
		}
		//添加绑定
		return this.registrationBindingService.addBinding(reg_code, 
																						appRegister.getClientTypeCode(),
																						appRegister.getClientMachine(),
																						appRegister.getUserId());
	}
}