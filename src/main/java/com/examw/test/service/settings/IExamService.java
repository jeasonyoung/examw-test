package com.examw.test.service.settings;

import java.util.List;

import com.examw.test.domain.settings.Exam;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.ExamInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 考试设置服务接口
 * @author fengwei.
 * @since 2014年8月7日 上午10:00:21.
 */
public interface IExamService  extends IBaseDataService<ExamInfo>{
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 类型转换。
	 * @param exam
	 * @return
	 */
	ExamInfo conversion(Exam exam);
	/**
	 * 加载考试所在地区集合。
	 * @param examId
	 * @return
	 */
	List<AreaInfo> loadExamAreas(String examId);
	/**
	 *  加载考试集合。
	 * @param categoryId
	 * 所属考试类别。
	 * @param status
	 * 考试状态。
	 * @return
	 */
	List<ExamInfo> loadExams(String categoryId, ExamStatus status);
	/**
	 * 加载考试信息。
	 * @param examId
	 * @return
	 */
	ExamInfo loadExam(String examId);
	/**
	 * 根据考试简称加载考试数据。
	 * @param abbr
	 * 考试简称。
	 * @return 考试数据
	 */
	Exam loadExamByAbbr(String abbr);
}