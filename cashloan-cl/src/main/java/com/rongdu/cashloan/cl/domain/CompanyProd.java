package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;

public class CompanyProd implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 一级分类：1-商务渠道（上面栏），2-企业服务（下面栏）
     */
    private Integer big_type;

    /**
     * 二级分类：产品大分类（11贷款、12流量平台、13信用卡、14P2P、21公司支持、22技术支持、23风险控制、24营销策划）
     */
    private Integer type;

    /**
     * 类型名
     */
    private String type_name;

    /**
     * 产品分类图标
     */
    private String type_img_path;

    /**
     * 排序（big_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    private Integer sort;

    /**
     * b_company_prod
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
     * 一级分类：1-商务渠道（上面栏），2-企业服务（下面栏）
     * @return big_type 一级分类：1-商务渠道（上面栏），2-企业服务（下面栏）
     */
    public Integer getBig_type() {
        return big_type;
    }

    /**
     * 一级分类：1-商务渠道（上面栏），2-企业服务（下面栏）
     * @param big_type 一级分类：1-商务渠道（上面栏），2-企业服务（下面栏）
     */
    public void setBig_type(Integer big_type) {
        this.big_type = big_type;
    }

    /**
     * 二级分类：产品大分类（11贷款、12流量平台、13信用卡、14P2P、21公司支持、22技术支持、23风险控制、24营销策划）
     * @return type 二级分类：产品大分类（11贷款、12流量平台、13信用卡、14P2P、21公司支持、22技术支持、23风险控制、24营销策划）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 二级分类：产品大分类（11贷款、12流量平台、13信用卡、14P2P、21公司支持、22技术支持、23风险控制、24营销策划）
     * @param type 二级分类：产品大分类（11贷款、12流量平台、13信用卡、14P2P、21公司支持、22技术支持、23风险控制、24营销策划）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 类型名
     * @return type_name 类型名
     */
    public String getType_name() {
        return type_name;
    }

    /**
     * 类型名
     * @param type_name 类型名
     */
    public void setType_name(String type_name) {
        this.type_name = type_name == null ? null : type_name.trim();
    }

    /**
     * 产品分类图标
     * @return type_img_path 产品分类图标
     */
    public String getType_img_path() {
        return type_img_path;
    }

    /**
     * 产品分类图标
     * @param type_img_path 产品分类图标
     */
    public void setType_img_path(String type_img_path) {
        this.type_img_path = type_img_path == null ? null : type_img_path.trim();
    }

    /**
     * 排序（big_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     * @return sort 排序（big_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 排序（big_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     * @param sort 排序（big_type=1的，以数字123...从左往右排序；对于parent_type=2的，上下左右排序，以数字1234为序，不过1left-左,2top-上,3bottom-下,4right-右为序）
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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
        CompanyProd other = (CompanyProd) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBig_type() == null ? other.getBig_type() == null : this.getBig_type().equals(other.getBig_type()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getType_name() == null ? other.getType_name() == null : this.getType_name().equals(other.getType_name()))
            && (this.getType_img_path() == null ? other.getType_img_path() == null : this.getType_img_path().equals(other.getType_img_path()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()));
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
        result = prime * result + ((getBig_type() == null) ? 0 : getBig_type().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getType_name() == null) ? 0 : getType_name().hashCode());
        result = prime * result + ((getType_img_path() == null) ? 0 : getType_img_path().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
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
        sb.append(", big_type=").append(big_type);
        sb.append(", type=").append(type);
        sb.append(", type_name=").append(type_name);
        sb.append(", type_img_path=").append(type_img_path);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}