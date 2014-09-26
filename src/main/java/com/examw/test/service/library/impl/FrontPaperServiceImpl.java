package com.examw.test.service.library.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.library.PaperRelease;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.BasePaperInfo;
import com.examw.test.model.library.FrontPaperInfo;
import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IFrontPaperService;
import com.examw.test.service.library.IPaperService;

/**
 * 试卷前端服务接口。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontPaperServiceImpl implements IFrontPaperService  {
	private static final Logger logger = Logger.getLogger(FrontPaperServiceImpl.class);
	private IPaperReleaseDao paperReleaseDao;
	private IProductDao productDao;
	private IPaperService paperService;
	private ObjectMapper mapper  = new ObjectMapper();
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
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置试卷服务接口。
	 * @param paperService 
	 *	  试卷服务接口。
	 */
	public void setPaperService(IPaperService paperService) {
		 if(logger.isDebugEnabled()) logger.debug("注入试卷服务接口...");
		 this.paperService = paperService;
	}
	/*
	 * 加载科目下的试卷数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadPapersCount(java.lang.String[])
	 */
	@Override
	public Integer loadPapersCount(String[] subjetsId) {
		if(logger.isDebugEnabled()) logger.debug("科目下的试卷数量...");
		return this.paperReleaseDao.loadPapersCount(subjetsId);
	}
	/*
	 * 加载科目下试题数量。
	 * @see com.examw.test.service.library.IFrontPaperService#loadItemsCount(java.lang.String[])
	 */
	@Override
	public Integer loadItemsCount(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下试题数量...");
		return this.paperReleaseDao.loadItemsCount(subjectsId);
	}
	/*
	 * 加载科目下是否有真题。
	 * @see com.examw.test.service.library.IFrontPaperService#hasRealItem(java.lang.String[])
	 */
	@Override
	public boolean hasRealItem(String[] subjectsId) {
		if(logger.isDebugEnabled()) logger.debug("加载科目下是否有真题...");
		return this.paperReleaseDao.hasRealItem(subjectsId);
	}
	/*
	 * 加载产品下的试卷集合。
	 * @see com.examw.test.service.library.IFrontPaperService#loadProductPapers(java.lang.String)
	 */
	@Override
	public List<FrontPaperInfo> loadProductPapers(String productId) {
		 if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［productId = %s］下的试卷集合...", productId));
		 Product product = this.productDao.load(Product.class, productId);
		 if(product == null) throw new RuntimeException(String.format("产品［productId = %s］不存在！", productId));
		 return this.changeModel(this.paperReleaseDao.loadReleases(this.buildProductSubjectIds(product.getSubjects()), new String[]{ product.getArea().getId() }));
	}
	//构建产品科目ID数组。
	private String[] buildProductSubjectIds(Set<Subject> subjects){
		if(subjects == null || subjects.size() == 0) return null;
		List<String> list = new ArrayList<>();
		for(Subject subject : subjects){
			if(subject == null) continue;
			list.add(subject.getId());
		}
		return list.toArray(new String[0]);
	}
	//数据模型转换.
	private List<FrontPaperInfo> changeModel(List<PaperRelease> paperReleases){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换  List<PaperRelease> => List<FrontPaperInfo> ...");
		List<FrontPaperInfo> list = new ArrayList<>();
		if(paperReleases != null && paperReleases.size() > 0){
			for(PaperRelease paperRelease : paperReleases){
				FrontPaperInfo info = this.changeModel(paperRelease);
				if(info != null) list.add(info);
			}
		}
		return list;
	}
	//数据模型转换.
	private FrontPaperInfo changeModel(PaperRelease paperRelease){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 PaperRelease => FrontPaperInfo ...");
		if(paperRelease == null || paperRelease.getPaper() == null) return null;
		FrontPaperInfo frontPaperInfo = (FrontPaperInfo)((BasePaperInfo)this.paperService.conversion(paperRelease.getPaper()));
		if(frontPaperInfo != null){
			frontPaperInfo.setId(paperRelease.getId());//重置试卷ID。
			frontPaperInfo.setTotal(paperRelease.getTotal());
		}
		return frontPaperInfo;
	}
	/*
	 * 加载试卷内容。
	 * @see com.examw.test.service.library.IFrontPaperService#loadPaperContent(java.lang.String)
	 */
	@Override
	public PaperPreview loadPaperContent(String paperId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］内容...", paperId));
		if(StringUtils.isEmpty(paperId)) return null;
		PaperRelease paperRelease = this.paperReleaseDao.load(PaperRelease.class, paperId);
		if(paperRelease == null) throw new RuntimeException(String.format("试卷［%s］不存在!", paperId));
		if(StringUtils.isEmpty(paperRelease.getContent())) throw new RuntimeException(String.format("试卷［%s］序列化内容丢失!", paperId));
		return this.mapper.readValue(paperRelease.getContent(), PaperPreview.class);
	}

}