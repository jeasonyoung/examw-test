package com.examw.test.service.products;

import com.examw.test.domain.products.Product;
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
	/**
	 * 加载状态名称
	 * @param stauts
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载产品数据。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 产品数据。
	 */
	Product loadProduct(String productId);
	/**
	 * 数据模型转换。
	 * @param product
	 * 产品数据。
	 * @return
	 * 产品信息。
	 */
	ProductInfo conversion(Product product);
}