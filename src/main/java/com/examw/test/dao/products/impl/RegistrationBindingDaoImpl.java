package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IRegistrationBindingDao;
import com.examw.test.domain.products.RegistrationBinding;
import com.examw.test.model.products.RegistrationBindingInfo;

/**
 * 注册码绑定数据接口实现类。
 * @author fengwei.
 * @since 2014年8月14日 下午2:23:27.
 */
public class RegistrationBindingDaoImpl extends BaseDaoImpl<RegistrationBinding> implements IRegistrationBindingDao{
	private static final Logger logger = Logger.getLogger(RegistrationBindingDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.products.IRegistrationBindingDao#findBindings(com.examw.test.model.products.RegistrationBindingInfo)
	 */
	@Override
	public List<RegistrationBinding> findBindings(RegistrationBindingInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from RegistrationBinding r where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			switch(info.getSort()){
				case "registrationCode"://注册码
					info.setSort("registration.code");
					break;
				case "softwareTypeName"://软件类型
					info.setSort("softwareType.name");
					break;
				case "userName"://产品用户
					info.setSort("user.name");
					break;
			}
			hql += String.format(" order by r.%1$s %2$s", info.getSort(), info.getOrder());
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.products.IRegistrationBindingDao#total(com.examw.test.model.products.RegistrationBindingInfo)
	 */
	@Override
	public Long total(RegistrationBindingInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from RegistrationBinding r where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(RegistrationBindingInfo info,String hql,Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getRegistrationId())){//注册码ID
			hql += " and (r.registration.id = :registerId) ";
			parameters.put("registerId", info.getRegistrationId());
		}
		if(!StringUtils.isEmpty(info.getSoftwareTypeId())){//软件类型ID
			hql += " and (r.softwareType.id = :typeId) ";
			parameters.put("typeId", info.getSoftwareTypeId());
		}
		if(!StringUtils.isEmpty(info.getRegistrationCode())){//注册码
			hql += " and (r.registration.code like :registerCode) ";
			parameters.put("registerCode", "%" + info.getRegistrationCode() + "%");
		}
		if(!StringUtils.isEmpty(info.getUserName())){//产品用户
			hql += " and ((r.user.code like :userName) or (r.user.name like :userName) or (r.user.mobile like :userName)) ";
			parameters.put("userName", "%"+info.getUserName()+"%");
		}
		return hql;
	}
	/*
	 * 加载注册码绑定。
	 * @see com.examw.test.dao.products.IRegistrationBindingDao#loadBinding(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RegistrationBinding loadBinding(String registrationId,String softwareTypeId, String machine) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载注册码绑定[registrationId=%1$s][softwareTypeId=%2$s][machine=%3$s]...",registrationId,softwareTypeId,machine));
		final String hql = "from RegistrationBinding r where (r.registration.id = :registrationId) and (r.softwareType.id = :softwareTypeId) and (r.machine = :machine) order by r.createTime desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("registrationId", registrationId);
		parameters.put("softwareTypeId", softwareTypeId);
		parameters.put("machine", machine);
		List<RegistrationBinding> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}