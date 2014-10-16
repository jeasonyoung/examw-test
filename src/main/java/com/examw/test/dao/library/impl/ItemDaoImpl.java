package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.ItemStatus;
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
			if(info.getSort().equalsIgnoreCase("typeName")){
				info.setSort("type");
			}
			if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			if(info.getSort().equalsIgnoreCase("optName")){
				info.setSort("opt");
			}
			if(info.getSort().equalsIgnoreCase("examName")){
				info.setSort("subject.exam.name");
			}
			if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}
			if(info.getSort().equalsIgnoreCase("sourceName")){
				info.setSort("source.name");
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
			msg = String.format("试题［%1$s］被关联在［%2$d］试卷结构中，暂不能删除！", parent.getContent(), count);
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		super.delete(parent);
	}
//	/**
//	 * 自动删除题目数据。
//	 * @param data
//	 */
//	protected void autoDelete(Item data){
//		if(data == null) return;
//		if(data.getChildren() == null || data.getChildren().size() == 0){
//			super.delete(data);
//			return;
//		}
//		for(Item item : data.getChildren()){
//			if(item == null) continue;
//			this.autoDelete(item);
//		} 
//	}
	/*
	 * 加载试题。
	 * @see com.examw.test.dao.library.IItemDao#loadItemByCode(java.lang.String)
	 */
	@Override
	public Item loadItemByCode(String checkCode) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载［checkcode = %s］试题...", checkCode));
		if(StringUtils.isEmpty(checkCode)) return null;
		final String hql = "from Item i where (i.parent is null) and (i.checkCode = :checkCode) ";
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
}