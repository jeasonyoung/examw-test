package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.TextBook;
import com.examw.test.model.syllabus.TextBookInfo;
/**
 * 教材设置数据接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface ITextBookDao extends IBaseDao<TextBook> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<TextBook> findTextBooks(TextBookInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(TextBookInfo info);
}
