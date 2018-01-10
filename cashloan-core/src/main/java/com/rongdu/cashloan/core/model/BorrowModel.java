package com.rongdu.cashloan.core.model;

import com.rongdu.cashloan.core.domain.Borrow;

import java.util.ArrayList;

/**
 * 借款进度状态model
 * @author jdd
 * @version 1.0.0
 * @date 2016年1月12日 下午2:35:42
 * Copyright 杭州民华金融信息服务有限公司 ARC  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public class BorrowModel extends Borrow {

	private static final long serialVersionUID = 1L;
	
	
	/** 申请成功待审核*/
	public static final String STATE_PRE = "10";
	/** 自动审核通过 */
	public static final String STATE_AUTO_PASS = "20";
	/** 自动审核不通过*/
	public static final String STATE_AUTO_REFUSED = "21";
	/** 自动审核未决待人工复审*/
	public static final String STATE_NEED_REVIEW = "22";
	/** 人工复审通过 */
	public static final String STATE_PASS = "26";
	/** 人工复审不通过*/
	public static final String STATE_REFUSED ="27";
	public static final String STATE_REFUSED1 = "27,01";
	public static final String STATE_REFUSED2 = "27,02";
	public static final String STATE_REFUSED3 = "27,03";
	public static final String STATE_REFUSED4 = "27,04";
	public static final String STATE_REFUSED5 = "27,05";
	public static final String STATE_REFUSED6 = "27,06";
	public static final String STATE_REFUSED7 = "27,07";
	public static final String STATE_REFUSED8 = "27,08";
	public static final String STATE_REFUSED9 = "27,09";
	public static final String STATE_REFUSED10 = "27,10";
	public static final String STATE_REFUSED11 = "27,11";
	public static final String STATE_REFUSED12= "27,12";
	public static final String STATE_REFUSED13 = "27,13";
	public static final String STATE_REFUSED14 = "27,14";

	/** 放款成功 */
	public static final String STATE_REPAY = "30";
	/** 放款失败 */
	public static final String STATE_REPAY_FAIL = "31";
	/** 还款成功 */
	public static final String STATE_FINISH = "40";
	/** 还款成功-金额减免 */
	public static final String STATE_REMISSION_FINISH = "41";
	/** 逾期 */
	public static final String STATE_DELAY = "50";
	/** 坏账*/
	public static final String STATE_BAD = "90";

	/** 临时状态-自动审核 */
	public static final String STATE_TEMPORARY_AUTO_PASS = "12";   //12-初审审核成功 14初审待人工复审

	/** 临时状态-自动审核未决待人工复审 */
	public static final String STATE_TEMPORARY_NEED_REVIEW = "14";   //12-初审审核成功 14初审待人工复审

	//pantheon 20170802 冗余字段
	public static final  String STATE_SUCCESS_AUTO_PASS = "10";//冗余字段：10-机审通过 20-人工复审通过
	public static final  String STATE_SUCCESS_PASS = "20";//冗余字段：10-机审通过 20-人工复审通过

	
	/**
	 * 状态中文描述
	 *  10申请成功待审核 20自动审核通过 21自动审核不通过 
	 *  22自动审核未决待人工复审 26人工复审通过 27人工复审不通过
	 * 30放款成功 31放款失败 40还款成功  41还款成功-金额减免  50逾期 90坏账
	 */
	private	String stateStr;
	
	/**
	 * 进度说明
	 */
	private	String remark;
	
	/**
	 * 获取状态中文描述
	 * @return stateStr
	 */
	public String getStateStr() {
		this.stateStr = BorrowModel.apiConvertBorrowState(this.getState());
		return stateStr;
	}

	/**
	 * 设置状态中文描述
	 * @param stateStr
	 */
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	/**
	 * 获取进度说明
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置进度说明
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** 
	 * 响应给管理后台的借款/借款进度状态中文描述转换
	 * @return stateStr  
	 */
	public static String manageConvertBorrowState(String state) {
		String stateStr = " - ";
		if(STATE_PRE.equals(state)){
			stateStr=("申请成功待审核");
		}else if(STATE_AUTO_PASS.equals(state)){
			stateStr=("自动审核通过");
		}else if(STATE_AUTO_REFUSED.equals(state)){
			stateStr=("自动审核不通过 ");
		}else if(STATE_NEED_REVIEW.equals(state)){
			stateStr=("待人工复审");
		}else if(STATE_PASS.equals(state)){
			stateStr=("人工复审通过");
		}else if(STATE_REFUSED.equals(state)){
			stateStr=("人工复审不通过");
		}else if(STATE_TEMPORARY_AUTO_PASS.equals(state)){//对应 自动审核通过
			stateStr=("初审通过");
		}else if(STATE_TEMPORARY_NEED_REVIEW.equals(state)){//对应  待人工复审
			stateStr=("初审待复审");
		}else if(STATE_REPAY.equals(state)){
			stateStr=("放款成功");
		}else if(STATE_REPAY_FAIL.equals(state)){
			stateStr=("放款失败");
		}else if(STATE_FINISH.equals(state)){
			stateStr=("还款成功");
		}else if(STATE_REMISSION_FINISH.equals(state)){
			stateStr=("还款成功-金额减免");
		}else if(STATE_DELAY.equals(state)){
			stateStr=("已逾期");
		}else if(STATE_BAD.equals(state)){
			stateStr=("已坏账");
		}
		
		return stateStr;
	}
	
	/**
	 * 响应给app的借款状态中文描述转换
	 * @param state
	 * @return
	 */
	public static String apiConvertBorrowState(String state) {
		String stateStr = state;
		if (BorrowModel.STATE_PRE.equals(state)) {
			stateStr = "待审核";
		} else if (BorrowModel.STATE_AUTO_PASS.equals(state)) {
			stateStr = "审核通过";
		} else if (BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
			stateStr = "审核不通过";
		} else if (BorrowModel.STATE_NEED_REVIEW.equals(state)) {
			stateStr = "待审核";
		} else if (BorrowModel.STATE_PASS.equals(state)) {
			stateStr = "审核通过";
		} else if (BorrowModel.STATE_REFUSED.equals(state)) {
			stateStr = "审核不通过";
		} else if (BorrowModel.STATE_TEMPORARY_AUTO_PASS.equals(state)){//对应 自动审核通过
			stateStr = "审核通过";
		} else if (BorrowModel.STATE_TEMPORARY_NEED_REVIEW.equals(state)){//对应  待人工复审
			stateStr = "待审核";
		} else if (BorrowModel.STATE_REPAY.equals(state)) {
			stateStr = "还款中";
		} else if (BorrowModel.STATE_REPAY_FAIL.equals(state)) {
			stateStr = "放款失败";
		} else if (BorrowModel.STATE_FINISH.equals(state)) {
			stateStr = "已还款";
		} else if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
			stateStr = "已还款";
		} else if (BorrowModel.STATE_DELAY.equals(state)) {
			stateStr = "已逾期";
		} else if (BorrowModel.STATE_BAD.equals(state)) {
			stateStr = "已逾期";
		}
		return stateStr;
	}

	/**
	 * 借款状态中文描述转换
	 * 
	 * @param state
	 * @return
	 */
	public static String convertBorrowRemark(String state) {
		String remarkStr = " - ";
		if (BorrowModel.STATE_PRE.equals(state)) {
			remarkStr = "系统审核中,请耐心等待";
		} else if (BorrowModel.STATE_AUTO_PASS.equals(state)) {
			remarkStr = "恭喜通过风控审核";
		} else if (BorrowModel.STATE_AUTO_REFUSED.equals(state)) {
			remarkStr = "很遗憾,您未通过审核";
		} else if (BorrowModel.STATE_NEED_REVIEW.equals(state)) {
			remarkStr = "系统复审中,请耐心等待";
		} else if (BorrowModel.STATE_PASS.equals(state)) {
			remarkStr = "恭喜通过风控复审";
		} else if (BorrowModel.STATE_TEMPORARY_AUTO_PASS.equals(state)){//对应 自动审核通过
			remarkStr = "恭喜通过初步审核";
		} else if (BorrowModel.STATE_TEMPORARY_NEED_REVIEW.equals(state)){//对应  待人工复审
			remarkStr = "系统核实中,请耐心等待";
		} else if (BorrowModel.STATE_REFUSED.equals(state)) {
			remarkStr = "很遗憾,您未通过复审";
		} else if (BorrowModel.STATE_REPAY.equals(state)) {
			remarkStr = "已打款,请查看您的提现银行卡";
		} else if (BorrowModel.STATE_REPAY_FAIL.equals(state)) {
			remarkStr = "放款失败";
		} else if (BorrowModel.STATE_FINISH.equals(state)) {
			remarkStr = "还款成功";
		} else if (BorrowModel.STATE_REMISSION_FINISH.equals(state)) {
			remarkStr = "借款人无法支付全部借款金额,减免还款成功";
		} else if (BorrowModel.STATE_DELAY.equals(state)) {
			remarkStr = "您的借款已逾期";
		} else if (BorrowModel.STATE_BAD.equals(state)) {
			remarkStr = "经长时间催收,没有结果";
		}
		return remarkStr;
	}


}