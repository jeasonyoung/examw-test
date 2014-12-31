package com.examw.test.service.publish.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.service.Status;
import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.dao.publish.IPublishRecordDao;
import com.examw.test.dao.publish.IStaticPageDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.service.publish.ConfigurationTemplateType;
import com.examw.test.service.publish.IPublishService;
import com.examw.test.service.publish.ITemplateProcess;

/**
 * 发布服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public class PublishServiceImpl implements IPublishService {
	private static final Logger logger = Logger.getLogger(PublishServiceImpl.class);
	private IConfigurationDao configurationDao;
	private IPublishRecordDao publishRecordDao;
	private IStaticPageDao staticPageDao;
	private Map<Integer, ITemplateProcess> processes;
	/**
	 * 设置发布配置数据接口。
	 * @param configurationDao 
	 *	  发布配置数据接口。
	 */
	public void setConfigurationDao(IConfigurationDao configurationDao) {
		if(logger.isDebugEnabled()) logger.debug("注入发布配置数据接口...");
		this.configurationDao = configurationDao;
	}
	/**
	 * 设置发布记录数据接口。
	 * @param publishRecordDao 
	 *	  发布记录数据接口。
	 */
	public void setPublishRecordDao(IPublishRecordDao publishRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入发布记录数据接口...");
		this.publishRecordDao = publishRecordDao;
	}
	/**
	 * 设置静态页面数据接口。
	 * @param staticPageDao 
	 *	  静态页面数据接口。
	 */
	public void setStaticPageDao(IStaticPageDao staticPageDao) {
		if(logger.isDebugEnabled()) logger.debug("注入静态页面数据接口...");
		this.staticPageDao = staticPageDao;
	}
	/**
	 * 设置模版处理器集合。
	 * @param processes 
	 *	  模版处理器集合。
	 */
	public void setProcesses(Map<Integer, ITemplateProcess> processes) {
		if(logger.isDebugEnabled()) logger.debug("注入模版处理器集合...");
		this.processes = processes;
	}
	/**
	 * 加载模版类型处理器。
	 * @param templateType
	 * 模版类型枚举。
	 * @return
	 * 模版处理器。
	 */
	protected ITemplateProcess loadProcess(ConfigurationTemplateType templateType){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载模版类型［%s］处理器...", templateType));
		if(templateType == null || this.processes == null || this.processes.size() == 0) return null;
		return this.processes.get(templateType.getValue());
	}
	/*
	 * 更新发布。
	 * @see com.examw.test.service.publish.IPublishService#updatePublish()
	 */
	@Override
	public synchronized void updatePublish() {
		if(logger.isDebugEnabled()) logger.debug("=>开始更新发布....");
		Configuration configuration = this.configurationDao.loadTopConfiguration();
		if(configuration == null){
			if(logger.isDebugEnabled()) logger.debug("未找到需要发布的配置！");
			return;
		}
		ConfigurationTemplateType[] templates = ConfigurationTemplateType.convert(configuration.getTemplate());
		if(templates == null || templates.length == 0){
			if(logger.isDebugEnabled()) logger.debug("未配置需要发布的模版！");
			return;
		}
		PublishRecord record = new PublishRecord(configuration, Status.DISABLE);
		this.publishRecordDao.save(record);//
		String error = null;
		for(ConfigurationTemplateType template : templates){
			ITemplateProcess process = this.loadProcess(template);
			if(process == null){
				if(logger.isDebugEnabled()) logger.debug(String.format("模版［%s］未配置！", template));
				continue;
			}
			try {
				List<StaticPage> listPages = process.process();
				if(listPages == null || listPages.size() == 0) throw new Exception("没有生成静态页面!");
				for(StaticPage page : listPages){
					if(page == null) continue;
					if(StringUtils.isEmpty(page.getId())){
						page.setId(UUID.randomUUID().toString());
					}
					page.setCreateTime(new Date());
					page.setPublish(record);
					this.staticPageDao.saveOrUpdate(page);
				}
			} catch (Exception e) {
				error = e.getMessage();
				logger.error(String.format("处理模版［%1$s］时发生异常：%2$s", templates,  e.getMessage()), e);
			}
		}
		if(!StringUtils.isEmpty(error)){
			record.setName(error);
		}
		record.setStatus(Status.ENABLED.getValue());
		record.setEndTime(new Date());
		//this.publishRecordDao.saveOrUpdate(record);
		
		if(logger.isDebugEnabled()) logger.debug("=>更新发布结束！");
	}
}