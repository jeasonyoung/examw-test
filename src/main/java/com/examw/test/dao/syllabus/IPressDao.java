package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.model.syllabus.PressInfo;
/**
 * 出版社数据接口。
 * @author lq.
 * @since 2014-08-06.
 */
public interface IPressDao extends IBaseDao<Press>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<Press> findPresss(PressInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(PressInfo info);
}
