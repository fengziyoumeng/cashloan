package com.rongdu.cashloan.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String orderNo;

    private String orderName;

    private Date orderCreateDate;

    private Long userId;


    public UserOrder() {

    }

    public UserOrder(String orderNo, String orderName,Date orderCreateDate,Long userId) {
        this.orderNo = orderNo;
        this.orderName = orderName;
        this.orderCreateDate = orderCreateDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getCreateDate() {
        return orderCreateDate;
    }

    public void setCreateDate(Date orderCreateDate) {
        this.orderCreateDate = orderCreateDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", orderName='" + orderName + '\'' +
                ", orderCreateDate=" + orderCreateDate +
                ", userId=" + userId +
                '}';
    }
}

