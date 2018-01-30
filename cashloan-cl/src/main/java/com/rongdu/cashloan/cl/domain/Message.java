package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户消息实体
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-01-26 13:31:57
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    * 消息标题
    */
    private String title;

    /**
    * 消息类型  1-入驻，2-活动，3-口子
    */
    private Integer type;

    /**
    * 消息主题
    */
    private String message;

    /**
    * 消息模板，当标识为入驻时才要用到
    */
    private Integer msgTemplate;

    /**
    * 接收对象，0-所有人，非0-指定userId
    */
    private Long receiving;

    /**
    * 跳转地址
    */
    private String skipUrl;

    /**
    * 创建时间
    */
    private Date createTime;

    /**
    * 阅读时间
    */
    private Date readTime;


    private Long pId;

    private String tag;

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

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
    * 获取消息标题
    *
    * @return 消息标题
    */
    public String getTitle(){
        return title;
    }

    /**
    * 设置消息标题
    * 
    * @param title 要设置的消息标题
    */
    public void setTitle(String title){
        this.title = title;
    }

    /**
    * 获取消息类型  1-入驻，2-活动，3-口子
    *
    * @return 消息类型  1-入驻，2-活动，3-口子
    */
    public Integer getType(){
        return type;
    }

    /**
    * 设置消息类型  1-入驻，2-活动，3-口子
    * 
    * @param type 要设置的消息类型  1-入驻，2-活动，3-口子
    */
    public void setType(Integer type){
        this.type = type;
    }

    /**
    * 获取消息主题
    *
    * @return 消息主题
    */
    public String getMessage(){
        return message;
    }

    /**
    * 设置消息主题
    * 
    * @param message 要设置的消息主题
    */
    public void setMessage(String message){
        this.message = message;
    }

    /**
    * 获取消息模板，当标识为入驻时才要用到
    *
    * @return 消息模板，当标识为入驻时才要用到
    */
    public Integer getMsgTemplate(){
        return msgTemplate;
    }

    /**
    * 设置消息模板，当标识为入驻时才要用到
    * 
    * @param msgTemplate 要设置的消息模板，当标识为入驻时才要用到
    */
    public void setMsgTemplate(Integer msgTemplate){
        this.msgTemplate = msgTemplate;
    }

    /**
    * 获取接收对象，0-所有人，非0-指定userId
    *
    * @return 接收对象，0-所有人，非0-指定userId
    */
    public Long getReceiving(){
        return receiving;
    }

    /**
    * 设置接收对象，0-所有人，非0-指定userId
    * 
    * @param receiving 要设置的接收对象，0-所有人，非0-指定userId
    */
    public void setReceiving(Long receiving){
        this.receiving = receiving;
    }

    /**
    * 获取跳转地址
    *
    * @return 跳转地址
    */
    public String getSkipUrl(){
        return skipUrl;
    }

    /**
    * 设置跳转地址
    * 
    * @param skipUrl 要设置的跳转地址
    */
    public void setSkipUrl(String skipUrl){
        this.skipUrl = skipUrl;
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
    * 获取阅读时间
    *
    * @return 阅读时间
    */
    public Date getReadTime(){
        return readTime;
    }

    /**
    * 设置阅读时间
    * 
    * @param readTime 要设置的阅读时间
    */
    public void setReadTime(Date readTime){
        this.readTime = readTime;
    }


    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", msgTemplate=" + msgTemplate +
                ", receiving=" + receiving +
                ", skipUrl='" + skipUrl + '\'' +
                ", createTime=" + createTime +
                ", readTime=" + readTime +
                ", pId=" + pId +
                '}';
    }
}