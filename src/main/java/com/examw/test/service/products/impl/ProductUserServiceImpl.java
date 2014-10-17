package com.examw.test.service.products.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.model.products.ProductUserInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.products.FrontUserInfo;
import com.examw.test.service.products.IProductUserService;

/**
 * 
 * @author fengwei.
 * @since 2014年8月12日 上午8:57:35.
 */
public class ProductUserServiceImpl extends BaseDataServiceImpl<ProductUser,ProductUserInfo> implements IProductUserService{
	private static final Logger logger = Logger.getLogger(ProductUserServiceImpl.class);
	private IProductUserDao productUserDao;
	private Map<Integer, String> statusMap;
	/**
	 * 设置产品用户数据接口。
	 * @param ProductUserDao
	 *  产品用户数据接口。
	 */
	public void setProductUserDao(IProductUserDao ProductUserDao) {
		if(logger.isDebugEnabled()) logger.debug("注入产品用户数据接口...");
		this.productUserDao = ProductUserDao;
	}
	/**
	 * 设置状态名称映射。
	 * @param statusMap
	 * 	状态名称映射。
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		if(logger.isDebugEnabled()) logger.debug("注入状态名称映射...");
		this.statusMap = statusMap;
	}
	/*
	 * 加载状态名称。
	 * @see com.examw.test.service.products.IProductUserService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载状态［status ＝ %d］名称...", status));
		if(status == null || this.statusMap == null || this.statusMap.size() == 0) return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ProductUser> find(ProductUserInfo info) {
		if (logger.isDebugEnabled()) logger.debug("查询[产品用户]数据...");
		return this.productUserDao.findProductUsers(info);
	}
	/*
	 * 数据模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ProductUserInfo changeModel(ProductUser data) {
		if (logger.isDebugEnabled())logger.debug("[产品用户]数据模型转换 ProductUser => ProductUserInfo...");
		if (data == null) return null;
		ProductUserInfo info = new ProductUserInfo();
		BeanUtils.copyProperties(data, info);
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ProductUserInfo info) {
		if (logger.isDebugEnabled())logger.debug("查询统计...");
		return productUserDao.total(info);
	}
	/*
	 * 新增或更新数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ProductUserInfo update(ProductUserInfo info) {
		if (logger.isDebugEnabled())logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		ProductUser  data = StringUtils.isEmpty(info.getId()) ?  null : this.productUserDao.load(ProductUser.class, info.getId());
		if((isAdded = (data ==  null)) && !StringUtils.isEmpty(info.getCode())){
			data = this.productUserDao.loadUserByCode(info.getCode());
		}
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new ProductUser();
			data.setCreateTime(new Date());
		}else {
			info.setId(data.getId());
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) info.setCreateTime(new Date());
		}
		info.setLastTime(new Date());
		BeanUtils.copyProperties(info, data);
		//新增数据。
		if(isAdded) this.productUserDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if (logger.isDebugEnabled())logger.debug("删除数据...");
		if (ids == null || ids.length == 0) return;
		for(String id :  ids){
			if(StringUtils.isEmpty(id)) continue;
			ProductUser data = this.productUserDao.load(ProductUser.class, id);
			if(data != null){
				logger.debug(String.format("删除产品用户［id = %1$s］［code = %2$s］［name = %3$s］...", data.getId(), data.getCode(), data.getName()));
				this.productUserDao.delete(data);
			}
		}
	}
	/*
	 * 验证前端用户。
	 * @see com.examw.test.service.products.IProductUserService#verifyFrontUser(com.examw.test.service.products.FrontUserInfo)
	 */
	@Override
	public ProductUserInfo verifyFrontUser(FrontUserInfo info) {
		if(logger.isDebugEnabled()) logger.debug("验证前端用户...");
		if(info == null || StringUtils.isEmpty(info.getCode())) return null;
		ProductUser productUser = this.productUserDao.loadUserByCode(info.getCode());
		if(productUser == null){
			ProductUserInfo productUserInfo = new ProductUserInfo();
			productUserInfo.setCode(info.getCode());
			productUserInfo.setName(info.getName());
			return this.update(productUserInfo);
		}
		return this.changeModel(productUser);
	}	
}