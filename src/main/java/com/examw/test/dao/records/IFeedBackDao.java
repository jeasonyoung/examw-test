package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.FeedBack;
import com.examw.test.model.records.FeedBackInfo;

/**
 * 用户反馈数据接口
 * @author fengwei.
 * @since 2015年1月12日 下午4:45:57.
 */
public interface IFeedBackDao extends IBaseDao<FeedBack> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	List<FeedBack> findFeedBacks(FeedBackInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(FeedBackInfo info);
}
