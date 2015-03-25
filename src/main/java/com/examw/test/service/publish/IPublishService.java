package com.examw.test.service.publish;
/**
 * 发布服务接口。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public interface IPublishService {
	/**
	 * 更新发布。
	 */
	void updatePublish();
	/**
	 * 更新发布。
	 * @param configId
	 * 配置ID。
	 */
	void updatePublish(String configId);
}