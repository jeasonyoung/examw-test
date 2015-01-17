package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.domain.library.Paper;
/**
 * 最热试卷模版处理。
 * 
 * @author yangyong
 * @since 2015年1月15日
 */
public class HotsPaperTemplateProcess  extends NewsPaperTemplateProcess {
	private static final Logger logger = Logger.getLogger(HotsPaperTemplateProcess.class);
	/*
	 * 试卷模版处理。
	 * @see com.examw.test.service.publish.impl.NewsPaperTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled())logger.debug("最热试卷模版处理...");
		long count =  this.userPaperRecordDao.totalHotsPapers(null);
		int total = 0;
		String prefix = "hots",path = "/hots";
		Map<String, Object> parameters = new HashMap<>();
		if(count == 0){
			total += this.createStaticPages(prefix, path,parameters, null);
		}else{
			if(count > this.maxPaperCount) count =  this.maxPaperCount;
			int totalPages = (int)(count / page_count) + ((count % page_count) > 0 ? 1 : 0);
			total += this.createStaticPages(prefix, path,parameters, totalPages);
		}
		return total;
	}
	//创建静态页面
	private int createStaticPages(String prefix,String path,Map<String, Object> parameters, Integer totalPages) throws Exception{
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
		Paper paper =  null;
		for(int i = 0; i < totalPages; i++){
			parameters.put("current", i + 1);
			papers = new ArrayList<>();
			List<Paper> listPapers =  this.userPaperRecordDao.loadHotsPapers(null, i+1, page_count);
			for(int j = 0; j < listPapers.size(); j++){
				paper = listPapers.get(j);
				if(paper == null) continue;
				PaperSortingViewData ps =  this.buildPaperListViewData((i * page_count) + (j + 1), paper, this.loadItemsCount(paper.getId()));
				if(ps != null) papers.add(ps);
			}
			parameters.put("papers", papers);
			this.updateStaticPage(prefix + "-" + ((i == 0) ? "index": (i + 1)),
					(i == 0) ? String.format("%s/index.html", path) : String.format("%1$s/%2$d.html", path, (i + 1)),
							this.createStaticPageContent(parameters));
			total += 1;
		}
		return total;
	}
}