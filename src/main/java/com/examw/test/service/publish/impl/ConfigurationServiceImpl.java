package com.examw.test.service.publish.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.publish.IConfigurationDao;
import com.examw.test.dao.settings.ICategoryDao;
import com.examw.test.dao.settings.IExamDao;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.settings.Category;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.publish.ConfigurationInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
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
	private ICategoryDao categoryDao;
	private IExamDao examDao;
	private IProductDao productDao;
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
	 * 设置考试类别数据接口。
	 * @param categoryDao 
	 *	  考试类别数据接口。
	 */
	public void setCategoryDao(ICategoryDao categoryDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试类别数据接口...");
		this.categoryDao = categoryDao;
	}
	/**
	 * 设置考试数据接口。
	 * @param examDao 
	 *	  考试数据接口。
	 */
	public void setExamDao(IExamDao examDao) {
		if(logger.isDebugEnabled()) logger.debug("注入考试数据接口...");
		this.examDao = examDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
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
		if((template & Configuration.TEMPLATE_TYPE_HOME) == Configuration.TEMPLATE_TYPE_HOME){//主页
			list.add(Configuration.TEMPLATE_TYPE_HOME);
		}
		if((template & Configuration.TEMPLATE_TYPE_EXAM) == Configuration.TEMPLATE_TYPE_EXAM){//考试
			list.add(Configuration.TEMPLATE_TYPE_EXAM);
		}
		if((template & Configuration.TEMPLATE_TYPE_PRODUCT) == Configuration.TEMPLATE_TYPE_PRODUCT){//产品
			list.add(Configuration.TEMPLATE_TYPE_PRODUCT);
		}
		if((template & Configuration.TEMPLATE_TYPE_PAPERLIST) == Configuration.TEMPLATE_TYPE_PAPERLIST){//试卷列表
			list.add(Configuration.TEMPLATE_TYPE_PAPERLIST);
		}
		if((template & Configuration.TEMPLATE_TYPE_PAPERDETAIL) == Configuration.TEMPLATE_TYPE_PAPERDETAIL){//试卷详细
			list.add(Configuration.TEMPLATE_TYPE_PAPERDETAIL);
		}
		return list.toArray(new Integer[0]);
	}
	//合并模版值
	private Integer mergeTemplateValues(Integer[] templates){
		if(templates == null || templates.length == 0) return null;
		int result = Configuration.TEMPLATE_TYPE_NONE;
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
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ConfigurationInfo changeModel(Configuration data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换：Configuration => ConfigurationInfo...");
		ConfigurationInfo info = new ConfigurationInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getCategories() != null){//考试类别
			List<String> categoryIds = new ArrayList<>(), categoryNames = new ArrayList<>();
			for(Category category : data.getCategories()){
				if(category == null) continue;
				categoryIds.add(category.getId());
				categoryNames.add(category.getName());
			}
			info.setCategoryId(categoryIds.toArray(new String[0]));
			info.setCategoryName(categoryNames.toArray(new String[0]));
			info.setCategoriesCount(categoryIds.size());
		}
		if(data.getExams() != null){//考试
			List<String> examIds = new ArrayList<>(), examNames = new ArrayList<>();
			for(Exam exam : data.getExams()){
				if(exam == null) continue;
				examIds.add(exam.getId());
				examNames.add(exam.getName());
			}
			info.setExamId(examIds.toArray(new String[0]));
			info.setExamName(examNames.toArray(new String[0]));
			info.setExamsCount(examIds.size());
		}
		if(data.getProducts() != null){//产品
			List<String> productIds = new ArrayList<>(),productNames = new ArrayList<>();
			for(Product product : data.getProducts()){
				if(product == null) continue;
				productIds.add(product.getId());
				productNames.add(product.getName());
			}
			info.setProductId(productIds.toArray(new String[0]));
			info.setProductName(productNames.toArray(new String[0]));
			info.setProductsCount(productIds.size());
		}
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
		BeanUtils.copyProperties(info, data);
		data.setTemplate(this.mergeTemplateValues(info.getTemplates()));
		//考试类别
		Set<Category> categories = null;
		if(info.getCategoryId() != null && info.getCategoryId().length > 0){
			categories = new HashSet<>();
			for(String id : info.getCategoryId()){ 
				Category category = StringUtils.isEmpty(id) ? null : this.categoryDao.load(Category.class, id);
				if(category != null){
					categories.add(category);
				}
			}
		}
		data.setCategories(categories);
		//考试
		Set<Exam> exams = null;
		if(info.getExamId() != null && info.getExamId().length > 0){
			exams = new HashSet<>();
			for(String id : info.getExamId()){
				Exam exam = StringUtils.isEmpty(id) ? null : this.examDao.load(Exam.class, id);
				if(exam != null){
					exams.add(exam);
				}
			}
		}
		data.setExams(exams);
		//产品
		Set<Product> products = null;
		if(info.getProductId() != null && info.getProductId().length > 0){
			products = new HashSet<>();
			for(String id :  info.getProductId()){
				Product product = StringUtils.isEmpty(id) ? null : this.productDao.load(Product.class, id);
				if(product != null){
					products.add(product);
				}
			}
		}
		data.setProducts(products);
		
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
}