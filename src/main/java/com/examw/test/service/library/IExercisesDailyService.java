package com.examw.test.service.library;

import com.examw.test.domain.settings.Subject;

/**
 * 每日一练服务接口。
 * 
 * @author yangyong
 * @since 2014年10月23日
 */
public interface IExercisesDailyService {
	/**
	 * 创建科目的每日一练试卷。
	 * @param subject
	 * 所属科目。
	 */
	void addPaper(Subject subject) throws Exception;
	/**
	 * 自动创建科目每日一练试卷。
	 */
	void autoDailyPapers();
}