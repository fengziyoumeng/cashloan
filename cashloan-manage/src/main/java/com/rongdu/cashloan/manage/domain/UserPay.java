package com.rongdu.cashloan.manage.domain;

import java.io.Serializable;
import java.util.Date;

public class UserPay implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Double price;

    private Long userId;

    private Date OrderCreateDate;


    public UserPay() {

    }

    public UserPay(Double price, Long userId, Date orderCreateDate) {
        this.price = price;
        this.userId = userId;
        OrderCreateDate = orderCreateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOrderCreateDate() {
        return OrderCreateDate;
    }

    public void setOrderCreateDate(Date orderCreateDate) {
        OrderCreateDate = orderCreateDate;
    }

    @Override
    public String toString() {
        return "UserPay{" +
                "id=" + id +
                ", price=" + price +
                ", userId=" + userId +
                ", OrderCreateDate=" + OrderCreateDate +
                '}';
    }
}

