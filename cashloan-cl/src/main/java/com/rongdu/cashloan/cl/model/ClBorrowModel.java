package com.rongdu.cashloan.cl.model;

import java.util.Date;

import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.model.BorrowModel;

/**
 * 借款
 * @author Administrator
 *
 */
public class ClBorrowModel extends Borrow {

	private static final long serialVersionUID = 1L;
	
	private String cardNo;
	
	private String bank;
	
	private String stateStr;
	
	private double penaltyAmount;
	
	private int penaltyDay;
	
	private Date realTime;
	
	private Date repayTime;
	
	private double agentAmount;
	
	private double repayAmount;
	
	private String creditTimeStr;
	
	private String penalty;
	
	/**
	 * 逾期应还总金额
	 */
	private String overdueAmount;
	
	/**
	 * @return the penaltyAmount
	 */
	public double getPenaltyAmount() {
		return penaltyAmount;
	}

	/**
	 * @param penaltyAmount the penaltyAmount to set
	 */
	public void setPenaltyAmount(double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}

	/**
	 * @return the agentAmount
	 */
	public double getAgentAmount() {
		return agentAmount;
	}

	/**
	 * @param agentAmount the agentAmount to set
	 */
	public void setAgentAmount(double agentAmount) {
		this.agentAmount = agentAmount;
	}

	/**
	 * @return the penaltyDay
	 */
	public int getPenaltyDay() {
		return penaltyDay;
	}

	/**
	 * @param penaltyDay the penaltyDay to set
	 */
	public void setPenaltyDay(int penaltyDay) {
		this.penaltyDay = penaltyDay;
	}

	/**
	 * @return the realTime
	 */
	public Date getRealTime() {
		return realTime;
	}

	/**
	 * @param realTime the realTime to set
	 */
	public void setRealTime(Date realTime) {
		this.realTime = realTime;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * @return the stateStr
	 */
	public String getStateStr() {
		this.stateStr = BorrowModel.apiConvertBorrowState(this.getState());
		return stateStr;
	}

	/**
	 * @param stateStr the stateStr to set
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	/**
	 * @return the repayTime
	 */
	public Date getRepayTime() {
		return repayTime;
	}

	/**
	 * @param repayTime the repayTime to set
	 */
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}

	/**
	 * @return the repayAmount
	 */
	public double getRepayAmount() {
		return repayAmount;
	}

	/**
	 * @param repayAmount the repayAmount to set
	 */
	public void setRepayAmount(double repayAmount) {
		this.repayAmount = repayAmount;
	}

	/**
	 * @return the creditTimeStr
	 */
	public String getCreditTimeStr() {
		return creditTimeStr;
	}

	/**
	 * @param creditTimeStr the creditTimeStr to set
	 */
	public void setCreditTimeStr(String creditTimeStr) {
		this.creditTimeStr = creditTimeStr;
	}

	/**
	 * @return the overdue
	 */
	public String getPenalty() {
		return penalty;
	}

	/**
	 * @param overdue the overdue to set
	 */
	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getOverdueAmount() {
		return overdueAmount;
	}

	public void setOverdueAmount(String overdueAmount) {
		this.overdueAmount = overdueAmount;
	}

}
