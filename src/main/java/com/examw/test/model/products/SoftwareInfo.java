package com.examw.test.model.products;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;
import com.examw.support.CustomDateSerializer;

/**
 * 产品软件信息
 * @author fengwei.
 * @since 2014年8月12日 下午2:07:55.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SoftwareInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,name,content,img,url,categoryId,examId,examName,productId,productName,softTypeId,softTypeName,channelId,channelName,statusName;
	private float version;
	private Integer count,orderNo,status;
	private Date createTime,lastTime; 
	/**
	 * 获取软件ID。
	 * @return 软件ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置软件ID。
	 * @param id 
	 *	  软件ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取软件名称。
	 * @return 软件名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置软件名称。
	 * @param name 
	 *	  软件名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取软件版本。
	 * @return 软件版本。
	 */
	public float getVersion() {
		return version;
	}
	/**
	 * 设置软件版本。
	 * @param version
	 * 软件版本。
	 */
	public void setVersion(float version) {
		this.version = version;
	}
	/**
	 * 获取软件状态。
	 * @return 软件状态。
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置软件状态。
	 * @param status
	 * 	软件状态。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取软件状态名称。
	 * @return 软件状态名称。
	 */
	public String getStatusName() {
		return statusName;
	}
	/**
	 * 设置软件状态名称。
	 * @param statusName
	 * 软件状态名称。
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	/**
	 * 获取宣传图片。
	 * @return 宣传图片。
	 */
	public String getImg() {
		return img;
	}
	/**
	 * 设置宣传图片。
	 * @param img 
	 *	  宣传图片。
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取下载地址。
	 * @return 下载地址。
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置下载地址。
	 * @param url 
	 *	 下载地址。
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * 获取软件介绍。
	 * @return 软件介绍。
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置软件介绍。
	 * @param content 
	 *	  软件介绍。
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取下载次数。
	 * @return 下载次数。
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置下载次数。
	 * @param count
	 * 下载次数。
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取所属考试类别ID。
	 * @return 所属考试类别ID。
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * 设置所属考试类别ID。
	 * @param categoryId 
	 *	  所属考试类别ID。
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * 获取所属考试ID。
	 * @return 所属考试ID。
	 */
	public String getExamId() {
		return examId;
	}
	/**
	 * 设置所属考试ID。
	 * @param examId
	 * 所属考试ID。
	 */
	public void setExamId(String examId) {
		this.examId = examId;
	}
	/**
	 * 获取所属考试名称。
	 * @return 所属考试名称。
	 */
	public String getExamName() {
		return examName;
	}
	/**
	 * 设置所属考试名称。
	 * @param examName
	 * 所属考试名称。
	 */
	public void setExamName(String examName) {
		this.examName = examName;
	}
	/**
	 * 获取所属产品ID。
	 * @return 所属产品ID。
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置所属产品ID。
	 * @param productId
	 * 所属产品ID。
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取所属产品名称。
	 * @return 所属产品名称。
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置所属产品名称。
	 * @param productName
	 * 所属产品名称。
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取所属软件类型ID。
	 * @return 所属软件类型ID。
	 */
	public String getSoftTypeId() {
		return softTypeId;
	}
	/**
	 * 设置所属软件类型ID。
	 * @param softTypeId
	 * 	所属软件类型ID。
	 */
	public void setSoftTypeId(String softTypeId) {
		this.softTypeId = softTypeId;
	}
	/**
	 * 获取所属软件类型名称。
	 * @return 所属软件类型名称。
	 */
	public String getSoftTypeName() {
		return softTypeName;
	}
	/**
	 * 设置所属软件类型名称。
	 * @param softTypeName
	 * 所属软件类型名称。
	 */
	public void setSoftTypeName(String softTypeName) {
		this.softTypeName = softTypeName;
	}
	/**
	 * 获取所属渠道ID。
	 * @return 所属渠道ID。
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * 设置所属渠道ID。
	 * @param channelId
	 *  所属渠道ID。
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取所属渠道名称。
	 * @return 所属渠道名称。
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * 设置所属渠道名称。
	 * @param channelName
	 * 所属渠道名称。
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime
	 * 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取排序号。
	 * @return 排序号。
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * 设置排序号。
	 * @param orderNo 
	 *	  排序号。
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * 获取最后修改时间。
	 * @return 最后修改时间。
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后修改时间。
	 * @param lastTime
	 * 最后修改时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
}