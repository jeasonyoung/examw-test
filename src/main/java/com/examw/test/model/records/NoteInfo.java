package com.examw.test.model.records;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.support.CustomDateSerializer;
import com.examw.test.domain.records.Note;

/**
 * 用户试题笔记
 * @author fengwei.
 * @since 2014年9月17日 下午5:14:31.
 */
public class NoteInfo extends Note{
	private static final long serialVersionUID = 1L;
	private	String itemId;
	
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

	/**
	 * 获取 创建时间
	 * @return lastTime
	 * 
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	@Override
	public Date getCreateTime() {
		return super.getCreateTime();
	}
}
