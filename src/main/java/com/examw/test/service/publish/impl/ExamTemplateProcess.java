package com.examw.test.service.publish.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.examw.test.domain.publish.StaticPage;

/**
 * 考试模版处理。
 * 
 * @author yangyong
 * @since 2014年12月31日
 */
public class ExamTemplateProcess extends BaseTemplateProcess {
	private static final Logger logger = Logger.getLogger(ExamTemplateProcess.class);
	/*
	 * 模版处理。
	 * @see com.examw.test.service.publish.impl.BaseTemplateProcess#templateProcess()
	 */
	@Override
	protected List<StaticPage> templateProcess() throws Exception {
		if(logger.isDebugEnabled()) logger.debug("模版处理...");
		// TODO Auto-generated method stub
		return null;
	}

}