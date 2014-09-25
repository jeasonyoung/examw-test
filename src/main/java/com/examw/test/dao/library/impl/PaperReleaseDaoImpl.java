package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.dao.library.IPaperReleaseDao;
import com.examw.test.domain.library.PaperRelease;

/**
 * 试卷发布数据接口实现类。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public class PaperReleaseDaoImpl extends BaseDaoImpl<PaperRelease> implements IPaperReleaseDao {
	private static final Logger logger = Logger.getLogger(PaperReleaseDaoImpl.class);
	/*
	 * 试卷是否已发布。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#hasRelease(java.lang.String)
	 */
	@Override
	public boolean hasRelease(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("试卷[%s]是否已发布", paperId));
		final String hql = "select count(*) from PaperRelease p where p.paper.id = :paperId";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		Object obj = this.uniqueResult(hql, parameters);
		return obj == null ? false : ((long)obj > 0);
	}
	/*
	 * 删除试卷发布。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#deleteRelease(java.lang.String)
	 */
	@Override
	public void deleteRelease(String paperId) {
		if(logger.isDebugEnabled()) logger.debug(String.format("删除试卷[%s]发布...", paperId));
		final String hql = "from PaperRelease p where p.paper.id = :paperId order by p.createTime";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		List<PaperRelease> paperReleases = this.find(hql, parameters, null, null);
		if(paperReleases != null && paperReleases.size() > 0){
			if(logger.isDebugEnabled())logger.debug(String.format("须删除［%d］分试卷...", paperReleases.size()));
			int index = 0;
			for(PaperRelease paperRelease : paperReleases){
				if(paperRelease == null) continue;
				if(logger.isDebugEnabled()) logger.debug(String.format("［%1$d］.删除试卷:[%2$s][%3$s]", ++index, paperRelease.getId(), paperRelease.getTitle()));
				this.delete(paperRelease);
			}
		}
	}
}