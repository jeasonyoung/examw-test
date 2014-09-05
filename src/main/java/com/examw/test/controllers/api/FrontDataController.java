package com.examw.test.controllers.api;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.service.settings.ICategoryService;

/**
 * 考试分类-考试控制器
 * @author fengwei.
 * @since 2014年9月5日 上午10:41:23.
 */
@Controller
@RequestMapping(value = { "/api/data" })
public class FrontDataController {
	private static final Logger logger = Logger.getLogger(FrontDataController.class);
	@Resource
	private ICategoryService categoryService;
	/**
	 * 加载所有考试分类和其下的所有考试
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = {"/category-exams"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<CategoryFrontInfo> loadAllCategoryExams(){
		if(logger.isDebugEnabled()) logger.debug("考试分类-考试数据");
		return this.categoryService.loadAllCategoryAndExams();
	}
}
