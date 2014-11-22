package test.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IItemDao;
import com.examw.test.domain.library.Item;
import com.examw.test.domain.settings.Area;
import com.examw.test.domain.settings.Subject;
import com.examw.test.service.library.ItemType;

/**
 * 
 * @author fengwei.
 * @since 2014年11月22日 上午11:03:07.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class TestItemSearch {
	@Resource
	private IItemDao itemDao;
	@Test
	@Transactional
	public void testOrderNo()
	{
		Subject subject = new Subject();
		subject.setId("9b2e789f-edee-4f09-94bb-9e696691b1fa");
		Area area = new Area();
		area.setId("1ec32281-3408-4e51-9e5b-0fbaf8f4be56");
		List<Item> list = itemDao.loadItems(subject, ItemType.convert(6), area);
		System.out.println(list.size());
	}
}
