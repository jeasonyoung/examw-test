package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Structure;
import com.examw.test.domain.library.StructureItem;
import com.examw.test.model.library.StructureItemInfo;

/**
 * 试卷结构数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月14日
 */
public class StructureDaoImpl extends BaseDaoImpl<Structure> implements IStructureDao  {
	private static final Logger logger = Logger.getLogger(StructureDaoImpl.class);
	/*
	 * 加载试卷下的结构数据。
	 * @see com.examw.test.dao.library.IStructureDao#finaStructures(java.lang.String)
	 */
	@Override
	public List<Structure> loadStructures(String paperId) {
		if(logger.isDebugEnabled()) logger.debug("加载试卷下的结构数据...");
		final String hql = "from Structure s where (s.paper.id = :paperId) order by s.orderNo";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Structure data) {
		if(logger.isDebugEnabled()) logger.debug("删除试卷结构数据...");
		if(data == null) return;
		if(data.getItems() != null && data.getItems().size() > 0){
			String msg = String.format("该试卷结构［%1$s］下有试题［%2$d题］，请先删除试题！", data.getTitle(), data.getItems().size());
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷结构［%1$s  %2$s］", data.getTitle(), data.getId()));
		super.delete(data);
	}
	/*
	 * 加载试卷试题。
	 * @see com.examw.test.dao.library.IStructureDao#loadStructureItem(java.lang.String, java.lang.String)
	 */
	@Override
	public StructureItem loadStructureItem(String structureId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷试题［structureId = %1$s, itemId = %2$s］...", structureId, itemId));
		final String hql = "from StructureItem s where s.structure.id = :structureId and s.item.id = :itemId order by s.createTime desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		parameters.put("itemId", itemId);
		List<?> list = this.query(hql, parameters, null, null);
		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			if(obj instanceof StructureItem){
				return (StructureItem)obj;
			}
		}
		return null;
	}
	/*
	 * 查询试卷下试题数据。
	 * @see com.examw.test.dao.library.IStructureDao#findItems(com.examw.test.model.library.StructureItemInfo)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureItem> findItems(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询试卷下试题数据...");
		String hql = "from StructureItem s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhereItems(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("typeName")){
				info.setSort("type");
			}
			hql += String.format(" order by s.%1$s  %2$s", info.getSort(), info.getOrder());
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.query(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计试卷下试题数据。
	 * @see com.examw.test.dao.library.IStructureDao#totalItems(com.examw.test.model.library.StructureItemInfo)
	 */
	@Override
	public Long totalItems(StructureItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询统计试卷下试题数据...");
		String hql = "select count(*) from StructureItem s where (1 = 1) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhereItems(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhereItems(StructureItemInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getPaperId())){
			hql += " and (s.structure.paper.id = :paperId) ";
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
	 * 加载试卷结构最大排序号。
	 * @see com.examw.test.dao.library.IStructureDao#loadPaperMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadPaperMaxOrder(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷［paperId = %s］结构最大排序号...", paperId));
		final String hql = "select max(s.orderNo) from Structure s where s.paper.id = :paperId";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("paperId", paperId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ?  null : (int)obj;
	}
	/*
	 * 加载试卷结构下试题最大排序号。
	 * @see com.examw.test.dao.library.IStructureDao#loadItemMaxOrderNo(java.lang.String)
	 */
	@Override
	public Integer loadItemMaxOrder(String structureId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试卷结构［structureId = %s］下试题最大排序号...", structureId));
		final String hql = "select max(s.orderNo) from StructureItem s where s.structure.id = :structureId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ?  null : (int)obj;
	}
	/*
	 * 统计结构下的试题数量。
	 * @see com.examw.test.dao.library.IStructureDao#totalStructureItems(java.lang.String)
	 */
	@Override
	public Long totalStructureItems(String structureId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计试卷结构［structureId = %s］下的试题数量...", structureId));
		if(StringUtils.isEmpty(structureId)) return null;
		final String hql = "select sum(s.item.count) from StructureItem s where s.structure.id = :structureId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ?  null : (long)obj;
	}
	/*
	 * 删除试卷结构试题。
	 * @see com.examw.test.dao.library.IStructureDao#deleteStructureItems(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteStructureItems(String structureId, String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷［structureId = %1$s］结构［itemId = %2$s］试题..", structureId,itemId));
		final String hql = "delete from StructureItem si where (si.structure.id = :structureId) and (si.item.id = :itemId)";
		if(StringUtils.isEmpty(structureId) || StringUtils.isEmpty(itemId)) return;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("structureId", structureId);
		parameters.put("itemId", itemId);
		int count = this.execuateUpdate(hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(String.format("删除数据［%d］", count));
	}
	/*
	 * 统计试题被试卷关联次数。
	 * @see com.examw.test.dao.library.IStructureDao#totalItemCount(java.lang.String)
	 */
	@Override
	public Long totalItemCount(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("统计试题［itemId = %s］被试卷关联次数...", itemId));
		final String hql = "select count(*) from StructureItem si where (si.item.id = :itemId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemId", itemId);
		if(logger.isDebugEnabled()) logger.debug(hql);
		Object obj = this.uniqueResult(hql, parameters);
		return (obj == null) ? null : (long)obj;
	}
}