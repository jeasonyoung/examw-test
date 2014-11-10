package com.examw.test.service.security;

import java.util.List;

import com.examw.model.TreeNode;
import com.examw.test.model.security.MenuRightInfo;
import com.examw.test.service.IBaseDataService;
/**
 * 菜单权限服务。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface IMenuRightService extends IBaseDataService<MenuRightInfo> {
	/**
	 * 菜单权限初始化。
	 * @throws Exception
	 */
	void init() throws Exception;
	/**
	 * 加载全部的菜单权限树。
	 * @return
	 * 菜单权限树。
	 */
	List<TreeNode> loadAllMenuRights();
}