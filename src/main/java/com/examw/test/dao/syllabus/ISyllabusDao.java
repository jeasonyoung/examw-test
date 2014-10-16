package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.SyllabusInfo;
/**
 * 大纲设置数据接口。
 * @author lq.
 * @since 2014-08-06.
 */
public interface ISyllabusDao extends IBaseDao<Syllabus>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<Syllabus> findSyllabuses(SyllabusInfo info);
	/**
	 * 查询科目下的大纲集合。
	 * @param subjectId
	 * 所属科目ID。
	 * @return
	 * 大纲集合。
	 */
	List<Syllabus> findSyllabusesBySubject(String subjectId);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(SyllabusInfo info);
	/**
	 * 加载科目下的最新考试大纲。
	 * @param subjectId
	 * 所属科目。
	 * @return
	 * 查询结果。
	 */
	Syllabus loadSyllabussLast(String subjectId);
	/**
	 * 加载最大排序号。
	 * @param parentSyllabusId
	 * 上级大纲ID。
	 * @return
	 */
	Integer loadMaxOrder(String parentSyllabusId);
}