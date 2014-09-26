package com.examw.test.service.library;

import com.examw.test.model.library.PaperPreview;

/**
 * 试卷预览服务接口。
 * 
 * @author yangyong
 * @since 2014年9月22日
 */
public interface IPaperPreviewService {
	/**
	 * 加载试卷预览。
	 * @param paperId
	 * 试卷ID。
	 * @return
	 * 试卷信息。
	 */
	PaperPreview loadPaperPreview(String paperId);
}