package com.examw.test.dao.syllabus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IPressDao;
import com.examw.test.domain.syllabus.Press;
import com.examw.test.model.syllabus.PressInfo;
/**
 * 出版社数据接口实现类。
 * @author lq.
 * @since 2014-08-06.
 */
public class PressDaoImpl extends BaseDaoImpl<Press> implements IPressDao{
	private static final Logger logger = Logger.getLogger(PressDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.IPressDao#findUsers(com.examw.test.model.syllabus.PressInfo)
	 */
	@Override
	public List<Press> findPresss(PressInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询数据...");
		String hql = "from Press p where 1=1 "; 
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by p." + info.getSort() + " " + info.getOrder();
		}
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询统计。
	 * @see com.examw.test.dao.syllabus.IPressDao#total(com.examw.test.model.syllabus.PressInfo)
	 */
	@Override
	public Long total(PressInfo info) {
		if(logger.isDebugEnabled())logger.debug("查询统计...");
		String hql = "select count(*) from Press p where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(logger.isDebugEnabled()) logger.debug(hql);
		return this.count(hql, parameters);
	}
	//添加查询条件到HQL。
	private String addWhere(PressInfo info, String hql, Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += "  and (p.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		return hql;
	}
}