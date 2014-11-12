package com.examw.test.model.syllabus;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 大纲信息基类
 * @author fengwei.
 * @since 2014年11月8日 上午11:13:16.
 */
@JsonSerialize(include =Inclusion.NON_NULL)
public class BaseSyllabusInfo  extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String pid,id,title,fullTitle;
	/**
	 * 获取 父ID
	 * @return pid
	 * 父ID
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 父ID
	 * @param pid
	 * 父ID
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 要点
	 * @return title
	 * 要点
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置 要点
	 * @param title
	 * 要点
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取  完整的要点
	 * @return fullTitle
	 * 完整的要点
	 */
	public String getFullTitle() {
		return fullTitle;
	}
	/**
	 * 设置 完整的要点
	 * @param fullTitle
	 * 完整的要点
	 */
	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}
	
}
