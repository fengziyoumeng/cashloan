package com.rongdu.cashloan.core.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;

import com.rongdu.cashloan.core.common.util.CacheUtil;

/**
 * 监听器
 * @author gc
 * @version 1.0.0
 * @date 2016年11月11日 下午3:40:52
 * Copyright 杭州民华金融信息服务有限公司  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */

public class WebConfigContextListener implements ServletContextListener,HttpSessionAttributeListener{

	private static Logger logger=Logger.getLogger(WebConfigContextListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		logger.info("启动加载...");
		
		// 系统参数
		CacheUtil.initSysConfig();
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		
	}
}
