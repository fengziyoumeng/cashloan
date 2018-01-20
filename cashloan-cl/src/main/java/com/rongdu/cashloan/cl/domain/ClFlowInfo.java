package com.rongdu.cashloan.cl.domain;

import com.rongdu.cashloan.cl.util.StateUtil;
import org.apache.poi.ss.formula.functions.T;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.Serializable;
import java.util.*;

/**
 * 运营商认证通知详情表实体
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-11-12 17:27:45
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 public class ClFlowInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键Id
    */
    private Long id;

    /**
    *  产品编码（redis中使用）
    */
    private String pCode;

    private String picName;

    /**
    * 图片地址
    */
    private String picUrl;

    /**
    * 产品名称
    */
    private String pName;

    /**
    * 贷款类型(分为热门、极速)，1:热门，2:急速，3:普通
    */
    private Integer pType;

    /**
     * 录入人信息
     */
    private String pHandPerson;

    /**
     * 渠道洽谈价格
     */
    private Double pChannelPrice;


    private Integer[] pType_convert;

    /**
    * 最小额度
    */
    private Long minLimit;

    /**
    * 最大额度
    */
    private Long maxLimit;

    /**
    * 最小借款天数
    */
    private Integer minDay;

    /**
    * 最大借款天数
    */
    private Integer maxDay;

    /**
    * 日利率
    */
    private String pInterest;

    /**
    * 申请条件
    */
    private String pCondition;

    /**
    * 申请流程
    */
    private String pProcess;

    /**
    * 排序
    */
    private Integer pSort;

    /**
    * 产品是否上架，如果上架为10，否则没有上架
    */
    private Integer pState;


    /**
     * 产品标签
     */
    private String pTag;

    /**
    * 产品描述
    */
    private String pRemark;

    /**
    * 产品链接地址
    */
    private String pHttp;


    /**
    *   最晚放款时间
    */
    private Integer pLoanMaxTime;

    /**
    *   放款时间类型（1：小时，2分钟）
    */
    private Integer pLoanTimeType;

    /**
    *   最快放款时间
    */
    private Integer pLoanMinTime;

    /**
    *   当天点击次数（非实时，例如每30分钟从redis同步一次）
    */
    private Long pTodayClickCount;

    /**
    *   历史总点击次数，不含今天的点击次数
    */
    private Long pHistoryTotalClickCount;

    /**
     *  本月点击数（不包括今天的点击数）
     */
    private Long pPreMonthClickCount;

    /**
    *  上个月的点击次数
    */
    private Long pPreMonthClickCount1;

    /**
     *  前两个月的当月点击次数
     */
    private Long pPreMonthClickCount2;

    /**
    *   前三个月的当月点击次数
    */
    private Long pPreMonthClickCount3;

   /**
    *   申请数量
    */
   private int amounts;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

   /**
    * 已经借款人数
    */
   private Integer pBorrowNum;

    /**
     * 营销信息
     */
   private String pMessage;

    /**
     * 类别分类
     */
   private Integer typeSort;

    /**
     * 右上角标记
     */
   private String pMarks;

    /**
     * 角标背景图
     */
   private String backgroundImage;

    //=========================================


    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * 获取右上角标记
     * @return
     */

    public String getPMarks(){
        return this.pMarks;
    }
    /**
     * 设置右上角标记
     * @return
     */
    public void setPMarks(String pMarks){
        this.pMarks = pMarks;
    }


    /**
     * 获取营销信息
     * @return
     */

    public String getPMessage() {
        return pMessage;
    }


    /**
     * 设置营销信息
     * @return
     */
    public void setPMessage(String pMessage) {
        this.pMessage = pMessage;
    }


    /**
    * 获取主键Id
    *
    * @return id
    */

    public Long getId(){
        return id;
    }

    /**
    * 设置主键Id
    * 
    * @param
    */
    public void setId(Long id){
        this.id = id;
    }

    /**
    * 获取
    *
    * @return 
    */
    public String getPCode(){
        return pCode;
    }

    /**
    * 设置
    * 
    * @param pCode 要设置的
    */
    public void setPCode(String pCode){
        this.pCode = pCode;
    }

    /**
    * 获取图片地址
    *
    * @return 图片地址
    */
    public String getPicUrl(){
        return picUrl;
    }

    /**
    * 设置图片地址
    * 
    * @param picUrl 要设置的图片地址
    */
    public void setPicUrl(String picUrl){
        this.picUrl = picUrl;
    }

    /**
    * 获取产品名称
    *
    * @return 产品名称
    */
    public String getPName(){
        return pName;
    }

    /**
    * 设置产品名称
    * 
    * @param pName 要设置的产品名称
    */
    public void setPName(String pName){
        this.pName = pName;
    }

    /**
    * 获取贷款类型(分为热门、极速)，1:热门，2:急速，3:普通
    *
    * @return 贷款类型(分为热门、极速)，1:热门，2:急速，3:普通
    */
    public Integer getPType(){
        return pType;
    }

    /**
    * 设置贷款类型(分为热门、极速)，1:热门，2:急速，3:普通
    * 
    * @param pType 要设置的贷款类型(分为热门、极速)，1:热门，2:急速，3:普通
    */
    public void setPType(Integer pType){
        this.pType = pType;
    }

    /**
    * 获取最小额度
    *
    * @return 最小额度
    */
    public Long getMinLimit(){
        return minLimit;
    }

    /**
    * 设置最小额度
    * 
    * @param minLimit 要设置的最小额度
    */
    public void setMinLimit(Long minLimit){
        this.minLimit = minLimit;
    }

    /**
    * 获取最大额度
    *
    * @return 最大额度
    */
    public Long getMaxLimit(){
        return maxLimit;
    }


    /**
    * 设置最大额度
    * 
    * @param maxLimit 要设置的最大额度
    */
    public void setMaxLimit(Long maxLimit){
        this.maxLimit = maxLimit;
    }

    /**
    * 获取最小借款天数
    *
    * @return 最小借款天数
    */
    public Integer getMinDay(){
        return minDay;
    }

    /**
    * 设置最小借款天数
    * 
    * @param minDay 要设置的最小借款天数
    */
    public void setMinDay(Integer minDay){
        this.minDay = minDay;
    }

    /**
    * 获取最大借款天数
    *
    * @return 最大借款天数
    */
    public Integer getMaxDay(){
        return maxDay;
    }

    /**
    * 设置最大借款天数
    * 
    * @param maxDay 要设置的最大借款天数
    */
    public void setMaxDay(Integer maxDay){
        this.maxDay = maxDay;
    }

    /**
    * 获取日利率
    *
    * @return 日利率
    */
    public String getPInterest(){
        return pInterest;
    }


    /**
    * 设置日利率
    * 
    * @param pInterest 要设置的日利率
    */
    public void setPInterest(String pInterest){
        this.pInterest = pInterest;
    }

    /**
    * 获取申请条件
    *
    * @return 申请条件
    */
    public String getPCondition(){
        return pCondition;
    }

    /**
    * 设置申请条件
    * 
    * @param pCondition 要设置的申请条件
    */
    public void setPCondition(String pCondition){
        this.pCondition = pCondition;
    }

    /**
    * 获取申请流程
    *
    * @return 申请流程
    */
    public String getPProcess(){
        return pProcess;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    /**
    * 设置申请流程
    *

    * @param pProcess 要设置的申请流程
    */
    public void setPProcess(String pProcess){
        this.pProcess = pProcess;
    }

    /**
    * 获取排序
    *
    * @return 排序
    */
    public Integer getPSort(){
        return pSort;
    }

    /**
    * 设置排序
    * 
    * @param pSort 要设置的排序
    */
    public void setPSort(Integer pSort){
        this.pSort = pSort;
    }

    /**
    * 获取产品是否上架，如果上架为10，否则没有上架
    *
    * @return 产品是否上架，如果上架为10，否则没有上架
    */
    public Integer getPState(){
        return pState;
    }

    /**
    * 设置产品是否上架，如果上架为10，否则没有上架
    * 
    * @param pState 要设置的产品是否上架，如果上架为10，否则没有上架
    */
    public void setPState(Integer pState){
        this.pState = pState;
    }


    public String getPTag() {
        return pTag;
    }

    public void setPTag(String pTag) {
        this.pTag = pTag;
    }

    /**
    * 获取产品描述
    *
    * @return 产品描述
    */
    public String getPRemark(){
        return pRemark;
    }

    /**
    * 设置产品描述
    * 
    * @param pRemark 要设置的产品描述
    */
    public void setPRemark(String pRemark){
        this.pRemark = pRemark;
    }

    /**
    * 获取产品链接地址
    *
    * @return 产品链接地址
    */
    public String getPHttp(){
        return pHttp;
    }

    /**
    * 设置产品链接地址
    * 
    * @param pHttp 要设置的产品链接地址
    */
    public void setPHttp(String pHttp){
        this.pHttp = pHttp;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }
/**
    * 获取创建时间
    *
    * @return 创建时间
    */

    /**
    * 设置创建时间
    * 
    * @param createTime 要设置的创建时间
    */

    /**
    * 获取
    *
    * @return 
    */
    public Integer getPLoanMaxTime(){
        return pLoanMaxTime;
    }

    /**
    * 设置
    * 
    * @param pLoanMaxTime 要设置的
    */
    public void setPLoanMaxTime(Integer pLoanMaxTime){
        this.pLoanMaxTime = pLoanMaxTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Integer getPLoanTimeType(){
        return pLoanTimeType;
    }

    /**
    * 设置
    * 
    * @param pLoanTimeType 要设置的
    */
    public void setPLoanTimeType(Integer pLoanTimeType){
        this.pLoanTimeType = pLoanTimeType;
    }

    public Long getpPreMonthClickCount() {
        return pPreMonthClickCount;
    }

    public void setpPreMonthClickCount(Long pPreMonthClickCount) {
        this.pPreMonthClickCount = pPreMonthClickCount;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Integer getPLoanMinTime(){
        return pLoanMinTime;
    }

    /**
    * 设置
    * 
    * @param pLoanMinTime 要设置的
    */
    public void setPLoanMinTime(Integer pLoanMinTime){
        this.pLoanMinTime = pLoanMinTime;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getPTodayClickCount(){
        return pTodayClickCount;
    }

    /**
    * 设置
    * 
    * @param pTodayClickCount 要设置的
    */
    public void setPTodayClickCount(Long pTodayClickCount){
        this.pTodayClickCount = pTodayClickCount;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getPHistoryTotalClickCount(){
        return pHistoryTotalClickCount;
    }

    /**
    * 设置
    * 
    * @param pHistoryTotalClickCount 要设置的
    */
    public void setPHistoryTotalClickCount(Long pHistoryTotalClickCount){
        this.pHistoryTotalClickCount = pHistoryTotalClickCount;
    }

    public Long getpPreMonthClickCount1() {
        return pPreMonthClickCount1;
    }

    public void setpPreMonthClickCount1(Long pPreMonthClickCount1) {
        this.pPreMonthClickCount1 = pPreMonthClickCount1;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getPPreMonthClickCount3(){
        return pPreMonthClickCount3;
    }

    /**
    * 设置
    * 
    * @param pPreMonthClickCount3 要设置的
    */
    public void setPPreMonthClickCount3(Long pPreMonthClickCount3){
        this.pPreMonthClickCount3 = pPreMonthClickCount3;
    }

    /**
    * 获取
    *
    * @return 
    */
    public Long getPPreMonthClickCount2(){
        return pPreMonthClickCount2;
    }

    /**
    * 设置
    * 
    * @param pPreMonthClickCount2 要设置的
    */
    public void setPPreMonthClickCount2(Long pPreMonthClickCount2){
        this.pPreMonthClickCount2 = pPreMonthClickCount2;
    }

   public int getAmounts() {
      return amounts;
   }

   public ClFlowInfo setAmounts(int amounts) {
      this.amounts = amounts;
      return this;
   }

    public Integer getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    /**
     * 获取已经借款人数
     * @return
     */
    public Integer getpBorrowNum() {
        return pBorrowNum;
    }

    /**
     * 设置已经借款人数
     * @param pBorrowNum
     */
    public void setpBorrowNum(Integer pBorrowNum) {
        this.pBorrowNum = pBorrowNum;
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

    public void setpType_convert(Integer ptype){
        this.pType = ptype;
    }

    public Object[] getpType_convert(){
        boolean state1 = StateUtil.hasState(pType, 1);
        boolean state2 = StateUtil.hasState(pType, 2);
        boolean state4 = StateUtil.hasState(pType, 4);
        boolean state8 = StateUtil.hasState(pType, 8);
        boolean state16 = StateUtil.hasState(pType, 16);
        boolean state32 = StateUtil.hasState(pType, 32);
        boolean state64 = StateUtil.hasState(pType, 64);
        boolean state128 = StateUtil.hasState(pType, 128);
        boolean state256 = StateUtil.hasState(pType, 256);
        boolean state512 = StateUtil.hasState(pType, 512);

        List<String> states = new ArrayList<>();
        if (state1){
            states.add("1");
        }
        if(state2){
            states.add("2");
        }
        if(state4){
            states.add("4");
        }
        if(state8){
            states.add("8");
        }
        if(state16){
            states.add("16");
        }
        if(state32){
            states.add("32");
        }
        if(state64){
            states.add("64");
        }
        if(state128){
            states.add("128");
        }
        if(state256){
            states.add("256");
        }
        if(state512){
            states.add("512");
        }
        return states.toArray();
    }

    public String getpHandPerson() {
        return pHandPerson;
    }

    public void setpHandPerson(String pHandPerson) {
        this.pHandPerson = pHandPerson;
    }

    public Double getpChannelPrice() {
        return pChannelPrice;
    }

    public void setpChannelPrice(Double pChannelPrice) {
        this.pChannelPrice = pChannelPrice;
    }

    @Override
    public String toString() {
        return "ClFlowInfo{" +
                "id=" + id +
                ", pCode='" + pCode + '\'' +
                ", picName='" + picName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", pName='" + pName + '\'' +
                ", pType=" + pType +
                ", pHandPerson='" + pHandPerson + '\'' +
                ", pChannelPrice=" + pChannelPrice +
                ", pType_convert=" + Arrays.toString(pType_convert) +
                ", minLimit=" + minLimit +
                ", maxLimit=" + maxLimit +
                ", minDay=" + minDay +
                ", maxDay=" + maxDay +
                ", pInterest='" + pInterest + '\'' +
                ", pCondition='" + pCondition + '\'' +
                ", pProcess='" + pProcess + '\'' +
                ", pSort=" + pSort +
                ", pState=" + pState +
                ", pTag='" + pTag + '\'' +
                ", pRemark='" + pRemark + '\'' +
                ", pHttp='" + pHttp + '\'' +
                ", pLoanMaxTime=" + pLoanMaxTime +
                ", pLoanTimeType=" + pLoanTimeType +
                ", pLoanMinTime=" + pLoanMinTime +
                ", pTodayClickCount=" + pTodayClickCount +
                ", pHistoryTotalClickCount=" + pHistoryTotalClickCount +
                ", pPreMonthClickCount=" + pPreMonthClickCount +
                ", pPreMonthClickCount1=" + pPreMonthClickCount1 +
                ", pPreMonthClickCount2=" + pPreMonthClickCount2 +
                ", pPreMonthClickCount3=" + pPreMonthClickCount3 +
                ", amounts=" + amounts +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", pBorrowNum=" + pBorrowNum +
                ", pMessage='" + pMessage + '\'' +
                ", typeSort=" + typeSort +
                ", pMarks='" + pMarks + '\'' +
                ", backgroundImage='" + backgroundImage + '\'' +
                '}';
    }
}