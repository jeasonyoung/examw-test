package com.examw.test.junittest;
 
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.test.dao.library.IPaperDao;
import com.examw.test.service.library.IPaperService;

/**
 * 测试试卷预览数据
 * 
 * @author yangyong
 * @since 2014年8月16日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resource/spring-examw-test-2.xml"})
public class PaperPreviewTest {
	private static Logger logger = Logger.getLogger(PaperPreviewTest.class);
	@Resource
	private IPaperDao paperDao;
	@Resource
	private IPaperService paperService;
	
	
	ObjectMapper mapper = new ObjectMapper();
	{
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	String paperId = "237ae820-d52f-4ada-ae80-2ec5f0d6aeb7";

	@Transactional
	@Test
	public void Preview() throws Exception{
		
		//PaperPreview paperPreview = this.paperService.loadPaperPreview(paperId);
	    //String json = 	mapper.writeValueAsString(paperPreview);

		
	    logger.debug("PaperPreview-JSON:");
	    logger.debug(paperDao);
	   // logger.debug(mapper.writeValueAsString(this.paperService.loadStructures(paperId)));
	    logger.debug(mapper.writeValueAsString(this.paperService.loadPaperPreview(paperId)));
	}
	
}