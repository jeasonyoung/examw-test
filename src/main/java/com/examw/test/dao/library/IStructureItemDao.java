package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.StructureItemInfo;

/**
 * 结构下试题数据接口。
 * 
 * @author yangyong
 * @since 2014年8月13日
 */
public interface IStructureItemDao extends IBaseDao<StructureItem> {
	/**
	 * 查询数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<StructureItem> findStructureItems(String paperId, StructureItemInfo info);
	/**
	 * 查询数据统计。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(String paperId, StructureItemInfo info);
}