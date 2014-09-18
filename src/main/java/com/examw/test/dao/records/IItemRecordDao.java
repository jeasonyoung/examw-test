package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.ItemRecord;

/**
 * 试题做题记录数据接口
 * @author fengwei.
 * @since 2014年9月17日 下午5:33:27.
 */
public interface IItemRecordDao  extends IBaseDao<ItemRecord>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<ItemRecord> findItemRecords(ItemRecord info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(ItemRecord info);
	/**
	 * 批量插入
	 * @param list
	 */
	void insertItemRecordList(List<ItemRecord> list);
}
