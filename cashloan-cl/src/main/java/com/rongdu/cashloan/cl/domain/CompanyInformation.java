package com.rongdu.cashloan.cl.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CompanyInformation implements Serializable{
    private Long id;
    //用户id
    private Long userId;
    //公司名称
    private String companyName;
    //企业法人
    private String legalPersonName;
    //身份证号
    private String IDNumber;
    //企业联系人
    private String contactPerson;
    //联系人电话
    private String contactTel;
    //公司简介
    private String introduction;

    //营业执照
    private String licensePic;
    //身份证正面
    private String identityFrontPic;
    //身份证反面
    private String identityReversePic;
    //手持身份证
    private String holdCardPic;
    //公司类型
    private Integer companyType;

    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //审核时间
    private Date auditTime;
    //审核状态
    private Integer auditState;
    //审核信息
    private String auditMessage;
    //拒绝理由
    private List<Integer> regectMessage;
    //审核人
    private String auditPerson;
    //公司地址
    private String companyAddress;
    //状态
    private Integer state;
    //注册资金
    private String registeredCapital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Integer> getRegectMessage() {
        return regectMessage;
    }

    public void setRegectMessage(List<Integer> regectMessage) {
        this.regectMessage = regectMessage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(String licensePic) {
        this.licensePic = licensePic;
    }

    public String getIdentityFrontPic() {
        return identityFrontPic;
    }

    public void setIdentityFrontPic(String identityFrontPic) {
        this.identityFrontPic = identityFrontPic;
    }

    public String getIdentityReversePic() {
        return identityReversePic;
    }

    public void setIdentityReversePic(String identityReversePic) {
        this.identityReversePic = identityReversePic;
    }

    public String getHoldCardPic() {
        return holdCardPic;
    }

    public void setHoldCardPic(String holdCardPic) {
        this.holdCardPic = holdCardPic;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }

    public String getAuditMessage() {
        return auditMessage;
    }

    public void setAuditMessage(String auditMessage) {
        this.auditMessage = auditMessage;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    @Override
    public String toString() {
        return "CompanyInformation{" +
                "id=" + id +
                ", userId=" + userId +
                ", companyName='" + companyName + '\'' +
                ", legalPersonName='" + legalPersonName + '\'' +
                ", IDNumber='" + IDNumber + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", introduction='" + introduction + '\'' +
                ", licensePic='" + licensePic + '\'' +
                ", identityFrontPic='" + identityFrontPic + '\'' +
                ", identityReversePic='" + identityReversePic + '\'' +
                ", holdCardPic='" + holdCardPic + '\'' +
                ", companyType=" + companyType +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", auditTime=" + auditTime +
                ", auditState=" + auditState +
                ", auditMessage='" + auditMessage + '\'' +
                ", regectMessage=" + regectMessage +
                ", auditPerson='" + auditPerson + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", state=" + state +
                ", registeredCapital='" + registeredCapital + '\'' +
                '}';
    }
}
