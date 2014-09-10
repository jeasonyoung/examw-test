package com.examw.test.service.syllabus;

import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 知识点服务接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IKnowledgeService extends IBaseDataService<KnowledgeInfo> {
	/**
	 * 根据大纲ID加载知识点内容[前台调用方法]
	 * @param syllabusId
	 * @return
	 */
	KnowledgeInfo loadKnowledge(String syllabusId);
}
