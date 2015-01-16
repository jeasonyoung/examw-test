package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.settings.ICategoryDao;
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
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("首页模版处理...");
		this.updateStaticPage("index","/index.html", this.createStaticPageContent(this.createParameters()));
		return 1;
	}
	//构建参数集合。
	private Map<String, Object> createParameters(){
		Map<String, Object> parametersMap = new HashMap<>();
		//考试分类
		List<Category> topCategories = this.categoryDao.loadTopCategories();
		if(topCategories != null && topCategories.size() > 0){
			List<CategoryViewData> categories = new ArrayList<>();
			for(Category category  : topCategories){
				if(category == null) continue;
				CategoryViewData categoryViewData = new CategoryViewData(category.getAbbr(), category.getName());
				if(category.getExams() != null && category.getExams().size() > 0){
					List<ViewListData> examViewDatas = new ArrayList<>();
					this.buildCategoryExams(category, examViewDatas);
					categoryViewData.setExams(examViewDatas);
				}
				if(categoryViewData.getExams() != null && categoryViewData.getExams().size() > 0){
					categories.add(categoryViewData);
				}
			}
			parametersMap.put("categories", categories);
		}
		//最新试卷
		parametersMap.put("newsPapers", this.loadNewsPapers(null));
		//最热试卷
		parametersMap.put("hotsPapers", this.loadHotsPapers(null));
		//常见问题
		parametersMap.put("questions", this.loadQuestions());
		
		return parametersMap;
	}
	//构建考试类别下考试
	private void buildCategoryExams(Category category, List<ViewListData> examViewDatas){
		if(category == null) return;
		if(category.getExams() != null && category.getExams().size() > 0){
			for(Exam exam : category.getExams()){
				if(exam == null) continue;
				examViewDatas.add(new ViewListData(exam.getAbbr(), exam.getName()));
			}
		}
		if(category.getChildren() != null && category.getChildren().size() > 0){
			for(Category child : category.getChildren()){
				this.buildCategoryExams(child, examViewDatas);
			}
		}
	}
	/**
	 * 考试类别显示集合。
	 * 
	 * @author yangyong
	 * @since 2015年1月7日
	 */
	public static class CategoryViewData extends ViewListData{
		private static final long serialVersionUID = 1L;
		private List<ViewListData> exams;
		/**
		 * 构造函数。
		 * @param id
		 * 
		 * @param text
		 */
		public CategoryViewData(String id, String text) {
			super(id, text);
			this.setExams(new ArrayList<ViewListData>());
		}
		/**
		 * 获取考试显示集合。
		 * @return 考试显示集合。
		 */
		public List<ViewListData> getExams() {
			return exams;
		}
		/**
		 * 设置考试显示集合。
		 * @param exams 
		 *	  考试显示集合。
		 */
		public void setExams(List<ViewListData> exams) {
			this.exams = exams;
		}
	}
}