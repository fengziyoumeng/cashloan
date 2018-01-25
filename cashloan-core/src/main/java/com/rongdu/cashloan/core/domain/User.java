package com.rongdu.cashloan.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息实体
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-24 15:24:59
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;
    
    /**
     * 唯一标识uuid
     */
     private String uuid;

    /**
    * 登录名
    */
    private String loginName;

    /**
    * 登录密码
    */
    private String loginPwd;

    /**
    * 上次登录密码修改时间
    */
    private Date loginpwdModifyTime;

	private Date loginTime;

    /**
    * 交易密码
    */
    private String tradePwd;

    /**
    * 上次交易密码修改时间
    */
    private Date tradepwdModifyTime;
    
    /**
     * 注册时间
     */
     private Date registTime;

     /**
     * 注册客户端
     */
     private String registerClient;

    /**
    * 邀请码
    */
    private String invitationCode;

	private String byInvitationCode;

	private Integer shareFlag;
    
	/**
	 * 渠道
	 */
    private Long channelId;
     
    /**
    * 代理等级 ，1一级，2二级，3普通用户
    */
    private Integer level;

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * 获取主键Id
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键Id
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取唯一标识uuid
	 * @return 唯一标识uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 设置唯一标识uuid
	 * @param
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 获取登录名
	 * @return loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 设置登录名
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 获取登录密码
	 * @return loginPwd
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * 设置登录密码
	 * @param loginPwd
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	/**
	 * 获取上次登录密码修改时间
	 * @return loginpwdModifyTime
	 */
	public Date getLoginpwdModifyTime() {
		return loginpwdModifyTime;
	}

	/**
	 * 设置上次登录密码修改时间
	 * @param loginpwdModifyTime
	 */
	public void setLoginpwdModifyTime(Date loginpwdModifyTime) {
		this.loginpwdModifyTime = loginpwdModifyTime;
	}

	/**
	 * 获取交易密码
	 * @return tradePwd
	 */
	public String getTradePwd() {
		return tradePwd;
	}

	/**
	 * 设置交易密码
	 * @param tradePwd
	 */
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}

	/**
	 * 获取上次交易密码修改时间
	 * @return tradepwdModifyTime
	 */
	public Date getTradepwdModifyTime() {
		return tradepwdModifyTime;
	}

	/**
	 * 设置上次交易密码修改时间
	 * @param tradepwdModifyTime
	 */
	public void setTradepwdModifyTime(Date tradepwdModifyTime) {
		this.tradepwdModifyTime = tradepwdModifyTime;
	}

	/**
	 * 获取注册时间
	 * @return registTime
	 */
	public Date getRegistTime() {
		return registTime;
	}

	/**
	 * 设置注册时间
	 * @param registTime
	 */
	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	/**
	 * 获取注册客户端
	 * @return registerClient
	 */
	public String getRegisterClient() {
		return registerClient;
	}

	/**
	 * 设置注册客户端
	 * @param registerClient
	 */
	public void setRegisterClient(String registerClient) {
		this.registerClient = registerClient;
	}

	/**
	 * 获取邀请码
	 * @return invitationCode
	 */
	public String getInvitationCode() {
		return invitationCode;
	}

	/**
	 * 设置邀请码
	 * @param invitationCode
	 */
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	/**
	 * 获取渠道
	 * @return channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * 设置渠道
	 * @param channelId
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * 获取代理等级，1一级，2二级，3普通用户
	 * @return level
	 */
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置代理等级，1一级，2二级，3普通用户
	 * @param level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getByInvitationCode() {
		return byInvitationCode;
	}

	public void setByInvitationCode(String byInvitationCode) {
		this.byInvitationCode = byInvitationCode;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(Integer shareFlag) {
		this.shareFlag = shareFlag;
	}
}