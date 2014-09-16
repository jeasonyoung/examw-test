package com.examw.test.dao.settings;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.settings.Exam;
import com.examw.test.model.settings.ExamInfo;

/**
 * 考试数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:43:30.
 */
public interface IExamDao extends IBaseDao<Exam>{
	/**
	 * 查询考试数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Exam> findExams(ExamInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ExamInfo info);
}
