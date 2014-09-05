package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.service.security.IUserService;
/**
 *  客户端导入控制器。
 * 
 * @author yangyong
 * @since 2014年9月4日
 */
@Controller
@RequestMapping(value = { "/api/imports" })
public class ClientImportsController {
	private static final Logger logger = Logger.getLogger(ClientImportsController.class);
	@Resource
	private IUserService userService;
	/**
	 * 身份验证。
	 * @param username
	 * @param token
	 * @return
	 */
	@RequestMapping(value = {"/authen"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json authention(String username,String token){
		if(logger.isDebugEnabled()) logger.debug(String.format("客户端身份［username=%1$s, token=%2$s］验证...", username, token));
		Json result = new Json();
		try {
			///TODO:
		} catch (Exception e) {
			result.setSuccess(false);
		    result.setMsg(e.getMessage());
		}
		return result;
	}
}