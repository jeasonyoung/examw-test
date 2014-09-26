package com.examw.test.model.library;

import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
/**
 * 试卷结构信息。
 * @author yangyong.
 * @since 2014-08-07.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class StructureInfo extends PaperStructureInfo {
	private static final long serialVersionUID = 1L;
	private Set<StructureItemInfo> items;
	/**
	 * 获取结构上试题信息集合。
	 * @return 结构上试题信息集合。
	 */
	public Set<StructureItemInfo> getItems() {
		return items;
	}
	/**
	 * 设置结构上试题信息集合。
	 * @param items 
	 *	  结构上试题信息集合。
	 */
	public void setItems(Set<StructureItemInfo> items) {
		this.items = items;
	}
}