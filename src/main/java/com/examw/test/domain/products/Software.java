package com.examw.test.domain.products;

import java.io.Serializable;
import java.util.Date;

/**
 * 产品软件。
 * @author yangyong.
 * @since 2014-08-04.
 */
public class Software implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,content,img,url;
	private Product product;
	private SoftwareType type;
	private Channel channel;
	private float version;
	private Date createTime,lastTime;
	private Long count;
	private Integer status;
	private Integer code;
	/**
	 * 状态－正常。
	 */
	public static final int STATUS_NONE = 0;
	/**
	 * 状态－删除。
	 */
	public static final int STATUS_DELETE = -1;
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
	public Integer getCode() {
		return code;
	}
	/**
	 * 设置软件代码。
	 * @param code 
	 *	  软件代码。
	 */
	public void setCode(Integer code) {
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
	 * 获取所属产品。
	 * @return 所属产品。
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 设置所属产品。
	 * @param product 
	 *	  所属产品。
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 获取软件类型。
	 * @return 软件类型。
	 */
	public SoftwareType getType() {
		return type;
	}
	/**
	 * 设置软件类型。
	 * @param type 
	 *	  软件类型。
	 */
	public void setType(SoftwareType type) {
		this.type = type;
	}
	/**
	 * 获取版本。
	 * @return 版本。
	 */
	public float getVersion() {
		return version;
	}
	/**
	 * 设置版本。
	 * @param version 
	 *	 版本。
	 */
	public void setVersion(float version) {
		this.version = version;
	}
	/**
	 * 获取所属渠道。
	 * @return 所属渠道。
	 */
	public Channel getChannel() {
		return channel;
	}
	/**
	 * 设置所属渠道。
	 * @param channel 
	 *	  所属渠道。
	 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	/**
	 * 获取创建时间。
	 * @return 创建时间。
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置创建时间。
	 * @param createTime 
	 *	  创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取最后时间。
	 * @return 最后时间。
	 */
	public Date getLastTime() {
		return lastTime;
	}
	/**
	 * 设置最后时间。
	 * @param lastTime 
	 *	  最后时间。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	/**
	 * 获取下载次数。
	 * @return 下载次数。
	 */
	public Long getCount() {
		return count;
	}
	/**
	 * 设置下载次数。
	 * @param count 
	 *	 下载次数。
	 */
	public void setCount(Long count) {
		this.count = count;
	}
	/**
	 * 获取状态。
	 * @return 状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置状态
	 * @param status 
	 *	  状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}