package com.examw.test.service.records.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.dao.records.IFeedBackDao;
import com.examw.test.domain.records.FeedBack;
import com.examw.test.model.records.FeedBackInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.records.IFeedBackService;

/**
 * 意见反馈服务实现类
 * @author fengwei.
 * @since 2015年1月12日 下午4:58:47.
 */
public class FeedBackServiceImpl extends BaseDataServiceImpl<FeedBack, FeedBackInfo> implements IFeedBackService {
	private static final Logger logger = Logger.getLogger(FeedBackServiceImpl.class);
	private IFeedBackDao FeedBackDao;
	private ISoftwareTypeDao softwareTypeDao;
	/**
	 * 设置常见问题数据接口。
	 * @param FeedBackDao 
	 *	  常见问题数据接口。
	 */
	public void setFeedBackDao(IFeedBackDao FeedBackDao) {
		if(logger.isDebugEnabled()) logger.debug("注入常见问题数据接口...");
		this.FeedBackDao = FeedBackDao;
	}
	/**
	 * 设置 软件类型
	 * @param softwareTypeDao
	 * 软件类型
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}

	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<FeedBack> find(FeedBackInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.FeedBackDao.findFeedBacks(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected FeedBackInfo changeModel(FeedBack data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 FeedBack => FeedBackInfo ...");
		FeedBackInfo info = new FeedBackInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getTerminal() != null){//终端
			info.setTerminalCode(data.getTerminal().getCode());
			info.setTerminalName(data.getTerminal().getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(FeedBackInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.FeedBackDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public FeedBackInfo update(FeedBackInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		boolean isAdded = false;
		FeedBack data = StringUtils.isEmpty(info.getId()) ? null : this.FeedBackDao.load(FeedBack.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new FeedBack();
		}
		info.setCreateTime(new Date());
		BeanUtils.copyProperties(info, data);
		//终端
		if(info.getTerminalCode() != null) data.setTerminal(this.softwareTypeDao.load(info.getTerminalCode()));
		
		if(isAdded) this.FeedBackDao.save(data);
		
		return this.changeModel(data);
	}
	/*
	 * 数据删除。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("数据删除:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			FeedBack data = this.FeedBackDao.load(FeedBack.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s", ids[i]));
				this.FeedBackDao.delete(data);
			}
		}
	}
}