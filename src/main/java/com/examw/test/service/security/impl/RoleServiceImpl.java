package com.examw.test.service.security.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.test.dao.security.IMenuRightDao;
import com.examw.test.dao.security.IRoleDao;
import com.examw.test.domain.security.MenuRight;
import com.examw.test.domain.security.Role;
import com.examw.test.model.security.MenuRightInfo;
import com.examw.test.model.security.RoleInfo;
import com.examw.test.service.impl.BaseDataServiceImpl;
import com.examw.test.service.security.IRoleService;
import com.examw.test.service.security.RoleStatus;
/**
 * 角色服务接口实现类。
 * @author yangyong.
 * @since 2014-05-06.
 */
public class RoleServiceImpl extends BaseDataServiceImpl<Role, RoleInfo> implements IRoleService {
	private static final Logger logger = Logger.getLogger(RoleServiceImpl.class);
	private IRoleDao roleDao;
	private IMenuRightDao menuRightDao;
	private Map<Integer, String> roleStatusNameMap;
	/**
	 * 设置角色数据接口。
	 * @param roleDao
	 * 角色数据接口。
	 */
	public void setRoleDao(IRoleDao roleDao) {
		if(logger.isDebugEnabled()) logger.debug("注入角色数据接口...");
		this.roleDao = roleDao;
	}
	/**
	 * 设置菜单权限数据接口。
	 * @param menuRightDao
	 * 菜单权限数据接口。
	 */
	public void setMenuRightDao(IMenuRightDao menuRightDao) {
		if(logger.isDebugEnabled()) logger.debug("注入菜单权限数据接口...");
		this.menuRightDao = menuRightDao;
	}
	/**
	 * 设置角色状态名称。
	 * @param roleStatusNameMap
	 * 角色状态名称。
	 */
	public void setRoleStatusNameMap(Map<Integer, String> roleStatusNameMap) {
		if(logger.isDebugEnabled()) logger.debug("注入角色状态名称...");
		this.roleStatusNameMap = roleStatusNameMap;
	}
	/*
	 *  加载状态名称。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadStatusName(int)
	 */
	@Override
	public String loadStatusName(Integer status) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入加载状态［%d］名称...", status));
		if(status == null || this.roleStatusNameMap == null || this.roleStatusNameMap.size() == 0) return null;
		return this.roleStatusNameMap.get(status);
	}
	/*
	 * 查询数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<Role> find(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据...");
		return this.roleDao.findRoles(info);
	}
	/*
	 * 数据模型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected RoleInfo changeModel(Role data) {
		if(logger.isDebugEnabled()) logger.debug("数据模型转换 Role => RoleInfo ... ");
		if(data == null) return null;
		RoleInfo info = new RoleInfo();
		BeanUtils.copyProperties(data, info);
		info.setStatusName(this.loadStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("查询数据统计...");
		return this.roleDao.total(info);
	}
	/*
	 * 加载全部角色数据。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadAll()
	 */
	@Override
	public List<RoleInfo> loadAll() {
		if(logger.isDebugEnabled()) logger.debug("加载全部角色数据...");
		return this.changeModel(this.roleDao.findRoles(new RoleInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public Integer getStatus() {return RoleStatus.ENABLED.getValue();}
			@Override
			public String getSort() { return "name";}
			@Override
			public String getOrder() { return "asc";}
		}));
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public RoleInfo update(RoleInfo info) {
		if(logger.isDebugEnabled()) logger.debug("更新数据...");
		if(info == null) return null;
		boolean isAdded = false;
		Role role = StringUtils.isEmpty(info.getId()) ? null : this.roleDao.load(Role.class, info.getId());
		if(isAdded = (role == null)){
			if(StringUtils.isEmpty(info.getId())) info.setId(UUID.randomUUID().toString());
			role = new Role();
		}
		BeanUtils.copyProperties(info, role);
		if(isAdded) this.roleDao.save(role);
		return this.changeModel(role);
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		if(logger.isDebugEnabled()) logger.debug("删除数据...");
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			Role data = this.roleDao.load(Role.class, ids[i]);
			if(data != null){
				if(logger.isDebugEnabled()) logger.debug("更新数据：" + ids[i]);
				this.roleDao.delete(data);
			}
		}
	}
	/*
	 * 初始化角色。
	 * @see com.examw.wechat.service.security.IRoleService#init()
	 */
	@Override
	public void init(String roleId) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("初始化角色...");
		if(StringUtils.isEmpty(roleId)){
			throw new Exception("角色ID未空！");
		}
		Role role = this.roleDao.load(Role.class, roleId);
		if(role == null){
			role = new Role();
			role.setId(roleId);
			role.setName("系统管理员");
			role.setDescription("系统管理员");
			role.setStatus(RoleStatus.ENABLED.getValue());
			this.roleDao.save(role);
		}
		role.setRights(new HashSet<>(this.menuRightDao.findMenuRights(new MenuRightInfo())));
		if(logger.isDebugEnabled()) logger.debug("初始化角色成功！");
	}
	/*
	 *  加载角色权限集合。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#loadRoleRightIds(java.lang.String)
	 */
	@Override
	public String[] loadRoleRightIds(String roleId) {
		if(logger.isDebugEnabled()) logger.debug(String.format(" 加载角色［%s］权限集合...", roleId));
		if(StringUtils.isEmpty(roleId)) return null;
		List<String> list = new ArrayList<>();
		Role role = this.roleDao.load(Role.class, roleId);
		if(role != null && role.getRights() != null && role.getRights().size() > 0){
			for(MenuRight right : role.getRights()){
				if(right == null) continue;
				if(!list.contains(right.getId())){
					list.add(right.getId());
				}
			}
		}
		return list.toArray(new String[0]);
	}
	/*
	 * 更新角色权限。
	 * @see com.examw.netplatform.service.admin.security.IRoleService#updateRoleRights(java.lang.String, java.lang.String[])
	 */
	@Override
	public void updateRoleRights(String roleId, String[] rightIds) throws Exception {
		if(logger.isDebugEnabled()) logger.debug(String.format("更新角色［%1$s］权限［%2$s］...", roleId, Arrays.toString(rightIds)));
		if(StringUtils.isEmpty(roleId)) throw new Exception("角色ID为空！");
		Role role = this.roleDao.load(Role.class, roleId);
		if(role == null) throw new Exception(String.format("角色［%s］不存在！", roleId));
		Set<MenuRight> rights = new HashSet<>();
		if(rightIds != null && rightIds.length > 0){
			for(int i = 0; i < rightIds.length; i++){
				if(StringUtils.isEmpty(rightIds[i])) continue;
				MenuRight menuRight = this.menuRightDao.load(MenuRight.class, rightIds[i]);
				if(menuRight != null) rights.add(menuRight);
			}
		}
		role.setRights(rights.size() == 0 ? null : rights); 
		if(logger.isDebugEnabled()) logger.debug("更新角色权限完毕！");
	}
}