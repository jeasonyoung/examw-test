package com.examw.test.service.products;

import java.util.List;

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
	 * 加载最大排序号。
	 * @param examId
	 * 所属考试ID。
	 * @return 最大排序号。
	 */
	Integer loadMaxOrder(String examId);
	/**
	 * 加载状态名称
	 * @param stauts
	 * @return
	 */
	String loadStatusName(Integer status);
	/**
	 * 数据模型转换。
	 * @param product
	 * 产品数据。
	 * @return
	 * 产品信息。
	 */
	ProductInfo conversion(Product product);
	/**
	 * 加载产品数据。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 产品数据。
	 */
	Product loadProduct(String productId);
	/**
	 * 加载考试下产品数据集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 * 产品数据集合。
	 */
	List<ProductInfo> loadProducts(String examId);
}