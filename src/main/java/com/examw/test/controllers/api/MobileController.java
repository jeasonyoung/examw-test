package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.model.Json;
import com.examw.test.model.api.LoginUser;
import com.examw.test.model.api.RegisterUser;
import com.examw.test.service.api.IHostAccessProxyService;

/**
 * 移动接口控制器。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
@Controller
@RequestMapping(value = { "/api/m" })
public class MobileController {
	private static final Logger logger = Logger.getLogger(MobileController.class);
	//注入访问中华考试网代理服务接口。
	@Resource
	private IHostAccessProxyService  hostAccessProxyService;
	/**
	 * 注册新用户
	 * @param info
	 * 注册新用户信息。
	 * @return 反馈数据。
	 */
	@RequestMapping(value = {"/user/register"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json userRegister(@RequestBody RegisterUser info){
		if(logger.isDebugEnabled()) logger.debug("注册新用户...");
		Json  result = new Json();
		try {
			if(info != null){
				this.hostAccessProxyService.registerUser(info);
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
	/**
	 * 用户登录。
	 * @param info
	 * @return
	 */
	@RequestMapping(value = {"/user/login"}, method = {RequestMethod.POST})
	@ResponseBody
	public Json userLogin(@RequestBody LoginUser info){
		if(logger.isDebugEnabled()) logger.debug("用户登录...");
		Json  result = new Json();
		try {
			if(info != null){
				result.setData(this.hostAccessProxyService.login(info));
				result.setSuccess(true);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}
		return result;
	}
}