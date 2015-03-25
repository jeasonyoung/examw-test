package com.examw.test.service.publish.impl;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

import com.examw.service.Status;
import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.dao.publish.IPublishRecordDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.service.publish.ConfigurationTemplateType;
import com.examw.test.service.publish.IPublishService;
import com.examw.test.service.publish.ITemplateProcess;
import com.examw.test.service.publish.RemotePublishEvent;
import com.examw.utils.HttpUtil;

/**
 * 发布服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public class PublishServiceImpl implements IPublishService,ApplicationListener<RemotePublishEvent> {
	private static final Logger logger = Logger.getLogger(PublishServiceImpl.class);
	private static final int remote_createpage_max_times = 3,remote_create_waiting = 100;
	private IConfigurationDao configurationDao;
	private IPublishRecordDao publishRecordDao;
	private Map<Integer, ITemplateProcess> processes;
	//远程生成页面地址
	private String remoteCreatePagesUrl;
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
	 * 设置模版处理器集合。
	 * @param processes 
	 *	  模版处理器集合。
	 */
	public void setProcesses(Map<Integer, ITemplateProcess> processes) {
		if(logger.isDebugEnabled()) logger.debug("注入模版处理器集合...");
		this.processes = processes;
	}
	/**
	 * 设置 生成页面的远程调用地址
	 * @param remoteCreatePagesUrl
	 *  生成页面的远程调用地址
	 */
	public void setRemoteCreatePagesUrl(String remoteCreatePagesUrl) {
		this.remoteCreatePagesUrl = remoteCreatePagesUrl;
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
		this.updatePublish(this.configurationDao.loadTopConfiguration());
	}
	/*
	 * 更新发布指定配置。
	 * @see com.examw.test.service.publish.IPublishService#updatePublish(java.lang.String)
	 */
	@Override
	public void updatePublish(String configId) {
		if(logger.isDebugEnabled()) logger.debug("更新发布指定配置...");
		this.updatePublish(StringUtils.isEmpty(configId) ? null : this.configurationDao.load(Configuration.class, configId));
	}
	//更新发布。
	private void updatePublish(Configuration configuration){
		String error =  null;
		if(configuration == null){
			error = "未找到需要发布的配置！";
			if(logger.isDebugEnabled()) logger.debug(error);
			throw new RuntimeException(error);
		}
		ConfigurationTemplateType[] templates = ConfigurationTemplateType.convert(configuration.getTemplate());
		int count = 0;
		if(templates == null || (count = templates.length) == 0){
			error = "未配置需要发布的模版！";
			if(logger.isDebugEnabled()) logger.debug(error);
			throw new RuntimeException(error);
		}
		//发布记录
		PublishRecord record = new PublishRecord(configuration, Status.DISABLE);
		this.publishRecordDao.save(record);
		int total = 0;
		for(int i = 0; i < count; i++){
			if(templates[i] == ConfigurationTemplateType.NONE) continue;
			ITemplateProcess process = this.loadProcess(templates[i]);
			if(process == null){
				if(logger.isDebugEnabled()) logger.debug(String.format("模版［%s］未配置！", templates[i]));
				continue;
			}
			try {
				if(i == 0){//第一个模版运行前清空缓存。
					process.cleanCache();
				}
				if(logger.isDebugEnabled()) logger.debug(String.format("开始生成模版［％s］页面...",  templates[i]));
				int pages = process.addProcess(record);
				total += pages;
				if(logger.isDebugEnabled()) logger.debug(String.format("生成模版［%1$s］页面［%2$d］，完成！", templates[i], pages));
			} catch (Exception e) {
				error = e.getMessage();
				logger.error(String.format("处理模版［%1$s］时发生异常：%2$s", templates,  e.getMessage()), e);
			}
			if(i == count - 1){//最后一个模版运行后清空缓存
				process.cleanCache();
			}
		} 
		if(!StringUtils.isEmpty(error)){
			record.setName(error);
		}
		record.setName(String.format("%1$s:%2$d", record.getName(), total));
		record.setStatus(Status.ENABLED.getValue());
		record.setEndTime(new Date());
		this.publishRecordDao.saveOrUpdate(record);
		if(logger.isDebugEnabled()) logger.debug("=>更新发布结束！");
	}
	/*
	 * 响应远程发布事件
	 * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(RemotePublishEvent event) {
		if(event != null && !StringUtils.isEmpty(event.getStaticPageID()) && !StringUtils.isEmpty(this.remoteCreatePagesUrl)){
			String staticPageId = event.getStaticPageID();
			int index = 0;
			while(index < remote_createpage_max_times){
				try {
					Thread.sleep(remote_create_waiting);
					if(logger.isDebugEnabled()) logger.debug(String.format("开始生成[id=%1$s,%2$d]...", staticPageId,index));
					String callback = HttpUtil.sendRequest(String.format(this.remoteCreatePagesUrl, staticPageId), "GET", null);
					if(logger.isDebugEnabled()) logger.debug(String.format("生成[id=%1$s,%2$d]结束:%3$s", staticPageId,index,callback));
					if(!StringUtils.isEmpty(callback) && callback.toLowerCase().contains("succes")){
						break;
					}
				} catch (Exception e) {
					 logger.error(String.format("远程生成页面[id=%1$s][%2$d]发生异常:%3$s", staticPageId, index, e.getMessage()), e);
				}
			}
		}
	}
}