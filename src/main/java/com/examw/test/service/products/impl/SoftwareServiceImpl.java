package com.examw.test.service.products.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IChannelDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.products.ISoftwareDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Software;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.products.SoftwareInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.ISoftwareService;

/**
 * 产品软件服务接口实现类
 * @author fengwei.
 * @since 2014年8月13日 上午11:44:26.
 */
public class SoftwareServiceImpl extends BaseDataServiceImpl<Software,SoftwareInfo> implements ISoftwareService{
	private static final Logger logger = Logger.getLogger(SoftwareServiceImpl.class);
	private ISoftwareDao softwareDao;
	private IChannelDao channelDao;
	private IProductDao productDao;
	private ISoftwareTypeDao softwareTypeDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置 产品软件数据接口
	 * @param softwareDao
	 * 
	 */
	public void setSoftwareDao(ISoftwareDao softwareDao) {
		this.softwareDao = softwareDao;
	}
	/**
	 * 设置 渠道数据接口
	 * @param channelDao
	 * 
	 */
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}
	/**
	 * 设置 产品数据接口
	 * @param productDao
	 * 
	 */
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	/**
	 * 设置 产品软件类型数据接口
	 * @param softwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置 状态名称类型
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称
	 * @see com.examw.test.service.products.ISoftwareService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || statusMap ==null)
		return null;
		return statusMap.get(status);
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Software> find(SoftwareInfo info) {
		return this.softwareDao.findSoftwares(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SoftwareInfo changeModel(Software data) {
		if (logger.isDebugEnabled())	logger.debug("[产品]数据模型转换...");
		if (data == null)
			return null;
		SoftwareInfo info = new SoftwareInfo();
		BeanUtils.copyProperties(data, info);
		//所属产品
		if(data.getProduct()!=null){
			info.setProductId(data.getProduct().getId());
			info.setProductName(data.getProduct().getName());
			Exam e = data.getProduct().getExam();
			if(e!=null){
				info.setExamId(e.getId());
				info.setExamName(e.getName());
			}
		}
		//渠道
		if(data.getChannel()!=null){
			info.setChannelId(data.getChannel().getId());
			info.setChannelName(data.getChannel().getName());
		}
		//类型
		if(data.getType()!=null){
			info.setSoftTypeId(data.getType().getId());
			info.setSoftTypeName(data.getType().getName());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SoftwareInfo info) {
		return this.softwareDao.total(info);
	}
	/*
	 * 插入or更新数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SoftwareInfo update(SoftwareInfo info) {
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Software  data = StringUtils.isEmpty(info.getId()) ?  null : this.softwareDao.load(Software.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Software();
			data.setCreateTime(new Date());
		}
		info.setCreateTime(data.getCreateTime());
		BeanUtils.copyProperties(info, data);
		if(data.getStatus() == null){
			data.setStatus(Product.STATUS_NONE);
			info.setStatus(Product.STATUS_NONE);
		}
		if(info.getProductId()!=null && (data.getProduct()==null || !data.getProduct().getId().equalsIgnoreCase(info.getProductId()))){
			Product product = this.productDao.load(Product.class, info.getProductId());
			if(product!=null)
			{
				data.setProduct(product);
				info.setProductId(product.getId());
				info.setProductName(product.getName());
				if(product.getExam()!=null)
				{
					info.setExamId(product.getExam().getId());
					info.setExamName(product.getExam().getName());
				}
			}
		}
		if(info.getChannelId()!=null && (data.getChannel()==null || !data.getChannel().getId().equalsIgnoreCase(info.getChannelId()))){
			Channel channel = this.channelDao.load(Channel.class, info.getChannelId());
			if(channel!=null)
			{
				data.setChannel(channel);
				info.setChannelId(channel.getId());
				info.setChannelName(channel.getName());
			}
		}
		if(info.getSoftTypeId()!=null && (data.getType()==null || !data.getType().getId().equalsIgnoreCase(info.getSoftTypeId()))){
			SoftwareType type = this.softwareTypeDao.load(SoftwareType.class, info.getSoftTypeId());
			if(type!=null)
			{
				data.setType(type);
				info.setSoftTypeId(type.getId());
				info.setSoftTypeName(type.getName());
			}
		}
		//新增数据。
		if(isAdded) this.softwareDao.save(data);
		else{
			data.setLastTime(new Date());
			info.setLastTime(new Date());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
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
			Software data = this.softwareDao.load(Software.class, ids[i]);
			if (data != null) {
				logger.debug("删除产品软件数据：" + ids[i]);
				this.softwareDao.delete(data);
			}
		}
	}
	/*
	 * 加载最大代码值
	 * @see com.examw.test.service.products.ISoftwareService#loadMaxCode()
	 */
	@Override
	public Integer loadMaxCode() {
		if(logger.isDebugEnabled()) logger.debug("加载最大代码值...");
		List<Software> sources = this.softwareDao.findSoftwares(new SoftwareInfo(){
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
