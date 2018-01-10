package com.rongdu.cashloan.cl.domain;

import java.util.Date;

public class Expressage {

    private Long id;

    private Long userId;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 电话
     */
    private String tel;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 是否收到货 1-已收货，0-未收货
     */
    private Integer flag;

    private Date creatTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Expressage{" +
                "id=" + id +
                ", userId=" + userId +
                ", consignee='" + consignee + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", flag=" + flag +
                ", creatTime=" + creatTime +
                '}';
    }
}
