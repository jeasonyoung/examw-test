package com.examw.test.dao.library;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.PaperRelease;

/**
 * 试卷发布数据接口。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public interface IPaperReleaseDao extends IBaseDao<PaperRelease> {
	/**
	 * 加载试卷发布。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 */
	PaperRelease loadRelease(String paperId);
}