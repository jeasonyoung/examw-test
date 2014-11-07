package com.examw.test.service.library.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.dao.library.IItemErrorRecorveryDao;
import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.ItemErrorRecorvery;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.model.library.ItemErrorRecorveryInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.library.ErrorRecorveryStatus;
import com.examw.test.service.library.IItemErrorRecorveryService;
import com.examw.test.service.library.IItemService;

/**
 * 试题纠错服务接口实现类
 * @author fengwei.
 * @since 2014年11月4日 下午2:48:27.
 */
public class ItemErrorRecorveryServiceImpl extends BaseDataServiceImpl<ItemErrorRecorvery, ItemErrorRecorveryInfo> implements IItemErrorRecorveryService{
	private static final Logger logger = Logger.getLogger(ItemErrorRecorveryServiceImpl.class);
	private IItemErrorRecorveryDao itemErrorRecorveryDao;
	private IItemDao itemDao;
	private IItemService itemService;
	private IProductUserDao productUserDao;
	private ISoftwareTypeDao softwareTypeDao;
	private Map<String,String> typeMap;
	private Map<Integer,String> statusMap;
	/**
	 * 设置 试题纠错数据接口
	 * @param itemErrorRecorveryDao
	 * 
	 */
	public void setItemErrorRecorveryDao(
			IItemErrorRecorveryDao itemErrorRecorveryDao) {
		this.itemErrorRecorveryDao = itemErrorRecorveryDao;
	}
	/**
	 * 设置 试题服务接口
	 * @param itemService
	 * 
	 */
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}
	
	/**
	 * 设置 错误类型映射
	 * @param typeMap
	 * 
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}
	/**
	 * 设置 状态映射
	 * @param statusMap
	 * 
	 */
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	/**
	 * 设置 产品用户数据接口
	 * @param productUserDao
	 * 
	 */
	public void setProductUserDao(IProductUserDao productUserDao) {
		this.productUserDao = productUserDao;
	}
	
	/**
	 * 设置 软件类型数据接口
	 * @param softwareTypeDao
	 * 
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置 试题接口
	 * @param itemDao
	 * 
	 */
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}
	/*
	 * 加载错误类型名称
	 * @see com.examw.test.service.library.IItemErrorRecorveryService#loadErrorTypeName(java.lang.Integer)
	 */
	@Override
	public String loadErrorTypeName(Integer type) {
		if(this.typeMap == null || type == null)
		return null;
		return this.typeMap.get(type.toString());
	}
	/*
	 * 加载处理状态名称
	 * @see com.examw.test.service.library.IItemErrorRecorveryService#loadStatusName(java.lang.Integer)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(this.statusMap == null || status == null)
		return null;
		return this.statusMap.get(status);
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<ItemErrorRecorvery> find(ItemErrorRecorveryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询试题纠错信息");
		return this.itemErrorRecorveryDao.findItems(info);
	}
	/*
	 * 模型转换
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected ItemErrorRecorveryInfo changeModel(ItemErrorRecorvery data) {
		if(data == null) return null;
		ItemErrorRecorveryInfo info = new ItemErrorRecorveryInfo();
		BeanUtils.copyProperties(data, info);
		//用户
		if(data.getUser()!=null){
			info.setUserId(data.getUser().getId());
			info.setUserName(data.getUser().getName());
		}
		//题
		if(data.getItem()!=null){
			Item item = data.getItem();
			info.setItemId(item.getId());
			info.setItemType(item.getType());
			info.setItemTypeName(this.itemService.loadTypeName(item.getType()));
			info.setItemContent(item.getContent());
		}
		//终端
		if(data.getTerminal()!=null){
			info.setTerminalCode(data.getTerminal().getCode());
			///info.setTerminalName(data.getTerminal().getName());
		}
		info.setErrorTypeName(this.loadErrorTypeName(data.getErrorType()));	//设置类型名称
		info.setStatusName(this.loadStatusName(data.getStatus()));			//设置状态名称
		return info;
	}
	/*
	 * 查询统计
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(ItemErrorRecorveryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询试题纠错信息统计");
		return this.itemErrorRecorveryDao.total(info);
	}
	/*
	 * 更新或插入
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public ItemErrorRecorveryInfo update(ItemErrorRecorveryInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		ItemErrorRecorvery data = StringUtils.isEmpty(info.getId()) ? null : this.itemErrorRecorveryDao.load(ItemErrorRecorvery.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new ItemErrorRecorvery();
		}
		if(info.getStatus()==null) info.setStatus(ErrorRecorveryStatus.NONE.getValue());
		if(!isAdded){	//如果是更新的话 就更新
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) {
				info.setCreateTime(new Date());
			}
			data.setRemarks(info.getRemarks());
			data.setStatus(ErrorRecorveryStatus.WORKED.getValue()); //已经处理
			data.setAdminUserId(info.getAdminUserId());
			data.setAdminUserName(info.getAdminUserName());
			data.setDealTime(new Date());
			return this.changeModel(data);
		}
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getUserId())) throw new RuntimeException("用户ID不能为空！");
		ProductUser user = this.productUserDao.load(ProductUser.class, info.getUserId());
		if(user == null) throw new RuntimeException(String.format("用户［userId = %s］不存在！", info.getUserId()));
		data.setUser(user);
		if(info.getTerminalCode() == null) throw new RuntimeException("用户终端代码为空！");
		SoftwareType terminal = this.softwareTypeDao.load(info.getTerminalCode());
		if(terminal == null) throw new RuntimeException(String.format("用户终端［code = %d］不存在！", info.getTerminalCode()));
		data.setTerminal(terminal);
		if(info.getItemId() == null) throw new RuntimeException("试题ID不能为空");
		Item item = this.itemDao.load(Item.class, info.getItemId());
		if(item == null) throw new RuntimeException(String.format("试题ID不存在！", info.getItemId()));
		data.setItem(item);
		if(isAdded) this.itemErrorRecorveryDao.save(data);
		return this.changeModel(data);
	}
	/*
	 * 删除
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			ItemErrorRecorvery data = this.itemErrorRecorveryDao.load(ItemErrorRecorvery.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%s］", ids[i]));
				this.itemErrorRecorveryDao.delete(data);
			}
		}
	}
	/*
	 * 加载错误类型映射
	 * @see com.examw.test.service.library.IItemErrorRecorveryService#loadErrorTypes()
	 */
	@Override
	public Map<String, String> loadErrorTypes() {
		return this.typeMap;
	}
}
