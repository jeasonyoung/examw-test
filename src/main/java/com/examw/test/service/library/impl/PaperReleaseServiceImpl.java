package com.examw.test.service.library.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger; 
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IPaperPreviewService;
import com.examw.test.service.library.IPaperReleaseService;
import com.examw.test.service.library.PaperStatus;
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
		if(logger.isDebugEnabled())logger.debug(String.format("删除已发布试卷:%s", paperId));
		if(StringUtils.isEmpty(paperId)) return;
		this.paperReleaseDao.deleteRelease(paperId);
	}
	/*
	 * 试卷发布。
	 * @see com.examw.test.service.library.IPaperReleaseService#updateRelease()
	 */
	@Override
	public void updateRelease() {
		try{
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
		}catch(Exception e){
			logger.error(String.format("批量发布试卷时发生异常：%s", e.getMessage()), e);
			e.printStackTrace();
		}
	}
	/*
	 * 发布指定试卷。
	 * @see com.examw.test.service.library.IPaperReleaseService#updateRelease(java.lang.String)
	 */
	@Override
	public void updateRelease(String paperId) {
		try{
			if(logger.isDebugEnabled())logger.debug(String.format("发布试卷［paperId = %s］...", paperId));
			if(StringUtils.isEmpty(paperId))return;
			//试卷。
			Paper paper = this.paperDao.load(Paper.class, paperId);
			if(paper == null){
				if(logger.isDebugEnabled()) logger.debug(String.format("试卷［paperId = %s］不存在!", paperId));
				return;
			}
			this.updateRelease(paper);
		}catch(Exception e){
			logger.error(String.format("发布试卷［%1$s］发生异常：%2$s", paperId,e.getMessage()),  e);
			e.printStackTrace();
		}
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
		PaperRelease paperRelease = this.paperReleaseDao.load(PaperRelease.class, paper.getId());
		if(paperRelease == null){
			paperRelease = new PaperRelease();
			paperRelease.setId(paper.getId());
			paperRelease.setPaper(paper);
			paperRelease.setCreateTime(new Date());
		}
		paperRelease.setTitle(paperPreview.getName());
		paperRelease.setTotal(paperPreview.getTotal());
		paperRelease.setContent(this.mapper.writeValueAsString(paperPreview));
		 this.paperReleaseDao.saveOrUpdate(paperRelease);
		paper.setStatus(PaperStatus.PUBLISH.getValue());
		paper.setPublishTime(new Date());
		this.paperDao.saveOrUpdate(paper);
	}
	/*
	 * 检查发布。
	 * @see com.examw.test.service.library.IPaperReleaseService#checkRelease()
	 */
	@Override
	public void updateCheckRelease() {
		try{
			if(logger.isDebugEnabled()) logger.debug("检查试卷发布...");
			this.updateCheckPublishedRelease();
			this.updateCheckNonAuditRelease();
			//清除已删除的试卷发布。
			this.paperReleaseDao.clearRelease();
		}catch(Exception e){
			logger.debug(String.format("检查试卷发布时发生异常：%s", e.getMessage()), e);
			e.printStackTrace();
		}
	}
	//检查已发布的试卷。
	private void updateCheckPublishedRelease(){
		if(logger.isDebugEnabled()) logger.debug("检查试卷试卷状态为已发布的试卷是否存在....");
		List<Paper> papers = this.paperDao.findPapers(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return PaperStatus.PUBLISH.getValue(); }
			@Override
			public String getSort() { return "lastTime";}
			@Override
			public String getOrder() {return "desc";}
		});
		if(papers == null || papers.size() == 0){
			if(logger.isDebugEnabled()) logger.debug("未找到状态为［已发布］的试卷数据！");
			return;
		}
		for(Paper paper : papers){
			if(paper == null) continue;
			if(!this.paperReleaseDao.hasRelease(paper.getId())){
				if(logger.isDebugEnabled()) logger.debug(String.format("未找到试卷［paperId = %1$s］［paperName = %2$s］的发布数据，开始重新发布...", paper.getId(), paper.getName()));
				try{
					this.updateRelease(paper);
				}catch(Exception e){
					logger.error(String.format("重新发布试卷［paperId = %1$s, paperName = %2$s］时发生异常：%3$s", paper.getId(),paper.getName(),e.getMessage()), e);
					e.printStackTrace();
				}
			}
		}
	}
	//删除试卷状态重置为未审核的已发布的试卷内容。
	private void updateCheckNonAuditRelease(){
		if(logger.isDebugEnabled()) logger.debug("删除试卷状态重置为未审核的已发布的试卷内容...");
		List<Paper> papers = this.paperDao.findPapers(new PaperInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return PaperStatus.NONE.getValue(); }
			@Override
			public String getSort() { return "lastTime";}
			@Override
			public String getOrder() {return "desc";}
		});
		if(papers == null || papers.size() == 0){
			if(logger.isDebugEnabled()) logger.debug("未找到状态为［未审核］的试卷数据！");
			return;
		}
		for(Paper paper : papers){
			if(paper == null) continue;
			if(this.paperReleaseDao.hasRelease(paper.getId())){
				try{
					if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷状态重置为［未审核］的试卷［paperId = %1$s］［paperName = %2$s］发布内容...", paper.getId(), paper.getName()));
					this.deleteRelease(paper.getId());
				}catch(Exception e){
					logger.error(String.format("删除发布试卷［paperId = %1$s, paperName = %2$s］时发生异常：%3$s", paper.getId(),paper.getName(),e.getMessage()), e);
					e.printStackTrace();
				}
			}
		}
	}
}