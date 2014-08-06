package com.examw.test.service.library;

import com.examw.test.model.library.SourceInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 来源服务接口。
 * @author yangyong.
 * @since 2014-08-06.
 */
public interface ISourceService extends IBaseDataService<SourceInfo> {
	/**
	 * 加载最大的代码值。
	 * @return 最大代码值。
	 */
	Integer loadMaxCode();
}