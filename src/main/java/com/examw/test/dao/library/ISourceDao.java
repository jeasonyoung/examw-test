package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Source;
import com.examw.test.model.library.SourceInfo;

/**
 * 来源数据接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface ISourceDao extends IBaseDao<Source> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Source> findSources(SourceInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SourceInfo info);
}