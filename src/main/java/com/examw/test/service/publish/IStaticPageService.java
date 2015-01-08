package com.examw.test.service.publish;

import java.util.List;

import com.examw.test.model.publish.StaticPageInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 静态页面服务接口。
 * 
 * @author yangyong
 * @since 2014年12月26日
 */
public interface IStaticPageService extends IBaseDataService<StaticPageInfo> {
	/**
	 * 加载静态页面内容。
	 * @param pageId
	 * 静态页面ID。
	 * @return 静态页面内容。
	 */
	 String loadPageContent(String pageId);
	 /**
	  * 加载静态页面总数。
	  * @param info
	  * 查询条件。
	  * @return
	  */
	 Long loadTotal(StaticPageInfo info);
	 /**
	  * 加载静态页面数据。
	  * @param info
	  * @return
	  */
	 List<StaticPageInfo> loadPages(StaticPageInfo info);
}