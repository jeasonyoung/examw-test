package com.examw.test.model.api;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 登录用户信息。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginUser extends AppClient {
	private static final long serialVersionUID = 1L;
	private String account,password;
	/**
	 * 获取用户名。
	 * @return 用户名。
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置用户名。
	 * @param account 
	 *	  用户名。
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取用户密码。
	 * @return 用户密码。
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置用户密码。
	 * @param password 
	 *	  用户密码。
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}