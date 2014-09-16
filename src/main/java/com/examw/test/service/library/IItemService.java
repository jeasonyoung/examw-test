package com.examw.test.service.library;

import java.util.Set;

import com.examw.test.domain.library.Item;
import com.examw.test.domain.library.StructureShareItemScore;
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
	 * 加载类型名称。
	 * @param opt
	 * 类型值。
	 * @return
	 * 类型名称。
	 */
	String loadOptName(Integer opt);
	/**
	 * 加载判断题答案名称。
	 * @param answer
	 * 答案值。
	 * @return
	 * 答案名称。
	 */
	String loadJudgeAnswerName(Integer answer);
	/**
	 * 更新题目状态。
	 * @param itemId
	 * 所属题目ID。
	 * @param status
	 * 状态枚举。
	 */
	void updateStatus(String itemId, ItemStatus status);
	/**
	 * 更新题目。
	 * @param info
	 * 题目信息。
	 * @return
	 * 题目对象。
	 */
	Item updateItem(BaseItemInfo<?> info,Set<StructureShareItemScore> shareItemScores);
	/**
	 * 加载试题预览。
	 * @param itemId
	 * 试题ID。
	 * @return
	 * 试题预览信息。
	 */
	ItemInfo loadItemPreview(String itemId);
}