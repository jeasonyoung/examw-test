package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.RegistrationLog;
import com.examw.test.model.products.RegistrationLogInfo;

/**
 * 注册码日志数据接口
 * @author fengwei.
 * @since 2014年8月14日 上午11:56:00.
 */
public interface IRegistrationLogDao extends IBaseDao<RegistrationLog>{
	/**
	 * 查询注册码日志数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<RegistrationLog> findRegistrationLogs(RegistrationLogInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RegistrationLogInfo info);
}
