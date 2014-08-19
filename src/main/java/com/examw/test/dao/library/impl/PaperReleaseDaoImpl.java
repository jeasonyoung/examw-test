package com.examw.test.dao.library.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	/*
	 * 加载试卷发布。
	 * @see com.examw.test.dao.library.IPaperReleaseDao#loadRelease(java.lang.String)
	 */
	@Override
	public PaperRelease loadRelease(String paperId) {
		final String hql = "select top 1 p.* from PaperRelease p where p.paper.id = :paperId order by p.createTime desc";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("paperId", paperId);
		List<PaperRelease> list = this.find(hql, parameters, null, null);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
}