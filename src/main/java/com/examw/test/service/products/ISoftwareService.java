package com.examw.test.service.products;

import com.examw.test.model.products.SoftwareInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 产品软件服务接口
 * @author fengwei.
 * @since 2014年8月13日 上午11:44:15.
 */
public interface ISoftwareService  extends IBaseDataService<SoftwareInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 加载状态名称
	 * @param stauts
	 * @return
	 */
	String loadStatusName(Integer status);
}
