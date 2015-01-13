package com.examw.test.controllers.api;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.examw.model.Json;
import com.examw.test.service.products.IProductRegisterService;

/**
 * 产品注册控制器
 * 
 * @author fengwei.
 * @since 2015年1月12日 上午11:18:37.
 */
@Controller
@RequestMapping(value = { "/api/data/register" })
public class ProductRegisterController {
	private static final Logger logger = Logger
			.getLogger(ProductRegisterController.class);

	// 用户试卷记录服务。
	@Resource
	private IProductRegisterService productRegisterService;
	
	/**
	 * 注册码注册
	 * @param code
	 * @return
	 */
	public Json register(String code,String userId,String productId,String machine,Integer terminalCode) {
		if(logger.isDebugEnabled()) logger.debug("注册码注册:"+code);
		Json json = productRegisterService.register(code,userId,productId,machine,terminalCode);
		return json;
	}
	
	public Json verify(String code,String userId,String productId,String machine,Integer terminalCode)
	{
		if(logger.isDebugEnabled()) logger.debug("注册码验证:"+code);
		Json json = productRegisterService.verify(code,userId,productId,machine,terminalCode);
		return json;
	}
}
