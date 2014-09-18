package com.examw.test.service.library;

import java.util.List;

import com.examw.model.DataGrid;
import com.examw.test.model.library.PaperInfo;
import com.examw.test.model.library.PaperPreview;
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
	 * @param paperId
	 * 试卷ID。
	 * @param status
	 * 试卷状态。
	 */
	void updateStatus(String paperId,PaperStatus status);
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
	 * @param structureIds
	 * 试卷结构ID。
	 */
	void deleteStructure(String[] structureIds);
	/**
	 * 加载结构下试题数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @param info
	 * @return
	 */
	DataGrid<StructureItemInfo> loadStructureItems(String paperId, StructureItemInfo info);
	/**
	 * 加载试卷结构下最大的排序号。
	 * @param structureId
	 * 所属结构ID。
	 * @return
	 */
	Long loadStructureItemMaxOrderNo(String structureId);
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
	/**
	 * 加载试卷预览。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 * 试卷信息。
	 */
	PaperPreview loadPaperPreview(String paperId);
	/**
	 * 加载试卷基本信息[前台调用方法]
	 * @param paperId
	 * @return
	 */
	PaperPreview loadPaperInfo(String paperId);
	/**
	 * 加载试卷详细信息并且添加试卷记录[前台调用方法]
	 * @param paperId	试卷ID
	 * @param userId 	用户ID
	 * @return
	 */
	PaperPreview loadPaperPreviewAndAddRecord(String paperId,String userId);
}