package com.examw.test.dao.products;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.products.RelationProduct;

/**
 * 关联产品数据接口
 * @author fengwei.
 * @since 2014年8月14日 下午2:54:40.
 */
public interface IRelationProductDao extends IBaseDao<RelationProduct>{
	/*
	 * 根据注册码ID删除关联的产品
	 */
	void delete(String registrationId);
	/**
	 * 根据注册码和产品ID找关联信息
	 * @param registrationId
	 * @param productId
	 * @return
	 */
	RelationProduct load(String registrationId,String productId);
}
