package com.examw.test.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.test.service.security.VerifyCodeType;
import com.examw.utils.VerifyCodeUtil;

/**
 * 工具控制器。
 * @author yangyong.
 * @since 2014-06-10.
 */
@Controller
public class HelperController {
	/**
	 * 构建uuid字符串。
	 * @return uuid字符串。
	 */
	@RequestMapping(value = "/uuid", method = RequestMethod.GET)
	@ResponseBody
	public String[] buildUUID(Integer count){
		if(count == null || count < 1) count = 1;
		String[] result = new String[count];
		for(int i = 0; i < count; i++){
			 result[i] = UUID.randomUUID().toString();
		} 
		return result;
	}
	/**
	 * 加载随机码。
	 * @param length
	 * @return
	 */
	@RequestMapping(value = {"/random/code"}, method = RequestMethod.GET)
	@ResponseBody
	public String[] randomCode(Integer type, Integer length){
		VerifyCodeType codeType = VerifyCodeType.NUM_UPPER;
		if(type != null){ codeType = VerifyCodeType.conversion(type); }
		if(length == null){ length = 6; }
		
		return new String[] { VerifyCodeUtil.generateTextCode(codeType.getValue(), length, null) };
	}
	/**
	 * 获取客户端IP地址。
	 * @param request
	 * 客户端请求对象。
	 * @return 客户端IP地址。
	 */
	public static String getRemoteAddr(HttpServletRequest request){
		if(request == null) throw new IllegalArgumentException("request");
		String client =  request.getHeader("x-forwarded-for");
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client =  request.getHeader("Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client  = request.getHeader("WL-Proxy-Client-IP");
		}
		if(StringUtils.isEmpty(client) || "unknown".equalsIgnoreCase(client)){
			client = request.getRemoteAddr();
		}
		return client;
	}
}