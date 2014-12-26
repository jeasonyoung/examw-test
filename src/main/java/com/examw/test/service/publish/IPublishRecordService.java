package com.examw.test.service.publish;

import com.examw.test.model.publish.PublishRecordInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 发布记录服务接口。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public interface IPublishRecordService extends IBaseDataService<PublishRecordInfo> {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
}