package com.examw.test.junittest;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.products.IProductDao;
import com.examw.test.domain.products.Product;
import com.examw.test.junit.dao.IQuestionDao;
import com.examw.test.model.products.ProductInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午5:18:48.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class QuestionTest {
//	@Resource
//	private IQuestionDao questionDao;
	@Resource
	private IProductDao productDao;

	@Transactional
	@Test
	public void questionTest(){
		//System.out.println(questionDao);
		//List<Question> list = this.questionDao.findAll();
		//System.out.println("list size ================ "+list.size());
		List<Product> list = this.productDao.findProducts(new ProductInfo(){
			@Override
			public String getExamId() {
				return "cbe9d85a-fd6b-4e6b-8fac-745a37f4d980,b83e1d20-c1f5-4876-bbfa-aa56a243298c,16ed2088-e503-425d-a301-31241079d8e8";
			}
			@Override
			public String getSort() {
				return "code";
			}
			@Override
			public String getOrder() {
				return "asc";
			}
		});
		System.out.println(list.size());
	}
}
