package com.examw.test.junit.dao.impl;

import java.util.List;

import com.examw.test.dao.impl.BaseDaoImpl;
import com.examw.test.junit.dao.IQuestionDao;
import com.examw.test.junit.domain.Question;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午5:27:38.
 */
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements IQuestionDao{

	@Override
	public List<Question> findAll() {
		String hql = "from Question";
		return this.find(hql, null, null, null);
	}
}
