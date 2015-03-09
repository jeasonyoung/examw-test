package com.examw.test.support;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
 

/**
 * Freemaker生成静态页面引擎。
 * 
 * @author yangyong
 * @since 2014年12月23日
 */
public class FreeMakerEngine {
	private static final  Logger logger = Logger.getLogger(FreeMakerEngine.class);
	private Configuration config;
	private ResourceLoader resourceLoader;
	/**
	 * 构造函数。
	 * @param resourceLoader
	 * 资源加载器。
	 */
	public FreeMakerEngine(ResourceLoader resourceLoader){
		if(logger.isDebugEnabled()) logger.debug("构造函数。");
		this.resourceLoader = resourceLoader;
		this.config = new Configuration();
		//设置包装器，并将对象包装为数据模型
		this.config.setObjectWrapper(new DefaultObjectWrapper());
	}
	/**
	 * 设置模版所在目录。
	 * @param templateDir
	 * 模版所在目录。
	 * @throws IOException
	 */
	public void setTemplateDir(String templateDir) throws IOException{
		if(logger.isDebugEnabled()) logger.debug(String.format("注入模版所在目录:%s", templateDir));
		if(StringUtils.isEmpty(templateDir)) return;
		if(this.resourceLoader == null) throw new IllegalArgumentException("未设置资源加载器！");
		Resource resource = this.resourceLoader.getResource(templateDir);
		if(resource.exists()){
			if(logger.isDebugEnabled()) logger.debug("模版目录:" + resource.getFilename());
			//resource.getInputStream();
		}
		this.config.setDirectoryForTemplateLoading(resource.getFile());
	}
	/**
	 * 解析模版。
	 * @param templateName
	 * 模版名称。
	 * @param model
	 * 数据模型。
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public String analysisTemplate(String templateName,Map<String, Object> model) throws IOException, TemplateException{
		if(StringUtils.isEmpty(templateName)) return null;
		Template template = this.config.getTemplate(templateName, "UTF-8");
		
		StringWriter writer = new StringWriter();
		//2015.03.09  添加生成时间
		if(model!=null) model.put("updateTime", new Date());
		template.process(model, writer);  
		
		return  writer.toString();
	}
	
}