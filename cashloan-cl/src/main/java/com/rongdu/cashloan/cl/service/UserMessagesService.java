package com.rongdu.cashloan.cl.service;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.UserMessages;
import com.rongdu.cashloan.core.common.service.BaseService;

/**
 * 用户资料--联系人Service
 * 
 * @author chenxy
 * @version 1.0.0
 * @date 2017-03-04 11:54:57
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface UserMessagesService extends BaseService<UserMessages, Long>{

	/**
	 * 短信查询
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @return
	 */
	Page<UserMessages> listMessages(long userId, int current, int pageSize);

}
