package com.examw.test.dao.publish;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.model.publish.PublishRecordInfo;

/**
 * 发布记录数据接口。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public interface IPublishRecordDao extends IBaseDao<PublishRecord> {
	/**
	 * 查询发布记录数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<PublishRecord> findRecords(PublishRecordInfo info);
	/**
	 * 查询发布记录数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 统计结果。
	 */
	Long total(PublishRecordInfo info);
}