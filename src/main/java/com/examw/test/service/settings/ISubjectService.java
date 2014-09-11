package com.examw.test.service.settings;

import java.util.List;

import com.examw.test.domain.settings.Subject;
import com.examw.test.model.settings.SubjectInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 科目服务接口
 * @author fengwei.
 * @since 2014年8月7日 上午10:13:56.
 */
public interface ISubjectService extends IBaseDataService<SubjectInfo>{
	/**
	 * 加载最大代码值
	 * @return
	 */
	Integer loadMaxCode();
	/**
	 * 科目集合类型转换
	 * @param data
	 * @return
	 */
	List<SubjectInfo> changeModel(List<Subject> data);
}
