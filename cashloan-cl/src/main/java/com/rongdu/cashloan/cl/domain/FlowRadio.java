package com.rongdu.cashloan.cl.domain;


import java.io.Serializable;

/**
 * 广播数据实体
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 20:42:52
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class FlowRadio implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 广播数据
    */
    private String radio;


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
    * 获取广播数据
    *
    * @return 广播数据
    */
    public String getRadio(){
        return radio;
    }

    /**
    * 设置广播数据
    * 
    * @param radio 要设置的广播数据
    */
    public void setRadio(String radio){
        this.radio = radio;
    }

}