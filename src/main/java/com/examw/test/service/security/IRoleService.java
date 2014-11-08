package com.examw.test.service.security;

import java.util.List;

import com.examw.test.model.security.RoleInfo;
import com.examw.test.service.IBaseDataService;

/**
 * 角色服务接口。
 * @author yangyong.
 * @since 2014-05-06.
 */
public interface IRoleService extends IBaseDataService<RoleInfo> {
	/**
	 * 加载状态名称。
	 * @param status
	 * 状态值。
	 * @return
	 * 状态名称。
	 */
	String loadStatusName(Integer status);
	/**
	 * 加载全部角色数据集合。
	 * @return
	 */
	List<RoleInfo> loadAll();
	/**
	 * 加载角色权限集合。
	 * @param roleId
	 * 角色ID。
	 * @return
	 */
	String[] loadRoleRightIds(String roleId);
	/**
	 * 初始化角色。
	 * @param roleId
	 * 角色ID。
	 * @throws Exception
	 */
	void init(String roleId) throws Exception;
	/**
	 * 更新角色权限。
	 * @param roleId
	 * 角色ID。
	 * @param rightIds
	 * 权限ID集合。
	 */
	void updateRoleRights(String roleId,String[] rightIds) throws Exception;
}