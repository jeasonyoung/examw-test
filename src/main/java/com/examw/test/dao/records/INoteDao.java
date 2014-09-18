package com.examw.test.dao.records;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.records.Note;

/**
 * 试题笔记数据接口
 * @author fengwei.
 * @since 2014年9月17日 下午5:36:13.
 */
public interface INoteDao extends IBaseDao<Note>{
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果集合。
	 */
	List<Note> findNotes(Note info);
	/**
	 * 查询数据统计。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	Long total(Note info);
}
