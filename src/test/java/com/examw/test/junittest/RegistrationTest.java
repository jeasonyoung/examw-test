package com.examw.test.junittest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.products.IRelationProductDao;

/**
 * 
 * @author fengwei.
 * @since 2014年8月20日 下午4:13:56.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class RegistrationTest {
	@Resource
	private IRelationProductDao relationProductDao;
	@Transactional
	@Test
	public void Preview() throws Exception{
		relationProductDao.delete("34f5b841-739b-436f-8b54-45e3dc00e2bc");
	}
}
