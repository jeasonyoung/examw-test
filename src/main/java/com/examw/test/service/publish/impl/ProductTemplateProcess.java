package com.examw.test.service.publish.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.products.ProductInfo;
import com.examw.test.service.products.IProductService;
import com.examw.test.service.publish.impl.ExamTemplateProcess.ProductListViewData;

/**
 * 产品模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class ProductTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(ProductTemplateProcess.class);
	private IProductDao productDao;
	private IProductService productService;
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
	 * 设置产品服务接口。
	 * @param productService 
	 *	  产品服务接口。
	 */
	public void setProductService(IProductService productService) {
		if(logger.isDebugEnabled()) logger.debug("注入产品服务接口...");
		this.productService = productService;
	}
	/*
	 * 模版静态化处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess(java.util.Date)
	 */
	@Override
	protected int addTemplateProcess(Date startTime) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("模版处理...");
		List<Product> products = this.productDao.findProducts(new ProductInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return Status.ENABLED.getValue();}
			@Override
			public String getSort() { return "lastTime";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(products == null || products.size() == 0) return 0;
		Map<String, Object>  parameters = new HashMap<>();
		parameters.put("updateTime", startTime);
		//常见问题
		parameters.put("questions", this.loadQuestions());
		int total = 0;
		Exam exam = null;
		for(Product product : products){
			if(product == null) continue;
			exam = product.getExam();
			if(exam == null) continue;
			//最新试卷
			parameters.put("newsPapers", this.loadNewsPapers(exam.getId()));
			//最热试卷
			parameters.put("hotsPapers", this.loadHotsPapers(exam.getId())); 
			//所属考试abbr
			parameters.put("abbr", exam.getAbbr());
			
			ProductDetailViewData p = new ProductDetailViewData();
			p.setExamAbbr(exam.getAbbr());//所属考试EN简称
			p.setExamName(exam.getName());//所属考试名称
			p.setId(product.getId());//产品ID
			p.setText(product.getName());//产品名称
			p.setContent(product.getContent());//产品描述
			p.setTotal(product.getItemTotal());//试题总数
			p.setPrice(product.getPrice());//产品价格
			p.setDiscount(product.getDiscount());//产品优惠价格
			p.setAnalysisTypeName(this.productService.loadAnalysisTypeName(product.getAnalysisType()));//解题思路
			p.setRealTypeName(this.productService.loadRealTypeName(product.getRealType()));//历年真题
			
			parameters.put("product", p);
			
			this.updateStaticPage(String.format("%1$s-%2$s", p.getExamAbbr(), p.getId()), 
					String.format("/%1$s/%2$s.html",p.getExamAbbr(), p.getId()), 
					this.createStaticPageContent(parameters));
			
			total += 1;
		}
		return total;
	}
	/**
	 * 产品详细信息。
	 * 
	 * @author yangyong
	 * @since 2015年1月8日
	 */
	public static class ProductDetailViewData extends ProductListViewData{
		private static final long serialVersionUID = 1L;
		private String examAbbr, examName,content,analysisTypeName,realTypeName;
		/**
		 * 构造函数。
		 * @param id
		 * @param text
		 * @param total
		 * @param price
		 * @param discount
		 */
		public ProductDetailViewData(String id, String text, Integer total, BigDecimal price, BigDecimal discount) {
			super(id, text, total, price, discount);
		}
		/**
		 * 构造函数。
		 */
		public ProductDetailViewData(){}
//		/**
//		 * 构造函数。
//		 * @param id
//		 * 产品ID。
//		 * @param examName
//		 * 所属考试名称。
//		 * @param text
//		 * 产品名称。
//		 * @param content
//		 * 产品描述。
//		 * @param pages
//		 * 包含试卷总数。
//		 * @param total
//		 * 包含试题总数。
//		 * @param price
//		 * 价格。
//		 * @param discount
//		 * 优惠价。
//		 */
//		public ProductDetailViewData(String id, String examName, String text, String content, Integer pages, Integer total, BigDecimal price, BigDecimal discount) {
//			super(id, text, total, price, discount);
//			this.setExamName(examName);
//			this.setContent(content);
//			this.setPages(pages);
//		}
		/**
		 * 获取所属考试EN简称。
		 * @return 所属考试EN简称。
		 */
		public String getExamAbbr() {
			return examAbbr;
		}
		/**
		 * 设置所属考试EN简称。
		 * @param examAbbr 
		 *	  所属考试EN简称。
		 */
		public void setExamAbbr(String examAbbr) {
			this.examAbbr = examAbbr;
		}
		/**
		 * 获取所属考试名称。
		 * @return 所属考试名称。
		 */
		public String getExamName() {
			return examName;
		}
		/**
		 * 设置所属考试名称。
		 * @param examName 
		 *	  所属考试名称。
		 */
		public void setExamName(String examName) {
			this.examName = examName;
		}
		/**
		 * 获取产品介绍。
		 * @return 产品介绍。
		 */
		public String getContent() {
			return content;
		}
		/**
		 * 设置产品介绍。
		 * @param content 
		 *	  产品介绍。
		 */
		public void setContent(String content) {
			this.content = content;
		}
		/**
		 * 获取答案解析类型名称。
		 * @return 答案解析类型名称。
		 */
		public String getAnalysisTypeName() {
			return analysisTypeName;
		}
		/**
		 * 设置答案解析类型名称。
		 * @param analysisTypeName 
		 *	  答案解析类型名称。
		 */
		public void setAnalysisTypeName(String analysisTypeName) {
			this.analysisTypeName = analysisTypeName;
		}
		/**
		 * 获取真题类型名称。
		 * @return 真题类型名称。
		 */
		public String getRealTypeName() {
			return realTypeName;
		}
		/**
		 * 设置真题类型名称。
		 * @param realTypeName 
		 *	  真题类型名称。
		 */
		public void setRealTypeName(String realTypeName) {
			this.realTypeName = realTypeName;
		}
	}
}