package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

public class BannerInfo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * banner图地址
     */
    private String banner_url;

    /**
     * t跳转地址
     */
    private String skip_url;

    /**
     * 排序
     */
    private String sort;

    /**
     * 位置 0-金融圈子banner图,1-首页banner图
     */
    private String site;

    /**
     * 0-不跳,1-跳转skip_url地址,2-跳转到一张图片,3-区分app和h5内部跳转
     */
    private String status;

    /**
     * 状态 10-开启,20-关闭
     */
    private String state;

    /**
     * cl_banner_info
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * banner图地址
     * @return banner_url banner图地址
     */
    public String getBanner_url() {
        return banner_url;
    }

    /**
     * banner图地址
     * @param banner_url banner图地址
     */
    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url == null ? null : banner_url.trim();
    }

    /**
     * t跳转地址
     * @return skip_url t跳转地址
     */
    public String getSkip_url() {
        return skip_url;
    }

    /**
     * t跳转地址
     * @param skip_url t跳转地址
     */
    public void setSkip_url(String skip_url) {
        this.skip_url = skip_url == null ? null : skip_url.trim();
    }

    /**
     * 排序
     * @return sort 排序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 排序
     * @param sort 排序
     */
    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    /**
     * 位置 0-金融圈子banner图,1-首页banner图
     * @return site 位置 0-金融圈子banner图,1-首页banner图
     */
    public String getSite() {
        return site;
    }

    /**
     * 位置 0-金融圈子banner图,1-首页banner图
     * @param site 位置 0-金融圈子banner图,1-首页banner图
     */
    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    /**
     * 0-不跳,1-跳转skip_url地址,2-跳转到一张图片,3-区分app和h5内部跳转
     * @return status 0-不跳,1-跳转skip_url地址,2-跳转到一张图片,3-区分app和h5内部跳转
     */
    public String getStatus() {
        return status;
    }

    /**
     * 0-不跳,1-跳转skip_url地址,2-跳转到一张图片,3-区分app和h5内部跳转
     * @param status 0-不跳,1-跳转skip_url地址,2-跳转到一张图片,3-区分app和h5内部跳转
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 状态 10-开启,20-关闭
     * @return state 状态 10-开启,20-关闭
     */
    public String getState() {
        return state;
    }

    /**
     * 状态 10-开启,20-关闭
     * @param state 状态 10-开启,20-关闭
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-10
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BannerInfo other = (BannerInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBanner_url() == null ? other.getBanner_url() == null : this.getBanner_url().equals(other.getBanner_url()))
            && (this.getSkip_url() == null ? other.getSkip_url() == null : this.getSkip_url().equals(other.getSkip_url()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getSite() == null ? other.getSite() == null : this.getSite().equals(other.getSite()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

    /**
     *
     * @mbggenerated 2018-01-10
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBanner_url() == null) ? 0 : getBanner_url().hashCode());
        result = prime * result + ((getSkip_url() == null) ? 0 : getSkip_url().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getSite() == null) ? 0 : getSite().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-10
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", banner_url=").append(banner_url);
        sb.append(", skip_url=").append(skip_url);
        sb.append(", sort=").append(sort);
        sb.append(", site=").append(site);
        sb.append(", status=").append(status);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}