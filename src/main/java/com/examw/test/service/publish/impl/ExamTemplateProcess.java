package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.settings.ExamInfo;

/**
 * 考试模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class ExamTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(ExamTemplateProcess.class);
	private IExamDao examDao;
	/**
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
	}
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("模版处理...");
		List<Exam> exams = this.examDao.findExams(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return Status.ENABLED.getValue();}
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(exams == null || exams.size()  == 0) return null;
		
		Map<String, Object>  parametersMap = new HashMap<>();
		//最新试卷
		parametersMap.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parametersMap.put("hotsPapers", this.loadHotsPapers());
		//常见问题
		parametersMap.put("questions", this.loadQuestions());
		
		List<StaticPage> list = new ArrayList<>();
		for(Exam exam : exams){
			if(exam == null) continue;
			Map<String, Object> parameters = new HashMap<>();
			parameters.putAll(parametersMap);
			parameters.put("exam", exam);
			
			StaticPage page = new StaticPage(String.format("index-exams-%s", exam.getAbbr()),"/exams");
			page.setContent(this.createStaticPageContent(parameters));
			page.setLastTime(new Date());
			
			list.add(page);
		}
		return list;
	}
}