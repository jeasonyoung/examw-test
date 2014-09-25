package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IStructureItemDao;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.StructureItemInfo;

/**
 * 结构下试题数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月13日
 */
public class StructureItemDaoImpl extends BaseDaoImpl<StructureItem> implements IStructureItemDao {
	private static final Logger logger = Logger.getLogger(StructureItemDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.library.IStructureItemDao#findStructureItems(java.lang.String, com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public List<StructureItem> findStructureItems(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from StructureItem s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("TypeName")){
				info.setSort("item.type");
			}
			if(info.getSort().equalsIgnoreCase("content")){
				info.setSort("item.content");
			}
			hql += " order by s." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.library.IStructureItemDao#total(com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public Long total(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from StructureItem s where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(StructureItemInfo info,String hql,Map<String,Object> parameters){
		if(!StringUtils.isEmpty(info.getPaperId())){
			hql += " and (s.structure.id in (select st.id from Structure st  where st.paper.id = :paperId)) ";
			parameters.put("paperId", info.getPaperId());
		}
		if(!StringUtils.isEmpty(info.getStructureId())){
			hql += " and (s.structure.id = :structureId) ";
			parameters.put("structureId", info.getStructureId());
		}
		if(!StringUtils.isEmpty(info.getContent())){
			hql += " and (s.item.content like :content) ";
			parameters.put("content", "%"+ info.getContent() +"%");
		}
		return hql;
	}
	/*
	 * 加载指定结构下的最大的排序号。
	 * @see com.examw.test.dao.library.IStructureItemDao#loadMaxOrderNo(java.lang.String)
	 */
	@Override
	public Long loadMaxOrderNo(String structureId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载指定结构［%s］下的最大排序号...", structureId));
		if(StringUtils.isEmpty(structureId)) return null;
		final String hql = "select max(s.orderNo) from StructureItem s where s.structure.id = :structureId order by s.orderNo desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null : (long)((int)obj);
	}
	/*
	 * 统计结构下题目总数。
	 * @see com.examw.test.dao.library.IStructureItemDao#totalItems(java.lang.String)
	 */
	@Override
	public Long totalItems(String structureId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("结构［structureId = %1$s］下题目总数...", structureId));
		final String hql = "select count(s.id) from StructureItem s where s.structure.id = :structureId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? null :((long)obj);
	}
}