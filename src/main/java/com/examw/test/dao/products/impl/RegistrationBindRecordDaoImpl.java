package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IRegistrationBindRecordDao;
import com.examw.test.domain.products.RegistrationBindRecord;
import com.examw.test.model.products.RegistrationBindRecordInfo;

/**
 * 注册码绑定记录数据接口实现类
 * @author fengwei.
 * @since 2014年8月14日 下午2:23:27.
 */
public class RegistrationBindRecordDaoImpl extends BaseDaoImpl<RegistrationBindRecord> implements IRegistrationBindRecordDao{
	private static final Logger logger = Logger.getLogger(RegistrationBindRecordDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IRegistrationBindRecordDao#findRegistrationBindRecords(com.examw.test.model.products.RegistrationBindRecordInfo)
	 */
	@Override
	public List<RegistrationBindRecord> findRecords(RegistrationBindRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码绑定记录]数据...");
		String hql = "from RegistrationBindRecord rbr where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by rbr." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IRegistrationBindRecordDao#total(com.examw.test.model.products.RegistrationBindRecordInfo)
	 */
	@Override
	public Long total(RegistrationBindRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[注册码绑定记录]数据统计...");
		String hql = "select count(*) from RegistrationBindRecord rbr where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(RegistrationBindRecordInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getRegistrationId())) {
			hql += " and (rbr.registration.id = :registrationId)";
			parameters.put("registrationId", info.getRegistrationId());
		}
		if (!StringUtils.isEmpty(info.getRegistrationCode())) {
			hql += " and (rbr.registration.code like :code)";
			parameters.put("code", "%" + info.getRegistrationCode() + "%");
		}
		if (!StringUtils.isEmpty(info.getSoftwareTypeId())) {
			hql += " and (rbr.softwareType.id = :typeId)";
			parameters.put("typeId", info.getSoftwareTypeId());
		}
		return hql;
	}
}
