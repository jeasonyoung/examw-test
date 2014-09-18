package com.examw.test.service.records;

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
}
