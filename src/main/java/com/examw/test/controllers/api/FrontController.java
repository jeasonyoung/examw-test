package com.examw.test.controllers.api;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.test.model.products.FrontProductInfo;
import com.examw.test.model.settings.FrontCategoryInfo;
import com.examw.test.service.products.IFrontProductService;
import com.examw.test.service.settings.IFrontCategoryService;

/**
 * 前端考试数据控制器。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
@Controller
@RequestMapping(value = { "/api/data" })
public class FrontController {
	private static final Logger logger = Logger.getLogger(FrontController.class);
	//注入前端考试类别服务接口。
	@Resource
	private IFrontCategoryService frontCategoryService;
	//注入产品服务接口。
	@Resource
	private IFrontProductService frontProductService;
	/**
	 * 加载所有考试分类和其下的所有考试
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = {"/category-exams"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<FrontCategoryInfo> loadAllCategoryExams(){
		if(logger.isDebugEnabled()) logger.debug("考试分类-考试数据...");
		return this.frontCategoryService.loadAllCategoryAndExams();
	}
	/**
	 * 加载考试下所有的产品
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = {"/products"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public List<FrontProductInfo> loadProducts(String examId){
		if(logger.isDebugEnabled()) logger.debug("考试下产品数据...");
		return this.frontProductService.loadProducts(examId);
	}
	/**
	 * 单个产品信息。
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}"}, method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public FrontProductInfo loadProduct(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug("选中产品数据");
		return  this.frontProductService.loadProduct(productId);
	}
}