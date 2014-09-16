package com.examw.test.service.settings;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 考试分类服务接口
 * @author fengwei.
 * @since 2014年8月6日 下午3:35:16.
 */
public interface ICategoryService extends IBaseDataService<CategoryInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 查询所有的考试分类
	 * @return
	 */
	List<TreeNode> loadAllCategorys(String ignoreCategoryId);
	/**
	 * 加载考试类型下考试设置树数据。
	 * @return
	 * 考试设置树数据。
	 */
	List<TreeNode> loadAllCategoryExams();
	/**
	 * 加载考试类型下考试科目树数据。
	 * @return
	 * 考试科目树数据。
	 */
	List<TreeNode> loadAllCategoryExamSubjects();
	/**
	 * 加载所有的考试类型-考试[前台调用方法]
	 * @return
	 */
	List<CategoryFrontInfo> loadAllCategoryAndExams();
}
