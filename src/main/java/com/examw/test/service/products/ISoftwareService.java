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
	 * 加载状态值名称。
	 * @param stauts
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载最大排序号。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 最大排序号。
	 */
	Integer loadMaxOrder(String productId);
}