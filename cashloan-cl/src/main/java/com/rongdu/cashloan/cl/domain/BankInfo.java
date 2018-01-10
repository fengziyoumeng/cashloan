package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

/**
 * 银行信息实体类（信用卡模块）
 * 
 * @author
 * @version 1.0.0
 * @date 2016-12-15 15:47:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class BankInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 银行名称
    */
    private String bankName;

    /**
    * logo地址
    */
    private String logoUrl;

    /**
     * 跳转地址
     */
    private String skipUrl;

    /**
     * 营销信息
     */
    private String message;

    /**
     * 类型 1-普通列表，2—分类列表
     */
    private Integer showType;

    /**
     * 类型 1-普通列表，2—分类列表
     */
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getShowType() {
        return showType;
    }

    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BankInfo{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", skipUrl='" + skipUrl + '\'' +
                ", message='" + message + '\'' +
                ", showType=" + showType +
                ", state=" + state +
                '}';
    }
}