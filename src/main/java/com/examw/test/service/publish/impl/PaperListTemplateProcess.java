package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.publish.ViewListData;
import com.examw.test.model.settings.ExamInfo;

/**
 * 试卷列表模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class PaperListTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(PaperListTemplateProcess.class);
	private static final int page_count = 10;
	private IExamDao examDao;
	private IPaperReleaseDao paperReleaseDao;
	/**
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
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
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("试卷列表模版处理...");
		List<Exam> exams = this.examDao.findExams(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return Status.ENABLED.getValue();}
		});
		if(exams == null || exams.size() == 0) return null;
		List<StaticPage> listPages = new ArrayList<>();
		for(Exam exam : exams){
			if(exam == null) continue;
			PaperInfo where = new PaperInfo();
			where.setExamId(exam.getId());
			where.setRows(page_count);
			where.setSort("createTime");
			where.setOrder("desc");
			String prefix =  String.format("index-exams-%s-papers", exam.getAbbr()),
					  path = String.format("/exams/%s/papers", exam.getAbbr());
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("exam", exam);
			Long count = this.paperReleaseDao.totalPaperReleases(where);
			if(count  == null || count == 0){
				this.createStaticPages(prefix, path,parameters, listPages, null, where);
			}else{
				this.createStaticPages(prefix, path, parameters, listPages,  (int)((count / page_count) +  ((count % page_count) > 0 ? 1 : 0)), where);
				if(exam.getSubjects() != null && exam.getSubjects().size() > 0){
					for(Subject subject : exam.getSubjects()){
						if(subject == null) continue;
						parameters.put("subject", subject);
						prefix =  String.format("index-exams-%1$s-%2$d-papers", exam.getAbbr(), subject.getCode());
						path =  String.format("/exams/%1$s/%2$d/papers", exam.getAbbr(), subject.getCode());
						where.setSubjectId(subject.getId());
						count = this.paperReleaseDao.totalPaperReleases(where);
						this.createStaticPages(prefix, path, parameters, listPages, (count == null || count == 0) ? null : (int)((count / page_count) +  ((count % page_count) > 0 ? 1 : 0)), where);
					}
				}
			}
		}
		return listPages;
	}
	//创建静态页面
	private void createStaticPages(String prefix,String path,Map<String, Object> parameters,List<StaticPage> listPages, Integer totalPages, PaperInfo where) throws Exception{
		if(totalPages == null){
			parameters.put("current", 1);
			listPages.add(new StaticPage(prefix +"-1", path, this.createStaticPageContent(parameters)));
			return;
		}
		parameters.put("total", totalPages);
		parameters.put("prefix", prefix);
		Paper paper = null;
		for(int i = 0; i < totalPages; i++){
			where.setPage(i+1);
			parameters.put("current", i + 1);
			List<PaperViewData> list = new ArrayList<>();
			List<PaperRelease> paperReleases = this.paperReleaseDao.findPaperReleases(where);
			for(PaperRelease release : paperReleases){
				if(release == null || (paper = release.getPaper()) == null) continue;
				PaperViewData pv = new PaperViewData(paper.getId(), paper.getName());
				pv.setItems(release.getTotal());
				pv.setTimes(paper.getTime());
				if(paper.getScore() != null){
					pv.setTotal(paper.getScore().intValue());
				}
				pv.setPrice(paper.getPrice());
				pv.setCreateTime(paper.getCreateTime());
				pv.setUsers(this.loadPaperUsersTotal(pv.getId()));
				list.add(pv);
			}
			parameters.put("papers", list);
			listPages.add(new StaticPage(prefix + "-" + where.getPage(), path, this.createStaticPageContent(parameters)));
		}
	}
	/**
	 * 试卷列表。
	 * 
	 * @author yangyong
	 * @since 2015年1月5日
	 */
	public class PaperViewData extends ViewListData{
		private static final long serialVersionUID = 1L;
		private Integer items,times,users,price;
		private Date createTime;
		/**
		 * 构造函数。
		 * @param id
		 * @param text
		 */
		public PaperViewData(String id, String text) {
			super(id, text);
		}
		/**
		 * 获取总题数。
		 * @return 总题数。
		 */
		public Integer getItems() {
			return items;
		}
		/**
		 * 设置总题数。
		 * @param items 
		 *	  总题数。
		 */
		public void setItems(Integer items) {
			this.items = items;
		}
		/**
		 * 获取考试时长。
		 * @return 考试时长。
		 */
		public Integer getTimes() {
			return times;
		}
		/**
		 * 设置考试时长。
		 * @param times 
		 *	  考试时长。
		 */
		public void setTimes(Integer times) {
			this.times = times;
		}
		/**
		 * 获取参考人次。
		 * @return 参考人次。
		 */
		public Integer getUsers() {
			return users;
		}
		/**
		 * 设置参考人次。
		 * @param users 
		 *	  参考人次。
		 */
		public void setUsers(Integer users) {
			this.users = users;
		}
		/**
		 * 获取试卷价格。
		 * @return 试卷价格。
		 */
		public Integer getPrice() {
			return price;
		}
		/**
		 * 设置试卷价格。
		 * @param 试卷价格。 
		 *	  试卷价格。
		 */
		public void setPrice(Integer price) {
			this.price = price;
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