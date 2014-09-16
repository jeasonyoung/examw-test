package com.examw.test.service.library;

import com.examw.test.model.library.PaperPreview;

/**
 * 试卷发布服务接口。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public interface IPaperReleaseService {
	/**
	 * 加载试卷数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 * 试卷数据。
	 */
	PaperPreview loadPaper(String paperId) throws Exception;
	/**
	 * 试卷发布。
	 */
	void addPaperRelease();
}