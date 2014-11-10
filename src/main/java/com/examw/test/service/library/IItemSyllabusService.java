package com.examw.test.service.library;

import java.util.List;

import com.examw.test.model.syllabus.BaseSyllabusInfo;

/**
 * 试题大纲服务接口
 * @author fengwei.
 * @since 2014年11月8日 上午11:29:39.
 */
public interface IItemSyllabusService {
	/**
	 * 加载试题关联的大纲要点
	 * @param itemId	试题ID
	 * @return
	 */
	List<BaseSyllabusInfo> loadItemSyllabuses(String itemId);
	
	/**
	 * 更新试题要点
	 * @param itemId
	 * @param syllabusIds
	 */
	void updateItemSyllabus(String itemId,String syllabusIds);
	
	/**
	 * 删除试题要点
	 * @param itemId
	 * @param syllabusIds
	 */
	void deleteItemSyllabus(String itemId,String syllabusIds);
}
