package com.examw.test.dao.syllabus.impl;
 
import java.util.HashMap;
import java.util.List; 
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IBookDao;
import com.examw.test.domain.syllabus.Book;
import com.examw.test.model.syllabus.BookInfo;
import com.examw.test.service.syllabus.BookStatus;
/**
 * 教材数据接口实现类。
 * @author lq.
 * @since 2014-08-07.
 */
public class BookDaoImpl extends BaseDaoImpl<Book> implements IBookDao {
	private static final Logger logger = Logger.getLogger(BookDaoImpl.class);
	/*
	 * 查询数据。
	 * @see com.examw.test.dao.syllabus.IBookDao#findBooks(com.examw.test.model.syllabus.BookInfo)
	 */
	@Override
	public List<Book> findBooks(BookInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		String hql = "from Book b where (1=1) ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			if(info.getSort().equalsIgnoreCase("pressName")){
				info.setSort("press.name");
			}else if(info.getSort().equalsIgnoreCase("subjectName")){
				info.setSort("subject.name");
			}else if(info.getSort().equalsIgnoreCase("statusName")){
				info.setSort("status");
			}
			hql += " order by b." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.test.dao.syllabus.IBookDao#total(com.examw.test.model.syllabus.BookInfo)
	 */
	@Override
	public Long total(BookInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		String hql = "select count(*) from Book b where (1=1) ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	//添加查询条件。
	private String addWhere(BookInfo info,String hql,Map<String, Object> parameters){
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (b.name like :name) ";
			parameters.put("name", "%" + info.getName() + "%");
		}
		if(!StringUtils.isEmpty(info.getPressId())){
			hql += " and (b.press.id = :pressId) ";
			parameters.put("pressId", info.getPressId());
		}
		if(!StringUtils.isEmpty(info.getExamId())){
			hql += " and (b.subject.exam.id = :examId) ";
			parameters.put("examId", info.getExamId());
		}
		if(!StringUtils.isEmpty(info.getSubjectId())){
			hql += " and (b.subject.id = :subjectId) ";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(info.getStatus() != null){
			hql += " and (b.status = :status) ";
			parameters.put("status", info.getStatus());
		}
		return hql;
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.syllabus.IBookDao#loadMaxOrder()
	 */
	@Override
	public Integer loadMaxOrder() {
		if(logger.isDebugEnabled()) logger.debug("加载最大排序号...");
		final String hql = "select max(b.orderNo) from Book b";
		Object obj = this.uniqueResult(hql, null);
		return obj == null ? null : (int)obj;
	}
	/*
	 * 加载科目地区下最新教材。
	 * @see com.examw.test.dao.syllabus.IBookDao#loadBookLast(java.lang.String, java.lang.String)
	 */
	@Override
	public Book loadBookLast(String subjectId, String areaId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载科目［subjectId = %1$s］地区［areaId =%2$s］下最新教材...", subjectId, areaId));
		final String hql = "select b from Book b left join b.areas a where (b.status = :status) and (b.subject.id = :subjectId) and (a.id = :areaId) order by b.orderNo desc";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", BookStatus.ENABLE.getValue());
		parameters.put("subjectId", subjectId);
		parameters.put("areaId", areaId);
		List<Book> list = this.find(hql, parameters, 0, 0);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}