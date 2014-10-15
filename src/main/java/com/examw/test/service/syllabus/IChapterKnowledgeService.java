package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.test.domain.syllabus.ChapterKnowledge;
import com.examw.test.model.syllabus.ChapterKnowledgeInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 知识点服务接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IChapterKnowledgeService extends IBaseDataService<ChapterKnowledgeInfo> {
	/**
	 * 加载大纲要点和章节下的知识点集合。
	 * @param syllabusId
	 * 所属考试大纲ID。
	 * @param chapterId
	 * 所属教材章节ID。
	 * @return
	 * 知识点集合。
	 */
	List<ChapterKnowledgeInfo> loadKnowledges(String syllabusId,String chapterId);
	/**
	 * 加载知识点数据。
	 * @param knowledgeId
	 * @return
	 */
	ChapterKnowledge loadKnowledge(String knowledgeId);
	/**
	 * 数据模型转换。
	 * @param knowledge
	 * @return
	 */
	ChapterKnowledgeInfo conversion(ChapterKnowledge knowledge);
	/**
	 * 获取章节下最大排序号。
	 * @param chapterId
	 * 章节ID。
	 * @return
	 */
	Integer loadMaxCode(String chapterId);
}