package com.rongdu.cashloan.cl.service.impl;


import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.mapper.ClFlowUVMapper;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流量平台uv统计ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-11-15 13:32:13
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("clFlowUVService")
public class ClFlowUVServiceImpl extends BaseServiceImpl<ClFlowUV, Long> implements ClFlowUVService {
	
    private static final Logger logger = LoggerFactory.getLogger(ClFlowUVServiceImpl.class);
   
    @Resource
    private ClFlowUVMapper clFlowUVMapper;

	@Override
	public BaseMapper<ClFlowUV, Long> getMapper() {
		return clFlowUVMapper;
	}


	@Override
	public List<ClFlowUV>  getAllFlowUv(Map<String,Object> params ) throws Exception {
		return clFlowUVMapper.listSelective(params);
	}

	@Override
	public Long getPreviousMonthClick(Map param) {
		return clFlowUVMapper.getPreviousMonthClick(param);
	}

	public ClFlowUV getClFlowUVByDate(Map<String,Object> params){
		return clFlowUVMapper.getClFlowUVByDate(params);
	}

	@Override
	public Integer queryNumberPlat(Map<String,Object> params){
		return clFlowUVMapper.queryNumberPlat(params);
	}

	@Override
	public List<ClFlowUV> listFlowUv(){
		return clFlowUVMapper.listFlowUv();
	}
}