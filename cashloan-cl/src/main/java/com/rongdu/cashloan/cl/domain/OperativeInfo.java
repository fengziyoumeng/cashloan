package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

public class OperativeInfo implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 公司产品id,关联b_company_prod_detail
     */
    private Long proc_id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 号码
     */
    private String tel;

    /**
     * 微信号
     */
    private String wx;

    /**
     * 
     */
    private String qq;

    /**
     * 职位
     */
    private String job;

    /**
     * 负责模块
     */
    private String module;

    /**
     * b_operative_info
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
     * 公司产品id,关联b_company_prod_detail
     * @return proc_id 公司产品id,关联b_company_prod_detail
     */
    public Long getProc_id() {
        return proc_id;
    }

    /**
     * 公司产品id,关联b_company_prod_detail
     * @param proc_id 公司产品id,关联b_company_prod_detail
     */
    public void setProc_id(Long proc_id) {
        this.proc_id = proc_id;
    }

    /**
     * 姓名
     * @return name 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 姓名
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 号码
     * @return tel 号码
     */
    public String getTel() {
        return tel;
    }

    /**
     * 号码
     * @param tel 号码
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 微信号
     * @return wx 微信号
     */
    public String getWx() {
        return wx;
    }

    /**
     * 微信号
     * @param wx 微信号
     */
    public void setWx(String wx) {
        this.wx = wx == null ? null : wx.trim();
    }

    /**
     * 
     * @return qq 
     */
    public String getQq() {
        return qq;
    }

    /**
     * 
     * @param qq 
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * 职位
     * @return job 职位
     */
    public String getJob() {
        return job;
    }

    /**
     * 职位
     * @param job 职位
     */
    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    /**
     * 负责模块
     * @return module 负责模块
     */
    public String getModule() {
        return module;
    }

    /**
     * 负责模块
     * @param module 负责模块
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     *
     * @mbggenerated 2018-01-09
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
        OperativeInfo other = (OperativeInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProc_id() == null ? other.getProc_id() == null : this.getProc_id().equals(other.getProc_id()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getWx() == null ? other.getWx() == null : this.getWx().equals(other.getWx()))
            && (this.getQq() == null ? other.getQq() == null : this.getQq().equals(other.getQq()))
            && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
            && (this.getModule() == null ? other.getModule() == null : this.getModule().equals(other.getModule()));
    }

    /**
     *
     * @mbggenerated 2018-01-09
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProc_id() == null) ? 0 : getProc_id().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getWx() == null) ? 0 : getWx().hashCode());
        result = prime * result + ((getQq() == null) ? 0 : getQq().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getModule() == null) ? 0 : getModule().hashCode());
        return result;
    }

    /**
     *
     * @mbggenerated 2018-01-09
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", proc_id=").append(proc_id);
        sb.append(", name=").append(name);
        sb.append(", tel=").append(tel);
        sb.append(", wx=").append(wx);
        sb.append(", qq=").append(qq);
        sb.append(", job=").append(job);
        sb.append(", module=").append(module);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}