package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
	/*
	 * 模版静态化处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("常见问题列表模版处理...");
		Long count = this.questionDao.total(new QuestionInfo()); 
		Map<String, Object> parameters = new HashMap<String, Object>();
		//最新试卷
		parameters.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parameters.put("hotsPapers", this.loadHotsPapers());
		
		QuestionInfo where = new QuestionInfo();
		where.setRows(page_count);
		where.setSort("createTime");
		where.setOrder("desc");
		String prefix = "questions-list", path = "/questions/list";
		
		int total = 0;
		if(count == null || count == 0){
			total += this.createStaticPages(prefix, path, parameters, null, where);
		}else {
			int totalPages = (int)((count / page_count) +  ((count % page_count) > 0 ? 1 : 0));
			for(int i = 0; i < totalPages; i++){
				total += this.createStaticPages(prefix, path, parameters, totalPages, where);
			}
		}
		return total;
	}
	//创建静态页面
	private int createStaticPages(String prefix,String path,Map<String, Object> parameters, Integer totalPages, QuestionInfo where) throws Exception {
		if(totalPages == null || totalPages == 0){
			parameters.put("current", 1);
			this.updateStaticPage(prefix +"-index", path + "/index.html", this.createStaticPageContent(parameters));
			return 1;
		}
		parameters.put("total", totalPages);
		parameters.put("prefix", prefix);
		parameters.put("path", path);
		int total = 0;
		for(int i = 0; i < totalPages; i++){
			where.setPage(i+1);
			parameters.put("current", i + 1);
			List<QuestionListViewData> list = new ArrayList<>();
			List<Question> questions = this.questionDao.findQuestions(where);
			for(Question question : questions){
				if(question == null) continue;
				list.add(new QuestionListViewData(question.getId(), question.getTitle(), question.getCreateTime()));
			}
			parameters.put("questions", list);
			this.updateStaticPage(prefix + "-" + ( i == 0 ? "index" : where.getPage()),
					(i == 0) ? String.format("%s/index.html", path) : String.format("%1$s/%2$d.html", path, where.getPage()), 
							this.createStaticPageContent(parameters));
			total += 1;
		}
		return total;
	}
	/**
	 * 常见问题列表。
	 * 
	 * @author yangyong
	 * @since 2015年1月7日
	 */
	public static class QuestionListViewData extends ViewListData{
		private static final long serialVersionUID = 1L;
		private Date createTime;
		/**
		 * 构造函数。
		 * @param id
		 * 常见问题ID。
		 * @param text
		 * 常见问题标题。
		 * @param createTime
		 * 创建时间。
		 */
		public QuestionListViewData(String id, String text, Date createTime) {
			super(id, text);
			this.setCreateTime(createTime);
		}
		/**
		 * 获取创建时间。
		 * @return 创建时间。
		 */
		public Date getCreateTime() {
			return createTime;
		}
		/**
		 * 设置创建时间。
		 * @param createTime 
		 *	  创建时间。
		 */
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
	}
}