package com.examw.test.interceptors;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.examw.test.model.security.UserInfo;
import com.examw.test.service.security.IUserAuthorization;
import com.examw.test.service.security.IUserService;
import com.examw.utils.MD5Util;

/**
 * HTTP摘要认证服务器端拦截器。
 * 
 * @author yangyong
 * @since 2014年12月20日
 */
public class HTTPDigestAuthenticateInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(HTTPDigestAuthenticateInterceptor.class);
	private String realm;
	private IUserAuthorization userAuthorization;
	private IUserService userService;
	/**
	 * HTTP Authorization header, equal to <code>Authorization</code>
	 */
	private static final String AUTHORIZATION_HEADER = "Authorization";
	/**
     * HTTP Authentication header, equal to <code>WWW-Authenticate</code>
     */
    private static final String AUTHENTICATE_HEADER = "WWW-Authenticate";
    /**
     * 构造函数。
     */
    public HTTPDigestAuthenticateInterceptor(){
    	this.realm = "realm@examw.com";
    }
	/**
	 * 设置认证域。
	 * @param realm 
	 *	  认证域
	 */
	public void setRealm(String realm) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入认证域：%s", realm));
		this.realm = realm;
	}
	/**
	 * 设置用户认证服务接口。
	 * @param userAuthorization 
	 *	  用户认证服务接口。
	 */
	public void setUserAuthorization(IUserAuthorization userAuthorization) {
		if(logger.isDebugEnabled()) logger.debug("注入用户认证服务接口...");
		this.userAuthorization = userAuthorization;
	}
	/**
	 * 设置用户服务接口。
	 * @param userService 
	 *	  用户服务接口。
	 */
	public void setUserService(IUserService userService) {
		if(logger.isDebugEnabled()) logger.debug("注入用户服务接口...");
		this.userService = userService;
	}
	/*
	 * 重载处理。
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("开始摘要认证处理...");
		//验证摘要。
		if(!this.authentication(request.getHeader(AUTHORIZATION_HEADER))){
			//认证失败，发送401错误。
			this.sendAuthenticate(response);
			return false;
		}
		if(logger.isDebugEnabled()) logger.debug("摘要认证处理完毕!");
		return super.preHandle(request, response, handler);
	}
	//发送401错误认证信息。
	private void sendAuthenticate(HttpServletResponse response){
		if(logger.isDebugEnabled()) logger.debug("Authentication required: sending 401 Authentication challenge response.");
		final String authc_header_temple = "%1$s  realm=\"%2$s\",qop=\"auth\",nonce=\"%3$s\"";
		String header = String.format(authc_header_temple, HttpServletRequest.DIGEST_AUTH, this.realm, createRandomCode());
		if(logger.isDebugEnabled()) logger.debug(String.format("http-head:%s", header));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setHeader(AUTHENTICATE_HEADER, header);
	}
	//生成随机码。
	private static String createRandomCode(){
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
	//验证摘要。
	private synchronized boolean authentication(String authz){
		if(logger.isDebugEnabled()) logger.debug(String.format("验证摘要：%s", authz));
		//摘要信息为空
		if(StringUtils.isEmpty(authz)){
			if(logger.isDebugEnabled()) logger.debug("摘要信息为空！");
			return false;
		}
		String username_value = this.getParameter(authz, "username");
		if(StringUtils.isEmpty(username_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到username值！");
			return false;
		}
		String realm_value = this.getParameter(authz, "realm");
		if(StringUtils.isEmpty(realm_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到realm值！");
			return false;
		}
		String nonce_value = this.getParameter(authz, "nonce");
		if(StringUtils.isEmpty(nonce_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到nonce值！");
			return false;
		}
//		String uri_value = this.getParameter(authz, "uri");
//		if(StringUtils.isEmpty(uri_value)){
//			if(logger.isDebugEnabled()) logger.debug("没有获取到uri值！");
//			return false;
//		}
		String qop_value = this.getParameter(authz, "qop");
		if(StringUtils.isEmpty(qop_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到qop值！");
			return false;
		}
		String nc_value = this.getParameter(authz, "nc");
		if(StringUtils.isEmpty(nc_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到nc值！");
			return false;
		}
		String cnonce_value = this.getParameter(authz, "cnonce");
		if(StringUtils.isEmpty(cnonce_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到cnonce值！");
			return false;
		}
		String response_value = this.getParameter(authz, "response");
		if(StringUtils.isEmpty(response_value)){
			if(logger.isDebugEnabled()) logger.debug("没有获取到response值！");
			return false;
		}
		//获取用户
		UserInfo user = this.userService.conversion(this.userAuthorization.loadUserByAccount(username_value),true);
		if(user == null){
			if(logger.isDebugEnabled()) logger.debug(String.format("用户［%s］不存在！", username_value));
			return false;
		}
		String ha1 = MD5Util.MD5(username_value + ":" + realm_value + ":" + user.getPassword());
		if(logger.isDebugEnabled()) logger.debug(String.format("HA1:%s", ha1));
		String response = MD5Util.MD5(ha1 + ":" + nonce_value + ":" + nc_value + ":" + cnonce_value + ":" + qop_value);
		if(logger.isDebugEnabled()) logger.debug(String.format("response:%s", response));
		return response.equalsIgnoreCase(response);
	}
	//获取参数。
	private String getParameter(String authz,String name){
		if(StringUtils.isEmpty(authz) || StringUtils.isEmpty(name)) return null;
		String regex = name + "=((.+?,)|((.+?)$))";
		Matcher m = Pattern.compile(regex).matcher(authz);
		if(m.find()){
			String p = m.group(1);
			if(!StringUtils.isEmpty(p)){
				if(p.endsWith(",")){
					p = p.substring(0, p.length() - 1);
				}
				if(p.startsWith("\"")){
					p = p.substring(1);
				}
				if(p.endsWith("\"")){
					p = p.substring(0, p.length() - 1);
				}
				return p;
			}
		}
		return null;
	}
}