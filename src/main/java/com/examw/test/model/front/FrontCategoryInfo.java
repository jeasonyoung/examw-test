package com.examw.test.model.front;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.test.model.settings.CategoryInfo;
import com.examw.test.model.settings.ExamInfo;

/**
 * 考试类别前台模型
 * @author fengwei.
 * @since 2014年9月4日 下午12:03:56.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FrontCategoryInfo extends CategoryInfo  {	
	private static final long serialVersionUID = 1L;
	private List<ExamInfo> exams;
	/**
	 * 获取 下属考试集合
	 * @return exams
	 * 
	 */
	public List<ExamInfo> getExams() {
		return exams;
	}
	/**
	 * 设置  下属考试集合
	 * @param exams
	 * 
	 */
	public void setExams(List<ExamInfo> exams) {
		this.exams = exams;
	}
}