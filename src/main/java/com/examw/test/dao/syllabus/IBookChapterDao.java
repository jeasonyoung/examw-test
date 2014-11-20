package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.BookChapter;
/**
 * 教材章节数据接口。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public interface IBookChapterDao extends IBaseDao<BookChapter> {
	/**
	 * 查询教材下的一级章节集合。
	 * @param bookId
	 * 教材ID。
	 * @return
	 * 章节集合。
	 */
	List<BookChapter> findFirstChapters(String bookId);
	/**
	 * 加载最大排序号。
	 * @param parentChapterId
	 * 上级章节ID。
	 * @return
	 */
	Integer loadMaxOrder(String parentChapterId);
	/**
	 * 判断是否已经创建了章节
	 * @param bookId
	 * @param syllabusId
	 * @return
	 */
	BookChapter hasCreated(String bookId,String syllabusId);
}