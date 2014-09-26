package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.Product;
import com.examw.test.model.products.ProductInfo;

/**
 * 产品数据接口
 * @author fengwei.
 * @since 2014年8月12日 下午3:17:16.
 */
public interface IProductDao extends IBaseDao<Product>{
	/**
	 * 查询产品数据
	 * @param info 查询条件
	 * @return	产品数据
	 */
	List<Product> findProducts(ProductInfo info);
	/**
	 * 查询统计
	 * @param info 查询条件
	 * @return	产品数据
	 */
	Long total(ProductInfo info);
	/**
	 * 加载最大代码。
	 * @return
	 */
	Integer loadMaxCode();
}