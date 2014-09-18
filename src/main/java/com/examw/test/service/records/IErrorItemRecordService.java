package com.examw.test.service.records;

import com.examw.test.domain.records.ErrorItemRecord;

/**
 * 错题记录数据接口
 * @author fengwei.
 * @since 2014年9月18日 下午3:53:09.
 */
public interface IErrorItemRecordService {
	/**
	 * 插入一条做题记录
	 * @param data
	 * @return
	 */
	boolean insertRecord(ErrorItemRecord data);
}
