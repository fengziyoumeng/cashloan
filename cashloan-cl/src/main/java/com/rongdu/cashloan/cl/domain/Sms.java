package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信记录实体
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class Sms implements Serializable {

	 private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private String phone;

    /**
    * 
    */
    private Date sendTime;

    /**
    * 
    */
    private String content;

    /**
    * 
    */
    private Date respTime;

    /**
    * 
    */
    private String resp;

    /**
    * 
    */
    private String smsType;

    /**
    * 验证码
    */
    private String code;

    /**
    * 订单号
    */
    private String orderNo;
    
    /**
     * 验证码状态
     */
    private String state;
    
    /**
     * 验证码验证次数
     */
    private int verifyTime;


    /**
    * 获取主键Id
    *
    * @return id
    */
    public Long getId(){
    return id;
    }

    /**
    * 设置主键Id
    * 
    * @param 要设置的主键Id
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPhone(){
    return phone;
    }

    /**
    * 设置
    * 
    * @param phone 要设置的
    */
    public void setPhone(String phone){
    this.phone = phone;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getSendTime(){
    return sendTime;
    }

    /**
    * 设置
    * 
    * @param sendTime 要设置的
    */
    public void setSendTime(Date sendTime){
    this.sendTime = sendTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getContent(){
    return content;
    }

    /**
    * 设置
    * 
    * @param content 要设置的
    */
    public void setContent(String content){
    this.content = content;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Date getRespTime(){
    return respTime;
    }

    /**
    * 设置
    * 
    * @param respTime 要设置的
    */
    public void setRespTime(Date respTime){
    this.respTime = respTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getResp(){
    return resp;
    }

    /**
    * 设置
    * 
    * @param resp 要设置的
    */
    public void setResp(String resp){
    this.resp = resp;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getSmsType(){
    return smsType;
    }

    /**
    * 设置
    * 
    * @param smsType 要设置的
    */
    public void setSmsType(String smsType){
    this.smsType = smsType;
    }

    /**
    * 获取验证码
    *
    * @return 验证码
    */
    public String getCode(){
    return code;
    }

    /**
    * 设置验证码
    * 
    * @param code 要设置的验证码
    */
    public void setCode(String code){
    this.code = code;
    }

    /**
    * 获取订单号
    *
    * @return 订单号
    */
    public String getOrderNo(){
    return orderNo;
    }

    /**
    * 设置订单号
    * 
    * @param orderNo 要设置的订单号
    */
    public void setOrderNo(String orderNo){
    this.orderNo = orderNo;
    }

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the verifyTime
	 */
	public int getVerifyTime() {
		return verifyTime;
	}

	/**
	 * @param verifyTime the verifyTime to set
	 */
	public void setVerifyTime(int verifyTime) {
		this.verifyTime = verifyTime;
	}

}