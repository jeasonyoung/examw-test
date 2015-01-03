package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.Question;
import com.examw.test.model.records.QuestionInfo;

/**
 * 常见问题数据接口。
 * 
 * @author yangyong
 * @since 2015年1月3日
 */
public interface IQuestionDao extends IBaseDao<Question> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 */
	List<Question> findQuestions(QuestionInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(QuestionInfo info);
}