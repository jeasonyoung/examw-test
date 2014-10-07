package com.examw.test.service.library;

import java.util.List;

import com.examw.test.domain.library.Structure;
import com.examw.test.model.library.StructureInfo;

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
	List<StructureInfo> loadStructures(String paperId);
	/**
	 * 数据模型转换。
	 * @param structure
	 * @return
	 */
	StructureInfo conversion(Structure structure);
	/**
	 * 更新试卷结构。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * 试卷结构。
	 */
	void updateStructure(String paperId, StructureInfo info);
	/**
	 * 删除试卷结构。
	 * @param structureIds
	 * 试卷结构ID。
	 */
	void deleteStructure(String[] structureIds);
}