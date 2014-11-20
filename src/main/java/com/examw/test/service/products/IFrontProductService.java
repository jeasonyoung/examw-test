package com.examw.test.service.products;

import java.util.List;

import com.examw.test.model.products.FrontProductInfo;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.SubjectInfo;

/**
 * 前端产品服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public interface IFrontProductService {
	/**
	 * 加载考试下的所有产品集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 * 产品集合。
	 */
	List<FrontProductInfo> loadProductsByExam(String examId);
	List<FrontProductInfo> loadProductsByCategory(String categoryId);
	/**
	 * 加载产品信息。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 产品信息。
	 */
	FrontProductInfo loadProduct(String productId);
	/**
	 * 加载产品科目信息。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 科目信息集合。
	 */
	List<SubjectInfo> loadProductSubjects(String productId);
	/**
	 * 加载产品地区信息。
	 * @param productId
	 * 产品ID。
	 * @return
	 * 地区信息集合。
	 */
	List<AreaInfo> loadProductAreas(String productId);
}