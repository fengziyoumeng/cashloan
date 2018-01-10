package com.rongdu.cashloan.core.service;

import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.model.ManagerUserModel;
import com.rongdu.cashloan.core.model.UserWorkInfoModel;
import com.rongdu.cashloan.system.domain.SysDict;
import com.rongdu.cashloan.system.domain.SysUser;


/**
 * 用户详情表Service
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:08:04
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface UserBaseInfoService extends BaseService<UserBaseInfo, Long>{
	//定义修改用户信息
	boolean addOrModify(UserBaseInfo userModel, String status) throws ServiceException;
	/**
	 * 据userId查询用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	UserBaseInfo findByUserId(Long userId);

	/**
	 * 据条件查询用户基本信息
	 * 
	 * @param paramMap
	 * @return
	 */
	UserBaseInfo findSelective(Map<String, Object> paramMap);

//	List<Map<String, Object>> getDictsCache(String type);
	
	/**
	 * 查询信息
	 * @param userId
	 * @return
	 */
	ManagerUserModel getBaseModelByUserId(Long userId);
	/**
	 * 修改用户信息
	 * @param id
	 * @return
	 */


	/**
	 * 添加取现黑名单
	 * @param id
	 * @param state
	 * @return
	 */
	int updateState(long id, String state);
	
	/**
	 * 修改用户基本信息
	 * 
	 * @param paramMap
	 * @return
	 */
	boolean updateSelective(Map<String, Object> paramMap);
	
	/**
	 * 查询用户工作信息
	 * @param userId
	 * @return
	 */
	UserWorkInfoModel getWorkInfo(Long userId);	
	
}
