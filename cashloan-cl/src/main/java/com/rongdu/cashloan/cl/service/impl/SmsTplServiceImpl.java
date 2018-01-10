package com.rongdu.cashloan.cl.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rongdu.cashloan.cl.domain.SmsTpl;
import com.rongdu.cashloan.cl.mapper.SmsTplMapper;
import com.rongdu.cashloan.cl.service.SmsTplService;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
/**
 * 短信记录ServiceImpl
 * 
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-13 18:36:01
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@SuppressWarnings("unused")
@Service("clSmsTplService")
public class SmsTplServiceImpl extends BaseServiceImpl<SmsTpl, Long> implements SmsTplService {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsTplServiceImpl.class);
   
    @Resource
    private SmsTplMapper clSmsTplMapper;

	@Override
	public BaseMapper<SmsTpl, Long> getMapper() {
		return clSmsTplMapper;
	}
	
	
}