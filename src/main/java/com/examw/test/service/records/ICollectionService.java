package com.examw.test.service.records;

import com.examw.test.domain.records.Collection;

/**
 * 试题收藏服务接口
 * @author fengwei.
 * @since 2014年9月18日 下午3:55:23.
 */
public interface ICollectionService {
	/**
	 * 插入一条做题记录
	 * @param data
	 * @return
	 */
	boolean insertCollection(Collection data);
}
