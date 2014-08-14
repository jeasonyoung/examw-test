package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.model.TreeNode;
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
	 * 查询条件。
	 * @param ignore
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	 List<TreeNode> loadSyllabuss(String sudId,String ignore);
	 /**
	 * 加载最大代码值。
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 加载所有的大纲要点。
	 * @return
	 */
	List<TreeNode> loadAllSyllabuss(String ignore);
}
