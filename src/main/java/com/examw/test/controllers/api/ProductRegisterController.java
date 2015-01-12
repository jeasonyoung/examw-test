package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.examw.model.Json;
import com.examw.test.service.products.IRegistrationService;

/**
 * 产品注册控制器
 * @author fengwei.
 * @since 2015年1月12日 上午11:18:37.
 */
@Controller
@RequestMapping(value = { "/api/data/register" })
public class ProductRegisterController {
	private static final Logger logger = Logger.getLogger(ProductRegisterController.class);
	
	//用户试卷记录服务。
	@Resource
	private IRegistrationService registrationService;
	
	
	public Json verifyCode(String code)
	{
		Json json = new Json();
		//先检查格式
		
		//再检查合法性
		return json;
	}
}
