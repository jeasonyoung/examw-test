package com.examw.test.service.library.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.ISourceDao;
import com.examw.test.domain.library.Source;
import com.examw.test.model.library.SourceInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.ISourceService;

/**
 * 来源服务接口实现。
 * @author yangyong.
 * @since 2014-08-06.
 */
public class SourceServiceImpl extends BaseDataServiceImpl<Source, SourceInfo> implements ISourceService {
	private static final Logger logger = Logger.getLogger(SourceServiceImpl.class);
	private ISourceDao sourceDao;
	/**
	 * 设置来源数据接口。
	 * @param sourceDao 
	 *	 来源数据接口。
	 */
	public void setSourceDao(ISourceDao sourceDao) {
		if(logger.isDebugEnabled()) logger.debug("注入来源数据接口...");
		this.sourceDao = sourceDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Source> find(SourceInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.sourceDao.findSources(info);
	}
	/*
	 * 类型转换.
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SourceInfo changeModel(Source data) {
		if(logger.isDebugEnabled()) logger.debug("类型转换(source -> sourceInfo)...");
		SourceInfo info = new SourceInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SourceInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.sourceDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SourceInfo update(SourceInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Source data = this.sourceDao.load(Source.class, StringUtils.isEmpty(info.getId()) ? null : info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Source();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded) this.sourceDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			Source data = this.sourceDao.load(Source.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除［"+ ids[i] +"］");
				this.sourceDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.service.library.ISourceService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Source> sources = this.find(new SourceInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "desc";}
		});
		if(sources != null && sources.size() > 0){
			return sources.get(0).getCode();
		}
		return null;
	}
}