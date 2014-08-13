package com.examw.test.service.library;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.StructureInfo;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 试卷服务接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface IPaperService extends IBaseDataService<PaperInfo> {
	/**
	 * 获取试卷类型名称。
	 * @param type
	 * 试卷类型值。
	 * @return
	 * 试卷类型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 获取试卷状态名称。
	 * @param status
	 * 试卷状态值。
	 * @return
	 * 试卷状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 更新试卷状态。
	 * @param id
	 * 试卷ID。
	 * @param status
	 * 试卷状态。
	 */
	void updateStatus(String id,PaperStatus status);
	/**
	 * 获取试卷结构集合。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 * 试卷结构集合。
	 */
	List<StructureInfo> loadStructures(String paperId);
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
	 * @param paperId
	 * 所属试卷ID。
	 * @param structureId
	 * 试卷结构ID。
	 */
	void deleteStructure(String paperId,String... structureId);
	/**
	 * 加载结构下试题数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * @return
	 */
	DataGrid<StructureItemInfo> loadStructureItems(String paperId, StructureItemInfo info);
	/**
	 * 更新结构下试题数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * @return
	 */
	StructureItemInfo updateStructureItem(String paperId,StructureItemInfo info);
	/**
	 * 删除结构下试题。
	 * @param ids
	 * 结构下试题ID。
	 */
	void deleteStructureItem(String[] ids);
}