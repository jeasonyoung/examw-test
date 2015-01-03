package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IQuestionDao;
import com.examw.test.domain.records.Question;
import com.examw.test.model.records.QuestionInfo;

/**
 * 常见问题数据接口实现类。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements IQuestionDao {
	private static final Logger logger = Logger.getLogger(QuestionDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.records.IQuestionDao#findQuestions(com.examw.test.model.records.QuestionInfo)
	 */
	@Override
	public List<Question> findQuestions(QuestionInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Question q where (1 = 1) ";
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
	 * @see com.examw.test.dao.records.IQuestionDao#count(com.examw.test.model.records.QuestionInfo)
	 */
	@Override
	public Long total(QuestionInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Question q where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(QuestionInfo info, String hql, Map<String, Object> parameters){
		if(info == null) return hql;
		if(!StringUtils.isEmpty(info.getTitle())){
			hql += " and (q.title like :title) ";
			parameters.put("title", "%" + info.getTitle() + "%");
		}
		return hql;
	}
	/*
	 * 加载最新常用问题数据。
	 * @see com.examw.test.dao.records.IQuestionDao#loadTopQuestions(java.lang.Integer)
	 */
	@Override
	public List<Question> loadTopQuestions(Integer top) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最新［%d］常用问题数据...", top));
		final String hql = "from Question q order by q.createTime desc";
		return this.find(hql, null, 0, top == null ? 0 : top);
	}
}