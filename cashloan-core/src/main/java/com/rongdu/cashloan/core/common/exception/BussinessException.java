package com.rongdu.cashloan.core.common.exception;

/**
 * 自定义异常类
 * @author zlh
 * @version 1.0
 * @date 2016年11月3日 下午3:22:21
 * Copyright 杭州民华金融信息服务有限公司  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 响应状态
	 */
	private String code;

	public BussinessException() {
		super();
	}
	
	
	public BussinessException(String message) {
		super(message);
	}
	
	/**
	 * 构造函数
	 * @param code
	 * @param message
	 * @param
	 */
	public BussinessException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	/**
	 * 获取 响应状态
	 * @return 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 响应状态
	 * @param 
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
}
