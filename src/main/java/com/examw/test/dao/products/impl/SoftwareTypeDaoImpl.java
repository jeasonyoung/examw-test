package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.model.products.SoftwareTypeInfo;

/**
 * 软件类型数据接口实现类
 * @author fengwei.
 * @since 2014年8月11日 下午4:09:14.
 */
public class SoftwareTypeDaoImpl extends BaseDaoImpl<SoftwareType> implements ISoftwareTypeDao{
	private static final Logger logger = Logger.getLogger(SoftwareTypeDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#findSoftwareTypes(com.examw.test.model.products.SoftwareTypeInfo)
	 */
	@Override
	public List<SoftwareType> findSoftwareTypes(SoftwareTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[软件类型]数据...");
		String hql = "from SoftwareType st where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by st.%1$s %2$s",info.getSort(),info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#total(com.examw.test.model.products.SoftwareTypeInfo)
	 */
	@Override
	public Long total(SoftwareTypeInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[软件类型]数据统计...");
		String hql = "select count(*) from SoftwareType st where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(SoftwareTypeInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (st.name like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载软件类型数据。
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#load(java.lang.Integer)
	 */
	@Override
	public SoftwareType load(Integer code) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载软件类型［code = %d］数据...", code));
		final String hql = "from SoftwareType s where s.code = :code";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("code", code);
		List<SoftwareType> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.products.ISoftwareTypeDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大排序号...");
		final String hql = "select max(st.code) from SoftwareType st order by st.code desc";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 重载数据删除。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(SoftwareType data) {
		if(logger.isDebugEnabled()) logger.debug("重载数据删除...");
		if(data == null) return;
		int count = 0;
		if(data.getSoftwares() != null && (count = data.getSoftwares().size()) > 0){
			throw new RuntimeException(String.format("产品软件类型［%1$s］关联［%2$d］软件，暂不能删除！", data.getName(), count));
		}
		if(data.getSoftwareTypeLimits() != null && (count = data.getSoftwareTypeLimits().size()) > 0){
			throw new RuntimeException(String.format("产品软件类型［%1$s］关联［%2$d］注册码，暂不能删除！", data.getName(), count));
		}
		if(data.getBindings() != null && (count = data.getBindings().size()) > 0){
			throw new RuntimeException(String.format("产品软件类型［%1$s］关联［%2$d］注册码绑定，暂不能删除！", data.getName(), count));
		}
		super.delete(data);
	}
}