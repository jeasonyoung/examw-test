package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.UserItemRecord;

/**
 * 用户试题记录数据接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserItemRecordDao extends IBaseDao<UserItemRecord> {
	/**
	 * 加载用户错题记录集合。
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID（可空）
	 * @return
	 * 错题记录。
	 */
	List<UserItemRecord> loadUserErrorItems(String userId,String paperId,String subjectId);
	/**
	 * 加载用户试卷的最新试题记录。
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	UserItemRecord loadUserPaperLastRecord(String userId,String paperId);
	/**
	 * 加载用户试卷下试题的最新记录。
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	UserItemRecord loadUserPaperLastRecord(String userId,String paperId,String itemId);
	/**
	 * 批量添加用户试题记录。
	 * @param itemRecords
	 */
	void batchSaveItemRecord(UserItemRecord[] itemRecords);
}