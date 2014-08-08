package com.examw.test.dao.syllabus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.ITextBookDao;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.TextBookInfo;
/**
 * 教材数据接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class TextBookDaoImpl extends BaseDaoImpl<TextBook> implements ITextBookDao {
	private static final Logger logger = Logger.getLogger(TextBookDaoImpl.class);
	/*
	 * 数据查询。
	 * @see com.examw.test.dao.syllabus.ITextBookDao#findTextBooks(com.examw.test.model.syllabus.TextBookInfo)
	 */
	@Override
	public List<TextBook> findTextBooks(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from TextBook t where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("subName")){
				info.setSort("subject.name");
			}
			if(info.getSort().equalsIgnoreCase("pressName")){
				info.setSort("press.name");
			}
			hql += " order by t." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.dao.syllabus.ITextBookDao#total(com.examw.test.model.syllabus.TextBookInfo)
	 */
	@Override
	public Long total(TextBookInfo info) {
		if(logger.isDebugEnabled())logger.debug("数据统计...");
		String hql = "select count(*) from TextBook t where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//查询条件
	private String addWhere(TextBookInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (t.name like :name) ";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		if(!StringUtils.isEmpty(info.getSubId())){
			hql += " and (t.subject.id like :subId) ";
			parameters.put("subId", "%"+ info.getSubId() +"%");
		}
		return hql;
	}
}