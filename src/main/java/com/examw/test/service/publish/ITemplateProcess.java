package com.examw.test.service.publish;

import java.util.List;

import com.examw.test.domain.publish.Configuration;
import com.examw.test.domain.publish.StaticPage;

/**
 * 发布模版处理接口。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public interface ITemplateProcess {
	/**
	 * 模版处理入口。
	 * @param configuration
	 * 发布配置。
	 * @return
	 * 静态页面对象。
	 */
	List<StaticPage> process(Configuration configuration) throws Exception;
}