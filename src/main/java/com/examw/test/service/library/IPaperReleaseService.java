package com.examw.test.service.library;
/**
 * 试卷发布服务接口。
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
public interface IPaperReleaseService {
	/**
	 * 检查发布。
	 * 1.将已发布状态的未找到发布试卷的重新发布；
	 * 2.将反审核状态的已发布的试卷删除；
	 * @throws Exception
	 */
	void updateCheckRelease();
	/**
	 * 试卷发布。
	 */
	void updateRelease();
	/**
	 * 发布指定试卷。
	 * @param paperId
	 * 试卷ID。
	 * @throws Exception
	 */
	void updateRelease(String paperId);
	/**
	 * 删除试卷的发布。
	 * @param paperId。
	 */
	void deleteRelease(String paperId);
}