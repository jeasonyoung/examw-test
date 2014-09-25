package com.examw.test.service.library;

import java.util.List;

import com.examw.test.model.library.PaperStructureInfo;

/**
 * 试卷结构服务接口。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public interface IPaperStructureService {
	/**
	 * 加载试卷结构集合。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 * 结构集合。
	 */
	List<PaperStructureInfo> loadStructures(String paperId);
	/**
	 * 更新试卷结构。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 试卷结构。
	 */
	void updateStructure(String paperId, PaperStructureInfo info);
	/**
	 * 删除试卷结构。
	 * @param structureIds
	 * 试卷结构ID。
	 */
	void deleteStructure(String[] structureIds);
}