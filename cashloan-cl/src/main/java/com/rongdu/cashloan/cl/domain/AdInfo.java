package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

public class AdInfo implements Serializable {
    private Long id;

    private String adUrl;

    private String skipUrl;

    private String title;

    private String message;

    private Integer site;

    private Integer sort;

    private Integer status;

    private Integer state;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl == null ? null : adUrl.trim();
    }

    public String getSkipUrl() {
        return skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl == null ? null : skipUrl.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getSite() {
        return site;
    }

    public void setSite(Integer site) {
        this.site = site;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", adUrl=").append(adUrl);
        sb.append(", skipUrl=").append(skipUrl);
        sb.append(", title=").append(title);
        sb.append(", message=").append(message);
        sb.append(", site=").append(site);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", state=").append(state);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}