package com.examw.test.service.publish;

import org.springframework.context.ApplicationEvent;

/**
 * 静态页面远程发布事件
 * 
 * @author yangyong
 * @since 2015年3月25日
 */
public class RemotePublishEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private String staticPageID;
	/**
	 * 获取静态页面ID。
	 * @return 静态页面ID。
	 */
	public String getStaticPageID() {
		return staticPageID;
	}
	/**
	 * 构造函数
	 * @param staticPageID
	 */
	public RemotePublishEvent(String	staticPageID) {
		super(staticPageID);
		this.staticPageID = staticPageID;
	}
}