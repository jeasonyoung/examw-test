package com.examw.test.service.library;
/**
 * 试卷随机添加试题服务接口。
 * 
 * @author yangyong
 * @since 2014年10月20日
 */
public interface IRandomItemService {
	/**
	 * 随机添加试题到试卷结构。
	 * @param structureId
	 * 所属试卷结构ID。
	 */
	void addRandomItem(String structureId) throws Exception;
	/**
	 * 整理试卷下试题的排序号。
	 * @param paperId
	 * 所属试卷ID。
	 */
	void updateItemOrder(String paperId);
}