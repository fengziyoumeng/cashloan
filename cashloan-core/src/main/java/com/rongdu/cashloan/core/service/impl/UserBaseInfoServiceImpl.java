package com.rongdu.cashloan.core.service.impl;

import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.mapper.UserBaseInfoMapper;
import com.rongdu.cashloan.core.model.ManagerUserModel;
import com.rongdu.cashloan.core.model.UserWorkInfoModel;
import com.rongdu.cashloan.core.service.UserBaseInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * 用户详情表ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-02-14 11:08:04
 * Copyright 杭州民华金融信息服务有限公司  cl All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("userBaseInfoService")
public class UserBaseInfoServiceImpl extends BaseServiceImpl<UserBaseInfo, Long> implements UserBaseInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserBaseInfoServiceImpl.class);
	
    @Resource
    private UserBaseInfoMapper userBaseInfoMapper;

	@Override
	public boolean addOrModify(UserBaseInfo userModel , String status) throws ServiceException {
		long num = 0;
		boolean isTrue;
		if (status != null && Constant.INSERT.equals(status)) {
			num = userBaseInfoMapper.save(userModel);
		} else if (status != null && Constant.UPDATE.equals(status)) {
			num = userBaseInfoMapper.updateForBase(userModel);
		}
		isTrue= num > 0L ?true:false;
		return isTrue;
	}
	@Override
	public BaseMapper<UserBaseInfo, Long> getMapper() {
		return  userBaseInfoMapper;
	}
	
	@Override
	public UserBaseInfo findByUserId(Long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		UserBaseInfo baseInfo = null;
		try {
			baseInfo = userBaseInfoMapper.findSelective(paramMap);
		} catch (Exception e) {
			logger.error("查询用户基本信息异常", e);
		}

		return baseInfo;
	}

	@Override
	public UserBaseInfo findSelective(Map<String, Object> paramMap) {
		return userBaseInfoMapper.findSelective(paramMap);
	}

//	@Override
//	public List<Map<String, Object>> getDictsCache(String type) {
//		//return userBaseInfoMapper.getDictsCache(type);
//		List<Map<String, Object>> dicList = null;
//		try {
//			dicList = sysDictService.getDictsCache(type);
//		} catch (ServiceException e) {
//			logger.error("通过类型获得字典明细失败",e);
//		}
//		return  dicList;
//	}

	@Override
	public ManagerUserModel getBaseModelByUserId(Long userId) {
		return userBaseInfoMapper.getBaseModelByUserId(userId);
	}

	@Override
	public int updateState(long id, String state) {
		int i = 0;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", id);
		UserBaseInfo base=userBaseInfoMapper.findSelective(paramMap);
		if (base != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", base.getId());
			map.put("state", state);
			i = userBaseInfoMapper.updateSelective(map);
		}
		return i;
	}

	@Override
	public boolean updateSelective(Map<String, Object> paramMap) {
		int result = userBaseInfoMapper.updateSelective(paramMap);
		if(result >0L){
			return true;
		}
		return false;
	}
	
	@Override
	public UserWorkInfoModel getWorkInfo(Long userId){
		return userBaseInfoMapper.findWorkInfo(userId);
	}


}