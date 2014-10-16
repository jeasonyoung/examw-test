package com.examw.test.service.records;

import java.util.List;

import com.examw.test.domain.records.UserItemRecord;
import com.examw.test.model.records.UserItemRecordInfo;

/**
 * 用户试题记录服务接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserItemRecordService {
	/**
	 * 加载用户错题记录集合。
	 * @param userId
	 * 用户ID。
	 * @param paperId
	 * 试卷ID（可空）
	 * @return
	 * 错题记录。
	 */
	List<UserItemRecordInfo> loadUserErrorItems(String userId,String paperId);
	List<UserItemRecordInfo> loadUserErrorItems(String userId,String paperId,String subjectId);
	/**
	 * 数据模型转换。
	 * @param source
	 * @return
	 */
	UserItemRecordInfo conversion(UserItemRecord source);
	/**
	 * 数据模型转换。
	 * @param source
	 * @return
	 */
	UserItemRecord conversion(UserItemRecordInfo source);
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
	 * 加载用户试题记录。
	 * @param id
	 * 用户试题记录ID。
	 * @return
	 * 用户试题记录对象。
	 */
	UserItemRecord load(String id);
	/**
	 * 添加试题记录。
	 * @param paperRecordId
	 * 所属用户试卷记录ID。
	 * @param list
	 * 试题记录集合。
	 */
	void addItemRecord(String paperRecordId,List<UserItemRecordInfo> list);
}