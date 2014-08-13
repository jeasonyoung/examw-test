package com.examw.test.dao.products;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.Software;
import com.examw.test.model.products.SoftwareInfo;

/**
 * 产品软件数据接口
 * @author fengwei.
 * @since 2014年8月13日 上午11:31:01.
 */
public interface ISoftwareDao extends IBaseDao<Software>{
	/**
	 * 查询产品数据
	 * @param info 查询条件
	 * @return	产品数据
	 */
	List<Software> findSoftwares(SoftwareInfo info);
	/**
	 * 查询统计
	 * @param info 查询条件
	 * @return	产品数据
	 */
	Long total(SoftwareInfo info);

}
