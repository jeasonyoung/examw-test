package com.examw.test.service.products;

import java.util.Map;

import com.examw.test.model.products.RegistrationLogInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 注册码日志服务接口
 * @author fengwei.
 * @since 2014年8月14日 下午3:53:19.
 */
public interface IRegistrationLogService extends IBaseDataService<RegistrationLogInfo>{
	/**
	 *	加载类型名称
	 * @param type
	 * @return
	 */
	String loadTypeName(Integer type);
	
	/**
	 * 获取类型映射
	 * @return
	 */
	Map<String,String> getTypeMap();
}
