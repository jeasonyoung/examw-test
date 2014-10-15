package com.examw.test.service.syllabus;
import com.examw.test.model.syllabus.BookInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 教材服务接口。
 * @author lq.
 * @since 2014-08-07.
 */
public interface IBookService extends IBaseDataService<BookInfo> {
	/**
	 * 加载教材状态名称。
	 * @param status
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载最大排序号。
	 * @return
	 */
	Integer loadMaxOrder();
}