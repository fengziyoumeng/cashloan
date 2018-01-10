package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.Sms;
import com.rongdu.cashloan.core.common.service.BaseService;

/**
 * 短信记录Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface ClSmsService extends BaseService<Sms, Long>{
	
	int smsBatch(String id,final String type);

	/**
	 * 查询与最近一条短信的时间差（秒）
	 * @param phone
	 * @param type
	 * @return
	 */
	long findTimeDifference(String phone, String type);
	
	/**
	 * 根据手机号码、短信验证码类型查询今日可获取次数，防短信轰炸
	 * @param phone
	 * @param type
	 * @return
	 */
	int countDayTime(String phone, String type);
	
	/**
	 * 发送短信
	 * @param phone
	 * @param type
	 * @return
	 */
	long sendSms(String phone, String type);

	/**
	 * 验证短信
	 * @param phone
	 * @param type
	 * @param code
	 * @return
	 */
	int verifySms(String phone, String type, String code);

	/**
	 * 查询用户
	 * @param phone 
	 * @return
	 */
	int findUser(String phone);
	
	/**
	 * 放款通知发送短信
	 * @param userId
	 * @param borrowId
	 * @return
	 */
	int loanInform(long userId,long borrowId);


	
	/**
	 * 审核不通过通知
	 */
	int refuse(long userId);

}