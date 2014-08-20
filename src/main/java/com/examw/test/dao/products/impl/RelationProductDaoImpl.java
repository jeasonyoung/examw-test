package com.examw.test.dao.products.impl;

import java.util.List;

import org.hibernate.Query;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.products.IRelationProductDao;
import com.examw.test.domain.products.RelationProduct;

/**
 * 关联产品数据接口实现类
 * @author fengwei.
 * @since 2014年8月14日 下午2:55:34.
 */
public class RelationProductDaoImpl extends BaseDaoImpl<RelationProduct> implements IRelationProductDao{
	/*
	 * 根据注册码ID删除关联数据
	 * @see com.examw.test.dao.products.IRelationProductDao#delete(java.lang.String)
	 */
	@Override
	public void delete(String registrationId) {
		String hql = "delete from RelationProduct rp where rp.registration.id = :registrationId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("registrationId", registrationId);
		query.executeUpdate();
	}
	@Override
	public RelationProduct load(String registrationId, String productId) {
		String hql = "from RelationProduct rp where rp.registration.id = :registrationId and rp.product.id = :productId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("registrationId", registrationId);
		query.setParameter("productId", productId);
		@SuppressWarnings("unchecked")
		List<RelationProduct> list = query.list();
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
}
