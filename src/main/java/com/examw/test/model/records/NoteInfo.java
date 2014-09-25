package com.examw.test.model.records;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.IPaging;
import com.examw.support.CustomDateSerializer;
import com.examw.test.domain.records.Note;

/**
 * 用户试题笔记
 * @author fengwei.
 * @since 2014年9月17日 下午5:14:31.
 */
public class NoteInfo extends Note implements IPaging{
	private static final long serialVersionUID = 1L;
	private Integer rows,page;
	private String sort,order;
	private String username;
	/*
	 * 获取每页数据量
	 * @see com.examw.model.IPaging#getRows()
	 */
	@Override
	public Integer getRows() {
		return rows;
	}
	/*
	 * 设置每页数据量。
	 * @see com.examw.model.IPaging#setRows(java.lang.Integer)
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	/*
	 * 获取页码。
	 * @see com.examw.model.IPaging#getPage()
	 */
	@Override
	public Integer getPage() {
		return page;
	}
	/*
	 * 设置页码。
	 * @see com.examw.model.IPaging#setPage(java.lang.Integer)
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	
	/*
	 * 获取排序字段名称。
	 * @see com.examw.model.IPaging#getSort()
	 */
	@Override
	public String getSort() {
		return sort;
	}
	
	/*
	 * 设置排序字段名称。
	 * @see com.examw.model.IPaging#setSort(java.lang.String)
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	/*
	 * 获取排序方式。
	 * @see com.examw.model.IPaging#getOrder()
	 */
	@Override
	public String getOrder() {
		return order;
	}
	
	/*
	 * 设置排序方式。
	 * @see com.examw.model.IPaging#setOrder(java.lang.String)
	 */
	@Override
	public void setOrder(String order) {
		this.order = order;
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
	/**
	 * 获取 用户名
	 * @return username
	 * 
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 设置 用户名
	 * @param username
	 * 
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
}
