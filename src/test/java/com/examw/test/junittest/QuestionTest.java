package com.examw.test.junittest;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.junit.dao.IQuestionDao;
import com.examw.test.junit.domain.Question;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午5:18:48.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-sql.xml"})
public class QuestionTest {
	@Resource
	private IQuestionDao questionDao;
	
	/**
	 * 设置 
	 * @param questionDao
	 * 
	 */
	public void setQuestionDao(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	}


	@Transactional
	@Test
	public void questionTest(){
		System.out.println(questionDao);
		List<Question> list = this.questionDao.findAll();
		System.out.println("list size ================ "+list.size());
	}
}
