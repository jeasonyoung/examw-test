package com.examw.test.service.products.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.model.Json;
import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.dao.products.IRegistrationBindingDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.RegistrationBinding;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.RegistrationBindingInfo;
import com.examw.test.service.products.IProductRegisterService;
import com.examw.test.service.products.IRegistrationService;
import com.examw.test.service.products.RegistrationStatus;

/**
 * 
 * @author fengwei.
 * @since 2015年1月13日 下午3:24:34.
 */
public class ProductRegisterServiceImpl implements IProductRegisterService{
	private static final Logger logger = Logger.getLogger(ProductRegisterServiceImpl.class);
	private IRegistrationService registrationService;
	private ISoftwareTypeDao softwareTypeDao;
	private IRegistrationBindingDao registrationBindingDao;
	private IProductUserDao productUserDao;
	
	/**
	 * 设置 
	 * @param registrationService
	 * 
	 */
	public void setRegistrationService(IRegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	/**
	 * 设置 
	 * @param softwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}

	/**
	 * 设置 
	 * @param registrationBindingDao
	 * 
	 */
	public void setRegistrationBindingDao(
			IRegistrationBindingDao registrationBindingDao) {
		this.registrationBindingDao = registrationBindingDao;
	}

	/**
	 * 设置 
	 * @param productUserDao
	 * 
	 */
	public void setProductUserDao(IProductUserDao productUserDao) {
		this.productUserDao = productUserDao;
	}

	/*
	 * 注册码注册  2015.01.13
	 * @see com.examw.test.service.products.IRegistrationService#register(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Json register(String code, String userId, String productId, String machine, Integer terminalCode) {
		if(logger.isDebugEnabled()) logger.debug("注册码注册绑定");
		Json json = new Json();
		try{
			//验证注册码合法性
			if(this.registrationService.verificationFormat(code))
			{
				Registration data = this.registrationService.loadRegistration(code);
				if(data == null) throw new Exception("注册码未登记！");
				if(!productId.equals(data.getProduct().getId()))
				{
					throw new Exception("注册码与该产品不匹配");
				}
				if(data.getStatus() == RegistrationStatus.LOGOFF.getValue() || data.getStatus() == RegistrationStatus.DISABLE.getValue()){
					throw new Exception(String.format("注册码：%s!",  this.registrationService.loadStatusName(data.getStatus())));
				}
				SoftwareType type = this.softwareTypeDao.load(terminalCode);
				if(type == null) throw new Exception("终端代码未登记!");
				//未被使用
				if(data.getStatus() == RegistrationStatus.NONE.getValue())
				{
					//修改注册码状态
					data.setStatus(RegistrationStatus.ACTIVE.getValue()); //激活
					//激活时间
					data.setStartTime(new Date());
					//过期时间
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(data.getStartTime());
					calendar.add(Calendar.MONTH, data.getLimits());
					calendar.set(Calendar.HOUR_OF_DAY, 23);//时
					calendar.set(Calendar.MINUTE, 59);//分
					calendar.set(Calendar.SECOND, 59);//秒
					calendar.set(Calendar.MILLISECOND, 0);//微秒
					data.setEndTime(calendar.getTime());//过期时间
					//增加绑定记录
					RegistrationBinding dataBinding = new RegistrationBinding();
					dataBinding.setRegistration(data);
					dataBinding.setSoftwareType(type);
					dataBinding.setTimes(1);
					dataBinding.setMachine(machine);
					dataBinding.setUser(StringUtils.isEmpty(userId) ? null : this.productUserDao.load(ProductUser.class, userId));
					this.registrationBindingDao.save(dataBinding);
					json.setSuccess(true);
					json.setMsg("绑定成功");
				}else
				{
					//检查过期时间,检查软件限制
					if(data.getStartTime() != null && data.getEndTime() != null){//验证有效期
						long current_time = System.currentTimeMillis(),start_time = data.getStartTime().getTime(),end_time = data.getEndTime().getTime();
						if(current_time < start_time || current_time > end_time){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							throw new Exception(String.format("注册码不在有效时间范围［%1$s - %2$s］内！", sdf.format(data.getStartTime()), sdf.format(data.getEndTime())));
						}
					}
					//增加绑定记录
					addRegisterBind(data, terminalCode, userId, machine);
					json.setSuccess(true);
					json.setMsg("绑定成功");
				}
			}else
			{
				json.setSuccess(false);
				json.setMsg("注册码不合法");
			}
		}catch(Exception e)
		{
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
	private void addRegisterBind(Registration data,Integer softwareTypeCode,String userId,String machine)throws Exception
	{
		SoftwareType softwareType = null;
		SoftwareTypeLimit softwareTypeLimit = null;
		if(data.getSoftwareTypeLimits() != null){//查找软件类型限制。
			for(SoftwareTypeLimit limit : data.getSoftwareTypeLimits()){
				if(limit == null || (softwareType = limit.getSoftwareType()) == null) continue;
				if(softwareType.getCode() == softwareTypeCode){
					softwareTypeLimit = limit;
					break;
				}
			}
		}
		if(softwareTypeLimit == null) throw  new Exception("注册码未设置使用软件类型！");
		final String reg_id = data.getId(), type_id = softwareType.getId();
		//是否重复绑定
		RegistrationBinding dataBinding = this.registrationBindingDao.loadBinding(reg_id, type_id, machine);
		if(dataBinding != null){
			//TODO 
			if(!dataBinding.getUser().getId().equals(userId))
			{
				throw new Exception("不允许不同用户绑定");
			}
//			dataBinding.setUser(StringUtils.isEmpty(userId) ? null : this.productUserDao.load(ProductUser.class, userId));
			dataBinding.setLastTime(new Date());
			dataBinding.setTimes(dataBinding.getTimes() + 1);
			return ;
		}
		int times = 0;
		if((times = softwareTypeLimit.getTimes()) <= 0) throw new Exception(String.format("注册码不允许在软件类型［%1$d:%2$s］上使用！", softwareType.getCode(),softwareType.getName()));
		long total = this.registrationBindingDao.total(new RegistrationBindingInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getRegistrationId() { return reg_id; }
			@Override
			public String getSoftwareTypeId() { return type_id;}
		});
		if(total >= times) throw new Exception("注册码已超过了允许绑定设备机器的上限！");
		dataBinding = new RegistrationBinding();
			dataBinding.setRegistration(data);
			dataBinding.setSoftwareType(softwareType);
			dataBinding.setTimes(1);
			dataBinding.setMachine(machine);
			dataBinding.setUser(StringUtils.isEmpty(userId) ? null : this.productUserDao.load(ProductUser.class, userId));
			this.registrationBindingDao.save(dataBinding);
	}
	
	@Override
	public Json verify(String code, String userId, String productId,
			String machine, Integer terminalCode) {
		if(logger.isDebugEnabled()) logger.debug("注册码注册绑定");
		Json json = new Json();
		try{
			//验证注册码合法性
			if(this.registrationService.verificationFormat(code))
			{
				Registration data = this.registrationService.loadRegistration(code);
				if(data == null) throw new Exception("注册码未登记！");
				if(!productId.equals(data.getProduct().getId()))
				{
					throw new Exception("注册码与该产品不匹配");
				}
				if(data.getStatus() == RegistrationStatus.LOGOFF.getValue() || data.getStatus() == RegistrationStatus.DISABLE.getValue()){
					throw new Exception(String.format("注册码：%s!",  this.registrationService.loadStatusName(data.getStatus())));
				}
				SoftwareType type = this.softwareTypeDao.load(terminalCode);
				if(type == null) throw new Exception("终端代码未登记!");
				RegistrationBinding dataBinding = this.registrationBindingDao.loadBinding(code, type.getId(), machine);
				if(dataBinding == null)
				{
					throw new Exception("没有该注册码的绑定记录!");
				}
				//TODO 是否验证用户
				json.setSuccess(true);
			}
		}catch(Exception e)
		{
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
}
