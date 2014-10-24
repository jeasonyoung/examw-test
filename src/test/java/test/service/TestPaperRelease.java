package test.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.service.library.IExercisesDailyService;
import com.examw.test.service.library.IPaperReleaseService;

/**
 * 测试试卷发布。
 * 
 * @author yangyong
 * @since 2014年10月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class TestPaperRelease {
	private static final Logger logger = Logger.getLogger(TestPaperRelease.class);
	@Resource
	private IPaperReleaseService paperReleaseService;
	@Resource
	private IExercisesDailyService exercisesDailyService;
	/**
	 * 测试试卷发布检查。
	 */
	@Test
	@Transactional
	public void testMain() throws Exception{
		logger.debug("自动发布每日一练....");
		this.exercisesDailyService.addAutoDailyPapers();
	}
	//测试试卷发布检查
	protected void testCheckPaperRelease(){
		logger.debug("测试试卷发布检查...");
		this.paperReleaseService.updateCheckRelease();
	}
	
}