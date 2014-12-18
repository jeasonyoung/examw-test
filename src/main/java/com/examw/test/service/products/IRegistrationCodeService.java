package com.examw.test.service.products;
/**
 * 注册码服务接口。
 * 
 * @author yangyong
 * @since 2014年12月17日
 */
public interface IRegistrationCodeService {
	/**
	 * 按位权重值常量数组。
	 */
	static final int [] WEIGHT_VALUE = new int []{16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1};
	/**
	 * 校验位取模的值。
	 */
	static final int CHECK_RESIDUE_VALUE = 99;
	/**
	 * 校验注册码格式。
	 * @param code
	 * 注册码。
	 * @return
	 * @throws Exception
	 */
	Boolean verificationFormat(String code) throws Exception;
	/**
	 * 生成注册码。
	 * @param price
	 * 价格。
	 * @param limit
	 * 有效期限。
	 * @return
	 */
	String generatedCode(int price, int limit) throws Exception;
}