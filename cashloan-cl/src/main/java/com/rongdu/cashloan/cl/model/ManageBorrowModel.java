package com.rongdu.cashloan.cl.model;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.model.BorrowModel;


/**
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-3-14 上午9:49:07
 * Copyright 杭州民华金融信息服务有限公司 金融创新事业部 cashloan  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class ManageBorrowModel extends Borrow {

	private static final long serialVersionUID = 1L;

	public static ManageBorrowModel instance(Borrow borrow) {
		ManageBorrowModel borrowModel = new ManageBorrowModel();
		BeanUtils.copyProperties(borrow, borrowModel);
		return borrowModel;
	}
	
	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 状态中文含义
	 */
	private String stateStr;
	/**
	 * 待还款金额（本金+管理费+逾期费用）
	 */
	private Double repayAmount;

	/**
	 * 还款时间
	 */
	private String repayTime;

	/**
	 * 逾期罚金
	 */
	private Double penaltyAmout;

	/**
	 * 逾期天数
	 */
	private String penaltyDay;

    /**
    * 借款订单id
    */
    private long borrowId;
    /**
     * 放款时间
     */
    private Date loanTime;
    
    /**
	 * 逾期等级
	 */
	private String level;

	/**
	 * 是否黑名单 ，10是20不是(对应cl_user_base_info中的state)
	 */
	private String blackState;

	/**
	 *  拉黑原因
	 */
	private String blackReason;

	/**
	 *  实际还款金额
	 */
	private Double realRepaymentAmout;

    
	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public Double getPenaltyAmout() {
		return penaltyAmout;
	}

	public void setPenaltyAmout(Double penaltyAmout) {
		this.penaltyAmout = penaltyAmout;
	}

	public String getPenaltyDay() {
		return penaltyDay;
	}

	public void setPenaltyDay(String penaltyDay) {
		this.penaltyDay = penaltyDay;
	}

	public String getStateStr() {
		this.stateStr = BorrowModel.manageConvertBorrowState(this.getState());
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取借款订单id
	 * @return borrowId
	 */
	public long getBorrowId() {
		return borrowId;
	}

	/**
	 * 设置借款订单id
	 * @param borrowId
	 */
	public void setBorrowId(long borrowId) {
		this.borrowId = borrowId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getBlackReason() {
		return blackReason;
	}

	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}

	public String getBlackState() {
		return blackState;
	}

	public void setBlackState(String blackState) {
		this.blackState = blackState;
	}

	public Double getRealRepaymentAmout() {
		return realRepaymentAmout;
	}

	public void setRealRepaymentAmout(Double realRepaymentAmout) {
		this.realRepaymentAmout = realRepaymentAmout;
	}
}
