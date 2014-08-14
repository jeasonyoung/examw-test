package com.examw.test.dao.library;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.library.Structure;
/**
 * 试卷结构数据接口。
 * 
 * @author yangyong
 * @since 2014年8月14日
 */
public interface IStructureDao extends IBaseDao<Structure> {
	/**
	 * 加载试卷下的结构数据。
	 * @param paperId
	 * 所属试卷ID。
	 * @return
	 */
	List<Structure> finaStructures(String paperId);
}