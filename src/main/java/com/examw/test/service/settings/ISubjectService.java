package com.examw.test.service.settings;

import java.util.List;

import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.AreaInfo;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 科目服务接口
 * @author fengwei.
 * @since 2014年8月7日 上午10:13:56.
 */
public interface ISubjectService extends IBaseDataService<SubjectInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 加载考试下科目集合。
	 * @param examId
	 * 所属考试ID。
	 * @return
	 * 科目集合。
	 */
	List<SubjectInfo> loadAllSubjects(String examId);
	/**
	 * 加载考试科目所在地区集合。
	 * @param examId
	 * @return
	 */
	List<AreaInfo> loadSubjectAreas(String subjectId);
	/**
	 * 模型类型转换。
	 * @param subject
	 * @return
	 */
	SubjectInfo conversion(Subject subject);
}