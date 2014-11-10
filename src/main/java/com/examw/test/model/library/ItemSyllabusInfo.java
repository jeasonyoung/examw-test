package com.examw.test.model.library;

import com.examw.test.model.syllabus.BaseSyllabusInfo;

/**
 * 试题与大纲关联信息
 * @author fengwei.
 * @since 2014年11月8日 上午11:36:47.
 */
public class ItemSyllabusInfo extends BaseSyllabusInfo{
	private static final long serialVersionUID = 1L;
	private String itemId;	

	/**
	 * 获取 试题ID
	 * @return itemId
	 * 试题ID
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * 设置 试题ID
	 * @param itemId
	 * 试题ID
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
