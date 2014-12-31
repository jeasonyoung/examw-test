package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.StaticPage;

/**
 * 首页模版处理器。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public class IndexTemplateProcess extends BaseTemplateProcess {
	private static Logger logger = Logger.getLogger(IndexTemplateProcess.class);
	private ICategoryDao categoryDao;
	/**
	 * 设置考试类别数据接口。
	 * @param categoryDao 
	 *	  考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess(com.examw.test.domain.publish.Configuration)
	 */
	@Override
	protected List<StaticPage> templateProcess(Configuration configuration) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("模版处理...");
		List<StaticPage> list = new ArrayList<>();
		StaticPage page = new StaticPage("index","/");
		page.setContent(this.createStaticPageContent(this.buildParameters(configuration)));
		page.setLastTime(new Date());
		list.add(page);
		return list;
	}
	//构建参数集合。
	private Map<String, Object> buildParameters(Configuration configuration){
		//configuration.getCategories();
		
		return null;
	}
}