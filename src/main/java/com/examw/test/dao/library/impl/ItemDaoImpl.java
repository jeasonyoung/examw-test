package com.examw.test.dao.library.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.ItemStatus;
import com.examw.test.service.library.ItemType;
import com.examw.test.service.library.PaperType;

/**
 * 题目数据操作接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月8日
 */
public class ItemDaoImpl extends BaseDaoImpl<Item> implements IItemDao {
	private static final Logger logger = Logger.getLogger(ItemDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.library.IItemDao#findItems(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public List<Item> findItems(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Item i where (i.parent is null) ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			switch(info.getSort()){
				case "typeName":
					info.setSort("type");
					break;
				case "statusName":
					info.setSort("status");
					break;
				case "optName":
					info.setSort("opt");
					break;
				case "examName":
					info.setSort("subject.exam.name");
					break;
				case "subjectName":
					info.setSort("subject.name");
					break;
				case "sourceName":
					info.setSort("source.name");
					break;
			}
			hql += " order by i." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.library.IItemDao#total(com.examw.test.model.library.ItemInfo)
	 */
	@Override
	public Long total(ItemInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Item i where (i.parent is null)"; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(ItemInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (i.subject.exam.id = :examId or (i.subject.exam.category.id in (select c.id  from Category c where (c.parent.id = :examId or c.id = :examId))))";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (i.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getSourceId())){
			hql += " and (i.source.id = :sourceId) ";
			parameters.put("sourceId", info.getSourceId());
		}
		if(!StringUtils.isEmpty(info.getAreaId())){
			hql += " and ((i.area is null) or (i.area.code = 1) or (i.area.id = :areaId)) ";
			parameters.put("areaId", info.getAreaId());
		}
		if(!StringUtils.isEmpty(info.getStatus())){
			hql += " and (i.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		if(!StringUtils.isEmpty(info.getType())){
			hql += " and (i.type = :type) ";
			parameters.put("type", info.getType());
		}
		if(!StringUtils.isEmpty(info.getOpt())){
			hql += " and (i.opt = :opt) ";
			parameters.put("opt", info.getOpt());
		}
		if(info.getYear() != null && info.getYear() > 0){
			hql += " and (i.year = :year)";
			parameters.put("year", info.getYear());
		}
		if(!StringUtils.isEmpty(info.getContent())){
			hql += " and (i.content like :content) ";
			parameters.put("content", "%"+ info.getContent() +"%");
		}
		return hql;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(Item data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		Item parent = data;
		while(parent.getParent() != null){
			parent = parent.getParent();
		}
		String msg = null;
		if(parent.getStatus() == ItemStatus.AUDIT.getValue()){
			msg = "试题已审核，不允许删除！";
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		int count = 0;
		if(parent.getStructures() != null && (count = parent.getStructures().size()) > 0){
			msg = String.format("被关联在［%d］试卷中，暂不能删除！", count);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		super.delete(parent);
	}
	/*
	 * 加载试题。
	 * @see com.examw.test.dao.library.IItemDao#loadItemByCode(java.lang.String)
	 */
	@Override
	public Item loadItemByCode(String checkCode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载［checkcode = %s］试题...", checkCode));
		if(StringUtils.isEmpty(checkCode)) return null;
		final String hql = "from Item i where (i.parent is null) and (i.checkCode = :checkCode) order by i.createTime desc ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("checkCode", checkCode);
		List<Item> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 加载试题。
	 * @see com.examw.test.dao.library.IItemDao#loadItem(java.lang.String)
	 */
	@Override
	public Item loadItem(String itemId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载［id = %s］试题...", itemId));
		final String hql = "from Item i where (i.parent is null) and (i.id = :itemId) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("itemId", itemId);
		List<Item> list = this.find(hql, parameters, null, null);
		if(list == null || list.size() == 0) return null;
		return list.get(0);
	}
	/*
	 * 检查科目下是否包含真题
	 * @see com.examw.test.dao.library.IItemDao#hasRealItem(java.lang.String)
	 */
	@Override
	public boolean hasRealItem(String[] subjectIds) {
		if(logger.isDebugEnabled()) logger.debug("查询科目下是否包含真题 ...");
		if(subjectIds == null || subjectIds.length > 0) return false;
		final String hql = "select count(*) from Item i where (i.parent is null) and (i.opt = :opt) and (i.subject.id in (:subjectId)) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("opt", PaperType.REAL.getValue());
		parameters.put("subjectId", subjectIds);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? false : (long)obj > 0;
	}
	/*
	 * 加载科目题型下的试题集合。
	 * @see com.examw.test.dao.library.IItemDao#loadItems(com.examw.test.domain.settings.Subject, com.examw.test.service.library.ItemType, com.examw.test.domain.settings.Area)
	 */
	@Override
	public List<Item> loadItems(Subject subject, ItemType itemType,Area area) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载试题［subject = %1$s］［itemType = %2$s］［area = %3$s］集合...", subject, itemType, area));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("from Item i where (i.parent is null) ");
		Map<String, Object> parameters = new HashMap<>();
		if(subject != null && !StringUtils.isEmpty(subject.getId())){
			hqlBuilder.append("and (i.subject.id = :subjectId) ");
			parameters.put("subjectId", subject.getId());
		}
		if(itemType != null){
			hqlBuilder.append(" and (i.type = :type) ");
			parameters.put("type", itemType.getValue());
		}
		if(area != null && !StringUtils.isEmpty(area.getId())){
			hqlBuilder.append("  and ((i.area is null) or (i.area.code = 1) or (i.area.id = :areaId)) ");
			parameters.put("areaId", area.getId());
		}
		if(logger.isDebugEnabled()) logger.debug(hqlBuilder.toString());
		return this.find(hqlBuilder.toString(), parameters, null, null);
	}
	/*
	 * 加载科目下的题型集合。
	 * @see com.examw.test.dao.library.IItemDao#loadItemTypes(com.examw.test.domain.settings.Subject)
	 */
	@Override
	public List<ItemType> loadItemTypes(Subject subject) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［%s］下的题型集合...", subject == null ? "" : subject.getId()));
		if(subject == null || StringUtils.isEmpty(subject.getId())) return null;
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select i.type from Item i where (i.parent is null) and (i.subject.id = :subjectId) ");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("subjectId", subject.getId());
		if(subject.getAreas() != null && subject.getAreas().size() > 0){
			hqlBuilder.append(" and ((i.area is null) or (i.area.code = 1) or (i.area.id in (:areaId))) ");
			List<String> list = new ArrayList<>();
			for(Area area : subject.getAreas()){
				if(area == null) continue;
				list.add(area.getId());
			}
			parameters.put("areaId", list);
		}
		hqlBuilder.append(" group by i.type order by i.type ");
		List<?> types = this.query(hqlBuilder.toString(), parameters, null, null);
		if(types == null || types.size() == 0) return null;
		List<ItemType> list = new ArrayList<>();
		for(Object obj : types){
			if(obj == null) continue;
			if(obj instanceof Integer){
				list.add(ItemType.convert((Integer)obj));
			}
		}
		return list;
	}
	/*
	 * 加载与试卷无关联的试题数据集合。
	 * @see com.examw.test.dao.library.IItemDao#deleteIsolatedItems()
	 */
	@Override
	public List<Item> loadIsolatedItems() {
		if(logger.isDebugEnabled()) logger.debug("加载与试卷无关联的试题数据集合...");
		final String hql = "from Item i where (i.parent is null) and (i.id not in (select si.item.id  from StructureItem si order by si.createTime)) ";
		return this.find(hql, null, null, null);
	}
}