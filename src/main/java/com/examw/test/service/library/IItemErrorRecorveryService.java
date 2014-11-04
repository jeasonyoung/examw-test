package com.examw.test.service.library;

import java.util.Map;

import com.examw.test.model.library.ItemErrorRecorveryInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 试题纠错服务
 * @author fengwei.
 * @since 2014年11月4日 下午2:30:37.
 */
public interface IItemErrorRecorveryService extends IBaseDataService<ItemErrorRecorveryInfo> {
	/**
	 * 加载错误类型名称。
	 * @param type
	 * 题型值。
	 * @return
	 * 题型名称。
	 */
	String loadErrorTypeName(Integer type);
	/**
	 * 加载题目纠错状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载错误类型映射
	 * @return
	 */
	Map<String,String> loadErrorTypes();
}
