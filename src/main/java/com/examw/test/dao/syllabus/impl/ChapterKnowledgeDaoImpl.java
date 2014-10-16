package com.examw.test.dao.syllabus.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IChapterKnowledgeDao;
import com.examw.test.domain.syllabus.ChapterKnowledge;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
/**
 * 知识点数据接口实现类。
 * @author lq.
 * @since2014-08-07.
 */
public class ChapterKnowledgeDaoImpl extends BaseDaoImpl<ChapterKnowledge> implements IChapterKnowledgeDao {
	private static final Logger logger = Logger.getLogger(ChapterKnowledgeDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#findKnowledges(com.examw.test.model.syllabus.KnowledgeInfo)
	 */
	@Override
	public List<ChapterKnowledge> findKnowledges(ChapterKnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from ChapterKnowledge k where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("chapterName")){
				info.setSort("chapter.title");
			}else if(info.getSort().equalsIgnoreCase("syllabusName")){
				info.setSort("syllabus.title");
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
	public Long total(ChapterKnowledgeInfo info) {
		if(logger.isDebugEnabled())logger.debug("统计数据...");
		String hql = "select count(*) from ChapterKnowledge k where (1=1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加条件查询。
	private String addWhere(ChapterKnowledgeInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getTitle())){
			hql += " and  (k.title like :title) ";
			parameters.put("title", "%" + info.getTitle()+"%");
		}
		if(!StringUtils.isEmpty(info.getBookId())){
			hql += " and (k.chapter.book.id = :bookId) ";
			parameters.put("bookId", info.getBookId());
		}
		if(!StringUtils.isEmpty(info.getChapterId())){
			hql += "  and (k.chapter.id = :chapterId) ";
			parameters.put("chapterId", info.getChapterId());
		}
		if(!StringUtils.isEmpty(info.getSyllabusId())){
			hql += " and (k.syllabus.id = :syllabusId) ";
			parameters.put("syllabusId", info.getSyllabusId());
		}
		return hql;
	}
	/*
	 * 加载大纲要点和章节下的知识点集合。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#loadSyllabusKnowledge(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ChapterKnowledge> loadSyllabusKnowledge(String syllabusId,String chapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载大纲要点［syllabusId = %1$s］和章节［chapterId = %2$s］下的知识点集合...", syllabusId, chapterId));
		final String hql = "from ChapterKnowledge k where (k.chapter.id = :chapterId) and (k.syllabus.id = :syllabusId) order by k.orderNo ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chapterId", chapterId);
		parameters.put("syllabusId", syllabusId); 
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 加载章节下最大代码值。
	 * @see com.examw.test.dao.syllabus.IKnowledgeDao#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode(String chapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载章节［chapterId = %s］下最大代码值...", chapterId));
		final String hql = "select max(k.orderNo) from ChapterKnowledge k where k.chapter.id = :chapterId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("chapterId", chapterId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (int)obj;
	}
}