package com.examw.test.junittest;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.model.library.ItemInfo;
import com.examw.test.service.library.IItemService;

/**
 * 
 * @author fengwei.
 * @since 2014年9月1日 下午6:01:30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class ItemTest {
	@Resource
	private IItemService itemService;
	
	@Transactional
	@Test
	public void itemTest(){
		ItemInfo info = this.itemService.loadItemPreview("ef50d06b-9f47-4767-b4d5-99e77105998f");
		System.out.println(info);
	}
}
