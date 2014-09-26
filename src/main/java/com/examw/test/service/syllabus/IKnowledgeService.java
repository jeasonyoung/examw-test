package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 知识点服务接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IKnowledgeService extends IBaseDataService<KnowledgeInfo> {
	/**
	 * 加载知识点集合。
	 * @param syllabusId
	 * 所属考试大纲ID。
	 * @param textBookId
	 * 所属教材ID。
	 * @return
	 * 知识点集合。
	 */
	List<KnowledgeInfo> loadKnowledges(String syllabusId,String textBookId);
	/**
	 * 加载知识点数据。
	 * @param knowledgeId
	 * @return
	 */
	Knowledge loadKnowledge(String knowledgeId);
	/**
	 * 数据模型转换。
	 * @param knowledge
	 * @return
	 */
	KnowledgeInfo conversion(Knowledge knowledge);
	/**
	 * 获取最大代码值。
	 * @return
	 */
	Integer loadMaxCode();
}