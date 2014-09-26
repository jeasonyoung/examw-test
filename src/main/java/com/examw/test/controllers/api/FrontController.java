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
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.model.syllabus.KnowledgeInfo;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.products.IFrontProductService;
import com.examw.test.service.settings.IFrontCategoryService;
import com.examw.test.service.syllabus.IKnowledgeService;
import com.examw.test.service.syllabus.ISyllabusService;

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
	//注入考试大纲服务接口。
	@Resource
	private ISyllabusService syllabusService;
	//注入大纲知识点服务接口。
	@Resource
	private IKnowledgeService knowledgeService;
	/**
	 * 加载考试集合。
	 * @return
	 */
	@RequestMapping(value = {"/categories/exams"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontCategoryInfo> loadAllCategoryExams(){
		if(logger.isDebugEnabled()) logger.debug("考试分类-考试数据...");
		return this.frontCategoryService.loadAllCategoryAndExams();
	}
	/**
	 * 加载考试下所有的产品
	 * @param examId
	 * 考试ID。
	 * @return
	 */
	@RequestMapping(value = {"/products/{examId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<FrontProductInfo> loadProducts(@PathVariable String examId){
		if(logger.isDebugEnabled()) logger.debug("考试下产品数据...");
		return this.frontProductService.loadProducts(examId);
	}
	/**
	 * 加载产品信息。
	 * @param productId
	 * 产品ID。
	 * @return
	 */
	@RequestMapping(value = {"/product/{productId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public FrontProductInfo loadProduct(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[productId = %s]数据...", productId));
		return  this.frontProductService.loadProduct(productId);
	}
	/**
	 * 加载产品科目集合。
	 * @param productId
	 * 产品ID。
	 * @return
	 */
	@RequestMapping(value = {"/products/{productId}/subjects"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<SubjectInfo> loadProductSubjects(@PathVariable String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品[productId = %s]科目集合...", productId));
		return this.frontProductService.loadProductSubjects(productId);
	}
	/**
	 * 加载科目下的考试大纲。
	 * @param subjectId
	 * @return
	 */
	@RequestMapping(value = {"/syllabuses/{subjectId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<SyllabusInfo> loadSubjectSyllabuses(@PathVariable String subjectId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目[subjectId = %s]下的考试大纲...", subjectId));
		return this.syllabusService.loadSyllabuses(subjectId);
	}
	/**
	 * 加载考试大纲数据。
	 * @param syllabusId
	 * @return
	 */
	@RequestMapping(value = {"/syllabus/{syllabusId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public SyllabusInfo loadSubjectSyllabus(@PathVariable String syllabusId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试大纲[subjectId = %s]信息...", syllabusId));
		return this.syllabusService.conversion(this.syllabusService.loadSyllabus(syllabusId));
	}
	/**
	 * 加载大纲知识点。
	 * @param syllabusId
	 * @param textBookId
	 * @return
	 */
	@RequestMapping(value = {"/syllabus/{syllabusId}/knowledges"}, method = {RequestMethod.GET})
	@ResponseBody
	public List<KnowledgeInfo> loadKnowledges(@PathVariable String syllabusId,String textBookId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点[subjectId = %1$s，textBookId = %2$s]信息...", syllabusId));
		return this.knowledgeService.loadKnowledges(syllabusId, textBookId);
	}
	/**
	 * 加载知识点信息。
	 * @param knowledgeId
	 * @return
	 */
	@RequestMapping(value = {"/knowledges/{knowledgeId}"}, method = {RequestMethod.GET})
	@ResponseBody
	public KnowledgeInfo loadKnowledge(@PathVariable String knowledgeId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载知识点［knowledgeId = %s］信息...", knowledgeId));
		return this.knowledgeService.conversion(this.knowledgeService.loadKnowledge(knowledgeId));
	}
}