package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.SoftwareTypeLimit;
import com.examw.test.model.products.SoftwareTypeLimitInfo;

/**
 * 软件类型限制数据接口。
 * 
 * @author yangyong
 * @since 2014年12月13日
 */
public interface ISoftwareTypeLimitDao extends IBaseDao<SoftwareTypeLimit> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<SoftwareTypeLimit> findSoftwareTypeLimits(SoftwareTypeLimitInfo info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据统计。
	 */
	Long total(SoftwareTypeLimitInfo info);
	/**
	 * 根据软件类型ID和注册码ID查询限制数据。
	 * @param softwareTypeId
	 * 软件类型ID。
	 * @param registerId
	 * 注册码ID。
	 * @return
	 * 限制数据。
	 */
	SoftwareTypeLimit loadSoftwareTypeLimit(String softwareTypeId, String registerId);
}