package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.RegistrationBindRecord;
import com.examw.test.model.products.RegistrationBindRecordInfo;

/**
 * 注册码绑定记录数据接口
 * @author fengwei.
 * @since 2014年8月14日 下午2:07:55.
 */
public interface IRegistrationBindRecordDao extends IBaseDao<RegistrationBindRecord>{
	/**
	 * 查询注册码绑定记录数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<RegistrationBindRecord> findRecords(RegistrationBindRecordInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RegistrationBindRecordInfo info);
}	
