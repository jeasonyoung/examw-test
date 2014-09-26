package com.examw.test.service.library;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.StructureItemInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 试卷题目服务接口。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public interface IPaperItemService extends IBaseDataService<StructureItemInfo> {
	/**
	 * 加载试卷结构下最大的排序号。
	 * @param structureId
	 * 所属结构ID。
	 * @return
	 */
	Long loadStructureItemMaxOrderNo(String structureId);
	/**
	 * 数据模型转换。
	 * @param source
	 * @return
	 */
	StructureItemInfo conversion(StructureItem source);
}