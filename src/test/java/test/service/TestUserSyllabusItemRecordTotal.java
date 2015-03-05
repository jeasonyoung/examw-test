package test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.records.IUserItemRecordDao;
import com.examw.test.service.records.UserItemRecordStatus;

/**
 * 
 * @author fengwei.
 * @since 2015年3月4日 上午9:21:08.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class TestUserSyllabusItemRecordTotal {
	@Resource
	private IUserItemRecordDao userItemRecordDao;
	@Test
	@Transactional
	public void testOrderNo()
	{
		String userId = "fec3c54e-caaf-49ab-b302-f68a67737b67";
		String syllabusId = "688abb5e-cb96-4d19-9337-1de7f9863ac8";
		Long total = this.userItemRecordDao.totalUserSyllabusItemRecord(userId, syllabusId, 
				UserItemRecordStatus.RIGHT.getValue(),UserItemRecordStatus.LESS.getValue(),UserItemRecordStatus.WRONG.getValue());
		System.out.println(total);
	}
}
