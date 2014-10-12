package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.products.IProductUserDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.dao.records.IUserItemFavoriteDao;
import com.examw.test.dao.settings.ISubjectDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.records.UserItemFavorite;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.records.UserItemFavoriteInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.records.IUserItemFavoriteService;

/**
 * 用户试题收藏服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserItemFavoriteServiceImpl extends BaseDataServiceImpl<UserItemFavorite, UserItemFavoriteInfo> implements IUserItemFavoriteService {
	private static final Logger logger = Logger.getLogger(UserItemFavoriteServiceImpl.class);
	private IUserItemFavoriteDao userItemFavoriteDao;
	private IProductUserDao productUserDao;
	private ISubjectDao subjectDao;
	private ISoftwareTypeDao softwareTypeDao;
	/**
	 * 设置用户试题收藏数据接口。
	 * @param userItemFavoriteDao 
	 *	  用户试题收藏数据接口。
	 */
	public void setUserItemFavoriteDao(IUserItemFavoriteDao userItemFavoriteDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户试题收藏数据接口...");
		this.userItemFavoriteDao = userItemFavoriteDao;
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
	 * 设置所属科目数据接口。
	 * @param subjectDao 
	 *	  subjectDao
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		if(logger.isDebugEnabled()) logger.debug("注入所属科目数据接口...");
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置用户终端类型数据接口。
	 * @param softwareTypeDao 
	 *	  用户终端类型数据接口。
	 */
	public void setSoftwareTypeDao(ISoftwareTypeDao softwareTypeDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户终端类型数据接口...");
		this.softwareTypeDao = softwareTypeDao;
	}
	/*
	 * 判断用户是否收藏试题。
	 * @see com.examw.test.service.records.IUserItemFavoriteService#exists(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean exists(String userId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("判断用户［userId = %1$s］是否收藏试题［itemId = %2$s］...", userId,itemId));
		return this.userItemFavoriteDao.exists(userId, itemId);
	}
	/*
	 * 统计试题被收藏次数。
	 * @see com.examw.test.service.records.IUserItemFavoriteService#totalItemFavorites(java.lang.String)
	 */
	@Override
	public Long totalItemFavorites(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计试题［itemId = %s］被收藏次数...", itemId));
		return this.userItemFavoriteDao.totalItemFavorites(itemId);
	}
	/*
	 * 统计用户收藏试题数量...
	 * @see com.examw.test.service.records.IUserItemFavoriteService#totalUserFavorites(java.lang.String)
	 */
	@Override
	public Long totalUserFavorites(String userId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计用户［userId = %s］收藏试题数量...", userId));
		return this.userItemFavoriteDao.totalUserFavorites(userId);
	}
	/*
	 * 查询数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<UserItemFavorite> find(UserItemFavoriteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.userItemFavoriteDao.findFavorites(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected UserItemFavoriteInfo changeModel(UserItemFavorite data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 UserItemFavorite => UserItemFavoriteInfo ");
		if(data == null) return null;
		UserItemFavoriteInfo info = new UserItemFavoriteInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getUser() != null){//用户ID。
			info.setUserId(data.getUser().getId());
		}
		if(data.getSubject() != null){//科目ID。
			info.setSubjectId(data.getSubject().getId());
		}
		if(data.getTerminal() != null){//终端代码
			info.setTerminalCode(data.getTerminal().getCode());
		}
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(UserItemFavoriteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.userItemFavoriteDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.test.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public UserItemFavoriteInfo update(UserItemFavoriteInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		boolean isAdded = false;
		UserItemFavorite data = StringUtils.isEmpty(info.getId()) ? null : this.userItemFavoriteDao.load(UserItemFavorite.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new UserItemFavorite();
		}
		if(!isAdded){
			info.setCreateTime(data.getCreateTime());
			if(info.getCreateTime() == null) {
				info.setCreateTime(new Date());
			}
		}
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getUserId())) throw new RuntimeException("用户ID不能为空！");
		ProductUser user = this.productUserDao.load(ProductUser.class, info.getUserId());
		if(user == null) throw new RuntimeException(String.format("用户［userId = %s］不存在！", info.getUserId()));
		data.setUser(user);
		if(StringUtils.isEmpty(info.getSubjectId())) throw new RuntimeException("试题所属科目ID为空！");
		Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
		if(subject == null) throw new RuntimeException(String.format("所属科目［subjectId = %s］不存在！", info.getSubjectId()));
		data.setSubject(subject);
		if(info.getTerminalCode() == null) throw new RuntimeException("用户终端代码为空！");
		SoftwareType terminal = this.softwareTypeDao.load(info.getTerminalCode());
		if(terminal == null) throw new RuntimeException(String.format("用户终端［code = %d］不存在！", info.getTerminalCode()));
		data.setTerminal(terminal);
		if(isAdded) this.userItemFavoriteDao.save(data);
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
			UserItemFavorite data = this.userItemFavoriteDao.load(UserItemFavorite.class, id);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug(String.format("删除用户收藏［id = %s］...", id));
				this.userItemFavoriteDao.delete(data);
			}
		}
	}
}