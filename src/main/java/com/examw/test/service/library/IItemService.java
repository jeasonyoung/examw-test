package com.examw.test.service.library;

import com.examw.test.domain.library.Item;
import com.examw.test.model.library.BaseItemInfo;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 题目服务接口。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
public interface IItemService extends IBaseDataService<ItemInfo> {
	/**
	 * 加载题型名称。
	 * @param type
	 * 题型值。
	 * @return
	 * 题型名称。
	 */
	String loadTypeName(Integer type);
	/**
	 * 加载题目状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载判断题答案名称。
	 * @param answer
	 * 答案值。
	 * @return
	 * 答案名称。
	 */
	String loadJudgeAnswerName(Integer answer);
	/**
	 * 计算包含的试题数量。
	 * @param source
	 * @return
	 */
	Integer calculationCount(BaseItemInfo<?> source);
	/**
	 * 更新试题状态。
	 * @param itemId
	 * 所属题目ID。
	 * @param status
	 * 状态枚举。
	 */
	void updateStatus(String itemId, ItemStatus status);
	/**
	 * 更新试题。
	 * @param info
	 * 试题信息。
	 * @return
	 * 试题对象。
	 */
	Item updateItem(BaseItemInfo<?> info);
	/**
	 * 更新试题	[Add by FW 2014.10.05 纠错时调用]
	 * @param info
	 * 试题信息
	 * @param andAudit
	 * 是否同时审核
	 * @return
	 */
	Item updateItem(BaseItemInfo<?> info,boolean andAudit);
	/**
	 * 纠正题目错误 同时还要重新发布修改试卷的状态
	 * @param info
	 */
	void recorveryItemError(BaseItemInfo<?> info);
	/**
	 * 加载试题数据。
	 * @param itemId
	 * 试题ID。
	 * @return
	 * 试题数据。
	 */
	Item loadItem(String itemId);
	/**
	 * 试题数据模型转换。
	 * @param source
	 * 试题数据。
	 * @param target
	 * 目标数据。
	 * @param isAll
	 * 是否全部转换。
	 */
	void conversion(Item source, BaseItemInfo<?> target,boolean isAll);
	/**
	 * 删除与试卷结构无关联的试题。
	 */
	Integer deleteIsolated();
	/**
	 * 强制删除试题。
	 * @param itemId
	 * 试题ID。
	 */
	void deleteStructureForced(String itemId);
	/**
	 * 重置试题的校验码。
	 * @param rows
	 * 每批数据。
	 * @param page
	 * 批次。
	 * @return 剩余批次。
	 */
	int resetCheckCode(Integer rows,Integer page);
	
	/**
	 * 强制修改子题的题型
	 * @param split
	 * @param type
	 * 2015.01.28
	 */
	String changeChildrenType(String[] split, Integer type);
}