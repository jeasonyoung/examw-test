package com.examw.test.service.products;

import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 产品服务接口
 * @author fengwei.
 * @since 2014年8月12日 下午3:36:33.
 */
public interface IProductService extends IBaseDataService<ProductInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	
}
