package com.examw.test.service.syllabus;

import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.model.syllabus.TextBookInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 教材服务接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface ITextBookService extends IBaseDataService<TextBookInfo> {
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 更新知识点数据。
	 * @param bookId
	 * 更新条件。
	 * @param info
	 * 更新条件。
	 */
	KnowledgeInfo updateKnowledge(String bookId, KnowledgeInfo info);
}
