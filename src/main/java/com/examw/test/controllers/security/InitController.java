package com.examw.test.controllers.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.test.service.security.IMenuRightService;
import com.examw.test.service.security.IMenuService;
import com.examw.test.service.security.IRightService;
import com.examw.test.service.security.IRoleService;
import com.examw.test.service.security.IUserService;

/**
 * 权限初始化控制器。
 * @author yangyong.
 * @since 2014-06-30.
 */
@Controller
@RequestMapping("/security/init")
public class InitController {
	private static final Logger logger = Logger.getLogger(InitController.class);
	//菜单服务接口。 
	@Resource
	private IMenuService menuService;
	//基础权限服务接口。
	@Resource
	private IRightService rightService;
	//菜单权限服务接口。
	@Resource
	private IMenuRightService menuRightService;
	//角色服务接口。
	@Resource
	private IRoleService roleService;
	//用户服务接口。
	@Resource
	private IUserService userService;
	/**
	 * 初始化。
	 */
	@RequestMapping(value = {"","/"}, method={RequestMethod.GET, RequestMethod.POST})
	public String init(Model model){
		if(logger.isDebugEnabled()) logger.debug("加载安全初始化...");
		StringBuilder msgBuilder = new StringBuilder();
		String msg = null;
		try{
			msgBuilder.append(msg = "权限初始化开始....").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			msgBuilder.append(msg = "开始初始化菜单数据...").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			this.menuService.init();
			msgBuilder.append(msg = "完成菜单数据初始化！").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			msgBuilder.append(msg = "开始基础权限数据初始化...").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			this.rightService.init();
			msgBuilder.append(msg = "完成基础权限数据初始化！").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			msgBuilder.append(msg = "开始菜单权限初始化...").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			this.menuRightService.init();
			msgBuilder.append(msg = "完成菜单权限数据初始化!").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			msgBuilder.append(msg = "开始角色初始化....").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			
			String roleId = "1a727962-905d-47a6-9002-5168d0f2cfcb",account = "admin",password = "123456";
 			this.roleService.init(roleId);
 			msgBuilder.append(msg = "完成角色数据初始化!［roleId:"+ roleId +"］").append("\r\n");
 			if(logger.isDebugEnabled())logger.debug(msg);
 			msgBuilder.append(msg = "开始用户初始化....").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			
			this.userService.init(roleId, account, password);
			msgBuilder.append(msg = "完成用户初始化.[账号："+ account+"  密码："+ password +"]").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			
		}catch(Exception e){
			msgBuilder.append(msg =  "初始化时异常：" + e.getMessage()).append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
			logger.error(e);
		}finally{
			msgBuilder.append(msg = "初始化结束！").append("\r\n");
			if(logger.isDebugEnabled())logger.debug(msg);
		}
		model.addAttribute("MESSAGE", msgBuilder.toString());
		return "security/init";
	}
}