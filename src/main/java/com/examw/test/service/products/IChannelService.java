package com.examw.test.service.products;

import com.examw.test.model.products.ChannelInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 渠道服务接口
 * @author fengwei.
 * @since 2014年8月11日 下午4:12:04.
 */
public interface IChannelService  extends IBaseDataService<ChannelInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
}
