package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Date;

public class AccountRecord implements Serializable {

    private Long id;
    private String pCode;           //贷款产品编码
    private String pName;           //贷款产品名称
    private String picUrl;          //图片地址
    private String userId;          //用户编码
    private String userName;        //用户名
    private String ordersName;      //账单名
    private Double amtByMonth;      //月供金额
    private Integer repayType;      //还款方式（0一次性付款/1分期付款）ps:一次性付款-->自动默认总期数为1，且不可修改
    private Integer totalPeriods;   //总期数
    private Integer currentPeriods; //当前期数
    private String repayDay;        //还款日
    private String remainRepayDay;  //剩余还款日
    private String lastRepayDay;    //最后还款日
    private String repayRemind;     //还款提醒，年月日时分秒
    private Integer repayStatus;    //还款状态（0未还/1已还）
    private Integer isEnabled;      //删除标志（0已删除/1正常）
    private Date createTime;        //创建时间

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

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getLastRepayDay() {
        return lastRepayDay;
    }

    public void setLastRepayDay(String lastRepayDay) {
        this.lastRepayDay = lastRepayDay;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrdersName() {
        return ordersName;
    }

    public void setOrdersName(String ordersName) {
        this.ordersName = ordersName;
    }

    public Double getAmtByMonth() {
        return amtByMonth;
    }

    public void setAmtByMonth(Double amtByMonth) {
        this.amtByMonth = amtByMonth;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public Integer getTotalPeriods() {
        return totalPeriods;
    }

    public void setTotalPeriods(Integer totalPeriods) {
        this.totalPeriods = totalPeriods;
    }

    public Integer getCurrentPeriods() {
        return currentPeriods;
    }

    public void setCurrentPeriods(Integer currentPeriods) {
        this.currentPeriods = currentPeriods;
    }

    public String getRemainRepayDay() {
        return remainRepayDay;
    }

    public void setRemainRepayDay(String remainRepayDay) {
        this.remainRepayDay = remainRepayDay;
    }

    public String getRepayDay() {
        return repayDay;
    }

    public void setRepayDay(String repayDay) {
        this.repayDay = repayDay;
    }

    public String getRepayRemind() {
        return repayRemind;
    }

    public void setRepayRemind(String repayRemind) {
        this.repayRemind = repayRemind;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "AccountRecord{" +
                "id=" + id +
                ", pCode='" + pCode + '\'' +
                ", pName='" + pName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", ordersName='" + ordersName + '\'' +
                ", amtByMonth=" + amtByMonth +
                ", repayType=" + repayType +
                ", totalPeriods=" + totalPeriods +
                ", currentPeriods=" + currentPeriods +
                ", repayDay='" + repayDay + '\'' +
                ", remainRepayDay='" + remainRepayDay + '\'' +
                ", lastRepayDay='" + lastRepayDay + '\'' +
                ", repayRemind='" + repayRemind + '\'' +
                ", repayStatus=" + repayStatus +
                ", isEnabled=" + isEnabled +
                ", createTime=" + createTime +
                '}';
    }
}
