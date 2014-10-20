package test.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.model.library.PaperPreview;
import com.examw.test.service.library.IFrontPaperService;
/**
 * 测试前端试卷。
 * 
 * @author yangyong
 * @since 2014年10月20日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-test.xml"})
public class TestFrontPaper {
	private static final Logger logger = Logger.getLogger(TestFrontPaper.class);
	@Resource
	private IFrontPaperService frontPaperService;
	/**
	 * 测试从发布中加载试卷预览对象.
	 * @throws Exception
	 */
	@Test
	@Transactional
	public void testPaperPreview() throws Exception{
		String paperId = "8b422a88-9d9b-4177-a4b5-5ed9398a5919";
		logger.debug(String.format("测试从发布中加载试卷［paperId = %s］预览对象...", paperId));
		 
		PaperPreview preview = this.frontPaperService.loadPaperContent(paperId);
		 
		logger.debug(preview);
		logger.debug("JSON对象：");
		ObjectMapper mapper = new ObjectMapper();
		logger.debug(mapper.writeValueAsString(preview));
	}
}