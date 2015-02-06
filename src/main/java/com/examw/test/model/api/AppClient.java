package com.examw.test.model.api;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 客户端应用。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class AppClient implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clientId,clientName,clientVersion;
	/**
	 * 获取客户端ID。
	 * @return 客户端ID。
	 */
	public String getClientId() {
		return clientId;
	}
	/**
	 * 设置客户端ID。
	 * @param clientId 
	 *	  客户端ID。
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	/**
	 * 获取客户端名称。
	 * @return 客户端名称。
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * 设置客户端名称。
	 * @param clientName 
	 *	  客户端名称。
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	/**
	 * 获取客户端版本。
	 * @return 客户端版本。
	 */
	public String getClientVersion() {
		return clientVersion;
	}
	/**
	 * 设置客户端版本。
	 * @param clientVersion 
	 *	  客户端版本。
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
}