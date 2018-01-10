package com.rongdu.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.UserAuth;
import com.rongdu.cashloan.cl.mapper.UserAuthMapper;
import com.rongdu.cashloan.cl.model.UserAuthModel;
import com.rongdu.cashloan.cl.service.UserAuthService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.mapper.UserBaseInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户认证信息表ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:18:17
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("userAuthService")
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, Long> implements UserAuthService {
	
    @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);
   
    @Resource
    private UserAuthMapper userAuthMapper;


	@Resource
	private UserBaseInfoMapper userBaseInfoMapper;

    
	@Override
	public BaseMapper<UserAuth, Long> getMapper() {
		return userAuthMapper;
	}

	
	@Override
	public UserAuth getUserAuth(Map<String, Object> paramMap) {
		UserAuth userAuth = userAuthMapper.findSelective(paramMap);
		return userAuth;
	}

	/**
	 * 获取用户认证状态，关联同盾认证状态
	 * @author pantheon
	 * @param paramMap
	 * @return
	 */
	@Override
	public UserAuth getUserAuthNewVersion(Map<String, Object> paramMap) {
		UserAuth userAuth = userAuthMapper.findSelective(paramMap);

		//beign pantheon 20170615 芝麻认证状态 = 芝麻认证 + 同盾认证
		if(userAuth == null){
			logger.error("获取用户认证状态失败，无用户数据-UserAuthServiceImpl-getUserAuthNewVersion()-paramMap:"+ JSONObject.toJSONString(paramMap));
			return null;
		}

		return userAuth;
	}

	@Override
	public Integer updateByUserId(Map<String, Object> paramMap) {
		return userAuthMapper.updateByUserId(paramMap);
	}
	
	@Override
	public Page<UserAuthModel> listUserAuth(Map<String, Object> params,
			int currentPage, int pageSize) {
		PageHelper.startPage(currentPage, pageSize);
		List<UserAuthModel> list = userAuthMapper.listUserAuthModel(params);
		return (Page<UserAuthModel>) list;
	}

	@Override
	public UserAuth findSelective(long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userAuthMapper.findSelective(map);
	}
	
	@Override
	public UserAuth findSelectiveWithVersion(long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		return userAuthMapper.findSelectiveWithVersion(map);
	}
	
	@Override
	public int updatePhoneState(Map<String, Object> userAuth) {
		// TODO Auto-generated method stub
		return userAuthMapper.updatePhoneState(userAuth);
	}
}