package com.examw.test.service.products.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IChannelDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.model.products.ChannelInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IChannelService;

/**
 * 渠道服务接口实现类。
 * @author fengwei.
 * @since 2014年8月11日 下午4:12:14.
 */
public class ChannelServiceImpl extends BaseDataServiceImpl<Channel,ChannelInfo> implements IChannelService{
	private static final Logger logger = Logger.getLogger(ChannelServiceImpl.class);
	private IChannelDao channelDao;
	/**
	 * 设置渠道数据接口。
	 * @param channelDao
	 *  渠道数据接口。
	 */
	public void setChannelDao(IChannelDao channelDao) {
		if(logger.isDebugEnabled()) logger.debug("注入渠道数据接口...");
		this.channelDao = channelDao;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Channel> find(ChannelInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询[渠道]数据...");
		return this.channelDao.findChannels(info);
	}
	/*
	 * 数据模型转化
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ChannelInfo changeModel(Channel data) {
		if (logger.isDebugEnabled())logger.debug("[渠道]数据模型转换...");
		if (data == null) return null;
		ChannelInfo info = new ChannelInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}
	/*
	 * 查询数据统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ChannelInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询统计...");
		return channelDao.total(info);
	}
	/*
	 * 插入更新数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ChannelInfo update(ChannelInfo info) {
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Channel  data = StringUtils.isEmpty(info.getId()) ?  null : this.channelDao.load(Channel.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Channel();
		}
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.channelDao.save(data);
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())logger.debug(String.format("删除数据: %s...", Arrays.toString(ids)));
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i])) continue;
			Channel data = this.channelDao.load(Channel.class, ids[i]);
			if (data != null) {
				logger.debug("删除渠道数据：" + ids[i]);
				this.channelDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大代码值
	 * @see com.examw.test.service.products.IChannelService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		return this.channelDao.loadMaxCode();
	}
	/*
	 * 查询全部渠道数据。
	 * @see com.examw.test.service.products.IChannelService#loadAll()
	 */
	@Override
	public List<ChannelInfo> loadAll() {
		if(logger.isDebugEnabled()) logger.debug("查询全部渠道数据...");
		return this.changeModel(this.channelDao.findChannels(new ChannelInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSort() { return "code";}
			@Override
			public String getOrder() { return "asc";}
		}));
	}
}