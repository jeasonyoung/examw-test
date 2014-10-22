package com.examw.test.service;

import com.examw.test.model.AttachmentInfo;

/**
 * 上传文件服务。
 * @author yangyong.
 * @since 2014-04-30.
 */
public interface IFileUploadService {
	/**
	 * 文件上传。
	 * @param fileName
	 * 文件名称。
	 * @param contentType
	 * 内容类型。
	 * @param data
	 * 数据。
	 * @return
	 */
	String addUpload(String fileName,String contentType, byte[] data) throws Exception;
	/**
	 * 附件下载。
	 * @param fileId
	 * 文件ID。
	 * @return
	 * 附件信息。
	 */
	AttachmentInfo download(String fileId) throws Exception;
}