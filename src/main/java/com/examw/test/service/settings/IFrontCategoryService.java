package com.examw.test.service.settings;

import java.util.List;

import com.examw.test.model.settings.FrontCategoryInfo;

/**
 * 前端类别服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public interface IFrontCategoryService {
	/**
	 * 加载所有考试类别。
	 * @return
	 */
	List<FrontCategoryInfo> loadCategories();
	/**
	 * 加载考试类别。
	 * @param categoryId
	 * @return
	 */
	FrontCategoryInfo loadCategory(String categoryId);
}