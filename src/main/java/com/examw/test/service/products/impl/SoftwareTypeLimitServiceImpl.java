package com.examw.test.service.products.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.SoftwareTypeLimitInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.ISoftwareTypeLimitService;

/**
 *  软件类型限制服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月16日
 */
public class SoftwareTypeLimitServiceImpl  extends BaseDataServiceImpl<SoftwareTypeLimit, SoftwareTypeLimitInfo> implements ISoftwareTypeLimitService  {
	private static final Logger logger = Logger.getLogger(SoftwareTypeLimitServiceImpl.class);
	private ISoftwareTypeLimitDao softwareTypeLimitDao;
	/**
	 * 设置软件类型限制数据接口。
	 * @param softwareTypeLimitDao 
	 *	  软件类型限制数据接口。
	 */
	public void setSoftwareTypeLimitDao(ISoftwareTypeLimitDao softwareTypeLimitDao) {
		if(logger.isDebugEnabled()) logger.debug("注入软件类型限制数据接口...");
		this.softwareTypeLimitDao = softwareTypeLimitDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<SoftwareTypeLimit> find(SoftwareTypeLimitInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.softwareTypeLimitDao.findSoftwareTypeLimits(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SoftwareTypeLimitInfo changeModel(SoftwareTypeLimit data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 SoftwareTypeLimit => SoftwareTypeLimitInfo ...");
		if(data == null) return null;
		SoftwareTypeLimitInfo info = new SoftwareTypeLimitInfo();
		BeanUtils.copyProperties(data, info);
		Registration register = null;
		if((register = data.getRegister()) != null){
			info.setRegisterId(register.getId());
			info.setRegisterCode(register.getCode());
		}
		SoftwareType type = null;
		if((type = data.getSoftwareType()) != null){
			info.setSoftwareTypeId(type.getId());
			info.setSoftwareTypeName(type.getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SoftwareTypeLimitInfo info) {
		return this.softwareTypeLimitDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SoftwareTypeLimitInfo update(SoftwareTypeLimitInfo info) {
		 throw new RuntimeException("暂未实现！");
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		 throw new RuntimeException("暂未实现！");
	}
}