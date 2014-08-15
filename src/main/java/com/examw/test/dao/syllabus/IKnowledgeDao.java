package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.Knowledge;
import com.examw.test.model.syllabus.KnowledgeInfo;
/**
 * 知识点管理数据接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IKnowledgeDao extends IBaseDao<Knowledge> {
	/**
	 * 查询数据。
	 * @param bookId
	 * 查询条件。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<Knowledge> findKnowledges(String bookId,KnowledgeInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(String bookId,KnowledgeInfo info);
}
