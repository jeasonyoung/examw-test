package com.examw.test.service.settings;

import com.examw.test.model.settings.AreaInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 行政地区服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:48:28.
 */
public interface IAreaService extends IBaseDataService<AreaInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
}
