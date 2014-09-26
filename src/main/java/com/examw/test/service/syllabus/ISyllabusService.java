package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.test.domain.syllabus.Syllabus;
import com.examw.test.model.syllabus.SyllabusInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 大纲服务接口。
 * @author lq.
 * @since 2014-08-06.
 */
public interface ISyllabusService extends IBaseDataService<SyllabusInfo> {
	/**
	 * 加载科目下大纲树数据。
	 * @param sudId
	 * 所属科目ID。
	 * @param ignore
	 * 需忽略的大纲ID。
	 * @return
	 * 大纲节点集合。
	 */
	 List<TreeNode> loadSyllabuses(String subjectId,String ignore);
	 /**
	  * 加载科目下考试大纲集合。
	  * @param subjectId
	  * 所属科目ID。
	  * @return
	  * 考试大纲集合。
	  */
	 List<SyllabusInfo> loadSyllabuses(String subjectId);
	 /**
	 * 加载最大代码值。
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 数据模型转换。
	 * @param syllabus
	 * 转换源数据。
	 * @return
	 * 目标数据。
	 */
	SyllabusInfo conversion(Syllabus syllabus);
	/**
	 * 加载考试大纲数据。
	 * @param syllabusId
	 * 考试大纲ID。
	 * @return
	 */
	Syllabus loadSyllabus(String syllabusId);
}