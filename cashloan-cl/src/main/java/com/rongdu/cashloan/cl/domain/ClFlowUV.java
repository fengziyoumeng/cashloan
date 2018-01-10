package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 流量平台uv统计实体
 * 
 * @author
 * @version 1.0.0
 * @date 2017-11-15 13:32:13
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class ClFlowUV implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 对应cl_flow_info的id
    */
    private Long flowId;

    /**
    * 产品编码
    */
    private String pCode;

    /**
    * 统计日期
    */
    private Date countDate;

    /**
    * 点击数
    */
    private Long clickCount;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 更新时间
    */
    private Date updateTime;

    /**
     * 产品名称
     */
    private String pName;


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
    * @param
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取对应cl_flow_info的id
    *
    * @return 对应cl_flow_info的id
    */
    public Long getFlowId(){
        return flowId;
    }

    /**
    * 设置对应cl_flow_info的id
    * 
    * @param flowId 要设置的对应cl_flow_info的id
    */
    public void setFlowId(Long flowId){
        this.flowId = flowId;
    }

    /**
    * 获取产品编码
    *
    * @return 产品编码
    */
    public String getPCode(){
        return pCode;
    }

    /**
    * 设置产品编码
    * 
    * @param pCode 要设置的产品编码
    */
    public void setPCode(String pCode){
        this.pCode = pCode;
    }

    /**
    * 获取统计日期
    *
    * @return 统计日期
    */
    public Date getCountDate(){
        return countDate;
    }

    /**
    * 设置统计日期
    * 
    * @param countDate 要设置的统计日期
    */
    public void setCountDate(Date countDate){
        this.countDate = countDate;
    }

    /**
    * 获取点击数
    *
    * @return 点击数
    */
    public Long getClickCount(){
        return clickCount;
    }

    /**
    * 设置点击数
    * 
    * @param clickCount 要设置的点击数
    */
    public void setClickCount(Long clickCount){
        this.clickCount = clickCount;
    }

    /**
    * 获取创建时间
    *
    * @return 创建时间
    */
    public Date getCreateTime(){
        return createTime;
    }

    /**
    * 设置创建时间
    * 
    * @param createTime 要设置的创建时间
    */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
    * 获取更新时间
    *
    * @return 更新时间
    */
    public Date getUpdateTime(){
        return updateTime;
    }

    /**
    * 设置更新时间
    * 
    * @param updateTime 要设置的更新时间
    */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }
}