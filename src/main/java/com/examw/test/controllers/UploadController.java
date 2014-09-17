package com.examw.test.controllers;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.examw.test.service.IFileUploadService;

/**
 * 文件上传控制器。
 * @author yangyong.
 * @since 2014-04-30.
 */
@Controller
public class UploadController {
	private static Logger logger = Logger.getLogger(UploadController.class);
	
	@Resource
	private IFileUploadService fileUploadService;
	/**
	 * 文件上传。
	 * 支持uploadify.
	 * @param file
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/uploads/upload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> upload(MultipartFile file, HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		try {
			logger.info("开始上传文件...");
			String root = request.getSession().getServletContext().getRealPath(".");
			String url = this.fileUploadService.upload(file.getOriginalFilename(), file.getBytes(), root);
			logger.info("url:" + url);
			result.put("error", 0);
			result.put("url", url.replaceAll("\\\\", "/")); //这里修改了下,加了工程名,在编辑器里不显示图片
			logger.info("上传文件完成。");
		} catch (IOException e) {
			logger.error("上传文件发生异常", e);
			result.put("error",1);
			result.put("message", e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
}