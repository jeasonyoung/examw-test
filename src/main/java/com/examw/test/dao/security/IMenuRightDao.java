package com.examw.test.dao.security;

import java.util.List;

import com.examw.test.dao.IBaseDao;
import com.examw.test.domain.security.MenuRight;
import com.examw.test.model.security.MenuRightInfo;

/**
 * 菜单权限数据访问接口。
 * @author yangyong.
 * @since 2014-05-04.
 */
public interface IMenuRightDao extends IBaseDao<MenuRight> {
	/**
	 * 加载菜单权限数据。
	 * @param menuId
	 * 菜单ID。
	 * @param rightId
	 * 权限ID。
	 * @return
	 * 菜单权限数据。
	 */
	MenuRight loadMenuRight(String menuId,String rightId);
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 查询结果。
	 */
	List<MenuRight> findMenuRights(MenuRightInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	 Long total(MenuRightInfo info);
}