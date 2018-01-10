package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

/**
 * 短信记录实体
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-13 18:36:01
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class SmsTpl implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 
    */
    private String type;

    /**
    * 
    */
    private String typeName;

    /**
    * 
    */
    private String tpl;

    /**
    * 模板编号
    */
    private String number;


    private String state;


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
    * @param id
    */
    public void setId(Long id){
    this.id = id;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getType(){
    return type;
    }

    /**
    * 设置
    * 
    * @param type 要设置的
    */
    public void setType(String type){
    this.type = type;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getTypeName(){
    return typeName;
    }

    /**
    * 设置
    * 
    * @param typeName 要设置的
    */
    public void setTypeName(String typeName){
    this.typeName = typeName;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getTpl(){
    return tpl;
    }

    /**
    * 设置
    * 
    * @param tpl 要设置的
    */
    public void setTpl(String tpl){
    this.tpl = tpl;
    }

    /**
    * 获取模板编号
    *
    * @return 模板编号
    */
    public String getNumber(){
    return number;
    }

    /**
    * 设置模板编号
    * 
    * @param number 要设置的模板编号
    */
    public void setNumber(String number){
    this.number = number;
    }

   public String getState() {
      return state;
   }

   public void setState(String state) {
      this.state = state;
   }
}