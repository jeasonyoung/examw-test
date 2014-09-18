package com.examw.test.model.records;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.IPaging;
import com.examw.support.CustomDateSerializer;
import com.examw.test.domain.records.PaperRecord;

/**
 * 试卷考试记录
 * @author fengwei.
 * @since 2014年9月17日 下午4:17:55.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PaperRecordInfo extends PaperRecord implements IPaging{
	private static final long serialVersionUID = 1L;
	private Integer rows,page;
	private String sort,order;
	private String paperName;
	private Integer type;
	private String typeName;
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
	 * 获取 试卷名称
	 * @return paperName
	 * 试卷名称
	 */
	public String getPaperName() {
		return paperName;
	}
	/**
	 * 设置 试卷名称
	 * @param paperName
	 * 试卷名称
	 */
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	/**
	 * 获取 试卷类型
	 * @return type
	 * 试卷类型
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 试卷类型
	 * @param type
	 * 试卷类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取 试卷类型名称
	 * @return typeName
	 * 试卷类型名称
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * 设置 试卷类型名称
	 * @param typeName
	 * 试卷类型名称
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
