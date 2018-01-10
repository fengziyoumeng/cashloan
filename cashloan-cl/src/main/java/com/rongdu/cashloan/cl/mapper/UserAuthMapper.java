package com.rongdu.cashloan.cl.mapper;

import java.util.List;
import java.util.Map;

import com.rongdu.cashloan.cl.domain.UserAuth;
import com.rongdu.cashloan.cl.model.UserAuthModel;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.mapper.RDBatisDao;

/**
 * 用户Dao
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:42:44
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@RDBatisDao
public interface UserAuthMapper extends BaseMapper<UserAuth,Long> {

	List<UserAuthModel> listUserAuthModel(Map<String, Object> params);

	int updateByUserId(Map<String, Object> paramMap);

//    public Map<String,Object> getAuthState(Map<String,Object> paramMap);

	/**
	 * 芝麻必填查询
	 * 
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmRequiredAuthState(Map<String, Object> authMap);

	/**
	 * 芝麻选填查询
	 * 
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmOptionalAuthState(Map<String, Object> authMap);

	/**
	 * 芝麻去除查询
	 * 
	 * @param authMap
	 * @return
	 */
	Map<String, Object> getZmRemoveAuthState(Map<String, Object> authMap);

	/**
	 * 插入带版本的userAuth
	 *
	 * @param userAuth
	 *            实体类
	 * @return 主键值
	 */
	int saveWithVersion(UserAuth userAuth);


	/**
	 * 获取条带版本的数据
	 *
	 * @param paramMap
	 *            查询条件
	 * @return 查询结果
	 */
	UserAuth findSelectiveWithVersion(Map<String, Object> paramMap);

	int updatePhoneState(Map<String, Object> userAuth);
	

}
