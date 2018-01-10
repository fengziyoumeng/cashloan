package com.rongdu.cashloan.cl.service.impl;


import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.domain.Transverter;
import com.rongdu.cashloan.cl.mapper.ClFlowUVMapper;
import com.rongdu.cashloan.cl.mapper.TransverterMapper;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.service.TransverterService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * app界面转换ServiceImpl
 * 
 * @author
 * @version 1.0.0
 * @date 2017-11-15 13:32:13
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
 
@Service("transverterService")
public class TransverterServiceImpl implements TransverterService {
	

    @Resource
    private TransverterMapper transverterMapper;


	@Override
	public Transverter getSwitchCode(String channel) {
		Transverter switchCode = transverterMapper.getSwitchCode(channel);
		return switchCode;
	}
}