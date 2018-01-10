package com.rongdu.cashloan.cl.model;

/**
 * 短信model
 */
public class SmsModel {
	
	/**
	 * 注册验证码-register
	 */
	public static final String SMS_TYPE_REGISTER = "REGISTER";
	
	/**
	 * 绑卡-bindCard
	 */
	public static final String SMS_TYPE_BINDCARD = "BINDCARD";
	
	/**
	 * 找回登录密码-findReg
	 */
	public static final String SMS_TYPE_FINDREG = "FINDREG";
	
	/**
	 * 找回交易密码-findPay
	 */
	public static final String SMS_TYPE_FINDPAY = "FINDPAY";


	//=========================后加的=============================

	/**
	 * 逾期催收
	 */
	public static final String SMS_TYPE_OVERDUE = "OVERDUE";


	/**
	 * 审核不通过
	 */
	public static final String SMS_TYPE_REFUSE = "REFUSE";


	/**
	 * 还款前通知
	 */
	public static final String SMS_TYPE_REPAYBEFORE = "REPAYBEFORE";


	/**
	 * 还款后通知
	 */
	public static final String SMS_TYPE_REPAYINFORM = "REPAYINFORM";



	/**
	 * 放款通知
	 */
	public static final String SMS_TYPE_LOANINFORM = "LOANINFORM";






}
