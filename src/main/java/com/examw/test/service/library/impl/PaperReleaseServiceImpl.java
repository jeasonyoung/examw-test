package com.examw.test.service.library.impl;
 
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger; 
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IPaperReleaseService;
import com.examw.test.service.library.IPaperService;
import com.examw.test.service.library.PaperStatus;

/**
 * 试卷发布服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public class PaperReleaseServiceImpl implements IPaperReleaseService {
	private static final Logger logger = Logger.getLogger(PaperReleaseServiceImpl.class);
	private IPaperReleaseDao paperReleaseDao;
	private IPaperDao paperDao;
	private IPaperService paperService;
	/**
	 * 设置试卷发布接口。
	 * @param paperReleaseDao 
	 *	  试卷发布接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		this.paperReleaseDao = paperReleaseDao;
	}
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		this.paperDao = paperDao;
	}
	/**
	 * 设置试卷服务接口。
	 * @param paperService 
	 *	  试卷服务接口。
	 */
	public void setPaperService(IPaperService paperService) {
		this.paperService = paperService;
	}
	/*
	 * 加载试卷数据。
	 * @see com.examw.test.service.library.IPaperReleaseService#loadPaper(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaper(String paperId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］的发布数据...", paperId));
		if(StringUtils.isEmpty(paperId)) return null;
		PaperRelease release = this.paperReleaseDao.loadRelease(paperId);
		if(release == null){
			Paper paper = this.paperDao.load(Paper.class, paperId);
			if(paper == null) throw new Exception(String.format("试卷［paperid = %s］不存在！", paperId));
			return  this.addPaperRelease(paper);
		}
		if(StringUtils.isEmpty(release.getContent())) return null;
		ObjectMapper mapper = new ObjectMapper();
		PaperPreview preview = mapper.readValue(release.getContent(), PaperPreview.class);
		if(preview == null) return null;
		return preview;
	}
	//加载并创建发布对象。
	private PaperPreview addPaperRelease(Paper paper) throws Exception{
		if(paper == null) return null;
		if(paper.getStatus() != PaperStatus.AUDIT.getValue()) throw new Exception(String.format("试卷［paperId = %s］未审核，不能被发布！", paper.getId()));
		PaperPreview preview = this.paperService.loadPaperPreview(paper.getId());
		if(preview == null) throw new Exception(String.format("加载试卷[paperId = %s]预览数据时失败！", paper.getId()));
		//创建发布对象。
		PaperRelease paperRelease = new PaperRelease();
		paperRelease.setId(UUID.randomUUID().toString());
		paperRelease.setPaper(paper);
		paperRelease.setTitle(paper.getName());
		paperRelease.setCreateTime(new Date());
		paperRelease.setContent(new ObjectMapper().writeValueAsString(preview));
		this.paperReleaseDao.saveOrUpdate(paperRelease);
		//更新试卷状态。
		paper.setStatus(PaperStatus.AUDIT.getValue());
		paper.setLastTime(new Date());
		return preview;
	}
	/*
	 *  试卷发布。
	 * @see com.examw.test.service.library.IPaperReleaseService#addPaperRelease()
	 */
	@Override
	public void addPaperRelease() {
		if(logger.isDebugEnabled()) logger.debug("开始发布试卷...");
		List<Paper> papers = this.paperDao.findPapers(new PaperInfo(){
			private static final long serialVersionUID = 1L; 
			@Override
			public Integer getStatus()  {return PaperStatus.AUDIT.getValue();}
			@Override
			public String getSort() {return "lastTime";} 
			@Override
			public String getOrder() {return "asc";}
		});
		if(papers == null || papers.size() == 0) return;
		for(int i = 0; i < papers.size(); i++){
			Paper paper = papers.get(i);
			try{
				if(paper == null) continue;
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］.开始发布试卷：［%2$s］%3$s", i+1, paper.getId(), paper.getName()));
				this.addPaperRelease(paper);
			}catch(Exception e){
				logger.error(String.format("［%1$d］.发布试卷［%2$s   %3$s］时发生异常：%4$s", i+1, paper.getId(), paper.getName()));
			}
		}
		if(logger.isDebugEnabled()) logger.debug("试卷发布完成！");
	}
}