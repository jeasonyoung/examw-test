package com.examw.test.controllers.front;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.service.settings.ICategoryService;

/**
 * 题库首页
 * @author fengwei.
 * @since 2014年9月4日 上午10:30:26.
 */
@Controller
@RequestMapping(value="/front")
public class FrontIndexController {
	@Resource
	private ICategoryService categoryService;
	private static final Logger logger = Logger.getLogger(FrontIndexController.class);
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","/index","/"}, method = RequestMethod.GET)
	public String index(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载index...");
		List<CategoryFrontInfo> list = this.categoryService.loadAllCategoryAndExams();
		model.addAttribute("CATEGORYLIST", list);
		return "front/index";
	}
}
