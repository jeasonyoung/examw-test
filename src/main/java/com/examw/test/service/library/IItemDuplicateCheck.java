package com.examw.test.service.library;

import com.examw.test.model.library.ItemInfo;

/**
 * 试题重复检查校验码计算接口。
 * 
 * @author yangyong
 * @since 2014年8月21日
 */
public interface IItemDuplicateCheck {
		/**
		 * 计算校验码。
		 * @param itemInfo
		 * @return
		 */
		String computeCheckCode(ItemInfo itemInfo);
}