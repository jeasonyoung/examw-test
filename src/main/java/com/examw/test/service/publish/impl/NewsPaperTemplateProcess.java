package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.settings.Exam;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.service.publish.impl.PaperListTemplateProcess.PaperListViewData;
/**
 * 最新试卷模版处理。
 * 
 * @author yangyong
 * @since 2015年1月15日
 */
public class NewsPaperTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(NewsPaperTemplateProcess.class);
	protected Integer maxPaperCount;
	//2015.01.19  增加考试数据接口,分考试发布试卷列表
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
	/**
	 * 构造函数。
	 */
	public NewsPaperTemplateProcess(){
		this.setMaxPaperCount(list_max_top * 50);
	}
	/**
	 * 设置最新试卷最大数据量。
	 * @param maxPaperCount 
	 *	  最新试卷最大数据量。
	 */
	public void setMaxPaperCount(Integer maxPaperCount) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入最新试卷最大数据量:%d", maxPaperCount));
		if(maxPaperCount == null || maxPaperCount == 0) return;
		this.maxPaperCount = maxPaperCount;
	}
	/*
	 * 模版静态化处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess(java.util.Date)
	 */
	@Override
	protected int addTemplateProcess(Date startTime) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("执行最新试卷模版处理...");
		PaperInfo where = new PaperInfo();
		where.setSort("createTime");
		where.setOrder("desc");
		where.setRows(page_count);
		long count = this.paperReleaseDao.totalPaperReleases(where);
		int total = 0;
		String prefix = "news",path = "/news";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("updateTime", startTime);
		if(count == 0){
			total += this.createStaticPages(prefix, path,parameters, null, where);
		}else{
			if(count > this.maxPaperCount) count =  this.maxPaperCount;
			int totalPages = (int)(count / page_count) + ((count % page_count) > 0 ? 1 : 0);
			total += this.createStaticPages(prefix, path,parameters, totalPages, where);
		}
		//分考试进行加载
		List<Exam> exams = this.examDao.findExams(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return Status.ENABLED.getValue();}
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(exams != null && exams.size()  > 0)
		{
			for(Exam exam : exams){
				if(exam == null) continue;
				where.setExamId(exam.getId());
				long exam_count = this.paperReleaseDao.totalPaperReleases(where);
				String exam_prefix = "news-"+exam.getAbbr(),exam_path = "/news/"+exam.getAbbr();
				Map<String, Object> exam_parameters = new HashMap<>();
				exam_parameters.put("abbr", exam.getAbbr());//考试简称
				exam_parameters.put("examName", exam.getName());//考试名称
				if(count == 0){
					this.createStaticPages(prefix, path,parameters, null, where);
				}else{
					if(exam_count > this.maxPaperCount) exam_count =  this.maxPaperCount;
					int exam_totalPages = (int)(exam_count / page_count) + ((exam_count % page_count) > 0 ? 1 : 0);
					this.createStaticPages(exam_prefix, exam_path,exam_parameters, exam_totalPages, where);
				}
			}
		}
		return total;
	}
	//创建静态页面
	private int createStaticPages(String prefix,String path,Map<String, Object> parameters, Integer totalPages, PaperInfo where) throws Exception{
		List<PaperSortingViewData> papers = null;
		if(totalPages == null || totalPages <= 0){
			parameters.put("current", 1);
			parameters.put("papers", papers); 
			this.updateStaticPage(prefix +"-index", path + "/index.html", this.createStaticPageContent(parameters));
			return 1;
		}
		parameters.put("total", totalPages);
		parameters.put("prefix", prefix);
		parameters.put("path", path);
		int total = 0;
		PaperRelease release = null;
		for(int i = 0; i < totalPages; i++){
			where.setPage(i+1);
			parameters.put("current", i + 1);
			papers = new ArrayList<>();
			List<PaperRelease> paperReleases = this.paperReleaseDao.findPaperReleases(where);
			for(int j = 0; j < paperReleases.size(); j++){
				release = paperReleases.get(j);
				if(release == null || release.getPaper() == null) continue;
				PaperSortingViewData ps =  this.buildPaperListViewData((i * page_count) + (j + 1), release.getPaper(), release.getTotal());
				if(ps != null) papers.add(ps);
			}
			parameters.put("papers", papers);
			this.updateStaticPage(prefix + "-" + ((i == 0) ? "index": where.getPage()),
					(i == 0) ? String.format("%s/index.html", path) : String.format("%1$s/%2$d.html", path, where.getPage()),
							this.createStaticPageContent(parameters));
			total += 1;
		}
		return total;
	}
	/**
	 * 构建试卷列表数据。
	 * @param paper
	 * @param items
	 * @return
	 */
	protected PaperSortingViewData buildPaperListViewData(Integer order, Paper paper, Integer items){
		if(paper == null) return null;
		Subject subject = paper.getSubject();
		if(subject == null || subject.getExam() == null) return null;
		PaperSortingViewData ps = new PaperSortingViewData(paper.getId(), paper.getName(), items, paper.getTime(),
				(paper.getScore() == null ? 0 : paper.getScore().intValue()), paper.getPrice());
		ps.setCategory(String.format("/%1$s/%2$d",subject.getExam().getAbbr(), subject.getCode()));
		ps.setCreateTime(paper.getCreateTime());
		ps.setUsers(this.loadPaperUsersTotal(ps.getId()));
		ps.setOrder(order);
		return ps;
	}
	/**
	 * 试卷排行数据。
	 * 
	 * @author yangyong
	 * @since 2015年1月17日
	 */
	public static class PaperSortingViewData extends PaperListViewData{
		private static final long serialVersionUID = 1L;
		private Integer order;
		/**
		 * 构造函数。
		 * @param id
		 * @param text
		 * @param items
		 * @param times
		 * @param total
		 * @param price
		 */
		public PaperSortingViewData(String id, String text, Integer items,Integer times, Integer total, Integer price) {
			super(id, text, items, times, total, price); 
		}
		/**
		 * 获取排序号。
		 * @return 排序号。
		 */
		public Integer getOrder() {
			return order;
		}
		/**
		 * 设置排序号。
		 * @param order 
		 *	  排序号。
		 */
		public void setOrder(Integer order) {
			this.order = order;
		}
	}
}