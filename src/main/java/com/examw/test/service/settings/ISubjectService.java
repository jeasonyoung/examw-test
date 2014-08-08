package com.examw.test.service.settings;

import java.util.List;

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
	 * 根据考试ID加载科目。
	 * @param examId
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<SubjectInfo> findSubject(String examId);
}
