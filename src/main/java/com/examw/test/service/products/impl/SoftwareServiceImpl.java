package com.examw.test.service.products.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.service.Status;
import com.examw.test.dao.products.IChannelDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.products.ISoftwareDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Software;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.settings.Category;
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
	private IProductDao productDao;
	private ISoftwareTypeDao softwareTypeDao;
	private IChannelDao channelDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置产品软件数据接口。
	 * @param softwareDao
	 * 产品软件数据接口。
	 */
	public void setSoftwareDao(ISoftwareDao softwareDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品软件数据接口...");
		this.softwareDao = softwareDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao
	 *  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置产品软件类型数据接口。
	 * @param softwareTypeDao
	 * 产品软件类型数据接口。
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品软件类型数据接口...");
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置渠道数据接口。
	 * @param channelDao
	 * 	渠道数据接口。
	 */
	public void setChannelDao(IChannelDao channelDao) {
		if(logger.isDebugEnabled()) logger.debug("注入渠道数据接口...");
		this.channelDao = channelDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap
	 * 状态值名称集合。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.test.service.products.ISoftwareService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap ==null || this.statusMap.size() == 0) return null;
		return statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Software> find(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.softwareDao.findSoftwares(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected SoftwareInfo changeModel(Software data) {
		if (logger.isDebugEnabled()) logger.debug("数据模型转换 Software => SoftwareInfo ...");
		if (data == null) return null;
		SoftwareInfo info = new SoftwareInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getProduct() != null){//所属产品
			info.setProductId(data.getProduct().getId());
			info.setProductName(data.getProduct().getName());
			Exam exam = null;
			if((exam = data.getProduct().getExam()) != null){//所属考试
				info.setExamId(exam.getId());
				info.setExamName(exam.getName());
				Category category = null;
				if((category = exam.getCategory()) != null){//所属考试类别
					info.setCategoryId(category.getId());
				}
			}
		}
		if(data.getChannel() != null){//渠道
			info.setChannelId(data.getChannel().getId());
			info.setChannelName(data.getChannel().getName());
		}
		if(data.getType() != null){//类型
			info.setSoftTypeId(data.getType().getId());
			info.setSoftTypeName(data.getType().getName());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(SoftwareInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.softwareDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public SoftwareInfo update(SoftwareInfo info) {
		if (logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Software  data = StringUtils.isEmpty(info.getId()) ? null : this.softwareDao.load(Software.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Software(); 
		}else {
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		if(info.getStatus() == null) info.setStatus(Status.ENABLED.getValue());
		
		BeanUtils.copyProperties(info, data);
		//所属产品
		if(StringUtils.isEmpty(info.getProductId())) throw new RuntimeException("所属产品ID为空!");
		Product product = this.productDao.load(Product.class, info.getProductId());
		if(product == null) throw new RuntimeException(String.format("产品［%s］不存在！", info.getProductId()));
		data.setProduct(product);
		//所属软件类型
		if(StringUtils.isEmpty(info.getSoftTypeId())) throw new RuntimeException("所属软件类型ID为空！");
		SoftwareType type = this.softwareTypeDao.load(SoftwareType.class, info.getSoftTypeId());
		if(type == null) throw new RuntimeException(String.format("软件类型［%s］不存在！", info.getSoftTypeId()));
		data.setType(type);
		//所属渠道
		data.setChannel(this.channelDao.load(Channel.class, info.getChannelId()));
		//新增数据。
		if(isAdded) this.softwareDao.save(data);
		//
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled()) logger.debug("删除数据...");
		if (ids == null || ids.length == 0) return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))continue;
			Software data = this.softwareDao.load(Software.class, ids[i]);
			if (data != null) {
				logger.debug("删除数据：" + ids[i]);
				this.softwareDao.delete(data);
			}
		}
	}
	/*
	 * 加载产品下最大排序号。
	 * @see com.examw.test.service.products.ISoftwareService#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String productId) {
		if(logger.isDebugEnabled()) logger.debug(String.format(" 加载产品［%s］下最大排序号...", productId));
		return this.loadMaxOrder(productId);
	}
}