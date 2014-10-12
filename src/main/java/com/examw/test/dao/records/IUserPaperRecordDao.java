package com.examw.test.dao.records;

import java.math.BigDecimal;
import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.UserPaperRecord;
import com.examw.test.model.records.UserPaperRecordInfo;

/**
 * 用户试卷记录数据接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserPaperRecordDao extends IBaseDao<UserPaperRecord> {
	/**
	 * 查询用户试卷记录数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<UserPaperRecord> findPaperRecords(UserPaperRecordInfo info);
	/**
	 * 查询用户试卷记录统计。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	Long total(UserPaperRecordInfo info);
	/**
	 * 查询某套试卷最高得分。
	 * @param paperId	
	 * 所属试卷ID
	 * @return
	 */
	BigDecimal findMaxScore(String paperId);
	/**
	 * 查询某套试卷的参考人次。
	 * @param 
	 * paperId	试卷ID
	 * @return
	 */
	Long findUsersTotal(String paperId);
	/**
	 * 加载用户的试卷记录。
	 * @param userId	
	 * 用户ID
	 * @param paperId
	 * 	试卷ID
	 * @return
	 * 试卷记录。
	 */
	UserPaperRecord load(String userId,String paperId);
}