package com.examw.test.service.syllabus;

import java.util.List;

import com.examw.test.model.syllabus.FrontSyllabusInfo;

/**
 * 前台大纲服务接口
 * @author fengwei.
 * @since 2015年3月4日 上午10:02:40.
 */
public interface IFrontSyllabusService {
	/**
	 * 查询科目下最新章节
	 * @param subjectId
	 * @param userId
	 * @return
	 */
	public List<FrontSyllabusInfo> loadLastSyllabuses(String subjectId,String userId);
}	
