package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 首页轮播图片实体
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 19:40:01
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class FlowPic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 图片地址
    */
    private String url;

    /**
     * 外链地址
     */
    private String wUrl;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * flowInfo表的id
    */
    private Integer pId;

    /**
     * flowInfo表的简码
     */
    private String pCode;

    /**
     * 图片类型 1-首页banner,2-分类图标,3-信用卡banner
     */
    private Integer pType;

    /**
     * 状态,10-开启,20-禁用
     */
    private Integer state;

    /**
     * 跳转类型,0-不跳，1-跳转到外链,2-跳转到图片,3-跳详情
     */
    private Integer skipType;

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
    * @param  id 要设置的主键Id
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取图片地址
    *
    * @return 图片地址
    */
    public String getUrl(){
        return url;
    }

    /**
    * 设置图片地址
    * 
    * @param url 要设置的图片地址
    */
    public void setUrl(String url){
        this.url = url;
    }

    /**
    * 获取排序
    *
    * @return 排序
    */
    public Integer getSort(){
        return sort;
    }

    /**
    * 设置排序
    * 
    * @param sort 要设置的排序
    */
    public void setSort(Integer sort){
        this.sort = sort;
    }

    /**
    * 获取flowInfo表的id
    *
    * @return flowInfo表的id
    */
    public Integer getPId(){
        return pId;
    }

    /**
    * 设置flowInfo表的id
    * 
    * @param pId 要设置的flowInfo表的id
    */
    public void setPId(Integer pId){
        this.pId = pId;
    }

    public String getPCode() {
        return pCode;
    }

    public String getwUrl() {
        return wUrl;
    }

    public void setwUrl(String wUrl) {
        this.wUrl = wUrl;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getSkipType() {
        return skipType;
    }

    public void setSkipType(Integer skipType) {
        this.skipType = skipType;
    }

    @Override
    public String toString() {
        return "FlowPic{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", wUrl='" + wUrl + '\'' +
                ", sort=" + sort +
                ", pId=" + pId +
                ", pCode='" + pCode + '\'' +
                ", pType=" + pType +
                ", state=" + state +
                ", skipType=" + skipType +
                '}';
    }
}