package com.examw.test.service.products.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.test.dao.products.IRegistrationDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IRegistrationService;

/**
 * 注册码服务接口
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:56.
 */
public class RegistrationServiceImpl extends BaseDataServiceImpl<Registration,RegistrationInfo> implements IRegistrationService{
	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	private IRegistrationDao registrationDao;
	private Map<String,String> statusMap;
	/**
	 * 设置 注册码数据接口
	 * @param registrationDao
	 * 
	 */
	public void setRegistrationDao(IRegistrationDao registrationDao) {
		this.registrationDao = registrationDao;
	}
	/**
	 * 设置 状态名称映射
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Registration> find(RegistrationInfo info) {
		return this.registrationDao.findRegistrations(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationInfo changeModel(Registration data) {
		if (logger.isDebugEnabled())	logger.debug("[注册码]数据模型转换...");
		if (data == null)
			return null;
		RegistrationInfo info = new RegistrationInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getChannel()!=null){
			info.setChannelId(data.getChannel().getId());
			info.setChannelName(data.getChannel().getName());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationInfo info) {
		return this.registrationDao.total(info);
	}
	@Override
	public RegistrationInfo update(RegistrationInfo info) {
		
		return null;
	}
	@Override
	public void delete(String[] ids) {
	}
	/*
	 * 加载状态名称
	 * @see com.examw.test.service.products.IRegistrationService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || statusMap == null)
		return null;
		return statusMap.get(status.toString());
	}
	/*
	 * 获取状态映射
	 * @see com.examw.test.service.products.IRegistrationService#getStatusMap()
	 */
	@Override
	public Map<String, String> getStatusMap() {
		return statusMap;
	}
}
