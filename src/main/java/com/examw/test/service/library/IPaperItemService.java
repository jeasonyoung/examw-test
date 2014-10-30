package com.examw.test.service.library;
import com.examw.test.domain.library.Structure;
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
	 * 加载试卷结构。
	 * @param structureId
	 * 所属试卷结构ID。
	 * @return
	 */
	Structure loadStructure(String structureId);
	/**
	 * 加载试卷试题。
	 * @param structureId
	 * 所属试卷结构ID。
	 * @param itemId
	 * 试题ID。
	 * @return
	 */
	StructureItemInfo loadPaperItem(String structureId,String itemId);
	/**
	 * 加载试卷结构下试题最大的排序号。
	 * @param structureId
	 * 所属结构ID。
	 * @return
	 */
	Integer loadMaxOrder(String structureId);
	/**
	 * 数据模型转换。
	 * @param source
	 * @param isAll
	 * @return
	 */
	StructureItemInfo conversion(StructureItem source,boolean isAll);
	/**
	 * 删除试卷结构与试题的关联。
	 * @param structureId
	 * 试卷结构ID。
	 * @param itemId
	 * 试题ID。
	 * @param isForced
	 * 是否删除试题。
	 */
	void delete(String structureId,String itemId,boolean isForced);
}