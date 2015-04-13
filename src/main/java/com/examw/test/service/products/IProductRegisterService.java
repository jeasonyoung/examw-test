package com.examw.test.service.products;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2015年1月13日 下午3:22:37.
 */
public interface IProductRegisterService {
	/**
	 * 注册码验证。
	 * @param productRegister 
	 * 产品注册对象。
	 * @return
	 */
	boolean verify(ProductRegister productRegister)throws Exception;
	/**
	 * 产品注册.
	 * @author fengwei
	 *
	 */
	public static class ProductRegister implements Serializable {
		private static final long serialVersionUID = 1L;
		private String code,userId,productId,machine;
		private Integer terminalCode;
		/**
		 * 获取 注册码
		 * @return code
		 * 注册码
		 */
		public String getCode() {
			return code;
		}
		/**
		 * 设置 注册码
		 * @param code
		 * 注册码
		 */
		public void setCode(String code) {
			this.code = code;
		}
		/**
		 * 获取	产品 用户ID
		 * @return userId
		 * 产品 用户ID
		 */
		public String getUserId() {
			return userId;
		}
		/**
		 * 设置 产品 用户ID
		 * @param userId
		 * 产品 用户ID
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}
		/**
		 * 获取 产品ID
		 * @return productId
		 * 产品ID
		 */
		public String getProductId() {
			return productId;
		}
		/**
		 * 设置 产品ID
		 * @param productId
		 * 产品ID
		 */
		public void setProductId(String productId) {
			this.productId = productId;
		}
		/**
		 * 获取 机器码
		 * @return machine
		 * 机器码
		 */
		public String getMachine() {
			return machine;
		}
		/**
		 * 设置 机器码
		 * @param machine
		 * 机器码
		 */
		public void setMachine(String machine) {
			this.machine = machine;
		}
		/**
		 * 获取 终端代码
		 * @return terminalCode
		 * 终端代码
		 */
		public Integer getTerminalCode() {
			return terminalCode;
		}
		/**
		 * 设置 终端代码
		 * @param terminalCode
		 * 终端代码
		 */
		public void setTerminalCode(Integer terminalCode) {
			this.terminalCode = terminalCode;
		}
	}
}