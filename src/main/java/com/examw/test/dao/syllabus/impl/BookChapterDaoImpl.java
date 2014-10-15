package com.examw.test.dao.syllabus.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.syllabus.IBookChapterDao;
import com.examw.test.domain.syllabus.BookChapter;

/**
 * 教材章节数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年10月13日
 */
public class BookChapterDaoImpl extends BaseDaoImpl<BookChapter> implements IBookChapterDao {
	private static final Logger logger = Logger.getLogger(BookChapterDaoImpl.class);
	/*
	 * 查询教材下的顶级章节集合。
	 * @see com.examw.test.dao.syllabus.IBookChapterDao#findFirstChapters(java.lang.String)
	 */
	@Override
	public List<BookChapter> findFirstChapters(String bookId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("查询教材［bookId = %s］下的顶级章节集合...", bookId));
		final String hql = "from BookChapter b where (b.parent is null) and (b.book.id = :bookId) order by b.orderNo ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("bookId", bookId);
		return this.find(hql, parameters, null, null);
	}
	/*
	 * 加载最大排序号。
	 * @see com.examw.test.dao.syllabus.IBookChapterDao#loadMaxOrder(java.lang.String)
	 */
	@Override
	public Integer loadMaxOrder(String parentChapterId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("加载最大排序号［parentChapterId = %s］...", parentChapterId));
		StringBuilder hqlBuilder = new StringBuilder();
		hqlBuilder.append("select max(b.orderNo) from BookChapter b ");
		Map<String, Object> parameters = new HashMap<>();
		if(StringUtils.isEmpty(parentChapterId)){
			hqlBuilder.append(" where ").append(" (b.parent is null) ");
		}else {
			hqlBuilder.append(" where ").append(" (b.parent.id = :parentChapterId) ");
			parameters.put("parentChapterId", parentChapterId);
		}
		Object obj = this.uniqueResult(hqlBuilder.toString(), parameters);
		return (obj == null) ? null : (int)obj;
	}
	/*
	 * 删除数据。
	 * @see com.examw.test.dao.impl.BaseDaoImpl#delete(java.lang.Object)
	 */
	@Override
	public void delete(BookChapter data) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(data == null) return;
		int size = 0;
		if(data.getKnowledges() != null && (size = data.getKnowledges().size()) > 0){
			throw new RuntimeException(String.format("关联［%d］知识点，暂不能删除！", size));
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			for(BookChapter chapter : data.getChildren()){
				if(chapter == null) continue;
				if(logger.isDebugEnabled()) logger.debug(String.format("删除章节［%1$s,%2$s］", chapter.getId(), chapter.getTitle()));
				this.delete(chapter);
			}
		}
		super.delete(data);
	}
}