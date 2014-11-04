package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.ItemErrorRecorvery;
import com.examw.test.model.library.ItemErrorRecorveryInfo;

/**
 * 试题纠错数据接口
 * @author fengwei.
 * @since 2014年11月4日 下午1:48:42.
 */
public interface IItemErrorRecorveryDao extends IBaseDao<ItemErrorRecorvery> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<ItemErrorRecorvery> findItems(ItemErrorRecorveryInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(ItemErrorRecorveryInfo info);
}
