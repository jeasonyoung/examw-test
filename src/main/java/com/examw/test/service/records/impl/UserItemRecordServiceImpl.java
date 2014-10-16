package com.examw.test.service.records.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.library.IStructureDao;
import com.examw.test.dao.products.ISoftwareTypeDao;
import com.examw.test.dao.records.IUserItemRecordDao;
import com.examw.test.dao.records.IUserPaperRecordDao;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.domain.records.UserItemRecord;
import com.examw.test.domain.records.UserPaperRecord;
import com.examw.test.model.records.UserItemRecordInfo;
import com.examw.test.service.records.IUserItemRecordService;

/**
 * 用户试题记录服务接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public class UserItemRecordServiceImpl implements IUserItemRecordService {
	private static final Logger logger = Logger.getLogger(UserItemRecordServiceImpl.class);
	private IUserItemRecordDao userItemRecordDao;
	private IUserPaperRecordDao userPaperRecordDao;
	private IStructureDao structureDao;
	private ISoftwareTypeDao softwareTypeDao;
	/**
	 * 设置用户试题记录数据接口。
	 * @param userItemRecordDao 
	 *	  用户试题记录数据接口。
	 */
	public void setUserItemRecordDao(IUserItemRecordDao userItemRecordDao) {
		if(logger.isDebugEnabled()) logger.debug("注入用户试题记录数据接口...");
		this.userItemRecordDao = userItemRecordDao;
	}
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
	 * 设置试卷结构数据接口。
	 * @param structureDao 
	 *	  试卷结构数据接口。
	 */
	public void setStructureDao(IStructureDao structureDao) {
		if(logger.isDebugEnabled()) logger.debug("注入试卷结构数据接口...");
		this.structureDao = structureDao;
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
	/*
	 * 加载用户试卷错题集合。
	 * @see com.examw.test.service.records.IUserItemRecordService#loadUserErrorItems(java.lang.String, java.lang.String)
	 */
	@Override
	public List<UserItemRecordInfo> loadUserErrorItems(String userId,String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］错题集合...", userId, paperId));
		List<UserItemRecord> dataList = this.userItemRecordDao.loadUserErrorItems(userId, paperId,null);
		if(dataList == null || dataList.size() == 0) return null;
		List<UserItemRecordInfo> list = new ArrayList<>();
		for(UserItemRecord item : dataList){
			if(item == null) continue;
			UserItemRecordInfo info = this.conversion(item);
			if(info != null) list.add(info);
		}
		return list;
	}
	@Override
	public List<UserItemRecordInfo> loadUserErrorItems(String userId,String paperId,String subjectId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］科目［subjectId = %2$s］错题集合...", userId, subjectId));
		List<UserItemRecord> dataList = this.userItemRecordDao.loadUserErrorItems(userId, paperId,subjectId);
		if(dataList == null || dataList.size() == 0) return null;
		List<UserItemRecordInfo> list = new ArrayList<>();
		for(UserItemRecord item : dataList){
			if(item == null) continue;
			UserItemRecordInfo info = this.conversion(item);
			if(info != null) list.add(info);
		}
		return list;
	}
	/*
	 * 数据模型类型转换。
	 * @see com.examw.test.service.records.IUserItemRecordService#conversion(com.examw.test.domain.records.UserItemRecord)
	 */
	@Override
	public UserItemRecordInfo conversion(UserItemRecord source) {
		if(logger.isDebugEnabled()) logger.debug(" 数据模型类型转换［UserItemRecord => UserItemRecordInfo］...");
		if(source == null) return null;
		UserItemRecordInfo target = new UserItemRecordInfo();
		BeanUtils.copyProperties(source, target);
		//试卷结构
		if(source.getStructure() != null){
			target.setStructureId(source.getStructure().getId());
		}
		//终端类型
		if(source.getTerminal() != null){
			target.setTerminalCode(source.getTerminal().getCode());
		}
		return target;
	}
	/*
	 * 数据模型类型转换。
	 * @see com.examw.test.service.records.IUserItemRecordService#conversion(com.examw.test.model.records.UserItemRecordInfo)
	 */
	@Override
	public UserItemRecord conversion(UserItemRecordInfo source) {
		if(logger.isDebugEnabled()) logger.debug("数据模型类型转换［UserItemRecordInfo => UserItemRecord］...");
		if(source == null) return null;
		UserItemRecord data = StringUtils.isEmpty(source.getId()) ?  null : this.userItemRecordDao.load(UserItemRecord.class, source.getId());
		if(data == null){
			if(StringUtils.isEmpty(source.getId())){
				source.setId(UUID.randomUUID().toString());
			}
			source.setCreateTime(new Date());
			data = new UserItemRecord();
		}else {
			source.setCreateTime(data.getCreateTime());
			if(source.getCreateTime() == null) source.setCreateTime(new Date());
		}
		source.setLastTime(new Date());
		BeanUtils.copyProperties(source, data);
		//data.setPaperRecord(paperRecord);
		if(StringUtils.isEmpty(source.getStructureId())){
			throw new RuntimeException("试卷结构structureId为空！");
		}
		Structure structure = this.structureDao.load(Structure.class, source.getStructureId());
		if(structure == null){
			throw new RuntimeException(String.format("试卷结构［structureId = %s］不存在!", source.getStructureId()));
		}
		data.setStructure(structure);
		if(source.getTerminalCode() == null){
			throw new RuntimeException("用户终端代码［terminalCode］不存在！");
		}
		SoftwareType terminal = this.softwareTypeDao.load(source.getTerminalCode());
		if(terminal == null){
			throw new RuntimeException(String.format("终端类型［code = %d］不存在！", source.getTerminalCode()));
		}
		data.setTerminal(terminal);
		return data;
	}
	/*
	 * 加载试题记录数据。
	 * @see com.examw.test.service.records.IUserItemRecordService#load(java.lang.String)
	 */
	@Override
	public UserItemRecord load(String id) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题记录［id = %s］数据...", id));
		if(StringUtils.isEmpty(id)) return null;
		return this.userItemRecordDao.load(UserItemRecord.class, id);
	}
	/*
	 * 加载用户试卷的最新试题记录。
	 * @see com.examw.test.service.records.IUserItemRecordService#loadUserPaperLastRecord(java.lang.String, java.lang.String)
	 */
	@Override
	public UserItemRecord loadUserPaperLastRecord(String userId, String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［%1$s］试卷［%2$s］的最新试题记录...", userId,paperId));
		return this.userItemRecordDao.loadUserPaperLastRecord(userId, paperId);
	}
	/*
	 * 加载用户试卷下试题的最新记录。
	 * @see com.examw.test.service.records.IUserItemRecordService#loadUserPaperLastRecord(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public UserItemRecord loadUserPaperLastRecord(String userId,String paperId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载用户［userId = %1$s］试卷［paperId = %2$s］下试题［itemId = %3$s］的最新记录...", userId,paperId,itemId));
		return this.userItemRecordDao.loadUserPaperLastRecord(userId, paperId, itemId);
	}
	/*
	 * 添加试题记录。
	 * @see com.examw.test.service.records.IUserItemRecordService#addItemRecord(java.lang.String, java.util.List)
	 */
	@Override
	public void addItemRecord(String paperRecordId,List<UserItemRecordInfo> list) {
		if(logger.isDebugEnabled()) logger.debug(String.format("添加用户试卷［paperRecordId = %s］的试题记录...", paperRecordId));
		if(StringUtils.isEmpty(paperRecordId)) throw new RuntimeException("用户试题记录不能为空！");
		if(list == null || list.size() == 0) return;
		UserPaperRecord paperRecord = this.userPaperRecordDao.load(UserPaperRecord.class, paperRecordId);
		if(paperRecord == null) throw new RuntimeException(String.format("用户试卷记录［paperRecordId = %s］数据不存在！", paperRecordId));
		List<UserItemRecord> records = new ArrayList<>();
		for(UserItemRecordInfo info : list){
			UserItemRecord record = this.conversion(info);
			if(record != null){
				record.setPaperRecord(paperRecord);
				records.add(record);
			}
		}
		if(records != null && records.size() > 0){
			this.userItemRecordDao.batchSaveItemRecord(records.toArray(new UserItemRecord[0]));
		}
	}
}