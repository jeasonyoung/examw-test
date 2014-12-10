package com.examw.test.service.products.impl;

import java.util.Arrays;
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
 * 软件类型服务接口实现类。
 * @author fengwei.
 * @since 2014年8月12日 上午8:29:37.
 */
public class SoftwareTypeServiceImpl extends BaseDataServiceImpl<SoftwareType,SoftwareTypeInfo> implements ISoftwareTypeService{
	private static final Logger logger = Logger.getLogger(SoftwareTypeServiceImpl.class);
	private ISoftwareTypeDao softwareTypeDao;
	/**
	 * 设置软件类型数据接口。
	 * @param SoftwareTypeDao
	 * 软件类型数据接口。
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao SoftwareTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入软件类型数据接口...");
		this.softwareTypeDao = SoftwareTypeDao;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<SoftwareType> find(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.softwareTypeDao.findSoftwareTypes(info);
	}
	/*
	 * 加载全部类型数据。
	 * @see com.examw.test.service.products.ISoftwareTypeService#loadAllSoftwareTypes()
	 */
	@Override
	public List<SoftwareTypeInfo> loadAllSoftwareTypes() {
		if(logger.isDebugEnabled()) logger.debug("加载全部类型数据...");
		return this.changeModel(this.softwareTypeDao.findSoftwareTypes(new SoftwareTypeInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort(){ return "code"; }
			@Override
			public String getOrder() { return "asc"; }
		}));
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SoftwareTypeInfo changeModel(SoftwareType data) {
		if (logger.isDebugEnabled()) logger.debug("数据模型转换  SoftwareType => SoftwareTypeInfo ...");
		if (data == null) return null;
		SoftwareTypeInfo info = new SoftwareTypeInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return softwareTypeDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SoftwareTypeInfo update(SoftwareTypeInfo info) {
		if (logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		SoftwareType data = StringUtils.isEmpty(info.getId()) ? null : this.softwareTypeDao.load(SoftwareType.class, info.getId());
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
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i])) continue;
			SoftwareType data = this.softwareTypeDao.load(SoftwareType.class, ids[i]);
			if (data != null) {
				logger.debug("删除软件类型数据：" + ids[i]);
				this.softwareTypeDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大代码值。
	 * @see com.examw.test.service.products.ISoftwareTypeService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.softwareTypeDao.loadMaxCode();
	}
}