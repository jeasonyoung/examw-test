package com.examw.test.service.publish.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.products.Product;
import com.examw.test.model.products.ProductInfo;
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
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/*
	 * 模版静态化处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
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
		//最新试卷
		parameters.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parameters.put("hotsPapers", this.loadHotsPapers());
		//常见问题
		parameters.put("questions", this.loadQuestions());
		int total = 0;
		for(Product product : products){
			if(product == null) continue;
			//parameters.put("abbr", product.getExam().getAbbr());
			parameters.put("product", new ProductDetailViewData(product.getId(), (product.getExam() == null ? "" : product.getExam().getName()), 
													product.getName(), product.getContent(), product.getPaperTotal(), product.getItemTotal(), product.getPrice(), product.getDiscount()));
			String abbr = product.getExam().getAbbr();
			
			this.updateStaticPage(String.format("%1$s-%2$s",abbr, product.getId()), 
					String.format("/%1$s/%2$s.html",abbr, product.getId()), 
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
		private String examName,content;
		private Integer pages;
		/**
		 * 构造函数。
		 * @param id
		 * 产品ID。
		 * @param examName
		 * 所属考试名称。
		 * @param text
		 * 产品名称。
		 * @param content
		 * 产品描述。
		 * @param pages
		 * 包含试卷总数。
		 * @param total
		 * 包含试题总数。
		 * @param price
		 * 价格。
		 * @param discount
		 * 优惠价。
		 */
		public ProductDetailViewData(String id, String examName, String text, String content, Integer pages, Integer total, BigDecimal price, BigDecimal discount) {
			super(id, text, total, price, discount);
			this.setExamName(examName);
			this.setContent(content);
			this.setPages(pages);
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
		 * 获取试卷总数。
		 * @return 试卷总数。
		 */
		public Integer getPages() {
			return pages;
		}
		/**
		 * 设置试卷总数。
		 * @param pages 
		 *	  试卷总数。
		 */
		public void setPages(Integer pages) {
			this.pages = (pages == null ? 0 : pages);
		}
	}
}