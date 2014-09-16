package com.examw.test.dao.settings;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.settings.Category;
import com.examw.test.model.settings.CategoryInfo;

/**
 * 考试分类数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午12:00:50.
 */
public interface ICategoryDao extends IBaseDao<Category>{
	/**
	 * 查询考试分类数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Category> findCategorys(CategoryInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(CategoryInfo info);
}
