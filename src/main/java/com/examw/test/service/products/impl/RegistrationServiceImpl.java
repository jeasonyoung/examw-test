package com.examw.test.service.products.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.examw.test.domain.products.Channel;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.IRegistrationService;
import com.examw.test.service.products.RegistrationStatus;

/**
 * 注册码服务接口
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:56.
 */
public class RegistrationServiceImpl extends BaseDataServiceImpl<Registration,RegistrationInfo> implements IRegistrationService{
	private static final Logger logger = Logger.getLogger(RegistrationServiceImpl.class);
	private IRegistrationDao registrationDao;
	private IProductDao productDao;
	private IChannelDao channelDao;
	private Map<Integer,String> statusMap;
	/**
	 * 设置注册码数据接口。
	 * @param registrationDao 
	 *	  注册码数据接口。
	 */
	public void setRegistrationDao(IRegistrationDao registrationDao) {
		if(logger.isDebugEnabled()) logger.debug("注入注册码数据接口...");
		this.registrationDao = registrationDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品数据接口...");
		this.productDao = productDao;
	}
	/**
	 * 设置渠道数据接口。
	 * @param channelDao 
	 *	  渠道数据接口。
	 */
	public void setChannelDao(IChannelDao channelDao) {
		if(logger.isDebugEnabled()) logger.debug("注入渠道数据接口...");
		this.channelDao = channelDao;
	}
	/**
	 * 设置状态值名称集合。
	 * @param statusMap 
	 *	  状态值名称集合。
	 */
	public void setStatusMap(Map<Integer,String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态值名称集合...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态值名称。
	 * @see com.examw.test.service.products.IRegistrationService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态值［%d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Registration> find(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.registrationDao.findRegistrations(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RegistrationInfo changeModel(Registration data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Registration => RegistrationInfo...");
		if(data == null) return null;
		RegistrationInfo info = new RegistrationInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getProducts() != null && data.getProducts().size() > 0){
			List<String> listProductId = new ArrayList<>(),listProductName = new ArrayList<>();
			for(Product product : data.getProducts()){
				if(product == null) continue;
				listProductId.add(product.getId());
				listProductName.add(product.getName());
			}
			info.setProductId(listProductId.toArray(new String[0]));
			info.setProductName(listProductName.toArray(new String[0]));
		}
		Channel channel = null;
		if((channel = data.getChannel()) != null){
			info.setChannelId(channel.getId());
			info.setChannelName(channel.getName());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.registrationDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RegistrationInfo update(RegistrationInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Registration data = StringUtils.isEmpty(info.getId()) ?  null : this.registrationDao.load(Registration.class, info.getId());
		if(isAdded = (data ==  null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			info.setCreateTime(new Date());
			data = new Registration();
		}else{
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		if(info.getStatus() == null) info.setStatus(RegistrationStatus.NONE.getValue());
		BeanUtils.copyProperties(info, data, new String[]{"startTime","endTime"});
		//创建注册码激活
		if(info.getLimits() > 0 && info.getStatus() == RegistrationStatus.ACTIVE.getValue() && (data.getStatus() != RegistrationStatus.ACTIVE.getValue() || data.getStartTime() == null)){
			data.setStartTime(new Date());//激活时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(data.getStartTime());
			calendar.add(Calendar.MONTH, info.getLimits());
			calendar.set(Calendar.HOUR_OF_DAY, 23);//时
			calendar.set(Calendar.SECOND, 59);//分
			calendar.set(Calendar.SECOND, 59);//秒
			calendar.set(Calendar.MILLISECOND, 0);//微妙
			data.setEndTime(calendar.getTime());//过期时间
		}
		//产品集合
		 if(data.getProducts() != null && data.getProducts().size() > 0) data.getProducts().clear();
		if(info.getProductId() != null && info.getProductId().length > 0){
			 Set<Product> products = new HashSet<>();
			 for(String productId : info.getProductId()){
				 if(StringUtils.isEmpty(productId)) continue;
				 Product p = this.productDao.load(Product.class, productId);
				 if(p != null){
					 products.add(p);
				 }
			 }
			 if(products.size() > 0) data.setProducts(products);
		}
		//渠道
		data.setChannel(StringUtils.isEmpty(info.getChannelId()) ?  null : this.channelDao.load(Channel.class, info.getChannelId()));
		if(isAdded)this.registrationDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据:%s...", Arrays.toString(ids)));
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Registration data = this.registrationDao.load(Registration.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据：%s", ids[i]));
				this.registrationDao.delete(data);
			}
		}
	}
}