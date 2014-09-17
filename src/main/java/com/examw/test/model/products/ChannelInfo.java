package com.examw.test.model.products;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.IPaging;
import com.examw.test.domain.products.Channel;

/**
 * 销售渠道信息。
 * @author fengwei.
 * @since 2014年8月11日 下午3:15:30.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ChannelInfo extends Channel implements IPaging{
	private static final long serialVersionUID = 1L;
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
}
