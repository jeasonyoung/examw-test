package com.examw.test.controllers.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.test.model.front.CategoryFrontInfo;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.settings.ICategoryService;
import com.examw.test.service.settings.ISubjectService;
import com.examw.test.service.syllabus.ISyllabusService;

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
	@Resource
	private IProductService productService;
	@Resource
	private ISubjectService subjectService;
	@Resource
	private ISyllabusService syllabusService;
	
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
	/**
	 * 加载考试下所有的产品
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = {"/products"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<ProductInfo> loadProducts(String examId){
		if(logger.isDebugEnabled()) logger.debug("考试下产品数据");
		if(StringUtils.isEmpty(examId)) return null;
		return this.productService.loadProducts(examId);
	}
	/**
	 * 单个产品信息
	 */
	@RequestMapping(value = {"/product"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ProductInfo loadProduct(String id){
		if(logger.isDebugEnabled()) logger.debug("选中产品数据");
		if(StringUtils.isEmpty(id)) return null;
		return this.productService.loadProduct(id);
	}
	/**
	 * 章节练习
	 */
	@RequestMapping(value = {"/chapter"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String,Object> loadChapter(final String examId,String subjectId){
		if(logger.isDebugEnabled()) logger.debug("章节数据");
		if(StringUtils.isEmpty(examId)) return null;
		Map<String,Object> map = new HashMap<String,Object>();
		List<SubjectInfo> subjects = this.subjectService.datagrid(new SubjectInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getExamId() {return examId;}
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "asc";}
		}).getRows();
		map.put("SUBJECTLIST",subjects);
		if(StringUtils.isEmpty(subjectId)){
			if(subjects!=null && subjects.size()>0)
				subjectId = subjects.get(0).getId();
		}
		map.put("CHAPTERLIST", this.syllabusService.loadSyllabuss(subjectId, null));
		return map;
	}
	
}
