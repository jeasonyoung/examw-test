package com.examw.test.service.products.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IChannelDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.products.IRegistrationDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.RelationProduct;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.model.products.SoftwareTypeLimitInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IRegistrationService;

/**
 * 注册码服务接口
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:56.
 */
public class RegistrationServiceImpl extends BaseDataServiceImpl<Registration,RegistrationInfo> implements IRegistrationService{
	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	private IRegistrationDao registrationDao;
	private ISoftwareTypeDao softwareTypeDao;
	private IProductDao productDao;
	private IChannelDao channelDao;
	private Map<String,String> statusMap;
	/**
	 * 设置 注册码数据接口
	 * @param registrationDao
	 * 
	 */
	public void setRegistrationDao(IRegistrationDao registrationDao) {
		this.registrationDao = registrationDao;
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
	 * 设置 设置渠道数据接口
	 * @param channelDao
	 * 
	 */
	public void setChannelDao(IChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	/**
	 * 设置 状态名称映射
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<String, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	/**
	 * 设置 软件类型数据接口
	 * @param softwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}

	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Registration> find(RegistrationInfo info) {
		return this.registrationDao.findRegistrations(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationInfo changeModel(Registration data) {
		if (logger.isDebugEnabled())	logger.debug("[注册码]数据模型转换...");
		if (data == null)
			return null;
		RegistrationInfo info = new RegistrationInfo();
		BeanUtils.copyProperties(data, info);
		//渠道
		if(data.getChannel()!=null){
			info.setChannelId(data.getChannel().getId());
			info.setChannelName(data.getChannel().getName());
		}
		//关联产品
		String productId ="", productName = "";
		if(data.getProducts()!=null && data.getProducts().size()>0){
			for(RelationProduct rp : data.getProducts()){
				productId = productId+rp.getProduct().getId()+",";	//注意这里是产品的ID
				productName = productName + rp.getName()+",";
			}
		}
		if(!"".equals(productId)){
			info.setProductId(productId.split(","));
			info.setProductName(productName);
		}
		//软件类型限制
		if(data.getTypeLimits()!=null && data.getTypeLimits().size()>0)
		{
			Set<SoftwareTypeLimitInfo> limit = new HashSet<SoftwareTypeLimitInfo>();
			for(SoftwareTypeLimit stl : data.getTypeLimits()){
				SoftwareTypeLimitInfo stlInfo = new SoftwareTypeLimitInfo();
				BeanUtils.copyProperties(stl, stlInfo);
				stlInfo.setTypeId(stl.getType().getId());
				stlInfo.setTypeName(stl.getType().getName());
				stlInfo.setRegistrationId(stl.getRegistration().getId());
				limit.add(stlInfo);
			}
			info.setLimit(limit);
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationInfo info) {
		return this.registrationDao.total(info);
	}
	@Override
	public RegistrationInfo update(RegistrationInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Registration data = StringUtils.isEmpty(info.getId()) ?  null : this.registrationDao.load(Registration.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			data = new Registration();
			data.setCreateTime(new Date());
		}
		info.setCreateTime(data.getCreateTime());
		if(info.getStatus() == null) info.setStatus(Registration.STATUS_NONE);
		//TODO 生成注册码  先随便模拟一个
		if(StringUtils.isEmpty(info.getCode()))info.setCode(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		BeanUtils.copyProperties(info, data);
		//渠道
		data.setChannel(this.channelDao.load(Channel.class,info.getChannelId()));
		if(data.getChannel() != null){
			info.setChannelName(data.getChannel().getName());
		}
		//注册码软件限制
		if(data.getTypeLimits() == null) data.setTypeLimits(new HashSet<SoftwareTypeLimit>());
		Set<SoftwareTypeLimit> oldTypeLimit = data.getTypeLimits();
		if(info.getLimit()!=null && info.getLimit().size()>0)
		{
			if(logger.isDebugEnabled())logger.debug("开始注册码软件限制...");
			for(SoftwareTypeLimitInfo stlInfo : info.getLimit()){
				SoftwareType type = this.softwareTypeDao.load(SoftwareType.class, stlInfo.getTypeId());
				if(type == null){
					if(logger.isDebugEnabled()) logger.debug(String.format("软件类型[id = %s]不存在!", stlInfo.getTypeId()));
					continue;
				}
				SoftwareTypeLimit stl = createSoftwareTypeLimit(oldTypeLimit,data,type);
				stl.setTime(stlInfo.getTime());
				if(logger.isDebugEnabled()) logger.debug(String.format("限制软件类型[%1$s,%2$s]...", stlInfo.getTypeId(), data.getId()));
				data.getTypeLimits().add(stl);
			}
		}else
			data.getTypeLimits().clear();
		//产品关联
		if(data.getProducts() == null) data.setProducts(new HashSet<RelationProduct>());
		//data.getProducts().clear();
		Set<RelationProduct> old = data.getProducts();
		if(info.getProductId() != null && info.getProductId().length > 0){
			if(logger.isDebugEnabled())logger.debug("开始注册码产品关联...");
			for(String productId : info.getProductId()){
				if(StringUtils.isEmpty(productId))continue;
				Product product = this.productDao.load(Product.class, productId);
				if(product == null){
					if(logger.isDebugEnabled()) logger.debug(String.format("产品[id = %s]不存在!", productId));
					continue;
				}
				RelationProduct rp = createRelationProduct(old,data,product);
				rp.setCreateTime(new Date());
				if(logger.isDebugEnabled()) logger.debug(String.format("关联产品[%1$s,%2$s]...", productId, data.getId()));
				data.getProducts().add(rp);
			}
			
		}else
			data.getProducts().clear();
		if(isAdded)this.registrationDao.save(data);
		else{
			data.setLastTime(new Date());
			info.setLastTime(data.getLastTime());
		}
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/**
	 * 构造关联产品
	 * @param old
	 * @param reg
	 * @param product
	 * @return
	 */
	private RelationProduct createRelationProduct(Set<RelationProduct> old,Registration reg,
			Product product) {
		RelationProduct data = null;
		for(RelationProduct rp:old)
		{
			if(rp.getProduct().getId().equals(product.getId()) && rp.getRegistration().getId().equals(reg.getId()))
			{
				data = rp;
				break;
			}
		}
		if(data == null)
		{
			data = new RelationProduct();
			data.setId(UUID.randomUUID().toString());
			data.setName(product.getName());
			data.setProduct(product);
			data.setRegistration(reg);
		}
		return data;
	}
	/**
	 * 构造软件类型限制
	 * @param old
	 * @param reg
	 * @param type
	 * @return
	 */
	private SoftwareTypeLimit createSoftwareTypeLimit(Set<SoftwareTypeLimit> old,Registration reg,
			SoftwareType type) {
		SoftwareTypeLimit data = null;
		for(SoftwareTypeLimit stl:old)
		{
			if(stl.getType().getId().equals(type.getId()) && stl.getRegistration().getId().equals(reg.getId()))
			{
				data = stl;
				break;
			}
		}
		if(data == null)
		{
			data = new SoftwareTypeLimit();
			data.setId(UUID.randomUUID().toString());
			data.setType(type);
			data.setRegistration(reg);
		}
		return data;
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
			Registration data = this.registrationDao.load(Registration.class, ids[i]);
			if (data != null) {
				logger.debug("删除注册码数据：" + ids[i]);
				this.registrationDao.delete(data);
			}
		}
	}
	/*
	 * 加载状态名称
	 * @see com.examw.test.service.products.IRegistrationService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(status == null || statusMap == null)
		return null;
		return statusMap.get(status.toString());
	}
	/*
	 * 获取状态映射
	 * @see com.examw.test.service.products.IRegistrationService#getStatusMap()
	 */
	@Override
	public Map<String, String> getStatusMap() {
		return statusMap;
	}
}
