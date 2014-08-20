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
import com.examw.test.dao.products.IRelationProductDao;
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.RelationProduct;
import com.examw.test.model.products.RegistrationInfo;
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
	private IRelationProductDao relationProductDao;
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
	 * 设置 关联产品数据接口
	 * @param relationProductDao
	 * 
	 */
	public void setRelationProductDao(IRelationProductDao relationProductDao) {
		this.relationProductDao = relationProductDao;
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
		if(data.getChannel()!=null){
			info.setChannelId(data.getChannel().getId());
			info.setChannelName(data.getChannel().getName());
		}
		String productId ="", productName = "";
		if(data.getProducts()!=null && data.getProducts().size()>0){
			for(RelationProduct rp : data.getProducts()){
				productId = productId+rp.getProduct().getId()+",";	//注意这里是产品的ID
				productName = productName + rp.getName()+",";
			}
		}
		if(!"".equals(productId)){
			info.setRelationProductId(productId.split(","));
			info.setRelationProductName(productName);
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
		if (logger.isDebugEnabled())	logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Registration  data = StringUtils.isEmpty(info.getId()) ?  null : this.registrationDao.load(Registration.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new Registration();
			//TODO 生成注册码  先随便模拟一个
			data.setCode(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			data.setCreateTime(new Date());
		}
		info.setCreateTime(data.getCreateTime());
		//重新设置注册码
		//data.setCode(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		//注册码
		info.setCode(data.getCode());
		BeanUtils.copyProperties(info, data);
		if(data.getStatus() == null){	//未激活
			data.setStatus(Registration.STATUS_NONE);
			info.setStatus(Registration.STATUS_NONE);
		}
		//渠道
		if(info.getChannelId()!=null && (data.getChannel()==null || !data.getChannel().getId().equalsIgnoreCase(info.getChannelId()))){
			Channel channel = this.channelDao.load(Channel.class, info.getChannelId());
			if(channel!=null){
				data.setChannel(channel);
			}
		}
		Set<RelationProduct> products = new HashSet<RelationProduct>();
		if(info.getRelationProductId()!=null && info.getRelationProductId().length>0){
			this.relationProductDao.delete(data.getId());
			for(String id:info.getRelationProductId()){
				Product s = this.productDao.load(Product.class, id);
				if(s!=null && s.getStatus().equals(Product.STATUS_NONE)){
					RelationProduct rp = this.relationProductDao.load(data.getId(), id);
					if(rp==null)
					{
						rp = new RelationProduct();
						rp.setRegistration(data);
						rp.setProduct(s);
						rp.setCreateTime(new Date());
						rp.setName(s.getName());
						rp.setId(UUID.randomUUID().toString());
					}
					products.add(rp);
				}
			}
		}
		data.setProducts(products);
//		if(products.size() == 0){
//			throw new RuntimeException("没有关联产品");
//		}
		//新增数据。
		if(isAdded) this.registrationDao.save(data);
		else{
			data.setLastTime(new Date());
			info.setLastTime(new Date());
		}
		info.setStatusName(this.loadStatusName(data.getStatus()));
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
			Registration data = this.registrationDao.load(Registration.class, ids[i]);
			if (data != null) {
				logger.debug("删除产品数据：" + ids[i]);
				this.relationProductDao.delete(ids[i]);
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
