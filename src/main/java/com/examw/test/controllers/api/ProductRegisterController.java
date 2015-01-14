package com.examw.test.controllers.api;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@RequestMapping(value = {"/register"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json register(String code,String userId,String productId,String machine,Integer terminalCode) {
		if(logger.isDebugEnabled()) logger.debug("注册码注册:"+code);
		Json json = productRegisterService.register(code,userId,productId,machine,terminalCode);
		return json;
	}
	/**
	 * 验证注册码[数据同步时调用]
	 * @param code
	 * @param userId
	 * @param productId
	 * @param machine
	 * @param terminalCode
	 * @return
	 */
	@RequestMapping(value = {"/verify"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json verify(String code,String userId,String productId,String machine,Integer terminalCode)
	{
		if(logger.isDebugEnabled()) logger.debug("注册码验证:"+code);
		Json json = productRegisterService.verify(code,userId,productId,machine,terminalCode);
		return json;
	}
	
	@RequestMapping(value = {"/generate"}, method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Json generateCode(String productId,String channelId,BigDecimal price,Integer limit)
	{
		if(logger.isDebugEnabled()) logger.debug(String.format("生成注册码,productId=[%1$s],channelId=[%2$s],price=[%3$s],期限=[%4$d]",productId,channelId,price,limit));
		Json json = productRegisterService.generateCode(productId,channelId,price,limit);
		return json;
	}
}
