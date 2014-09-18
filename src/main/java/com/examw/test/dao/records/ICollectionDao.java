package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.Collection;

/**
 * 试题收藏数据接口
 * @author fengwei.
 * @since 2014年9月17日 下午5:34:25.
 */
public interface ICollectionDao extends IBaseDao<Collection>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<Collection> findCollections(Collection info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(Collection info);
}
