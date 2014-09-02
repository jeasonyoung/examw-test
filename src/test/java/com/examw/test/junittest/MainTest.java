package com.examw.test.junittest;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.junit.dao.IQuestionDao;
import com.examw.test.junit.domain.Question;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:06:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test-sql.xml","classpath:spring-examw-test.xml"})
public class MainTest {
	@Resource
	private IItemDao itemDao;
	@Resource
	private SessionFactory sessionFactorySql;
	@Transactional
	@Test
	public void show(){
		Session session = sessionFactorySql.openSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<Question> questionList = session.createQuery("from Question").list();
		System.out.println(questionList.size());
		session.getTransaction().commit();
		session.close();
		Item item = itemDao.load(Item.class, "ef50d06b-9f47-4767-b4d5-99e77105998f");
		System.out.println(item);
	}
}
