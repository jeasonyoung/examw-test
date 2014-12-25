package com.examw.test.dao.publish;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.publish.StaticPage;
import com.examw.test.model.publish.StaticPageInfo;

/**
 * 静态页面数据接口。
 * 
 * @author yangyong
 * @since 2014年12月25日
 */
public interface IStaticPageDao extends IBaseDao<StaticPage> {
	/**
	 * 查询页面数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果数据。
	 */
	List<StaticPage> findPages(StaticPageInfo info);
	/**
	 * 查询页面数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询数据统计。
	 */
	Long total(StaticPageInfo info);
}