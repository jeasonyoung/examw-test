package com.examw.test.service.products;

import java.math.BigDecimal;

import com.examw.model.Json;

/**
 * 
 * @author fengwei.
 * @since 2015年1月13日 下午3:22:37.
 */
public interface IProductRegisterService {
	/**
	 * 注册码注册
	 * @param code
	 * @param userId
	 * @param productId
	 * @return
	 */
	Json register(String code, String userId, String productId,String machine,Integer terminalCode);
	/**
	 * 注册码验证
	 * @param code
	 * @param userId
	 * @param productId
	 * @param machine
	 * @param terminalCode
	 * @return
	 */
	Json verify(String code, String userId, String productId, String machine,
			Integer terminalCode);
	/**
	 * 生成注册码
	 * @param productId
	 * @param channelId
	 * @param price
	 * @param limit
	 * @return
	 */
	Json generateCode(String productId, String channelId, BigDecimal price,
			Integer limit);
}
