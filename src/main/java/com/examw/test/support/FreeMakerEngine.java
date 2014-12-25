package com.examw.test.support;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
	private Configuration config;
	/**
	 *  构造函数。
	 */
	public FreeMakerEngine(){
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
		if(StringUtils.isEmpty(templateDir)) return;
		Resource resource = new ClassPathResource(templateDir);
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
		template.process(model, writer);  
		
		return  writer.toString();
	}
	
}