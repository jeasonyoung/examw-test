package com.examw.test.service.products.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.dao.products.IRegistrationBindingDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.RegistrationBinding;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.RegistrationBindingInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IProductRegisterService.ProductRegister;
import com.examw.test.service.products.IRegistrationBindingService;
import com.examw.test.service.products.IRegistrationCodeService;

/**
 * 注册码绑定服务接口实现类。
 * @author fengwei.
 * @since 2014年8月15日 上午9:53:49.
 */
public class RegistrationBindingServiceImpl extends BaseDataServiceImpl<RegistrationBinding,RegistrationBindingInfo> implements IRegistrationBindingService{
	private static final Logger logger = Logger.getLogger(RegistrationBindingServiceImpl.class);
	private IRegistrationBindingDao registrationBindingDao;
	private IProductUserDao productUserDao;
	private IRegistrationCodeService registrationCodeService;
	/**
	 * 设置注册码绑定数据接口。
	 * @param registrationBindingDao 
	 *	 注册码绑定数据接口。
	 */
	public void setRegistrationBindingDao(IRegistrationBindingDao registrationBindingDao) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码绑定数据接口...");
		this.registrationBindingDao = registrationBindingDao;
	}
	/**
	 * 设置产品用户数据接口。
	 * @param productUserDao 
	 *	  产品用户数据接口。
	 */
	public void setProductUserDao(IProductUserDao productUserDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品用户数据接口...");
		this.productUserDao = productUserDao;
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
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<RegistrationBinding> find(RegistrationBindingInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.registrationBindingDao.findBindings(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationBindingInfo changeModel(RegistrationBinding data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换:RegistrationBinding => RegistrationBindingInfo ...  ");
		if(data == null) return null;
		RegistrationBindingInfo info = new RegistrationBindingInfo();
		BeanUtils.copyProperties(data, info);//复制属性。
		Registration register = null;
		if((register = data.getRegistration()) != null){//注册码
			info.setRegistrationId(register.getId());
			info.setRegistrationCode(this.registrationCodeService.formatCode(register.getCode()));
		}
		SoftwareType softwareType  = null;
		if((softwareType = data.getSoftwareType()) != null){//软件类型
			info.setSoftwareTypeId(softwareType.getId());
			info.setSoftwareTypeName(softwareType.getName());
		}
		ProductUser user = null;
		if((user = data.getUser()) != null){//产品用户
			info.setUserId(user.getId());
			info.setUserCode(user.getCode());
			info.setUserName(user.getName());
			info.setUserMobile(user.getMobile());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationBindingInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.registrationBindingDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RegistrationBindingInfo update(RegistrationBindingInfo info) {
		throw new RuntimeException("暂未实现本方法！");
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			RegistrationBinding data = this.registrationBindingDao.load(RegistrationBinding.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.registrationBindingDao.delete(data);
			}
		}
	}
	/*
	 *  添加注册码绑定。
	 * @see com.examw.test.service.products.IRegistrationBindingService#addBinding(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addBinding(String registerCode, String softwareTypeCode,String machine, String userId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("添加注册码绑定:[registerCode=%1$s][softwareTypeCode=%2$s][machine=%3$s][userId=%4$s]",
							registerCode,softwareTypeCode,machine,userId));
		if(StringUtils.isEmpty(registerCode)) throw new Exception("注册码为空！");
		if(StringUtils.isEmpty(softwareTypeCode)) throw new Exception("软件类型代码为空！");
		if(StringUtils.isEmpty(machine)) throw new Exception("设备机器标示为空！");
		if(!this.registrationCodeService.verificationFormat(registerCode)){//验证注册码格式合法性
			 throw new Exception("注册码不符合格式！");
		}
		Registration data = this.registrationCodeService.loadRegistration(registerCode);
		if(data == null) throw new Exception("加载注册码对象失败！");
		 return this.addBinding(data, softwareTypeCode, machine, userId);
	}
	/*
	 * 添加注册码绑定
	 * @see com.examw.test.service.products.IRegistrationBindingService#addBinding(com.examw.test.domain.products.Registration, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean addBinding(Registration data, String softwareTypeCode,String machine, String userId) throws Exception {
		if(data == null) throw new IllegalArgumentException("注册码不存在！");
		if(StringUtils.isEmpty(softwareTypeCode)) throw new IllegalArgumentException("软件类型代码为空！");
		if(StringUtils.isEmpty(machine)) throw new IllegalArgumentException("设备机器标示为空！");
		//软件类型
		SoftwareType softwareType = null;
		//软件类型限制
		SoftwareTypeLimit softwareTypeLimit = null;
		if(data.getSoftwareTypeLimits() != null){//查找软件类型限制。
			for(SoftwareTypeLimit limit : data.getSoftwareTypeLimits()){
				if(limit == null || (softwareType = limit.getSoftwareType()) == null) continue;
				if(Integer.parseInt(softwareTypeCode) ==  softwareType.getCode()){
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
			dataBinding.setLastTime(new Date());
			dataBinding.setTimes(dataBinding.getTimes() + 1);
			dataBinding.setUser(StringUtils.isEmpty(userId) ? null : this.productUserDao.load(ProductUser.class, userId));
			return true;
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
		if(total > times) throw new Exception("注册码已超过了允许绑定设备机器的上限！");
		dataBinding = new RegistrationBinding();
		dataBinding.setRegistration(data);
		dataBinding.setSoftwareType(softwareType);
		dataBinding.setMachine(machine);
		dataBinding.setUser(StringUtils.isEmpty(userId) ? null : this.productUserDao.load(ProductUser.class, userId));
		this.registrationBindingDao.save(dataBinding);
		return true;
	}
	/*
	 * 绑定注册码
	 * @see com.examw.test.service.products.IRegistrationBindingService#addBinding(com.examw.test.service.products.IProductRegisterService.ProductRegister)
	 */
	@Override
	public boolean addBinding(ProductRegister data) throws Exception {
		if(data == null) throw new IllegalArgumentException("产品注册对象为空！");
		return this.addBinding(data.getCode(), String.format("%d", data.getTerminalCode()), data.getMachine(), data.getUserId());
	}
}