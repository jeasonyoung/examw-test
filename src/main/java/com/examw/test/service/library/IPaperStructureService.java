package com.examw.test.service.library;

import java.util.List;
import java.util.Set;

import com.examw.model.TreeNode;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.StructureInfo;

/**
 * 试卷结构服务接口。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public interface IPaperStructureService {
	/**
	 * 加载试卷结构最大排序号。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	Integer loadMaxOrder(String paperId);
	/**
	 * 加载试卷结构集合。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 * 结构集合。
	 */
	List<StructureInfo> loadStructures(String paperId);
	/**
	 * 加载试卷结构集合。
	 * @param paperId
	 * 所属试卷ID。
	 * @param ignoreStructureId
	 * 需要排除的结构
	 * @return
	 * 结构树。
	 */
	List<TreeNode> loadStructureTree(String paperId);
	List<TreeNode> loadStructureTree(String paperId,String ignoreStructureId,boolean checkHasItem);
	/**
	 * 数据模型转换。
	 * @param structure
	 * @return
	 */
	StructureInfo conversion(Structure structure);
	StructureInfo conversion(Structure structure,boolean changeChild);
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
	/**
	 * 获取结构的科目集合
	 * @param structureId
	 * @return
	 */
	Set<Subject> loadStructureSubjects(String structureId);
}