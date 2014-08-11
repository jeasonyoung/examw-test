package com.examw.test.dao.syllabus;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.SyllabusInfo;
/**
 * 大纲数据接口。
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
	List<Syllabus> findSyllabuss(SyllabusInfo info);
	/**
	 * 查询统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(SyllabusInfo info);
	/**
	 * 加载一级大纲。
	 * @param subId
	 * @return
	 */
	List<Syllabus> loadFristSyllabuss(String subId);

	List<Syllabus> loadFristSyllabus();
}