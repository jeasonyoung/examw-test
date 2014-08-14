package com.examw.test.service.syllabus.impl;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.model.syllabus.PressInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.syllabus.IPressService;
/**
 * 出版社服务接口实现类。
 * @author lq.
 * @since 2014-08-06.
 */
public class PressServiceImpl extends BaseDataServiceImpl<Press, PressInfo> implements IPressService {
	private static final Logger logger = Logger.getLogger(PressServiceImpl.class);
	private IPressDao pressDao;
	/**
	 * 设置出版社数据接口。
	 * @param pressDao
	 * 出版社数据接口。
	 */
	public void setPressDao(IPressDao pressDao) {
		if(logger.isDebugEnabled())logger.debug("注入出版社数据接口...");
		this.pressDao = pressDao;
	}
	/*
	 * 数据查询。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Press> find(PressInfo info) {
		if(logger.isDebugEnabled())logger.debug("数据查询...");
		return this.pressDao.findPresss(info);
	}
	/*
	 * 数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(PressInfo info) {
		if(logger.isDebugEnabled())logger.debug("数据统计...");
		return this.pressDao.total(info);
	}
	/*
	 * 类型装换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected PressInfo changeModel(Press data) {
	    if(logger.isDebugEnabled())logger.debug("类型转换...");
	    if(data == null) return null;
		PressInfo info = new PressInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public PressInfo update(PressInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Press  data = StringUtils.isEmpty(info.getId()) ?  null : this.pressDao.load(Press.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Press();
		}
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.pressDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled())logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Press data = this.pressDao.load(Press.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("删除数据：" + ids[i]);
				this.pressDao.delete(data);
			}
		}
	}
}