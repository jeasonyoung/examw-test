package com.examw.test.dao.products.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.ISoftwareTypeLimitDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.SoftwareTypeLimitInfo;

/**
 * 软件类型限制数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月13日
 */
public class SoftwareTypeLimitDaoImpl extends BaseDaoImpl<SoftwareTypeLimit> implements ISoftwareTypeLimitDao {
	private static final Logger logger = Logger.getLogger(SoftwareTypeLimitDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#findSoftwareTypeLimits(com.examw.test.model.products.SoftwareTypeLimitInfo)
	 */
	@Override
	public List<SoftwareTypeLimit> findSoftwareTypeLimits(SoftwareTypeLimitInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from SoftwareTypeLimit s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by s.%1$s %2$s", info.getSort(), info.getOrder());
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#total(com.examw.test.model.products.SoftwareTypeLimitInfo)
	 */
	@Override
	public Long total(SoftwareTypeLimitInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from SoftwareTypeLimit s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(SoftwareTypeLimitInfo info,String hql,Map<String, Object> parameters){
		if(info != null){
			if(!StringUtils.isEmpty(info.getSoftwareTypeId())){//软件类型ID。
				hql += " and (s.softwareType.id = :softwareTypeId) ";
				parameters.put("softwareTypeId", info.getSoftwareTypeId());
			}
			if(!StringUtils.isEmpty(info.getRegisterId())){//注册码ID。
				hql += " and (s.register.id = :registerId) ";
				parameters.put("registerId", info.getRegisterId());
			}
		}
		return hql;
	}
	/*
	 * 删除注册码ID下的软件类型限制。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#deleteByRegistrationId(java.lang.String)
	 */
	@Override
	public void deleteByRegistrationId(String registerId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除注册码ID［%s］下的软件类型限制..", registerId));
		if(StringUtils.isEmpty(registerId)) return;
		final String hql = "delete from SoftwareTypeLimit s where (s.register.id = :registerId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("registerId", registerId);
		this.execuateUpdate(hql, parameters);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#update(com.examw.test.domain.products.Registration, com.examw.test.domain.products.SoftwareType, java.lang.Integer)
	 */
	@Override
	public SoftwareTypeLimit update(final Registration register, final SoftwareType type, Integer times) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(register == null || type == null) return null;
		SoftwareTypeLimit limit = this.load(SoftwareTypeLimit.class, new SoftwareTypeLimit(){ 
			private static final long serialVersionUID = 1L;
			public Registration getRegister() { return register; } 
			public SoftwareType getSoftwareType() { return type; }
		});
		boolean isAdded = false;
		if(isAdded = (limit == null)){
			limit = new SoftwareTypeLimit();
			limit.setRegister(register);
			limit.setSoftwareType(type);
			limit.setCreateTime(new Date());
		}
		limit.setTimes(times);
		if(isAdded){
			this.save(limit);
		}else {
			this.update(limit);
		} 
		return limit;
	}
	/*
	 * 加载软件类型和注册码的限制次数。
	 * @see com.examw.test.dao.products.ISoftwareTypeLimitDao#limits(java.lang.String, java.lang.String)
	 */
	@Override
	public Integer limits(String softwareTypeCode, String registrationCode) throws Exception {
		Assert.isTrue(!StringUtils.isEmpty(softwareTypeCode), "软件类型代码为空！");
		Assert.isTrue(!StringUtils.isEmpty(registrationCode),"注册码为空！");
		final String hql = "select st.times  from SoftwareTypeLimit st where st.softwareType.code = :softwareTypeCode and st.register.code = :registrationCode order by st.createTime desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("softwareTypeCode", Integer.parseInt(softwareTypeCode));
		parameters.put("registrationCode", registrationCode);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (Integer)obj;
	}
}