package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.Book;
import com.examw.test.model.syllabus.BookInfo;
/**
 * 教材设置数据接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IBookDao extends IBaseDao<Book> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<Book> findBooks(BookInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(BookInfo info);
	/**
	 * 加载最大排序号。
	 * @return 最大排序号。
	 */
	Integer loadMaxOrder();
	/**
	 * 加载科目地区下最新教材。
	 * @param subjectId
	 * 所属科目ID。
	 * @param areaId
	 * 所属地区ID。
	 * @return
	 * 教材。
	 */
	Book loadBookLast(String subjectId,String areaId);
	/**
	 * 加载科目下的所有教材
	 * @param subjectId
	 * @return
	 */
	List<Book> loadBookList(String subjectId);
}