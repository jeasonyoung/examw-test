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
import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IPaperPreviewService;
import com.examw.test.service.library.IPaperReleaseService;
/**
 * 试卷发布服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public class PaperReleaseServiceImpl implements IPaperReleaseService {
	private static final Logger logger = Logger.getLogger(PaperReleaseServiceImpl.class);
	private static final int CONST_BATCH_VALUE = 5;
	private IPaperDao paperDao;
	private IPaperReleaseDao paperReleaseDao;
	private IPaperPreviewService paperPreviewService;
	private ObjectMapper mapper;
	/**
	 * 构造函数。
	 */
	public PaperReleaseServiceImpl(){
		this.mapper = new ObjectMapper();
	}
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置试卷发布数据接口。
	 * @param paperReleaseDao 
	 *	 试卷发布数据接口。
	 */
	public void setPaperReleaseDao(IPaperReleaseDao paperReleaseDao) {
		if(logger.isDebugEnabled())logger.debug("注入试卷发布数据接口...");
		this.paperReleaseDao = paperReleaseDao;
	}
	/**
	 * 设置试卷阅览服务接口。
	 * @param paperPreviewService 
	 *	  试卷阅览服务接口。
	 */
	public void setPaperPreviewService(IPaperPreviewService paperPreviewService) {
		if(logger.isDebugEnabled())logger.debug("注入试卷阅览服务接口...");
		this.paperPreviewService = paperPreviewService;
	}
	/*
	 * 删除试卷发布。
	 * @see com.examw.test.service.library.IPaperReleaseService#deleteRelease(java.lang.String)
	 */
	@Override
	public void deleteRelease(String paperId) {
		if(logger.isDebugEnabled())logger.debug("删除已发布试卷:" + paperId);
		if(StringUtils.isEmpty(paperId)) return;
		this.paperReleaseDao.deleteRelease(paperId);
	}
	/*
	 * 试卷发布。
	 * @see com.examw.test.service.library.IPaperReleaseService#updateRelease()
	 */
	@Override
	public void updateRelease() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("开始发布试卷....");
		Long count = this.paperDao.loadAllAuditCount();
		if(count == null || count <= 0){
			if(logger.isDebugEnabled())logger.debug("没有需要发布的试卷!");
			return;
		}
		if(logger.isDebugEnabled())logger.debug(String.format("共有［%d］要发布...", count));
		int total = count.intValue(),index = 0;
		while(total > 0){
			int size = (total > CONST_BATCH_VALUE) ? CONST_BATCH_VALUE : total;
			List<Paper> papers = this.paperDao.loadAllAudit(size);
			if(papers != null && papers.size() > 0){
				for(Paper paper : papers){
					if(paper == null) continue;
					if(logger.isDebugEnabled()) logger.debug(String.format("[%1$d]发布试卷:[％2$s]%3$s", ++index, paper.getId(), paper.getName()));
					try{
						this.updateRelease(paper);
					}catch(Exception e){
						logger.error("发布试卷发生异常:" + e.getMessage(), e);
					}
				}
			}
			total -= size;
		}
	}
	/*
	 * 发布指定试卷。
	 * @see com.examw.test.service.library.IPaperReleaseService#updateRelease(java.lang.String)
	 */
	@Override
	public void updateRelease(String paperId) throws Exception {
		if(logger.isDebugEnabled())logger.debug("发布试卷:" + paperId);
		if(StringUtils.isEmpty(paperId))return;
		//试卷。
		Paper paper = this.paperDao.load(Paper.class, paperId);
		if(paper == null){
			if(logger.isDebugEnabled()) logger.debug("试卷不存在!");
			return;
		}
		this.updateRelease(paper);
	}
	//发布试卷。
	private void updateRelease(Paper paper) throws Exception {
		if(paper == null || StringUtils.isEmpty(paper.getId())) return;
		//删除存在的发布。
		if(this.paperReleaseDao.hasRelease(paper.getId())){
			if(logger.isDebugEnabled())logger.debug("试卷已发布，先将其删除...");
			this.paperReleaseDao.deleteRelease(paper.getId());
		}
		//试卷预览。
		PaperPreview paperPreview = this.paperPreviewService.loadPaperPreview(paper.getId());
		if(paperPreview == null){
			if(logger.isDebugEnabled()) logger.debug("试卷预览不存在!");
			return;
		}
		PaperRelease paperRelease = new PaperRelease();
		paperRelease.setId(UUID.randomUUID().toString());
		paperRelease.setPaper(paper);
		paperRelease.setTitle(paperPreview.getName());
		paperRelease.setTotal(paperPreview.getTotal());
		paperRelease.setCreateTime(new Date());
		paperRelease.setContent(this.mapper.writeValueAsString(paperPreview));
		this.paperReleaseDao.save(paperRelease);
		paper.setStatus(Paper.STATUS_PUBLISH);
	}
}