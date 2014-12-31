package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.model.publish.ConfigurationInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.publish.ConfigurationTemplateType;
import com.examw.test.service.publish.IConfigurationService;
import com.examw.utils.StringUtil;

/**
 * 发布配置服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public class ConfigurationServiceImpl extends BaseDataServiceImpl<Configuration, ConfigurationInfo> implements IConfigurationService {
	private static final Logger logger = Logger.getLogger(ConfigurationServiceImpl.class);
	private IConfigurationDao configurationDao;
	private Map<Integer, String> statusMap,templateMap;
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
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/**
	 * 设置模版值名称集合。
	 * @param templateMap 
	 *	  模版值名称集合。
	 */
	public void setTemplateMap(Map<Integer, String> templateMap) {
		if(logger.isDebugEnabled()) logger.debug("模版值名称集合...");
		this.templateMap = templateMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.publish.IConfigurationService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 加载模版名称。
	 * @see com.examw.test.service.publish.IConfigurationService#loadTemplateName(java.lang.Integer)
	 */
	@Override
	public String loadTemplateName(Integer template) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载模版值［%d］名称...", template));
		if(template == null || this.templateMap == null || this.templateMap.size() == 0) return null;
		return this.templateMap.get(template);
	}
	//拼接模版名称
	private String joinTemplateName(Integer[] templates){
		if(templates == null || templates.length == 0) return null;
		List<String> list = new ArrayList<>();
		for(int i = 0; i < templates.length; i++){
			String name = this.loadTemplateName(templates[i]);
			if(!StringUtils.isEmpty(name)){
				list.add(name);
			}
		}
		return list.size() == 0 ? null : StringUtil.join(list.toArray(new String[0]), ',');
	}
	//分离模版值
	private Integer[] splitTemplateValue(Integer template){
		List<Integer> list = new ArrayList<>();
		if((template & ConfigurationTemplateType.HOME.getValue()) == ConfigurationTemplateType.HOME.getValue()){//首页
			list.add(ConfigurationTemplateType.HOME.getValue());
		}
		if((template & ConfigurationTemplateType.EXAM.getValue()) == ConfigurationTemplateType.EXAM.getValue()){//考试
			list.add(ConfigurationTemplateType.EXAM.getValue());
		}
		if((template & ConfigurationTemplateType.PRODUCT.getValue()) == ConfigurationTemplateType.PRODUCT.getValue()){//产品
			list.add(ConfigurationTemplateType.PRODUCT.getValue());
		}
		if((template & ConfigurationTemplateType.PAPERLIST.getValue()) == ConfigurationTemplateType.PAPERLIST.getValue()){//试卷列表
			list.add(ConfigurationTemplateType.PAPERLIST.getValue());
		}
		if((template & ConfigurationTemplateType.PAPERDETAIL.getValue()) == ConfigurationTemplateType.PAPERDETAIL.getValue()){//试卷详细
			list.add(ConfigurationTemplateType.PAPERDETAIL.getValue());
		}
		return list.toArray(new Integer[0]);
	}
	//合并模版值
	private Integer mergeTemplateValues(Integer[] templates){
		if(templates == null || templates.length == 0) return null;
		int result = ConfigurationTemplateType.NONE.getValue();
		for(int i = 0; i < templates.length; i++){
			if(templates[i] == null) continue;
			result |= templates[i];
		}
		return result;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Configuration> find(ConfigurationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.configurationDao.findConfigurations(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.publish.IConfigurationService#convert(com.examw.test.domain.publish.Configuration)
	 */
	@Override
	public ConfigurationInfo convert(Configuration configuration) {
		return this.changeModel(configuration);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ConfigurationInfo changeModel(Configuration data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换：Configuration => ConfigurationInfo...");
		if(data == null) return null;
		ConfigurationInfo info = new ConfigurationInfo();
		BeanUtils.copyProperties(data, info);
		//状态名称
		info.setStatusName(this.loadStatusName(info.getStatus()));
		//模版
		if(data.getTemplate() != null){
			info.setTemplates(this.splitTemplateValue(data.getTemplate()));
			info.setTemplateName(this.joinTemplateName(info.getTemplates()));
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ConfigurationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.configurationDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ConfigurationInfo update(ConfigurationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		Configuration data = StringUtils.isEmpty(info.getId()) ? null : this.configurationDao.load(Configuration.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Configuration();
		}else{
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		data.setTemplate(this.mergeTemplateValues(info.getTemplates()));
		
		if(isAdded) this.configurationDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Configuration data = StringUtils.isEmpty(ids[i]) ? null : this.configurationDao.load(Configuration.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.configurationDao.delete(data);
			}
		}
	}
	/*
	 * 加载发布配置。
	 * @see com.examw.test.service.publish.IConfigurationService#loadConfiguration(java.lang.String)
	 */
	@Override
	public Configuration loadConfiguration(String configurationId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载发布配置［id = %s］...", configurationId));
		return StringUtils.isEmpty(configurationId) ? null :  this.configurationDao.load(Configuration.class, configurationId);
	}
}