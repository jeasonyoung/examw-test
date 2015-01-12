package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IFeedBackDao;
import com.examw.test.domain.records.FeedBack;
import com.examw.test.model.records.FeedBackInfo;

/**
 * 反馈数据接口实现类
 * @author fengwei.
 * @since 2015年1月12日 下午4:47:46.
 */
public class FeedBackDaoImpl extends BaseDaoImpl<FeedBack> implements IFeedBackDao {
	private static final Logger logger = Logger.getLogger(FeedBackDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.records.IFeedBackDao#findFeedBacks(com.examw.test.model.records.FeedBackInfo)
	 */
	@Override
	public List<FeedBack> findFeedBacks(FeedBackInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from FeedBack q where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(StringUtils.isEmpty(info.getOrder())) info.setOrder("asc");
			hql += String.format(" order by q.%1$s %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.records.IFeedBackDao#count(com.examw.test.model.records.FeedBackInfo)
	 */
	@Override
	public Long total(FeedBackInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from FeedBack q where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(FeedBackInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(info.getTerminalCode() != null){
			hql += " and (q.terminal.code = :terminalCode)";
			parameters.put("terminalCode", info.getTerminalCode());
		}
		return hql;
	}
}