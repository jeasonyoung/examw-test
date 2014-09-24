package com.examw.test.service.records.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.examw.test.dao.records.INoteDao;
import com.examw.test.domain.records.Note;
import com.examw.test.model.records.NoteInfo;
import com.examw.test.service.records.INoteService;

/**
 * 笔记服务接口实现类
 * @author fengwei.
 * @since 2014年9月18日 下午4:03:24.
 */
public class NoteServiceImpl implements INoteService{
	private static final Logger logger = Logger.getLogger(NoteServiceImpl.class);
	private INoteDao noteDao;
	
	/**
	 * 设置 笔记数据接口
	 * @param noteDao
	 * 
	 */
	public void setNoteDao(INoteDao noteDao) {
		this.noteDao = noteDao;
	}

	/*
	 * 插入一条笔记
	 * @see com.examw.test.service.records.INoteService#insertNote(com.examw.test.domain.records.Note)
	 */
	@Override
	public boolean insertNote(Note data) {
		if(logger.isDebugEnabled()) logger.debug(String.format("插入一条笔记记录[userId=%1$s,structureItemId=%2$s]",data.getUserId(),data.getStructureItemId()));
		if(data == null) return false;
		//写一次一条记录
		data.setId(UUID.randomUUID().toString());
		data.setCreateTime(new Date());
		this.noteDao.save(data);
		return true;
	}
	/*
	 * 查询数据
	 * @see com.examw.test.service.records.INoteService#findNotes(com.examw.test.domain.records.Note)
	 */
	@Override
	public List<Note> findNotes(NoteInfo info) {
		return this.noteDao.findNotes(info);
	}
	/*
	 * 数据统计
	 * @see com.examw.test.service.records.INoteService#total(com.examw.test.domain.records.Note)
	 */
	@Override
	public long total(NoteInfo info) {
		return this.noteDao.total(info);
	}
}
