package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IItemErrorRecorveryDao;
import com.examw.test.domain.library.ItemErrorRecorvery;
import com.examw.test.model.library.ItemErrorRecorveryInfo;

/**
 * 试题纠错数据接口实现类
 * @author fengwei.
 * @since 2014年11月4日 下午1:54:03.
 */
public class ItemErrorRecorveryDaoImpl extends BaseDaoImpl<ItemErrorRecorvery> implements IItemErrorRecorveryDao {
		private static final Logger logger = Logger.getLogger(ItemErrorRecorveryDaoImpl.class);
		/*
		 * 
		 */
		@Override
		public List<ItemErrorRecorvery> findItems(ItemErrorRecorveryInfo info) {
			if(logger.isDebugEnabled()) logger.debug("查询数据...");
			String hql = "from ItemErrorRecorvery i where 1 = 1 ";
			Map<String, Object> parameters = new HashMap<>();
			hql = this.addWhere(info, hql, parameters);
			if(!StringUtils.isEmpty(info.getSort())){
				switch(info.getSort()){
					case "errorTypeName":
						info.setSort("errorType");
						break;
					case "statusName":
						info.setSort("status");
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
		public Long total(ItemErrorRecorveryInfo info) {
			if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
			String hql = "select count(*) from ItemErrorRecorvery i where 1 = 1 "; 
			Map<String, Object> parameters = new HashMap<>();
			hql = this.addWhere(info, hql, parameters);
			if(logger.isDebugEnabled()) logger.debug(hql);
			return this.count(hql, parameters);
		}
		//添加查询条件。
		private String addWhere(ItemErrorRecorveryInfo info, String hql, Map<String, Object> parameters){
			if(!StringUtils.isEmpty(info.getExamId())){
				hql += " and (i.item.subject.exam.id = :examId or (i.item.subject.exam.category.id in (select c.id  from Category c where (c.parent.id = :examId or c.id = :examId))))";
				parameters.put("examId", info.getExamId());
			}
			if(!StringUtils.isEmpty(info.getSubjectId())){
				hql += " and (i.item.subject.id = :subjectId) ";
				parameters.put("subjectId", info.getSubjectId());
			}
			if(!StringUtils.isEmpty(info.getUserName())){
				hql += " and (i.user.name like :userName )";
				parameters.put("userName", info.getUserName());
			}
			if(!StringUtils.isEmpty(info.getAdminUserName())){
				hql += " and (i.adminUserName like :adminUserName) ";
				parameters.put("adminUserName", info.getAdminUserName());
			}
			if(!StringUtils.isEmpty(info.getStatus())){
				hql += " and (i.status = :status) ";
				parameters.put("status", info.getStatus());
			}
			if(!StringUtils.isEmpty(info.getErrorType())){
				hql += " and (i.errorType = :errorType) ";
				parameters.put("errorType", info.getErrorType());
			}
			return hql;
		}
}
