package com.examw.test.dao.publish;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.publish.Configuration;
import com.examw.test.model.publish.ConfigurationInfo;

/**
 * 发布配置数据接口。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public interface IConfigurationDao extends IBaseDao<Configuration> {
	/**
	 * 查询发布配置数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结构数据。
	 */
	List<Configuration> findConfigurations(ConfigurationInfo info);
	/**
	 * 查询发布配置数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 统计结果。
	 */
	Long total(ConfigurationInfo info);
	/**
	 * 加载当前可用发布配置。
	 * @return
	 * 当前可用发布配置。
	 */
	Configuration loadTopConfiguration();
}