package com.examw.test.service.products.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.model.products.SoftwareTypeInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.ISoftwareTypeService;

/**
 * 
 * @author fengwei.
 * @since 2014年8月12日 上午8:29:37.
 */
public class SoftwareTypeServiceImpl extends BaseDataServiceImpl<SoftwareType,SoftwareTypeInfo> implements ISoftwareTypeService{
	private static final Logger logger = Logger.getLogger(SoftwareTypeServiceImpl.class);
	private ISoftwareTypeDao softwareTypeDao;
	
	/**
	 * 设置 软件类型数据接口
	 * @param SoftwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao SoftwareTypeDao) {
		this.softwareTypeDao = SoftwareTypeDao;
	}

	@Override
	protected List<SoftwareType> find(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询[软件类型]数据...");
		return this.softwareTypeDao.findSoftwareTypes(info);
	}

	@Override
	protected SoftwareTypeInfo changeModel(SoftwareType data) {
		if (logger.isDebugEnabled())	logger.debug("[软件类型]数据模型转换...");
		if (data == null)
			return null;
		SoftwareTypeInfo info = new SoftwareTypeInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询统计...");
		return softwareTypeDao.total(info);
	}

	@Override
	public SoftwareTypeInfo update(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		SoftwareType  data = StringUtils.isEmpty(info.getId()) ?  null : this.softwareTypeDao.load(SoftwareType.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new SoftwareType();
		}
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.softwareTypeDao.save(data);
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除数据...");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			SoftwareType data = this.softwareTypeDao.load(SoftwareType.class, ids[i]);
			if (data != null) {
				logger.debug("删除软件类型数据：" + ids[i]);
				this.softwareTypeDao.delete(data);
			}
		}
	}
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<SoftwareType> sources = this.softwareTypeDao.findSoftwareTypes(new SoftwareTypeInfo(){
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
