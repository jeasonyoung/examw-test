package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.Registration;
import com.examw.test.domain.products.SoftwareType;
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
	 * 删除注册码ID下的软件类型限制。
	 * @param registerId
	 * 注册码ID。
	 */
	void deleteByRegistrationId(String registerId);
	/**
	 * 更新数据。
	 * @param register
	 * 注册码。
	 * @param type
	 * 软件类型。
	 * @param times
	 * 限制次数。
	 */
	SoftwareTypeLimit update(Registration register,SoftwareType type, Integer times);
	/**
	 * 加载软件类型和注册码的限制次数。
	 * @param softwareTypeCode
	 * 软件类型代码。
	 * @param registrationCode
	 * 注册码。
	 * @return 限制次数。
	 */
	Integer limits(String softwareTypeCode, String registrationCode)throws Exception;
}