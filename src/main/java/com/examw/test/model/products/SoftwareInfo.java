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
public class SoftwareInfo extends Paging{
	private static final long serialVersionUID = 1L;
	private String id,code,name,content,img,url;
	private String productId,productName;
	private String softTypeId,softTypeName;
	private String channelId,channelName;
	private float version;
	private Date createTime,lastTime;
	private Long count;
	private Integer status;
	private String statusName;
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
	 * 获取软件代码。
	 * @return 软件代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置软件代码。
	 * @param code 
	 *	  软件代码。
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取 创建时间
	 * @return createTime
	 * 
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 创建时间
	 * @param createTime
	 * 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取 最后修改时间
	 * @return lastTime
	 * 
	 */
	@JsonSerialize(using = CustomDateSerializer.LongDate.class)
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置 最后修改时间
	 * @param lastTime
	 * 
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
	/**
	 * 获取 所属产品ID
	 * @return productId
	 * 
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * 设置 所属产品ID
	 * @param productId
	 * 
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * 获取 所属产品名称
	 * @return productName
	 * 
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * 设置 所属产品名称
	 * @param productName
	 * 
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * 获取 软件类型ID
	 * @return softTypeId
	 * 
	 */
	public String getSoftTypeId() {
		return softTypeId;
	}
	/**
	 * 设置 软件类型ID
	 * @param softTypeId
	 * 
	 */
	public void setSoftTypeId(String softTypeId) {
		this.softTypeId = softTypeId;
	}
	/**
	 * 获取 软件类型名称
	 * @return softTypeName
	 * 
	 */
	public String getSoftTypeName() {
		return softTypeName;
	}
	/**
	 * 设置 软件类型名称
	 * @param softTypeName
	 * 
	 */
	public void setSoftTypeName(String softTypeName) {
		this.softTypeName = softTypeName;
	}
	/**
	 * 获取 渠道ID
	 * @return channelId
	 * 
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * 设置 渠道ID
	 * @param channelId
	 * 
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * 获取 渠道名称
	 * @return channelName
	 * 
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * 设置 渠道名称
	 * @param channelName
	 * 
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * 获取 版本
	 * @return version
	 * 
	 */
	public float getVersion() {
		return version;
	}
	/**
	 * 设置 版本
	 * @param version
	 * 
	 */
	public void setVersion(float version) {
		this.version = version;
	}
	/**
	 * 获取 下载次数
	 * @return count
	 * 
	 */
	public Long getCount() {
		return count;
	}
	/**
	 * 设置 下载次数
	 * @param count
	 * 
	 */
	public void setCount(Long count) {
		this.count = count;
	}
	/**
	 * 获取 状态
	 * @return status
	 * 
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
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
