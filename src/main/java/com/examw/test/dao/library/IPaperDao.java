package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.model.library.PaperInfo;

/**
 * 试卷数据接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface IPaperDao extends IBaseDao<Paper> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Paper> findPapers(PaperInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	Long total(PaperInfo info);
}