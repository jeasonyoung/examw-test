package com.examw.test.service.settings.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.settings.IAreaDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.settings.IAreaService;

/**
 * 行政地区服务数据接口实现类
 * @author fengwei.
 * @since 2014年8月6日 下午1:49:39.
 */
public class AreaServiceImpl extends BaseDataServiceImpl<Area, AreaInfo>
		implements IAreaService {
	private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);
	private IAreaDao areaDao;

	/**
	 * 设置 地区数据接口
	 * 
	 * @param areaDao
	 * 
	 */
	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
	}

	/**
	 * 根据条件查找地区集合
	 */
	@Override
	protected List<Area> find(AreaInfo info) {
		if (logger.isDebugEnabled())
			logger.debug("查询[地区]数据...");
		return areaDao.findAreas(info);
	}

	/**
	 * 模型转换
	 */
	@Override
	protected AreaInfo changeModel(Area data) {
		if (logger.isDebugEnabled())	logger.debug("[地区]数据模型转换...");
		if (data == null)
			return null;
		AreaInfo info = new AreaInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	/**
	 * 地区总数
	 */
	@Override
	protected Long total(AreaInfo info) {
		if (logger.isDebugEnabled())	logger.debug("查询统计...");
		return areaDao.total(info);
	}

	/**
	 * 地区修改更新
	 */
	@Override
	public AreaInfo update(AreaInfo info) {
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Area  data = StringUtils.isEmpty(info.getId()) ?  null : this.areaDao.load(Area.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Area();
		}
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.areaDao.save(data);
		return info;
	}

	/**
	 * 删除地区
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())
			logger.debug("删除数据...");
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			Area data = this.areaDao.load(Area.class, ids[i]);
			if (data != null) {
				logger.debug("删除地区数据：" + ids[i]);
				this.areaDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大的代码值
	 * @see com.examw.test.service.settings.IAreaService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Area> sources = this.areaDao.findAreas(new AreaInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() {return "code"; } 
			@Override
			public String getOrder() { return "desc";}
		});
		if(sources != null && sources.size() > 0){
			return new Integer(sources.get(0).getCode());
		}
		return null;
	}

}
