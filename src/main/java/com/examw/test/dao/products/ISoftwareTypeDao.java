package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.SoftwareType;
import com.examw.test.model.products.SoftwareTypeInfo;

/**
 * 软件类型数据接口
 * @author fengwei.
 * @since 2014年8月11日 下午4:07:14.
 */
public interface ISoftwareTypeDao extends IBaseDao<SoftwareType>{
	/**
	 * 查询软件类型用户数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<SoftwareType> findSoftwareTypes(SoftwareTypeInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SoftwareTypeInfo info);
}
