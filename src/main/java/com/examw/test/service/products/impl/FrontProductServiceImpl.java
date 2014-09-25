package com.examw.test.service.products.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.products.FrontProductInfo;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.library.IFrontPaperService;
import com.examw.test.service.products.IFrontProductService;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.settings.IAreaService;
import com.examw.test.service.settings.ISubjectService;

/**
 * 前端产品服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年9月25日
 */
public class FrontProductServiceImpl implements IFrontProductService {
	private static Logger logger = Logger.getLogger(FrontProductServiceImpl.class);
	private IProductService productService;
	private ISubjectService subjectService;
	private IAreaService areaService;
	private IFrontPaperService frontPaperService;
	/**
	 * 设置产品服务接口。
	 * @param productService 
	 *	  产品服务接口。
	 */
	public void setProductService(IProductService productService) {
		if(logger.isDebugEnabled()) logger.debug("注入产品服务接口...");
		this.productService = productService;
	}
	/**
	 * 设置科目服务接口。
	 * @param subjectService 
	 *	 科目服务接口。
	 */
	public void setSubjectService(ISubjectService subjectService) {
		this.subjectService = subjectService;
	}
	/**
	 * 设置地区服务接口。
	 * @param areaService 
	 *	  地区服务接口。
	 */
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	/**
	 * 设置试卷前端服务接口。
	 * @param frontPaperService 
	 *	  试卷前端服务接口。
	 */
	public void setFrontPaperService(IFrontPaperService frontPaperService) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷前端服务接口...");
		this.frontPaperService = frontPaperService;
	}
	/*
	 * 加载考试下的全部产品。
	 * @see com.examw.test.service.products.IFrontProductService#loadProducts(java.lang.String)
	 */
	@Override
	public List<FrontProductInfo> loadProducts(final String examId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载考试[examId = %s]下的全部产品...", examId));
		List<ProductInfo> rows = this.productService.datagrid(new ProductInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getExamId() { return examId;}
			@Override
			public Integer getStatus() {return Product.STATUS_NONE;}	//状态必须正常
			@Override
			public String getSort() {return "code"; }
		}).getRows();
		return this.changeModel(rows);
	}
	// 数据模型转换。
	private List<FrontProductInfo> changeModel(List<ProductInfo> products){
		if(logger.isDebugEnabled()) logger.debug("数据模型集合转换 List<ProductInfo> => List<FrontProductInfo>...");
		List<FrontProductInfo> targets = new ArrayList<>();
		if(products != null && products.size() > 0){
			for(ProductInfo product : products){
				if(product == null) continue;
				FrontProductInfo info = this.changeModel(product);
				if(info != null){
					targets.add(info);
				}
			}
		}
		if(targets.size() > 0){
			Collections.sort(targets);//排序
		}
		return targets;
	}
	//数据模型转换。
	private FrontProductInfo changeModel(ProductInfo product){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 ProductInfo => FrontProductInfo ");
		if(product == null) return null;
		FrontProductInfo info = (FrontProductInfo) product;
		info.setPaperCount(this.frontPaperService.loadPapersCount(info.getSubjectId()));
		info.setItemCount(this.frontPaperService.loadItemsCount(info.getSubjectId()));
		info.setHasRealItem(this.frontPaperService.hasRealItem(info.getSubjectId()));
		return info;
	}
	/*
	 * 加载产品信息。
	 * @see com.examw.test.service.products.IFrontProductService#loadProduct(java.lang.String)
	 */
	@Override
	public FrontProductInfo loadProduct(String productId) {
		if(logger.isDebugEnabled())logger.debug(String.format("加载产品［%s］...", productId));
		if(StringUtils.isEmpty(productId)) return null;
		return this.changeModel((FrontProductInfo)this.productService.conversion(this.productService.loadProduct(productId)));
	}
	/*
	 * 加载产品科目数据集合。
	 * @see com.examw.test.service.products.IFrontProductService#loadProductSubjects(java.lang.String)
	 */
	@Override
	public List<SubjectInfo> loadProductSubjects(String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［productId = %s］下的科目数据集合", productId));
		if(StringUtils.isEmpty(productId)) return null;
		Product product = this.productService.loadProduct(productId);
		if(product == null) throw new RuntimeException(String.format("产品［%s］不存在！", productId));
		return this.changeModel(product.getSubjects());
	}
	//数据模型转换。
	private List<SubjectInfo> changeModel(Set<Subject> subjects){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Set<Subject> => List<SubjectInfo> ...");
		List<SubjectInfo> list = new ArrayList<>();
		if(subjects != null && subjects.size() > 0){
			for(Subject subject : subjects){
				if(subject == null) continue;
				SubjectInfo info = this.subjectService.conversion(subject);
				if(info != null) list.add(info);
			}
		}
		if(list.size() > 0) Collections.sort(list);
		return list;
	}
	/*
	 * 加载产品地区数据集合。
	 * @see com.examw.test.service.products.IFrontProductService#loadProductAreas(java.lang.String)
	 */
	@Override
	public List<AreaInfo> loadProductAreas(String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载产品［%s］地区数据集合...", productId));
		if(StringUtils.isEmpty(productId)) return null;
		Product product = this.productService.loadProduct(productId);
		if(product == null) throw new RuntimeException(String.format("产品［%s］不存在！", productId));
		return this.changeModelArea(product.getExam().getAreas());
	}
	//数据模型转换。
	private List<AreaInfo> changeModelArea(Set<Area> areas){
		List<AreaInfo> list = new ArrayList<>();
		if(areas != null && areas.size() > 0){
			for(Area area : areas){
				if(area == null) continue;
				AreaInfo info = this.areaService.conversion(area);
				if(info != null) list.add(info);
			}
		}
		if(list.size() > 0){
			Collections.sort(list);
		}
		return list;
	}
}