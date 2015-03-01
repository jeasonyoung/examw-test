package com.examw.test.service.api.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import com.examw.test.model.api.LoginUser;
import com.examw.test.model.api.RegisterUser;
import com.examw.test.model.products.FrontUserInfo;
import com.examw.test.model.products.ProductUserInfo;
import com.examw.test.service.api.IHostAccessProxyService;
import com.examw.test.service.products.IProductUserService;
import com.examw.utils.HttpUtil;
import com.examw.utils.MD5Util;

/**
 * 访问中华考试网代理服务接口实现类。
 * 
 * @author yangyong
 * @since 2015年2月4日
 */
public class HostAccessProxyServiceImpl implements IHostAccessProxyService {
	private static final Logger logger = Logger.getLogger(HostAccessProxyServiceImpl.class);
	private static final String callback_perfix = "jsonpLogin";
	private String registerUrl,loginUrl,clientKey,source;
	private IProductUserService productUserService;
	private Map<String, String> registerErrors,loginErrors;
	/**
	 * 设置注册用户URL。
	 * @param registerUrl 
	 *	  注册用户URL。
	 */
	public void setRegisterUrl(String registerUrl) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入注册用户URL:%s", registerUrl));
		this.registerUrl = registerUrl;
	}
	/**
	 * 设置登录用户URL。
	 * @param loginUrl 
	 *	  登录用户URL。
	 */
	public void setLoginUrl(String loginUrl) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入登录用户URL:%s", loginUrl));
		this.loginUrl = loginUrl;
	}
	/**
	 * 设置访问密钥。
	 * @param clientKey 
	 *	  访问密钥。
	 */
	public void setClientKey(String clientKey) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入访问密钥:%s", clientKey));
		this.clientKey = clientKey;
	}
	/**
	 * 设置来源。
	 * @param source 
	 *	  来源。
	 */
	public void setSource(String source) {
		if(logger.isDebugEnabled()) logger.debug(String.format("注入来源:%s", source));
		this.source = source;
	}
	/**
	 * 设置产品用户服务接口。
	 * @param productUserService 
	 *	  产品用户服务接口。
	 */
	public void setProductUserService(IProductUserService productUserService) {
		this.productUserService = productUserService;
	}
	/**
	 * 设置注册用户错误代码集合。
	 * @param registerErrors 
	 *	  注册用户错误代码集合。
	 */
	public void setRegisterErrors(Map<String, String> registerErrors) {
		if(logger.isDebugEnabled()) logger.debug("注入注册用户错误代码集合...");
		this.registerErrors = registerErrors;
	}
	/**
	 * 设置登录用户错误代码集合。
	 * @param loginErrors 
	 *	  登录用户错误代码集合。
	 */
	public void setLoginErrors(Map<String, String> loginErrors) {
		if(logger.isDebugEnabled()) logger.debug("注入登录用户错误代码集合...");
		this.loginErrors = loginErrors;
	}
	/*
	 * 注册新用户。
	 * @see com.examw.test.service.api.IHostAccessProxyService#registerUser(com.examw.test.model.api.RegisterUser)
	 */
	@Override
	public void registerUser(RegisterUser registerUser) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("注册新用户...");
		if(StringUtils.isEmpty(this.registerUrl)) throw new Exception("未配置注册用户URL！");
		if(registerUser == null) throw new Exception("注册用户信息为空！");
		StringBuilder postBuilder = new StringBuilder(), checkBuilder = new StringBuilder();
		checkBuilder.append(registerUser.getAccount()).append("#")
							.append(registerUser.getPassword()).append("#")
							.append(registerUser.getPassword()).append("#")
							.append(registerUser.getEmail()).append("#")
							.append(this.clientKey).append("#")
							.append(registerUser.getUsername()).append("#")
							.append(registerUser.getPhone()).append("#")
							.append(registerUser.getChannel()).append("#")
							.append(this.source).append("#")
							.append(registerUser.getClientId());
		
		postBuilder.append("UserName=").append(registerUser.getAccount()).append("&")
						  .append("PassWord=").append(registerUser.getPassword()).append("&")
						  .append("repsw=").append(registerUser.getPassword()).append("&")
						  .append("e_mail=").append(registerUser.getEmail()).append("&")
						  //修改了数据
						  .append("r_name=").append(toUnicodeString(registerUser.getUsername())).append("&")
						  .append("Mobile=").append(registerUser.getPhone()).append("&")
						  .append("DoMain=").append(registerUser.getChannel()).append("&")
						  .append("SubSource=").append(this.source).append("&")
						  .append("ClientNo=").append(registerUser.getClientId()).append("&")
						  .append("Client=").append(registerUser.getClientName()).append("&")
						  .append("Version=").append(registerUser.getClientVersion()).append("&")
						  //.append("ClientKey=").append(this.clientKey).append("&")
						  .append("CheckType=RegUser").append("&")
						 .append("Md5Str=").append(MD5Util.MD5(checkBuilder.toString(),Charset.forName("GBK")));
		//访问中华考试网注册
		String callback =  HttpUtil.sendRequest(this.registerUrl, "POST", postBuilder.toString());
		if(logger.isDebugEnabled()) logger.debug(String.format("post-URL:%1$s?%2$s", this.registerUrl, postBuilder.toString()));
		if(logger.isDebugEnabled()) logger.debug(String.format("calback=>%s", callback));
		if(callback.indexOf("#") > -1){
			return;
		}
		if(!StringUtils.isEmpty(callback) && this.registerErrors != null){
			String error = this.registerErrors.get(callback); 
			if(!StringUtils.isEmpty(error)) throw new Exception(error);
		}
		throw new Exception(StringUtils.isEmpty(callback) ? "考试网未反馈数据!" : callback);
	}
	/*
	 * 登录用户。
	 * @see com.examw.test.service.api.IHostAccessProxyService#login(com.examw.test.model.api.LoginUser)
	 */
	@Override
	public String login(LoginUser loginUser) throws Exception {
		if(logger.isDebugEnabled()) logger.debug("登录用户...");
		if(StringUtils.isEmpty(this.loginUrl)) throw new Exception("未配置登录用户URL！");
		if(loginUser == null) throw new Exception("登录用户信息为空！");
		StringBuilder postBuilder = new StringBuilder(),checkBuilder = new StringBuilder();
		
		checkBuilder.append(loginUser.getAccount()).append("#")
							.append(loginUser.getPassword()).append("#")
							.append(this.clientKey).append("#")
							.append(this.source).append("#")
							.append(loginUser.getClientId());
		
		postBuilder.append("SubSource=").append(this.source).append("&")
						  .append("ClientNo=").append(loginUser.getClientId()).append("&")
						  //.append("ClientKey=").append(this.clientKey).append("&")
						  .append("CheckType=Login").append("&")
						  .append("UserName=").append(loginUser.getAccount()).append("&")
						  .append("PassWord=").append(loginUser.getPassword()).append("&")
						  .append("Md5Str=").append(MD5Util.MD5(checkBuilder.toString(),Charset.forName("GBK")));
		//登录中华考试网
		String callback = HttpUtil.sendRequest(this.loginUrl, "POST", postBuilder.toString(),"GBK");
		if(logger.isDebugEnabled()) logger.debug(String.format("post-URL:%1$s?%2$s", this.loginUrl, postBuilder.toString()));
		if(callback.indexOf(callback_perfix) > -1){
			Matcher m = Pattern.compile(callback_perfix +"\\((\\{.+?\\})\\)").matcher(callback);
			Map<String, String> callback_map =  null;
			if(m.find()){
				String p = m.group(1);
				if(logger.isDebugEnabled()) logger.debug(String.format("calback=>%s", p));
				callback_map = this.callbackParameters(p);
			}
			FrontUserInfo info = new FrontUserInfo();
			if(callback_map != null){
				info.setCode(callback_map.get("uid"));
				info.setName(callback_map.get("UserName"));
			}
			if(StringUtils.isEmpty(info.getCode()) || StringUtils.isEmpty(info.getName())){
				throw new Exception(String.format("未能从考试系统接口获取正确数据:%s", callback));
			}
			ProductUserInfo pui = this.productUserService.verifyFrontUser(info);
			if(pui == null) throw new Exception(String.format("验证题库系统时发生异常,未能得到用户［code=%1$s,name=%2$s］的题库系统ID!", info.getCode(), info.getName()));
			return pui.getId();
		}
		if(!StringUtils.isEmpty(callback) && this.loginErrors != null){
			String error = this.loginErrors.get(callback); 
			if(!StringUtils.isEmpty(error)) throw new Exception(error);
		}
		throw new Exception(StringUtils.isEmpty(callback) ? "考试网未反馈数据!" : callback);
	}
	//将反馈数据转换为Map
	@SuppressWarnings("unchecked")
	private Map<String, String> callbackParameters(String callback) throws JsonParseException, JsonMappingException, IOException{
		if(!StringUtils.isEmpty(callback)){
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> parameters = mapper.readValue(callback, Map.class);
			if(parameters != null && parameters.size() > 0){
				return parameters;
			}
		}
		return null;
	}
	//注册名字有点问题,必须要转一下码才行
	private String toUnicodeString(String s) {
		   StringBuffer sb = new StringBuffer();
		   for (int i = 0; i < s.length(); i++) {
		     char c = s.charAt(i);
		     if (c >= 0 && c <= 255) {
		       sb.append(c);
		     }
		     else {
		      sb.append("\\u"+Integer.toHexString(c));
		     }
		   }
		   return sb.toString();
		 }
}