package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * sdk识别记录表实体
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-04-20 09:47:04
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class UserSdkLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 识别类型
    */
    private String timeType;

    /**
    * 添加时间
    */
    private Date createTime;


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
    * 获取用户id
    *
    * @return 用户id
    */
    public Long getUserId(){
    return userId;
    }

    /**
    * 设置用户id
    * 
    * @param userId 要设置的用户id
    */
    public void setUserId(Long userId){
    this.userId = userId;
    }

    /**
    * 获取识别类型
    *
    * @return 识别类型
    */
    public String getTimeType(){
    return timeType;
    }

    /**
    * 设置识别类型
    * 
    * @param timeType 要设置的识别类型
    */
    public void setTimeType(String timeType){
    this.timeType = timeType;
    }

    /**
    * 获取添加时间
    *
    * @return 添加时间
    */
    public Date getCreateTime(){
    return createTime;
    }

    /**
    * 设置添加时间
    * 
    * @param createTime 要设置的添加时间
    */
    public void setCreateTime(Date createTime){
    this.createTime = createTime;
    }

}