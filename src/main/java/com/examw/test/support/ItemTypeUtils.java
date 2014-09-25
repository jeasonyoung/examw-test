package com.examw.test.support;

import org.springframework.ui.Model;

import com.examw.test.service.library.IItemService;
import com.examw.test.service.library.ItemType;

/**
 * 题型工具类。
 * 
 * @author yangyong
 * @since 2014年9月24日
 */
public final class ItemTypeUtils {
	/**
	 * 添加题型数据。
	 * @param itemService
	 * @param model
	 */
	public static void addAllItemType(IItemService itemService,Model model){
		if(itemService == null || model == null)return;
		addNormalItemType(itemService, model);
		//共提干
		model.addAttribute("TYPE_SHARE_TITLE_VALUE", ItemType.SHARE_TITLE.getValue());
		model.addAttribute("TYPE_SHARE_TITLE_NAME", itemService.loadTypeName(ItemType.SHARE_TITLE.getValue()));
		//共答案
		model.addAttribute("TYPE_SHARE_ANSWER_VALUE", ItemType.SHARE_ANSWER.getValue());
		model.addAttribute("TYPE_SHARE_ANSWER_NAME", itemService.loadTypeName(ItemType.SHARE_ANSWER.getValue()));
	}
	/**
	 * 添加普通题型。
	 * @param itemService
	 * @param model
	 */
	public static void addNormalItemType(IItemService itemService,Model model){
		if(itemService == null || model == null)return;
		//单选
		model.addAttribute("TYPE_SINGLE_VALUE", ItemType.SINGLE.getValue());
		model.addAttribute("TYPE_SINGLE_NAME", itemService.loadTypeName(ItemType.SINGLE.getValue()));
		//多选
		model.addAttribute("TYPE_MULTY_VALUE", ItemType.MULTY.getValue());
		model.addAttribute("TYPE_MULTY_NAME", itemService.loadTypeName(ItemType.MULTY.getValue()));
		//不定向选
		model.addAttribute("TYPE_UNCERTAIN_VALUE", ItemType.UNCERTAIN.getValue());
		model.addAttribute("TYPE_UNCERTAIN_NAME", itemService.loadTypeName(ItemType.UNCERTAIN.getValue()));
		//判断
		model.addAttribute("TYPE_JUDGE_VALUE", ItemType.JUDGE.getValue());
		model.addAttribute("TYPE_JUDGE_NAME", itemService.loadTypeName(ItemType.JUDGE.getValue()));
		//问答
		model.addAttribute("TYPE_QANDA_VALUE", ItemType.QANDA.getValue());
		model.addAttribute("TYPE_QANDA_NAME", itemService.loadTypeName(ItemType.QANDA.getValue()));
	}
}