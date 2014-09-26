package com.examw.test.service.library;
/**
 * 试卷发布服务接口。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public interface IPaperReleaseService {
	/**
	 * 试卷发布。
	 */
	void updateRelease() throws Exception;
	/**
	 * 发布指定试卷。
	 * @param paperId
	 * 试卷ID。
	 * @throws Exception
	 */
	void updateRelease(String paperId) throws Exception;
	/**
	 * 删除试卷的发布。
	 * @param paperId。
	 */
	void deleteRelease(String paperId);
}