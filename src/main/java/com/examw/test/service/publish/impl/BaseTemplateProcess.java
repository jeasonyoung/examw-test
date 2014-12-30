package com.examw.test.service.publish.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.service.publish.ITemplateProcess;
import com.examw.test.support.FreeMakerEngine;

/**
 * 模版处理器基础抽象类。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public abstract class BaseTemplateProcess implements ITemplateProcess {
	private static final Logger logger = Logger.getLogger(BaseTemplateProcess.class);
	private String templatesRoot,templateName;
	private FreeMakerEngine engine;
	/**
	 * 设置模版根目录。
	 * @param templatesRoot 
	 *	  模版根目录。
	 */
	public void setTemplatesRoot(String templatesRoot) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入模版根目录：%s", templatesRoot));
		this.templatesRoot = templatesRoot;
	}
	/**
	 * 设置模版名称。
	 * @param templateName 
	 *	  模版名称。
	 */
	public void setTemplateName(String templateName) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入模版名称：", templateName));
		this.templateName = templateName;
	}
	/*
	 * 模版处理入口。
	 * @see com.examw.test.service.publish.ITemplateProcess#process(com.examw.test.domain.publish.Configuration)
	 */
	@Override
	public List<StaticPage> process(Configuration configuration) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("模版［ root = %s］处理入口...", this.templatesRoot));
		if(configuration == null) return null;
		if(StringUtils.isEmpty(this.templatesRoot)) throw new Exception("未配置模版根路径地址！");
		if(StringUtils.isEmpty(this.templateName)) throw new Exception("未配置模版名称！");
		this.engine = new FreeMakerEngine();
		this.engine.setTemplateDir(this.templatesRoot);
		//模版处理。
		return this.templateProcess(configuration);
	}
	/**
	 * 模版处理。
	 * @param configuration
	 * 发布配置。
	 * @return
	 * 静态页面对象。
	 * @throws Exception
	 */
	protected abstract List<StaticPage> templateProcess(Configuration configuration) throws Exception;
	/**
	 * 生成静态页面内容。
	 * @param parameters
	 * 模版参数。
	 * @return
	 * 页面内容。
	 * @throws Exception
	 */
	protected String createStaticPageContent(Map<String, Object> parameters) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("生成模版［%s］静态页面内容...", this.templateName));
		return this.engine.analysisTemplate(this.templateName, parameters);
	}
}