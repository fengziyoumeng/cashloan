package com.rongdu.cashloan.cl.domain;

public class HomeSort {

    private Long id;

    /**
     * 图标url
     */
    private String picUrl;


    /**
     * 状态
     */
    private Integer state;


    /**
     *标题
     */
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HomeSort{" +
                "id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", state=" + state +
                ", title='" + title + '\'' +
                '}';
    }
}
