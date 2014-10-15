package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.ChapterKnowledge;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
/**
 * 知识点管理数据接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IChapterKnowledgeDao extends IBaseDao<ChapterKnowledge> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<ChapterKnowledge> findKnowledges(ChapterKnowledgeInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(ChapterKnowledgeInfo info);
	/**
	 * 加载大纲要点和章节下的知识点集合。
	 * @param syllabusId
	 * 所属大纲要点ID。
	 * @param chapterId
	 * 所属章节ID。
	 * @return
	 * 知识点集合。
	 */
	List<ChapterKnowledge> loadSyllabusKnowledge(String syllabusId,String chapterId);
	/**
	 * 获取最大代码值。
	 * @param chapterId
	 * 所属章节ID。
	 * @return
	 */
	Integer loadMaxCode(String chapterId);
}