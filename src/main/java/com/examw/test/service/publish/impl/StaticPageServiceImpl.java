package com.examw.test.service.publish.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.publish.IPublishRecordDao;
import com.examw.test.dao.publish.IStaticPageDao;
import com.examw.test.domain.publish.PublishRecord;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.model.publish.StaticPageInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.publish.IStaticPageService;

/**
 * 静态页面服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public class StaticPageServiceImpl extends BaseDataServiceImpl<StaticPage, StaticPageInfo> implements IStaticPageService {
	private static final Logger logger = Logger.getLogger(StaticPageServiceImpl.class);
	private IStaticPageDao staticPageDao;
	private IPublishRecordDao publishRecordDao;
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
	 * 设置发布记录数据接口。
	 * @param publishRecordDao 
	 *	  发布记录数据接口。
	 */
	public void setPublishRecordDao(IPublishRecordDao publishRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入发布记录数据接口...");
		this.publishRecordDao = publishRecordDao;
	}
	/*
	 * 加载静态页面内容。
	 * @see com.examw.test.service.publish.IStaticPageService#loadPageContent(java.lang.String)
	 */
	@Override
	public String loadPageContent(String pageId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载静态页面内容:%s...", pageId)); 
		StaticPage page = StringUtils.isEmpty(pageId) ? null : this.staticPageDao.load(StaticPage.class, pageId);
		if(page == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("静态页面［%s］不存在！", pageId));
			return null;
		}
		return page.getContent();
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<StaticPage> find(StaticPageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.staticPageDao.findPages(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected StaticPageInfo changeModel(StaticPage data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换：StaticPage => StaticPageInfo ...");
		StaticPageInfo info = new StaticPageInfo();
		BeanUtils.copyProperties(data, info);
		PublishRecord p = null;
		if((p = data.getPublish()) != null){
			info.setPublishId(p.getId());
			info.setPublishName(p.getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(StaticPageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.staticPageDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public StaticPageInfo update(StaticPageInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		StaticPage data = StringUtils.isEmpty(info.getId()) ? null  : this.staticPageDao.load(StaticPage.class, info.getId());
		if(isAdded = (data == null)){
			info.setCreateTime(new Date());
			data = new StaticPage();
		}else{
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		BeanUtils.copyProperties(info, data);
		data.setPublish(StringUtils.isEmpty(info.getPublishId()) ?  null : this.publishRecordDao.load(PublishRecord.class, info.getPublishId()));
		 
		if(isAdded) this.staticPageDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			StaticPage data = StringUtils.isEmpty(ids[i]) ? null : this.staticPageDao.load(StaticPage.class, ids[i]);
			if(data != null){
				this.staticPageDao.delete(data);
			}
		}
	}
}