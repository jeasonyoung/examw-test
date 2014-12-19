package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.RegistrationBinding;
import com.examw.test.model.products.RegistrationBindingInfo;

/**
 * 注册码绑定数据接口。
 * @author fengwei.
 * @since 2014年8月14日 下午2:07:55.
 */
public interface IRegistrationBindingDao extends IBaseDao<RegistrationBinding>{
	/**
	 * 查询注册码绑定记录数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<RegistrationBinding> findBindings(RegistrationBindingInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RegistrationBindingInfo info);
	/**
	 * 加载注册码绑定。
	 * @param registrationId
	 * 注册码ID。
	 * @param softwareTypeId
	 * 软件类型ID。
	 * @param machine
	 * 设备机器码。
	 * @return
	 */
	RegistrationBinding loadBinding(String registrationId,String softwareTypeId,String machine);
}