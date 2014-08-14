package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IStructureDao;
import com.examw.test.domain.library.Structure;

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
	public List<Structure> finaStructures(String paperId) {
		if(logger.isDebugEnabled()) logger.debug("加载试卷下的结构数据...");
		final String hql = "from Structure s where (s.parent is null) and (s.paper.id = :paperId)";
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
			String msg = String.format("该试卷结构或其子结构［%1$s］下有试题［%2$d题］，请先删除试题！", data.getTitle(), data.getItems().size());
			if(logger.isDebugEnabled()) logger.debug(msg);
			throw new RuntimeException(msg);
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			 for(Structure s : data.getChildren()){
				 this.delete(s);
			 }
		}
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷结构［%1$s  %2$s］", data.getTitle(), data.getId()));
		super.delete(data);
	}
}