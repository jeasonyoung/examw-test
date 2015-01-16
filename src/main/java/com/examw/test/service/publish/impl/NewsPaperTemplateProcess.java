package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.service.publish.impl.PaperListTemplateProcess.PaperListViewData;
/**
 * 最新试卷模版处理。
 * 
 * @author yangyong
 * @since 2015年1月15日
 */
public class NewsPaperTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(NewsPaperTemplateProcess.class);
	private Integer maxPaperCount;
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
	 * 模版静态化处理。。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("执行最新试卷模版处理...");
		PaperInfo where = new PaperInfo();
		where.setSort("createTime");
		where.setOrder("desc");
		long count = this.paperReleaseDao.totalPaperReleases(where);
		int total = 0;
		String prefix = "news",path = "/news";
		Map<String, Object> parameters = new HashMap<>();
		if(count == 0){
			total += this.createStaticPages(prefix, path,parameters, null, where);
		}else{
			if(count > this.maxPaperCount) count =  this.maxPaperCount;
			int totalPages = (int)(count / page_count) + ((count % page_count) > 0 ? 1 : 0);
			total += this.createStaticPages(prefix, path,parameters, totalPages, where);
		}
		return total;
	}
	//创建静态页面
	private int createStaticPages(String prefix,String path,Map<String, Object> parameters, Integer totalPages, PaperInfo where) throws Exception{
		List<PaperListViewData> papers = null;
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
		Paper paper = null;
		Subject subject = null;
		for(int i = 0; i < totalPages; i++){
			where.setPage(i+1);
			parameters.put("current", i + 1);
			papers = new ArrayList<>();
			List<PaperRelease> paperReleases = this.paperReleaseDao.findPaperReleases(where);
			for(PaperRelease release : paperReleases){
				if(release == null || (paper = release.getPaper()) == null) continue;
				PaperListViewData pv = new PaperListViewData(paper.getId(), paper.getName(), release.getTotal(), paper.getTime(),
						(paper.getScore() == null ? 0 : paper.getScore().intValue()), paper.getPrice());
				subject = paper.getSubject();
				if(subject == null || subject.getExam() == null) continue;
				pv.setCategory(String.format("/%1$s/%2$d",subject.getExam().getAbbr(), subject.getCode()));
				pv.setCreateTime(paper.getCreateTime());
				pv.setUsers(this.loadPaperUsersTotal(pv.getId()));
				papers.add(pv);
			}
			parameters.put("papers", papers);
			this.updateStaticPage(prefix + "-" + ((i == 0) ? "index": where.getPage()),
					(i == 0) ? String.format("%s/index.html", path) : String.format("%1$s/%2$d.html", path, where.getPage()),
							this.createStaticPageContent(parameters));
			total += 1;
		}
		return total;
	}
}