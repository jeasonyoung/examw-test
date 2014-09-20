package com.examw.test.service.settings;

import com.examw.test.domain.settings.Exam;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 考试设置服务接口
 * @author fengwei.
 * @since 2014年8月7日 上午10:00:21.
 */
public interface IExamService  extends IBaseDataService<ExamInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 根据ID加载考试[前台调用方法]
	 * @return
	 */
	Exam loadExam(String examId);
	/**
	 * 类型转换。
	 * @param exam
	 * @return
	 */
	ExamInfo conversion(Exam exam);
}