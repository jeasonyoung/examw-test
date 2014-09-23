package com.examw.test.dao.settings;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.SubjectInfo;
/**
 * 科目数据接口
 * @author fengwei.
 * @since 2014年8月6日 下午1:44:30.
 */
public interface ISubjectDao extends IBaseDao<Subject>{
	/**
	 * 查询科目数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Subject> findSubjects(SubjectInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubjectInfo info);
}