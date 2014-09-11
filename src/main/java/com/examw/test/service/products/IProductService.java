package com.examw.test.service.products;

import java.util.List;
import java.util.Map;

import com.examw.test.domain.settings.Subject;
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
	 * 加载考试下所有的产品 [前台调用方法]
	 * @param examId
	 * @return
	 */
	List<ProductInfo> loadProducts(String examId);
	/**
	 * 根据id加载产品 [前台调用方法]
	 * @param id
	 * @return
	 */
	ProductInfo loadProduct(String id);
	/**
	 * 根据产品ID,查询条件查询试卷列表[前台调用方法]
	 * @param productId
	 * @param info
	 * @return
	 */
	List<Subject> loadSubjectList(String productId);
	/**
	 * 获取试卷类型映射
	 * @return
	 */
	Map<String, String> getPaperTypeMap();
}
