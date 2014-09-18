package com.examw.test.service.records;

import java.util.List;

import com.examw.test.domain.records.ItemRecord;

/**
 * 做题记录数据接口
 * @author fengwei.
 * @since 2014年9月18日 下午2:31:30.
 */
public interface IItemRecordService {
	/**
	 * 插入一条做题记录
	 * @param data
	 * @return
	 */
	boolean insertRecord(ItemRecord data);
	/**
	 * 插入几条做题记录
	 * @param data	做题记录的集合
	 * @return	成功的条数
	 */
	Integer insertRecords(List<ItemRecord> list);
	
}
