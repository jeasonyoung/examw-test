package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.ICollectionDao;
import com.examw.test.domain.records.Collection;

/**
 * 收藏数据接口实现类
 * @author fengwei.
 * @since 2014年9月18日 上午9:12:01.
 */
public class CollectionDaoImpl extends BaseDaoImpl<Collection> implements ICollectionDao{
	private static final Logger logger = Logger.getLogger(PaperRecordDaoImpl.class);
	/*
	 * 查询数据
	 * @see com.examw.test.dao.records.ICollectionDao#findCollections(com.examw.test.domain.records.Collection)
	 */
	@Override
	public List<Collection> findCollections(Collection info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试题收藏]数据...");
		String hql = "from Collection c where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
//		if(!StringUtils.isEmpty(info.getSort())){
//			hql += " order by pr." + info.getSort() + " " + info.getOrder();
//		}
		hql += " order by c.createTime desc";
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, null, null);
	}
	
	/*
	 * 查询统计
	 * @see com.examw.test.dao.records.ICollectionDao#total(com.examw.test.domain.records.Collection)
	 */
	@Override
	public Long total(Collection info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试题收藏]数据统计...");
		String hql = "select count(*) from Collection c where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(Collection info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getStructureItemId())) {
			hql += " and (c.structureItemId = :structureItemId)";
			parameters.put("structureItemId", info.getStructureItemId());
		}
		if (!StringUtils.isEmpty(info.getItemId())) {
			hql += " and (c.itemId = :itemId)";
			parameters.put("itemId", info.getUserId());
		}
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (c.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		return hql;
	}
	
	/*
	 * 判断用户是否收藏了题目
	 * @see com.examw.test.dao.records.ICollectionDao#isCollected(java.lang.String, java.lang.String)
	 */
	@Override
	public Collection loadCollection(String structureItemId, String userId) {
		if(logger.isDebugEnabled()) logger.debug("查询用户是否收藏了题目...");
		String hql = "from Collection c where c.structureItemId = :structureItemId and c.userId = :userId";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("structureItemId", structureItemId);
		query.setParameter("userId", userId);
		@SuppressWarnings("unchecked")
		List<Collection> list = query.list();
		if(list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}
