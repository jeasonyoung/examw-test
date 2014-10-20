package test.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.service.library.IPaperReleaseService;

/**
 * 测试试卷发布。
 * 
 * @author yangyong
 * @since 2014年10月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class TestPaperRelease {
	private static final Logger logger = Logger.getLogger(TestPaperRelease.class);
	@Resource
	private IPaperReleaseService paperReleaseService;
	/**
	 * 测试试卷发布检查。
	 */
	@Test
	@Transactional
	public void testCheckPaperRelease(){
		logger.debug("测试试卷发布检查...");
		this.paperReleaseService.updateCheckRelease();
	}
	
}