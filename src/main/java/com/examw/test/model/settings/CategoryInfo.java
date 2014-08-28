package com.examw.test.model.settings;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 考试类别信息
 * @author fengwei.
 * @since 2014年8月6日 上午11:37:04.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CategoryInfo extends Paging {
	private static final long serialVersionUID = 1L;
	private String id,name,abbr,pid;
	private List<CategoryInfo> children;
	private String fullName;
	private Integer code;
	/**
	 * 获取类别ID。
	 * @return 类别ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置类别ID。
	 * @param id
	 * 类别ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取类别代码。
	 * @return 类别代码。
	 */
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置类别代码。
	 * @param code
	 * 类别代码。
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/**
	 * 获取类别名称。
	 * @return 类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置类别名称。
	 * @param name
	 * 类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取EN简称。
	 * @return EN简称。
	 */
	public String getAbbr() {
		return abbr;
	}
	/**
	 * 设置EN简称。
	 * @param abbr
	 * EN简称
	 */
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}
	/**
	 * 获取 上级菜单ID。
	 * @return pid
	 * 上级菜单ID。
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 上级菜单ID。
	 * @param pid
	 * 上级菜单ID。
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取 子分类集合
	 * @return children
	 * 子分类集合
	 */
	public List<CategoryInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子分类集合
	 * @param children
	 * 子分类集合
	 */
	public void setChildren(List<CategoryInfo> children) {
		this.children = children;
	}
	/**
	 * 获取 全称
	 * @return fullName
	 * 
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * 设置 全称
	 * @param fullName
	 * 
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
