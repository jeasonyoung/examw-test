package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.test.domain.syllabus.BookChapter;
import com.examw.test.model.syllabus.BookChapterInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 教材章节服务接口。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public interface IBookChapterService extends IBaseDataService<BookChapterInfo> {
	/**
	 * 加载教材下的章节集合。
	 * @param bookId
	 * 教材ID。
	 * @return
	 * 章节集合。
	 */
	List<BookChapterInfo> loadChapters(String bookId);
	/**
	 * 加载最大排序号。
	 * @param parentChapterId
	 * 父章节ID。
	 * @return
	 */
	Integer loadMaxOrder(String parentChapterId);
	/**
	 * 数据模型转换。
	 * @param chapter
	 * @return
	 */
	BookChapterInfo conversion(BookChapter chapter);
}