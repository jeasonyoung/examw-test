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
	 * 加载所有的考试类型-考试。
	 * @return
	 */
	List<FrontCategoryInfo> loadAllCategoryAndExams();
}