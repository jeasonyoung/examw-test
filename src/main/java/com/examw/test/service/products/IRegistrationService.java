package com.examw.test.service.products;

import java.util.Map;

import com.examw.test.model.products.RegistrationInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 注册码服务接口实现类
 * @author fengwei.
 * @since 2014年8月14日 下午3:06:38.
 */
public interface IRegistrationService extends IBaseDataService<RegistrationInfo>{
	/**
	 * 加载状态名称
	 * @param status 状态值
	 * @return
	 */
	String loadStatusName(Integer status);
	
	/**
	 * 获取状态映射
	 * @return
	 */
	Map<String,String> getStatusMap();
}
