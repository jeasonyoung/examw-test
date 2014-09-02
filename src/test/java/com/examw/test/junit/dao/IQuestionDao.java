package com.examw.test.junit.dao;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.junit.domain.Question;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午5:27:00.
 */
public interface IQuestionDao extends IBaseDao<Question>{
	List<Question> findAll();
}
