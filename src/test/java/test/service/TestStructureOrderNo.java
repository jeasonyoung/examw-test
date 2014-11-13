package test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.service.library.IPaperStructureService;

/**
 * 
 * @author fengwei.
 * @since 2014年11月13日 上午11:54:43.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class TestStructureOrderNo {
	@Resource
	private IPaperStructureService paperStructureService;
	@Test
	@Transactional
	public void testOrderNo()
	{
		String paperId = "b6b9950c-f724-40d0-b274-4a12a2da884c";
		int order = this.paperStructureService.loadMaxOrder(paperId);
		System.out.println("最大的ID号 为 ======== "+order);
	}
}
