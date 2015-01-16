package com.examw.test.service.publish.impl;

import org.apache.log4j.Logger;

/**
 * 最热试卷模版处理。
 * 
 * @author yangyong
 * @since 2015年1月15日
 */
public class HotsPaperTemplateProcess  extends NewsPaperTemplateProcess {
	private static final Logger logger = Logger.getLogger(HotsPaperTemplateProcess.class);
	
	@Override
	protected int addTemplateProcess() throws Exception {
		if(logger.isDebugEnabled())logger.debug("最热试卷模版处理...");
		// TODO Auto-generated method stub
		return 0;
	}

}