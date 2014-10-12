package com.examw.test.service.records;

import java.math.BigDecimal;

import com.examw.service.IDataService;
import com.examw.test.model.records.UserPaperRecordInfo;

/**
 * 用户试卷记录服务接口。
 * 
 * @author yangyong
 * @since 2014年10月11日
 */
public interface IUserPaperRecordService extends IDataService<UserPaperRecordInfo> {
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
	UserPaperRecordInfo load(String userId,String paperId);
}