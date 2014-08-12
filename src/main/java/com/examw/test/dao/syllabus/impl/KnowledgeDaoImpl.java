package com.examw.test.dao.syllabus.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IKnowledgeDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
/**
 * 知识点数据接口实现类。
 * @author lq.
 * @since2014-08-07.
 */
public class KnowledgeDaoImpl extends BaseDaoImpl<Knowledge> implements IKnowledgeDao {
	private static final Logger logger = Logger.getLogger(KnowledgeDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#findKnowledges(com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public List<Knowledge> findKnowledges(String bookId,KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from Knowledge k where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(bookId, info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("subName")){
				info.setSort("subject.name");
			}
			if(info.getSort().equalsIgnoreCase("syllName")){
				info.setSort("press.name");
			}
			hql += " order by k." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 统计数据。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#total(com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public Long total(String bookId,KnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		String hql = "select count(*) from Knowledge k where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(bookId, info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//条件查询。
	private String addWhere(String bookId,KnowledgeInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(bookId)){
			hql += "  and (k.book.id = :bookId) ";
			parameters.put("bookId", bookId);
		}
		if(!StringUtils.isEmpty(info.getSyllName())){
			hql += " and (k.syllabus.title like :syllName)";
			parameters.put("syllName", "%"+ info.getSyllName() +"%");
		}
		if(!StringUtils.isEmpty(info.getSyllId())){
			hql += " and (k.syllabus.id = :syllId) ";
			parameters.put("syllId", info.getSyllId());
		}
		return hql;
	}
}