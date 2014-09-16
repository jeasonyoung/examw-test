package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.IPaging;
import com.examw.support.CustomDateSerializer;
import com.examw.test.domain.products.ProductUser;

/**
 * 产品用户信息
 * @author fengwei.
 * @since 2014年8月11日 下午3:25:08.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductUserInfo extends ProductUser implements IPaging{
	private static final long serialVersionUID = 1L;
	private String statusName;
	private Integer rows,page;
	private String sort,order;
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
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@Override
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return super.getCreateTime();
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@Override
	@JsonSerialize(using=CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return super.getLastTime();
	}
	/**
	 * 获取 状态名称
	 * @return statusName
	 * 
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置 状态名称
	 * @param statusName
	 * 
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
