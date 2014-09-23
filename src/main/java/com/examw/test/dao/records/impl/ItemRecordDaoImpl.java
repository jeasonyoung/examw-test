package com.examw.test.dao.records.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.records.IItemRecordDao;
import com.examw.test.domain.records.ItemRecord;

/**
 * 做题记录数据接口实现类
 * @author fengwei.
 * @since 2014年9月18日 上午9:51:12.
 */
public class ItemRecordDaoImpl  extends BaseDaoImpl<ItemRecord> implements IItemRecordDao{
	private static final Logger logger = Logger.getLogger(ItemRecordDaoImpl.class);
	/*
	 * 
	 * @see com.examw.test.dao.records.IItemRecordDao#findItemRecords(com.examw.test.domain.records.ItemRecord)
	 */
	@Override
	public List<ItemRecord> findItemRecords(ItemRecord info) {
		if(logger.isDebugEnabled()) logger.debug("查询[做题记录]数据...");
		this.evict(ItemRecord.class);
		String hql = "from ItemRecord ir where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
//		if(!StringUtils.isEmpty(info.getSort())){
//			hql += " order by pr." + info.getSort() + " " + info.getOrder();
//		}
		hql += "group by structureItemId order by ir.lastTime desc";
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 
	 * @see com.examw.test.dao.records.IItemRecordDao#total(com.examw.test.domain.records.ItemRecord)
	 */
	@Override
	public Long total(ItemRecord info) {
		if(logger.isDebugEnabled()) logger.debug("查询[试题收藏]数据统计...");
		String hql = "select count(*) from ItemRecord ir where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件
	private String addWhere(ItemRecord info, String hql,Map<String, Object> parameters) {
		if (!StringUtils.isEmpty(info.getStructureItemId())) {
			hql += " and (ir.structureItemId = :structureItemId)";
			parameters.put("structureItemId", info.getStructureItemId());
		}
		if (!StringUtils.isEmpty(info.getUserId())) {
			hql += " and (ir.userId = :userId)";
			parameters.put("userId", info.getUserId());
		}
		if (!StringUtils.isEmpty(info.getPaperId())) {
			hql += " and (ir.paperId = :paperId)";
			parameters.put("paperId", info.getPaperId());
		}
		if (info.getLastTime() != null){
			hql += " and (ir.lastTime > :time)";
			parameters.put("time", info.getLastTime());
		}
		return hql;
	}
	
	@Override
	public void insertItemRecordList(List<ItemRecord> list) {
		Session session = this.getCurrentSession();
		for (int i=0; i<list.size();i++) {
			session.saveOrUpdate(list.get(i));
			if (i % 20 == 0) {
				//20个对象后才清理缓存，写入数据库
				session.flush();
				session.clear();
			}
				
		}
		session.flush();
		session.clear();
	}
}
