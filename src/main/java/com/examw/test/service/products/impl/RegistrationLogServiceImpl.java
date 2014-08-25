package com.examw.test.service.products.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IRegistrationLogDao;
import com.examw.test.domain.products.RegistrationLog;
import com.examw.test.model.products.RegistrationLogInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IRegistrationLogService;

/**
 * 注册码日志服务接口实现类
 * @author fengwei.
 * @since 2014年8月14日 下午3:54:41.
 */
public class RegistrationLogServiceImpl extends BaseDataServiceImpl<RegistrationLog,RegistrationLogInfo> implements IRegistrationLogService{
	private static final Logger logger = Logger.getLogger(RegistrationLogServiceImpl.class);
	private IRegistrationLogDao registrationLogDao;
	private Map<String,String> typeMap;
	/**
	 * 设置 注册码日志数据接口
	 * @param registrationLogDao
	 * 
	 */
	public void setRegistrationLogDao(IRegistrationLogDao registrationLogDao) {
		this.registrationLogDao = registrationLogDao;
	}
	/**
	 * 设置 类型名称
	 * @param typeName
	 * 
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<RegistrationLog> find(RegistrationLogInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询[注册码日志]数据...");
		return this.registrationLogDao.findRegistrationLogs(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationLogInfo changeModel(RegistrationLog data) {
		
		return null;
	}
	/*
	 * 查询数据统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationLogInfo info) {
		return this.registrationLogDao.total(info);
	}
	@Override
	public RegistrationLogInfo update(RegistrationLogInfo info) {
		
		return null;
	}
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除数据...");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			RegistrationLog data = this.registrationLogDao.load(RegistrationLog.class, ids[i]);
			if (data != null) {
				logger.debug("删除注册码日志：" + ids[i]);
				this.registrationLogDao.delete(data);
			}
		}
	}
	/*
	 * 获取类型映射
	 * @see com.examw.test.service.products.IRegistrationLogService#getTypeMap()
	 */
	@Override
	public Map<String, String> getTypeMap() {
		return typeMap;
	}
	/*
	 * 获取类型名称
	 * @see com.examw.test.service.products.IRegistrationLogService#loadTypeName(java.lang.Integer)
	 */
	@Override
	public String loadTypeName(Integer type) {
		if(type == null || typeMap == null)
		return null;
		return typeMap.get(type.toString());
	}
	
	
}
