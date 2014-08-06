package com.examw.test.service.settings;

import java.util.List;

import com.examw.test.domain.settings.Category;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 考试分类服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午3:35:16.
 */
public interface ICategoryService extends IBaseDataService<CategoryInfo>{
	/**
	 * 查询所有的考试分类
	 * @return
	 */
	List<Category> loadAllCategorys();
}
