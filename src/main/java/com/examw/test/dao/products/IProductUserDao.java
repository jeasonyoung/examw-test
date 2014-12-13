package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.ProductUser;
import com.examw.test.model.products.ProductUserInfo;

/**
 * 产品用户数据接口。
 * @author fengwei.
 * @since 2014年8月11日 下午4:00:55.
 */
public interface IProductUserDao extends IBaseDao<ProductUser> {
	/**
	 * 根据用户代码加载数据。
	 * @param code
	 * 用户代码。
	 * @return
	 * 用户数据。
	 */
	ProductUser loadUserByCode(String code);
	/**
	 * 查询产品用户数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<ProductUser> findProductUsers(ProductUserInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(ProductUserInfo info);
}