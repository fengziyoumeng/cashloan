package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户认证实体
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class UserAuth implements Serializable {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键Id
	 */

	private Long id;

	/**
	 * 客户表 外键
	 */

	private Long userId;

	/**
	 * 身份认证状态
	 */

	private String idState;

	/**
	 * 紧急联系人状态
	 */

	private String contactState;

	/**
	 * 银行卡状态
	 */

	private String bankCardState;

	/**
	 * 芝麻授信状态
	 */

	private String zhimaState;

	/**
	 * 手机运营商认证状态
	 */

	private String phoneState;

	/**
	 * 工作信息状态
	 */

	private String workInfoState;

	/**
	 * 更多信息状态
	 */
	private String otherInfoState;
	
	/**
	 * 活体识别认证状态
	 */
	private String livingIdentifyState;

	/**
	 * 同盾认证状态
	 * @author pantheon
	 */
	private String tongdunState;

	/**
	 * 同盾认证时间
	 */
	private Date tongdunStateTime;


	/**
	 * 用钱呗新流程标记，不会影响融都以前流程
	 * @author wangxb
	 */
	private Integer newVersion;


	/**
	 * 获取主键Id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键Id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取客户表外键
	 * 
	 * @return userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置客户表外键
	 * 
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取身份认证状态
	 * 
	 * @return idState
	 */
	public String getIdState() {
		return idState;
	}

	/**
	 * 设置身份认证状态
	 * 
	 * @param idState
	 */
	public void setIdState(String idState) {
		this.idState = idState;
	}

	/**
	 * 获取紧急联系人状态
	 * 
	 * @return contactState
	 */
	public String getContactState() {
		return contactState;
	}

	/**
	 * 设置紧急联系人状态
	 * 
	 * @param contactState
	 */
	public void setContactState(String contactState) {
		this.contactState = contactState;
	}

	/**
	 * 获取银行卡状态
	 * 
	 * @return bankCardState
	 */
	public String getBankCardState() {
		return bankCardState;
	}

	/**
	 * 设置银行卡状态
	 * 
	 * @param bankCardState
	 */
	public void setBankCardState(String bankCardState) {
		this.bankCardState = bankCardState;
	}

	/**
	 * 获取芝麻授信状态
	 * 
	 * @return zhimaState
	 */
	public String getZhimaState() {
		return zhimaState;
	}

	/**
	 * 设置芝麻授信状态
	 * 
	 * @param zhimaState
	 */
	public void setZhimaState(String zhimaState) {
		this.zhimaState = zhimaState;
	}

	/**
	 * 获取手机运营商认证状态
	 * 
	 * @return phoneState
	 */
	public String getPhoneState() {
		return phoneState;
	}

	/**
	 * 设置手机运营商认证状态
	 * 
	 * @param phoneState
	 */
	public void setPhoneState(String phoneState) {
		this.phoneState = phoneState;
	}

	/**
	 * 获取工作信息状态
	 * 
	 * @return workInfoState
	 */
	public String getWorkInfoState() {
		return workInfoState;
	}

	/**
	 * 设置工作信息状态
	 * 
	 * @param workInfoState
	 */
	public void setWorkInfoState(String workInfoState) {
		this.workInfoState = workInfoState;
	}

	/**
	 * 获取更多信息状态
	 * 
	 * @return otherInfoState
	 */
	public String getOtherInfoState() {
		return otherInfoState;
	}

	/**
	 * 设置更多信息状态
	 * 
	 * @param otherInfoState
	 */
	public void setOtherInfoState(String otherInfoState) {
		this.otherInfoState = otherInfoState;
	}
	
	/**
	 *  获得活体识别认证状态
	 * @return
	 */
	public String getLivingIdentifyState() {
		return livingIdentifyState;
	}

	/**
	 * 设置活体识别认证状态
	 * @param livingIdentifyState
	 */
	public void setLivingIdentifyState(String livingIdentifyState) {
		this.livingIdentifyState = livingIdentifyState;
	}

	public Integer getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(Integer newVersion) {
		this.newVersion = newVersion;
	}

	public String getTongdunState() {
		return tongdunState;
	}

	public void setTongdunState(String tongdunState) {
		this.tongdunState = tongdunState;
	}

	public Date getTongdunStateTime() {
		return tongdunStateTime;
	}

	public void setTongdunStateTime(Date tongdunStateTime) {
		this.tongdunStateTime = tongdunStateTime;
	}
}