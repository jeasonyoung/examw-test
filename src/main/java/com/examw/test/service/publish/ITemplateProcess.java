package com.examw.test.service.publish;

import java.util.LinkedList;
import java.util.Queue;

import com.examw.test.domain.publish.PublishRecord;
/**
 * 发布模版处理接口。
 * 
 * @author yangyong
 * @since 2014年12月30日
 */
public interface ITemplateProcess {
	//静态页面ID队列
	final static Queue<String> STATICPAGEID_QUEUE = new LinkedList<String>();
	/**
	 * 模版静态化处理入口。
	 * @param record
	 * 发布记录。
	 * @return 生成页面数。
	 * @throws Exception
	 */
	int addProcess(PublishRecord record) throws Exception;
	/**
	 * 清除缓存。
	 */
	void cleanCache();
}