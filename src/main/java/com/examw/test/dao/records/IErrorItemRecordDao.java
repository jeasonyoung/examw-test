package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.ErrorItemRecord;

/**
 * 错题记录数据接口
 * @author fengwei.
 * @since 2014年9月17日 下午5:35:57.
 */
public interface IErrorItemRecordDao extends IBaseDao<ErrorItemRecord>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<ErrorItemRecord> findErrorItemRecords(ErrorItemRecord info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(ErrorItemRecord info);
	/**
	 * 批量插入
	 * @param list
	 */
	void insertRecordList(List<ErrorItemRecord> list);
}
