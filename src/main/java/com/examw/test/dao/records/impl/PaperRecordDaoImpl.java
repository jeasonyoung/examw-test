package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IPaperRecordDao;
import com.examw.test.domain.records.PaperRecord;
import com.examw.test.model.records.PaperRecordInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年9月17日 下午5:38:47.
 */
public class PaperRecordDaoImpl extends BaseDaoImpl<PaperRecord> implements IPaperRecordDao {
	private static final Logger logger = Logger.getLogger(PaperRecordDaoImpl.class);
	
	@Override
	public List<PaperRecord> findPaperRecords(PaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据...");
		String hql = "from PaperRecord pr where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by pr." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	
	@Override
	public Long total(PaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询[地区]数据统计...");
		String hql = "select count(*) from PaperRecord pr where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}

	// 添加查询条件到HQL。
	private String addWhere(PaperRecordInfo info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (pr.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		return hql;
	}
	
}
