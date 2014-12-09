package com.examw.test.dao.products.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IChannelDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.model.products.ChannelInfo;

/**
 * 渠道数据接口实现类
 * @author fengwei.
 * @since 2014年8月11日 下午3:56:39.
 */
public class ChannelDaoImpl extends BaseDaoImpl<Channel> implements IChannelDao{
	private static final Logger logger = Logger.getLogger(ChannelDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.products.IChannelDao#findChannels(com.examw.test.model.products.ChannelInfo)
	 */
	@Override
	public List<Channel> findChannels(ChannelInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[渠道]数据...");
		String hql = "from Channel c where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by c.%1$s %2$s",info.getSort(),info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据总数
	 * @see com.examw.test.dao.products.IChannelDao#total(com.examw.test.model.products.ChannelInfo)
	 */
	@Override
	public Long total(ChannelInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[渠道]数据统计...");
		String hql = "select count(*) from Channel c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	// 添加查询条件到HQL。
	private String addWhere(ChannelInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getName())) {
			hql += " and (c.name like :name or c.code like :name)";
			parameters.put("name", "%" + info.getName() + "%");
		}
		return hql;
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.dao.products.IChannelDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		final String hql = "select max(c.code) from Channel c order by c.code desc";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
}