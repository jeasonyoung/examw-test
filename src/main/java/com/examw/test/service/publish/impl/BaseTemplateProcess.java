package com.examw.test.service.publish.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.records.IQuestionDao;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.records.Question;
import com.examw.test.service.publish.ITemplateProcess;
import com.examw.test.support.FreeMakerEngine;

/**
 * 模版处理器基础抽象类。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public abstract class BaseTemplateProcess implements ITemplateProcess {
	private static final Logger logger = Logger.getLogger(BaseTemplateProcess.class);
	protected static final int list_max_top = 10, page_count = 10;
	private String templatesRoot,templateName;
	private FreeMakerEngine engine;
	private IPaperReleaseDao paperReleaseDao;
	private IUserPaperRecordDao userPaperRecordDao;
	private IQuestionDao questionDao;
	/**
	 * 设置模版根目录。
	 * @param templatesRoot 
	 *	  模版根目录。
	 */
	public void setTemplatesRoot(String templatesRoot) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入模版根目录：%s", templatesRoot));
		this.templatesRoot = templatesRoot;
	}
	/**
	 * 设置模版名称。
	 * @param templateName 
	 *	  模版名称。
	 */
	public void setTemplateName(String templateName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入模版名称：%s", templateName));
		this.templateName = templateName;
	}
	/**
	 * 设置试卷发布数据接口。
	 * @param paperReleaseDao 
	 *	  试卷发布数据接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷发布数据接口...");
		this.paperReleaseDao = paperReleaseDao;
	}
	/**
	 * 设置用户试卷记录数据接口。
	 * @param userPaperRecordDao 
	 *	  用户试卷记录数据接口。
	 */
	public void setUserPaperRecordDao(IUserPaperRecordDao userPaperRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户试卷记录数据接口...");
		this.userPaperRecordDao = userPaperRecordDao;
	}
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
	 * 模版处理入口。
	 * @see com.examw.test.service.publish.ITemplateProcess#process()
	 */
	@Override
	public List<StaticPage> process() throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("模版［ root = %s］处理入口...", this.templatesRoot)); 
		if(StringUtils.isEmpty(this.templatesRoot)) throw new Exception("未配置模版根路径地址！");
		if(StringUtils.isEmpty(this.templateName)) throw new Exception("未配置模版名称！");
		this.engine = new FreeMakerEngine();
		this.engine.setTemplateDir(this.templatesRoot);
		//模版处理。
		return this.templateProcess();
	}
	/**
	 * 模版处理。
	 * @return
	 * 静态页面对象。
	 * @throws Exception
	 */
	protected abstract List<StaticPage> templateProcess() throws Exception;
	/**
	 * 生成静态页面内容。
	 * @param parameters
	 * 模版参数。
	 * @return
	 * 页面内容。
	 * @throws Exception
	 */
	protected String createStaticPageContent(Map<String, Object> parameters) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("生成模版［%s］静态页面内容...", this.templateName));
		return this.engine.analysisTemplate(this.templateName, parameters);
	}
	/**
	 * 加载试卷参考人次。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 * 参考人次。
	 */
	protected int loadPaperUsersTotal(String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%s］参考人次", paperId));
		return StringUtils.isEmpty(paperId) ? 0 : this.userPaperRecordDao.findUsersTotal(paperId).intValue();
	}
	/**
	 * 加载最新试卷数据。
	 * @return 最新试卷数据。
	 */
	protected List<ViewListData> loadNewsPapers(){
		if(logger.isDebugEnabled()) logger.debug("加载最新试卷数据...");
		List<ViewListData> list = null;
		List<PaperRelease> releases = this.paperReleaseDao.loadNewsReleases(list_max_top);
		if(releases != null && releases.size() > 0){
			list = new ArrayList<>();
			for(PaperRelease release : releases){
				Paper paper = null;
				if(release == null || (paper = release.getPaper()) == null) continue;
				ViewListData data = new ViewListData(paper.getId(), paper.getName());
				data.setTotal(this.loadPaperUsersTotal(data.getId()));
				list.add(data);
			}
		}
		return list;
	}
	/**
	 * 加载最热试卷数据。
	 * @return 最热试卷数据。
	 */
	protected List<ViewListData> loadHotsPapers(){
		if(logger.isDebugEnabled()) logger.debug("加载最热试卷数据...");
		List<ViewListData> list = null;
		List<Paper> papers = this.userPaperRecordDao.loadHotsPapers(list_max_top);
		if(papers != null && papers.size() > 0){
			list = new ArrayList<>();
			for(Paper paper : papers){
				if(paper == null) continue;
				ViewListData data = new ViewListData(paper.getId(), paper.getName());
				data.setTotal(this.loadPaperUsersTotal(data.getId()));
				list.add(data);
			}
		}
		return list;
	}
	/**
	 * 加载常见问题集合。
	 * @return
	 */
	protected List<ViewListData> loadQuestions(){
		if(logger.isDebugEnabled()) logger.debug("加载常见问题集合...");
		List<ViewListData> list = null;
		List<Question> questions = this.questionDao.loadTopQuestions(list_max_top);
		if(questions != null && questions.size() > 0){
			list = new ArrayList<>();
			for(Question question : questions){
				if(question == null) continue;
				list.add(new ViewListData(question.getId(), question.getTitle()));
			}
		}
		return list;
	}
	/**
	 * 列表数据。
	 * 
	 * @author yangyong
	 * @since 2015年1月6日
	 */
	public static class ViewListData implements Serializable {
		private static final long serialVersionUID = 1L;
		private String id,text;
		private Integer total;
		/**
		 * 构造函数。
		 * @param id
		 * 数据ID。
		 * @param text
		 * 显示内容。
		 * @param total
		 * 数据统计。
		 */
		public ViewListData(String id, String text, Integer total){
			this.setId(id);
			this.setText(text);
			this.setTotal(total);
		}
		/**
		 * 构造函数。
		 * @param id
		 * 数据ID。
		 * @param text
		 * 显示内容。
		 */
		public ViewListData(String id, String text){
			this(id, text, 0);
		}
		/**
		 * 获取数据ID。
		 * @return 数据ID。
		 */
		public String getId() {
			return id;
		}
		/**
		 * 设置数据ID。
		 * @param id 
		 *	  数据ID。
		 */
		public void setId(String id) {
			this.id = id;
		}
		/**
		 * 获取显示内容。
		 * @return 显示内容。
		 */
		public String getText() {
			return text;
		}
		/**
		 * 设置显示内容。
		 * @param text 
		 *	  显示内容。
		 */
		public void setText(String text) {
			this.text = text;
		}
		/**
		 * 获取统计数值。
		 * @return 统计数值。
		 */
		public Integer getTotal() {
			return total;
		}
		/**
		 * 设置统计数值。
		 * @param total 
		 *	  统计数值。
		 */
		public void setTotal(Integer total) {
			this.total = total;
		}
	}
}