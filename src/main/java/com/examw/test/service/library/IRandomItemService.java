package com.examw.test.service.library;

import com.examw.test.domain.library.Paper;
import com.examw.test.domain.library.Structure;

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
	 * 随机添加试题到试卷结构。
	 * @param structure
	 * 所属试卷结构。
	 * @param checkItemCount
	 * 检查试题数量。
	 * @throws Exception
	 */
	int addRandomItem(Structure structure,boolean checkItemCount) throws Exception;
	/**
	 * 整理试卷下试题的排序号。
	 * @param paperId
	 * 所属试卷ID。
	 */
	void updateItemOrder(String paperId);
	/**
	 * 整理试卷下试题的排序号。
	 * @param paper
	 * 所属试卷。
	 */
	void updateItemOrder(Paper paper);
}