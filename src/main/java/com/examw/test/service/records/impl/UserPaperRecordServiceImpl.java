package com.examw.test.service.records.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.dao.products.IProductDao;
import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.library.Paper;
import com.examw.test.domain.products.Product;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.records.UserItemRecord;
import com.examw.test.domain.records.UserPaperRecord;
import com.examw.test.model.records.UserItemRecordInfo;
import com.examw.test.model.records.UserPaperRecordInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.records.IUserItemRecordService;
import com.examw.test.service.records.IUserPaperRecordService;

/**
 * 用户试卷记录服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserPaperRecordServiceImpl extends BaseDataServiceImpl<UserPaperRecord,UserPaperRecordInfo> implements IUserPaperRecordService {
	private static final Logger logger = Logger.getLogger(UserPaperRecordServiceImpl.class);
	private IUserPaperRecordDao userPaperRecordDao;
	private IProductUserDao productUserDao;
	private IPaperDao paperDao;
	private IProductDao productDao;
	private ISoftwareTypeDao softwareTypeDao;
	private IUserItemRecordService userItemRecordService;
	/**
	 * 设置用户试卷记录数据接口。
	 * @param userPaperRecordDao 
	 *	  用户试卷记录数据接口。
	 */
	public void setUserPaperRecordDao(IUserPaperRecordDao userPaperRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户试卷记录数据接口...");
		this.userPaperRecordDao = userPaperRecordDao;
	}
	/**
	 * 设置用户数据接口。
	 * @param productUserDao 
	 *	  用户数据接口。
	 */
	public void setProductUserDao(IProductUserDao productUserDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户数据接口...");
		this.productUserDao = productUserDao;
	}
	/**
	 * 设置试卷数据接口。
	 * @param paperDao 
	 *	  试卷数据接口。
	 */
	public void setPaperDao(IPaperDao paperDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷数据接口...");
		this.paperDao = paperDao;
	}
	/**
	 * 设置产品数据接口。
	 * @param productDao 
	 *	  产品数据接口。
	 */
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}
	/**
	 * 设置终端类型数据接口。
	 * @param softwareTypeDao 
	 *	  终端类型数据接口。
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入终端类型数据接口...");
		this.softwareTypeDao = softwareTypeDao;
	}
	/**
	 * 设置用户试题记录服务接口。
	 * @param userItemRecordService 
	 *	  用户试题记录服务接口。
	 */
	public void setUserItemRecordService(IUserItemRecordService userItemRecordService) {
		if(logger.isDebugEnabled()) logger.debug("注入用户试题记录服务接口...");
		this.userItemRecordService = userItemRecordService;
	}
	/*
	 * 查询试卷最高得分。
	 * @see com.examw.test.service.records.IUserPaperRecordService#findMaxScore(java.lang.String)
	 */
	@Override
	public BigDecimal findMaxScore(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询试卷［paperId = %s］最高得分...", paperId));
		return this.userPaperRecordDao.findMaxScore(paperId);
	}
	/*
	 * 查询试卷的参考人次。
	 * @see com.examw.test.service.records.IUserPaperRecordService#findUsersTotal(java.lang.String)
	 */
	@Override
	public Long findUsersTotal(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询试卷［paperId = %s］的参考人次...", paperId));
		return this.userPaperRecordDao.findUsersTotal(paperId);
	}
	/*
	 *加载用户的试卷记录。
	 * @see com.examw.test.service.records.IUserPaperRecordService#load(java.lang.String, java.lang.String)
	 */
	@Override
	public UserPaperRecordInfo load(String userId, String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］的试卷［paperId = %2$s］记录...", userId, paperId));
		return this.changeModel(this.userPaperRecordDao.load(userId, paperId),true);
	}
	/*
	 * 加载某产品下最新的试卷考试记录	[Add by FW 2014.10.12]
	 * @see com.examw.test.service.records.IUserPaperRecordService#findLastedPaperRecordsOfProduct(java.lang.String, java.lang.String)
	 */
	public List<UserPaperRecordInfo> findLastedPaperRecordsOfProduct(String userId,String productId){
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］的产品［productId = %2$s］的最新考试记录...", userId, productId));
		List<UserPaperRecord> list = this.userPaperRecordDao.findLastedPaperRecordsOfProduct(userId, productId);
		List<UserPaperRecordInfo> results = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(UserPaperRecord data : list){
				UserPaperRecordInfo info = this.changeModel(data,false);
				if(info != null){
					results.add(info);
				}
			}
		}
		return results;
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<UserPaperRecord> find(UserPaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.userPaperRecordDao.findPaperRecords(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected UserPaperRecordInfo changeModel(UserPaperRecord data) {
		return changeModel(data,false);
	}
	private UserPaperRecordInfo changeModel(UserPaperRecord data,boolean isLoadItems){
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 UserPaperRecord => UserPaperRecordInfo ...");
		if(data == null) return null;
		UserPaperRecordInfo info = new UserPaperRecordInfo();
		BeanUtils.copyProperties(data, info, new String[]{"items"});
		if(data.getUser() != null){//用户
			info.setUserId(data.getUser().getId());
		}
		if(data.getPaper() != null){//试卷
			info.setPaperId(data.getPaper().getId());
		}
		if(data.getProduct() != null){//产品
			info.setProductId(data.getProduct().getId());
		}
		if(data.getTerminal() != null){//终端
			info.setTerminalCode(data.getTerminal().getCode());
		}
		if(isLoadItems) info.setItems(this.changeModelItems(data.getItems()));
		return info;
	}
	//
	private Set<UserItemRecordInfo> changeModelItems(Set<UserItemRecord> items){
		if(items == null || items.size() == 0) return null;
		Set<UserItemRecordInfo> set = new TreeSet<>();
		for(UserItemRecord item : items){
			if(item == null) continue;
			UserItemRecordInfo info = this.userItemRecordService.conversion(item);
			if(info != null) set.add(info);
		}
		return set;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(UserPaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.userPaperRecordDao.total(info);
	}
	/*
	 * 数据更新。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public UserPaperRecordInfo update(UserPaperRecordInfo info) {
		if(logger.isDebugEnabled()) logger.debug("数据更新...");
		if(info == null) return null;
		UserPaperRecord data = StringUtils.isEmpty(info.getId()) ? null : this.userPaperRecordDao.load(UserPaperRecord.class, info.getId());
		if(data == null){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new UserPaperRecord();
		}else {
			info.setCreateTime(data.getCreateTime());
			this.userPaperRecordDao.merge(data);
		}
		BeanUtils.copyProperties(info, data, new String[]{"items"});
		//用户
		if(!StringUtils.isEmpty(info.getUserId())) data.setUser(this.productUserDao.load(ProductUser.class, info.getUserId()));
		//试卷
		if(!StringUtils.isEmpty(info.getPaperId())) data.setPaper(this.paperDao.load(Paper.class, info.getPaperId()));
		//产品
		if(!StringUtils.isEmpty(info.getProductId())) data.setProduct(this.productDao.load(Product.class, info.getProductId()));
		//终端
		if(info.getTerminalCode() != null) data.setTerminal(this.softwareTypeDao.load(info.getTerminalCode()));
		//试题记录
		if(info.getItems() != null && info.getItems().size() > 0){
			if(data.getItems() == null) data.setItems(new HashSet<UserItemRecord>());
			for(UserItemRecordInfo itemInfo : info.getItems()){
				UserItemRecord record = this.userItemRecordService.conversion(itemInfo);
				if(record != null) {
					record.setPaperRecord(data);
					data.getItems().add(record);
				}
			}
		}
		this.userPaperRecordDao.saveOrUpdate(data);
		return this.changeModel(data);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(String id : ids){
			if(StringUtils.isEmpty(id)) continue;
			UserPaperRecord data = this.userPaperRecordDao.load(UserPaperRecord.class, id);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除用户试卷记录［id = %s］...", id));
				this.userPaperRecordDao.delete(data);
			}
		}
	}
}