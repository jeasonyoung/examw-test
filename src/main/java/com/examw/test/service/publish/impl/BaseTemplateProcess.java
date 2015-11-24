package com.examw.test.service.publish.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.publish.IStaticPageDao;
import com.examw.test.dao.records.IQuestionDao;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.records.Question;
import com.examw.test.domain.settings.Subject;
import com.examw.test.service.publish.ITemplateProcess;
import com.examw.test.support.FreeMakerEngine;

/**
 * 模版处理器基础抽象类。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public  abstract class BaseTemplateProcess implements ITemplateProcess,ResourceLoaderAware {
	private static final Logger logger = Logger.getLogger(BaseTemplateProcess.class);
	private static final Map<String, List<ViewListData>> view_data_cache = new HashMap<String, List<ViewListData>>();
	private static final Map<String, Integer> papers_data_cache = new HashMap<>();
	protected static final int list_max_top = 10, page_count = 10;
	private String templatesRoot,templateName;
	private ResourceLoader resourceLoader;
	private FreeMakerEngine engine;
	protected IUserPaperRecordDao userPaperRecordDao;
	private IStaticPageDao staticPageDao;
	protected IPaperReleaseDao paperReleaseDao;
	protected IQuestionDao questionDao;
	private PublishRecord record;
	/*
	 * 设置Spring资源加载器。
	 * @see org.springframework.context.ResourceLoaderAware#setResourceLoader(org.springframework.core.io.ResourceLoader)
	 */
	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		if(logger.isDebugEnabled()) logger.debug("注入Spring资源加载器...");
		this.resourceLoader = resourceLoader;
	}
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
	/**
	 * 设置静态页面数据接口。
	 * @param staticPageDao 
	 *	  静态页面数据接口。
	 */
	public void setStaticPageDao(IStaticPageDao staticPageDao) {
		if(logger.isDebugEnabled()) logger.debug("注入静态页面数据接口...");
		this.staticPageDao = staticPageDao;
	}
	/*
	 *  模版静态化处理入口。
	 * @see com.examw.test.service.publish.ITemplateProcess#addProcess(com.examw.test.domain.publish.PublishRecord)
	 */
	@Override
	public int addProcess(PublishRecord record) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("模版［ root = %s］处理入口...", this.templatesRoot)); 
		if(StringUtils.isEmpty(this.templatesRoot)) throw new Exception("未配置模版根路径地址！");
		if(StringUtils.isEmpty(this.templateName)) throw new Exception("未配置模版名称！");
		this.record = record;
		this.engine = new FreeMakerEngine(this.resourceLoader);
		this.engine.setTemplateDir(this.templatesRoot);
		//模版静态化处理。
		return this.addTemplateProcess(this.record.getStartTime());
	}
	/**
	 * 模版静态化处理。
	 * @param startTime
	 * @return
	 * @throws Exception
	 */
	protected abstract int addTemplateProcess(Date startTime) throws Exception;
	/**
	 * 更新静态页面数据。
	 * @param id
	 * 页面ID。
	 * @param path
	 * 页面路径。
	 * @param content
	 * 页面内容。
	 */
	protected void updateStaticPage(String id,String path, String content){
		if(logger.isDebugEnabled()) logger.debug(String.format("更新静态页面数据：%s...", id));
		boolean isAdded = false;
		StaticPage data = StringUtils.isEmpty(id) ? null : this.staticPageDao.load(StaticPage.class, id);
		if(isAdded = (data == null)){
			data = new StaticPage();
			data.setId(StringUtils.isEmpty(id) ? UUID.randomUUID().toString() : id);
			data.setCreateTime(new Date());
		}
		data.setPath(path);
		data.setContent(content);
		data.setStatus(0);
		data.setLastTime(new Date());
		data.setPublish(this.record);
		if(isAdded){
			this.staticPageDao.save(data);
		}
//		//添加到静态页面ID队列
//		if(STATICPAGEID_QUEUE != null && data != null && !StringUtils.isEmpty(data.getId())){
//			STATICPAGEID_QUEUE.offer(data.getId());
//		}
	}
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
	protected synchronized int loadPaperUsersTotal(String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%s］参考人次", paperId));
		if(StringUtils.isEmpty(paperId)) return 0; 
		String key = String.format("paperUsersTotal-%s", paperId);
		Integer total =  papers_data_cache.get(key);
		if(total == null){
			Long count = this.userPaperRecordDao.findUsersTotal(paperId);
			total = (count == null) ? 0 : count.intValue();
			papers_data_cache.put(key, total);
		}
		return total; 
	}
	/**
	 * 加载试卷
	 * @param paperId
	 * @return
	 */
	protected synchronized int loadItemsCount(String paperId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［%s］试题数目", paperId));
		if(StringUtils.isEmpty(paperId)) return 0; 
		String key = String.format("paperItems-%s", paperId);
		Integer total =  papers_data_cache.get(key);
		if(total == null){
			total = this.paperReleaseDao.loadItemsCount(paperId);
			papers_data_cache.put(key, total);
		}
		return total; 
	}
	/**
	 * 加载最新试卷数据。
	 * @return 最新试卷数据。
	 */
	protected synchronized List<ViewListData> loadNewsPapers(String examId){
		if(logger.isDebugEnabled()) logger.debug("加载最新试卷数据...");
		String key = String.format("news-%s", StringUtils.isEmpty(examId) ? "all" : examId);
		List<ViewListData> list =  view_data_cache.get(key);//从缓存中获取数据。
		if(list == null){
			list = new ArrayList<>();
			List<PaperRelease> releases = this.paperReleaseDao.loadNewsReleases(examId,list_max_top);
			if(releases != null && releases.size() > 0){
				for(PaperRelease release : releases){
					Paper paper = null;
					Subject subject =  null;
					if(release == null || (paper = release.getPaper()) == null) continue;
					ViewListData data = new ViewListData(paper.getId(), paper.getName());
					if((subject = paper.getSubject()) != null){
						data.setCategory(String.format("/%1$s/%2$s", subject.getExam().getAbbr(), subject.getCode()));
					}
					data.setTotal(this.loadPaperUsersTotal(data.getId()));
					list.add(data);
				}
			}
			//存入缓存。
			view_data_cache.put(key, list);
		}
		return list;
	}
	/**
	 * 加载最热试卷数据。
	 * @param examId
	 * 所属考试ID
	 * @return 最热试卷数据。
	 */
	protected synchronized List<ViewListData> loadHotsPapers(String examId){
		if(logger.isDebugEnabled()) logger.debug("加载最热试卷数据...");
		String key = String.format("hots-%s", StringUtils.isEmpty(examId) ?  "all" : examId);
		List<ViewListData> list = view_data_cache.get(key);//从缓存中获取数据。
		if(list == null){
			list = new ArrayList<>();
			List<Paper> papers = this.userPaperRecordDao.loadHotsPapers(examId, null, list_max_top);
			if(papers != null && papers.size() > 0){
				for(Paper paper : papers){
					if(paper == null) continue;
					Subject subject =  null;
					ViewListData data = new ViewListData(paper.getId(), paper.getName());
					if((subject = paper.getSubject()) != null){
						data.setCategory(String.format("/%1$s/%2$s", subject.getExam().getAbbr(), subject.getCode()));
					}
					data.setTotal(this.loadPaperUsersTotal(data.getId()));
					list.add(data);
				}
			}
			//存入缓存
			view_data_cache.put(key, list);
		}
		return list;
	}
	/**
	 * 加载常见问题集合。
	 * @return
	 */
	protected synchronized List<ViewListData> loadQuestions(){
		if(logger.isDebugEnabled()) logger.debug("加载常见问题集合...");
		List<ViewListData> list = view_data_cache.get("questions");//从缓存中获取数据。
		if(list == null){
			list = new ArrayList<>();
			List<Question> questions = this.questionDao.loadTopQuestions(list_max_top);
			if(questions != null && questions.size() > 0){
				for(Question question : questions){
					if(question == null) continue;
					list.add(new ViewListData(question.getId(), question.getTitle()));
				}
			}
			//存入缓存
			view_data_cache.put("questions", list);
		}
		return list;
	}
	/*
	 * 清除缓存。
	 * @see com.examw.test.service.publish.ITemplateProcess#cleanCache()
	 */
	@Override
	public void cleanCache(){
		if(logger.isDebugEnabled()) logger.debug("清除缓存....");
		view_data_cache.clear();
		papers_data_cache.clear();
	}
	/**
	 * 列表数据。
	 * 
	 * @author yangyong
	 * @since 2015年1月6日
	 */
	public static class ViewListData implements Serializable {
		private static final long serialVersionUID = 1L;
		private String id,text,category;
		private Integer total;
		/**
		 * 构造函数。
		 * @param id
		 * 数据ID。
		 * @param text
		 * 显示内容。
		 * @param category
		 * 类别。
		 * @param total
		 * 统计。
		 */
		public ViewListData(String id, String text,String category, Integer total){
			this(id, text, total);
			this.setCategory(category);
		}
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
		 * 构造函数。
		 */
		public ViewListData(){
			this(null, null);
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
		 * 获取类别。
		 * @return 类别
		 */
		public String getCategory() {
			return category;
		}
		/**
		 * 设置类别
		 * @param category 
		 *	  类别
		 */
		public void setCategory(String category) {
			this.category = category;
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
			this.total = (total == null ? 0 : total);
		}
	}
}