package com.examw.test.dao.records;

import java.math.BigDecimal;
import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.PaperRecord;
import com.examw.test.model.records.PaperRecordInfo;

/**
 * 试卷记录数据接口
 * @author fengwei.
 * @since 2014年9月17日 下午5:26:03.
 */
public interface IPaperRecordDao extends IBaseDao<PaperRecord> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<PaperRecord> findPaperRecords(PaperRecordInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(PaperRecordInfo info);
	/**
	 * 查询某套试卷最高得分
	 * @param paperId	试卷ID
	 * @return
	 */
	BigDecimal findMaxScore(String paperId);
	/**
	 * 查询某套试卷的参考人数
	 * @param paperId	试卷ID
	 * @return
	 */
	Long findUserSum(String paperId);
	/**
	 * 加载某用户的一套试卷
	 * @param paperId	试卷ID
	 * @param userId	用户ID
	 * @return
	 */
	PaperRecord load(String paperId,String userId);
}
