package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.model.products.ChannelInfo;

/**
 * 渠道数据接口
 * @author fengwei.
 * @since 2014年8月11日 下午3:56:15.
 */
public interface IChannelDao extends IBaseDao<Channel>{
	/**
	 * 查询渠道数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Channel> findChannels(ChannelInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ChannelInfo info);
}
