package com.examw.test.model.records;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.support.CustomDateSerializer;
import com.examw.test.domain.records.ItemRecord;
import com.examw.test.model.library.ItemInfo;

/**
 * 做题记录信息
 * @author fengwei.
 * @since 2014年9月17日 下午4:34:28.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ItemRecordInfo extends ItemRecord{
	private static final long serialVersionUID = 1L;
	private ItemInfo itemInfo;
	/**
	 * 获取 试题信息
	 * @return itemInfo
	 * 试题信息
	 */
	public ItemInfo getItemInfo() {
		return itemInfo;
	}
	/**
	 * 设置 试题信息
	 * @param itemInfo
	 * 试题信息
	 */
	public void setItemInfo(ItemInfo itemInfo) {
		this.itemInfo = itemInfo;
	}
	/**
	 * 获取 上次考试时间
	 * @return lastTime
	 * 
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return super.getLastTime();
	}
}
