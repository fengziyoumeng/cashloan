package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.UserEquipmentInfo;
import com.rongdu.cashloan.core.common.service.BaseService;


/**
 * 用户设备信息表Service
 * 
 * @author xx
 * @version 1.0.0
 * @date 2017-04-17 17:32:05
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface UserEquipmentInfoService extends BaseService<UserEquipmentInfo, Long>{

	/**
	 * 信息入库
	 * @param uei
	 * @return
	 */
	int saveOrUpdate(UserEquipmentInfo uei);

	/**
	 * 查询
	 * @param parseLong
	 * @return
	 */
	UserEquipmentInfo findSelective(long userId);

	/**
	 * 保存设备指纹
	 * @param loginName
	 */
	void save(String loginName, String blackBox);


}
