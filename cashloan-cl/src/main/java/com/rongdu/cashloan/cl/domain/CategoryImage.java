package com.rongdu.cashloan.cl.domain;

public class CategoryImage {

    private Long id;

    //图标地址
    private String iconUrl;

    //标题
    private String title;

    //排序
    private Integer sort;

    //跳转地址
    private String skipUrl;

    //图标所在的页面1-首页,2-金融圈子
    private Integer site;

    //类型值
    private Integer typeValue;

    //状态 10-开启,20-开启
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Integer getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(Integer typeValue) {
        this.typeValue = typeValue;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CategoryImage{" +
                "id=" + id +
                ", iconUrl='" + iconUrl + '\'' +
                ", title='" + title + '\'' +
                ", sort=" + sort +
                ", skipUrl='" + skipUrl + '\'' +
                ", site=" + site +
                ", typeValue=" + typeValue +
                ", state=" + state +
                '}';
    }
}
