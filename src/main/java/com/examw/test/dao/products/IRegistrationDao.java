package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.model.products.RegistrationInfo;

/**
 * 注册码数据接口。
 * @author fengwei.
 * @since 2014年8月14日 上午10:25:41.
 */
public interface IRegistrationDao extends IBaseDao<Registration>{
	/**
	 * 查询注册码数据
	 * @param info 查询条件
	 * @return	产品数据
	 */
	List<Registration> findRegistrations(RegistrationInfo info);
	/**
	 * 查询统计
	 * @param info 查询条件
	 * @return	产品数据
	 */
	Long total(RegistrationInfo info);
	/**
	 * 加载注册码对象。
	 * @param code
	 * 注册码。
	 * @return 注册码对象。
	 */
	Registration loadRegistration(String code);
	/**
	 * 判断注册码是否存在。
	 * @param code
	 * 注册码。
	 * @return
	 * 是否存在。
	 */
	boolean existCode(String code);
}