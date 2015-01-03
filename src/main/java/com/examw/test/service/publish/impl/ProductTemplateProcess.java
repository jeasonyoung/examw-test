package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.service.Status;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.model.products.ProductInfo;

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
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
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
		if(products == null || products.size() == 0) return null;
		Map<String, Object>  parametersMap = new HashMap<>();
		//最新试卷
		parametersMap.put("newsPapers", this.loadNewsPapers());
		//最热试卷
		parametersMap.put("hotsPapers", this.loadHotsPapers());
		//常见问题
		parametersMap.put("questions", this.loadQuestions());
		
		List<StaticPage> list = new ArrayList<>();
		for(Product product : products){
			if(product == null) continue;
			Map<String, Object> parameters = new HashMap<>();
			parameters.putAll(parametersMap);
			parameters.put("product", product);
			
			StaticPage page = new StaticPage(String.format("index-products-%s", product.getId()),"/products");
			page.setContent(this.createStaticPageContent(parameters));
			page.setLastTime(new Date());
			
			list.add(page);
		}
		return list;
	}

}