package com.examw.test.dao.settings;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.settings.Area;
import com.examw.test.model.settings.AreaInfo;

/**
 * 行政地区数据接口
 * @author fengwei.
 * @since 2014年8月6日 上午11:54:12.
 */
public interface IAreaDao extends IBaseDao<Area>{
	/**
	 * 查询地区数据
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<Area> findAreas(AreaInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(AreaInfo info);
}
