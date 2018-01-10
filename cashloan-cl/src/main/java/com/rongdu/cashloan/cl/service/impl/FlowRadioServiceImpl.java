package com.rongdu.cashloan.cl.service.impl;


import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.domain.FlowRadio;
import com.rongdu.cashloan.cl.mapper.FlowRadioMapper;
import com.rongdu.cashloan.cl.service.FlowRadioService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 广播数据ServiceImpl
 * 
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-13 20:42:52
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("flowRadioService")
public class FlowRadioServiceImpl extends BaseServiceImpl<FlowRadio, Long> implements FlowRadioService {
	
    private static final Logger logger = LoggerFactory.getLogger(FlowRadioServiceImpl.class);
   
    @Resource
    private FlowRadioMapper flowRadioMapper;

	@Resource
	private ShardedJedisClient redisClient;

	@Override
	public BaseMapper<FlowRadio, Long> getMapper() {
		return flowRadioMapper;
	}

	@Override
	public List<FlowRadio> getRadio() {
		List<FlowRadio> radios;
		try{
			radios = (List<FlowRadio>)redisClient.getObject(AppConstant.REDIS_KEY_RADIO_FLOW_INFO);
			if(radios != null && !radios.isEmpty()){
				return radios;
			}
		}catch (Exception e){
			logger.info("获取redis中的首页广播消息时发生错误"+e);
		}
		radios = flowRadioMapper.getList();
		try{
			if(radios != null && !radios.isEmpty()){
				redisClient.setObject(AppConstant.REDIS_KEY_RADIO_FLOW_INFO,radios);
			}
		}catch (Exception e){
			logger.info("将首页广播消息存储到redis时发生错误"+e);
		}
		return radios;
	}
}