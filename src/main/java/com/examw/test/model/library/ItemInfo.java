package com.examw.test.model.library;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 题目信息。
 * @author yangyong
 * @since 2014年8月7日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class ItemInfo extends BaseItemInfo<ItemInfo> {
	private static final long serialVersionUID = 1L;
	private Set<ItemInfo> children;
	/*
	 * 获取子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#getChildren()
	 */
	@Override
	public Set<ItemInfo> getChildren() {
		return children;
	}
	/*
	 * 设置子题目集合。
	 * @see com.examw.test.model.library.BaseItemInfo#setChildren(java.util.Set)
	 */
	@Override
	public void setChildren(Set<ItemInfo> children) {
		this.children = children;
	}
}