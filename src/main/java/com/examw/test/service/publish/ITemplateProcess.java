package com.examw.test.service.publish;

import java.util.List;

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
	 * @return
	 * @throws Exception
	 */
	List<StaticPage> process() throws Exception;
}