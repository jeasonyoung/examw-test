package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.records.IQuestionDao;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.records.Question;
import com.examw.test.model.records.QuestionInfo;

/**
 * 常见问题列表模版处理。
 * 
 * @author yangyong
 * @since 2015年1月6日
 */
public class QuestionListTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(QuestionListTemplateProcess.class);
	private IQuestionDao questionDao;
	/**
	 * 设置常见问题数据接口。
	 * @param questionDao 
	 *	  常见问题数据接口。
	 */
	public void setQuestionDao(IQuestionDao questionDao) {
		if(logger.isDebugEnabled()) logger.debug("注入常见问题数据接口...");
		this.questionDao = questionDao;
	}
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("常见问题列表模版处理...");
		Long count = this.questionDao.total(new QuestionInfo());
		List<StaticPage> listPages = new ArrayList<>();
		Map<String, Object> parameters = new HashMap<String, Object>();
		//最新试卷
		parameters.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parameters.put("hotsPapers", this.loadHotsPapers());
		
		QuestionInfo where = new QuestionInfo();
		where.setRows(page_count);
		where.setSort("lastTime");
		where.setOrder("desc");
		String prefix = "index-questions", path = "/questions";
		if(count == null || count == 0){
			this.createStaticPages(prefix, path, parameters, listPages, null, where);
			return listPages;
		}
		int totalPages = (count == null) ? 0 : (int)((count / page_count) +  ((count % page_count) > 0 ? 1 : 0));
		for(int i = 0; i < totalPages; i++){
			this.createStaticPages(prefix, path, parameters, listPages, totalPages, where);
		}
		return listPages;
	}
	//创建静态页面
	private void createStaticPages(String prefix,String path,Map<String, Object> parameters,List<StaticPage> listPages, Integer totalPages, QuestionInfo where) throws Exception {
		if(totalPages == null || totalPages == 0){
			parameters.put("current", 1);
			listPages.add(new StaticPage(prefix +"-1", path, this.createStaticPageContent(parameters)));
			return;
		}
		parameters.put("total", totalPages);
		parameters.put("prefix", prefix);
		for(int i = 0; i < totalPages; i++){
			where.setPage(i+1);
			parameters.put("current", i + 1);
			List<ViewListData> list = new ArrayList<>();
			List<Question> questions = this.questionDao.findQuestions(where);
			for(Question question : questions){
				if(question == null) continue;
				list.add(new ViewListData(question.getId(), question.getTitle()));
			}
			parameters.put("questions", list);
			listPages.add(new StaticPage(prefix + "-" + where.getPage(), path, this.createStaticPageContent(parameters)));
		}
	}
}