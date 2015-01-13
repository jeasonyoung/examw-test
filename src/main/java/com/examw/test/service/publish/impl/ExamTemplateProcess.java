package com.examw.test.service.publish.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.settings.ExamInfo;

/**
 * 考试模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class ExamTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(ExamTemplateProcess.class);
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
	/*
	 *  模版静态化处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#addTemplateProcess()
	 */
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("考试模版处理...");
		List<Exam> exams = this.examDao.findExams(new ExamInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() { return Status.ENABLED.getValue();}
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() { return "desc";}
		});
		if(exams == null || exams.size()  == 0) return 0;
		
		Map<String, Object>  parameters = new HashMap<>();
		//最新试卷
		parameters.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parameters.put("hotsPapers", this.loadHotsPapers());
		//常见问题
		parameters.put("questions", this.loadQuestions());
		
		int total = 0;
		for(Exam exam : exams){
			if(exam == null) continue;
			parameters.put("abbr", exam.getAbbr());//考试简称
			parameters.put("examName", exam.getName());//考试名称
			List<ProductListViewData> products = null;
			if(exam.getProducts() != null && exam.getProducts().size() > 0){
				products = new ArrayList<>();
				for(Product p : exam.getProducts()){
					if(p == null || p.getStatus() == Status.DISABLE.getValue()) continue;
					products.add(new ProductListViewData(p.getId(),p.getName(), p.getItemTotal(), p.getPrice(), p.getDiscount()));
				}
			}
			parameters.put("products", products);
			String abbr = exam.getAbbr();
			this.updateStaticPage(String.format("%s-index", abbr), String.format("/%s/index.html", abbr), this.createStaticPageContent(parameters));
			total += 1;
		}
		return total;
	}
	/**
	 * 产品显示数据。
	 * 
	 * @author yangyong
	 * @since 2015年1月6日
	 */
	public static class ProductListViewData extends ViewListData{
		private static final long serialVersionUID = 1L;
		private BigDecimal price,discount;
		/**
		 * 构造函数。
		 * @param id
		 * 产品ID。
		 * @param text
		 * 产品名称。
		 * @param total
		 * 试题数量。
		 * @param price
		 * 产品价格。
		 * @param discount
		 * 优惠价。
		 */
		public ProductListViewData(String id, String text, Integer total, BigDecimal price, BigDecimal discount) {
			super(id, text, total);
			this.setPrice(price);
			this.setDiscount(discount);
		}
		/**
		 * 获取定价。
		 * @return 定价。
		 */
		public BigDecimal getPrice() {
			return price;
		}
		/**
		 * 设置定价。
		 * @param price 
		 *	 定价。
		 */
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		/**
		 * 获取优惠价。
		 * @return 优惠价。
		 */
		public BigDecimal getDiscount() {
			return discount;
		}
		/**
		 * 设置优惠价。
		 * @param discount 
		 *	  优惠价。
		 */
		public void setDiscount(BigDecimal discount) {
			this.discount = discount;
		}
	}
}