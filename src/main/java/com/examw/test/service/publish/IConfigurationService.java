package com.examw.test.service.publish;

import com.examw.test.model.publish.ConfigurationInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 发布配置服务接口。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public interface IConfigurationService extends IBaseDataService<ConfigurationInfo> {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载模版名称。
	 * @param template
	 * 模版值。
	 * @return 模版名称。
	 */
	String loadTemplateName(Integer template);
}