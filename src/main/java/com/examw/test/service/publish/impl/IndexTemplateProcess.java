package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;

/**
 * 首页模版处理器。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public class IndexTemplateProcess extends BaseTemplateProcess {
	private static Logger logger = Logger.getLogger(IndexTemplateProcess.class);
	private ICategoryDao categoryDao;
	/**
	 * 设置考试类别数据接口。
	 * @param categoryDao 
	 *	  考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("模版处理...");
		List<StaticPage> list = new ArrayList<>();
		StaticPage page = new StaticPage("index","/");
		page.setContent(this.createStaticPageContent(this.createParameters()));
		page.setLastTime(new Date());
		list.add(page);
		return list;
	}
	//构建参数集合。
	private Map<String, Object> createParameters(){
		Map<String, Object> parametersMap = new HashMap<>();
		//考试分类
		List<Category> topCategories = this.categoryDao.loadTopCategories();
		if(topCategories != null && topCategories.size() > 0){
			Map<String, Map<String, String>> categoriesMap = new HashMap<>();
			for(Category category  : topCategories){
				if(category == null) continue;
				Map<String, String> examsMap = new HashMap<>();
				this.buildCategoryExams(category, examsMap);
				if(examsMap.size() > 0){
					categoriesMap.put(category.getName(), examsMap);
				}
			}
			parametersMap.put("categories", categoriesMap);
		}
		//最新试卷
		parametersMap.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parametersMap.put("hotsPapers", this.loadHotsPapers());
		//常见问题
		parametersMap.put("questions", this.loadQuestions());
		
		return parametersMap;
	}
	//构建考试类别下考试
	private void buildCategoryExams(Category category, Map<String, String> examsMap){
		if(category == null) return;
		if(category.getExams() != null && category.getExams().size() > 0){
			for(Exam exam : category.getExams()){
				if(exam == null) continue;
				examsMap.put(exam.getName(), exam.getAbbr());
			}
		}
		if(category.getChildren() != null && category.getChildren().size() > 0){
			for(Category child : category.getChildren()){
				this.buildCategoryExams(child, examsMap);
			}
		}
	}
}