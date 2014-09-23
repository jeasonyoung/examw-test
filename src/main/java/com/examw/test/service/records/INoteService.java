package com.examw.test.service.records;

import java.util.List;

import com.examw.test.domain.records.Note;

/**
 * 笔记服务接口
 * @author fengwei.
 * @since 2014年9月18日 下午3:57:05.
 */
public interface INoteService {
	/**
	 * 插入一条笔记
	 * @param data
	 * @return
	 */
	boolean insertNote(Note data);
	/**
	 * 查询数据
	 * @param info
	 * @return
	 */
	List<Note> findNotes(Note info);
	/**
	 * 数据统计
	 * @return
	 */
	long total(Note info);
}
