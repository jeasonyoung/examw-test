package com.examw.test.service.products;

import com.examw.test.model.products.ProductUserInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 产品用户服务接口
 * @author fengwei.
 * @since 2014年8月12日 上午8:37:43.
 */
public interface IProductUserService extends IBaseDataService<ProductUserInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 加载状态名称。
	 * @param status
	 * @return
	 */
	String loadStatusName(Integer status);
}
