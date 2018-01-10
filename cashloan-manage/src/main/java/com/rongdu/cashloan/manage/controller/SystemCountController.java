package com.rongdu.cashloan.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongdu.cashloan.cl.service.SystemCountService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;

/**
 * 后台登陆，首页统计数据
 * @author caitt
 * @version 1.0
 * @date 2017年3月15日下午2:28:26
 * Copyright 杭州民华金融信息服务有限公司 现金贷  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class SystemCountController extends ManageBaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemCountController.class);
	
	@Resource
	private SystemCountService systemCountService;

	@Resource
	private ShardedJedisClient redisClient;

	/**
	 * 统计首页信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/count/homeInfo.htm")
	public void homeInfo(HttpServletResponse response) throws Exception {
//		Map<String,Object> data = systemCountService.systemCount();
		Map<String,Object> data = null ;
		if(redisClient.exists(AppConstant.STATIS_WORKBENCH)){
			logger.info("=====>从redis中获取控制台数据");
			data = (Map<String,Object>)redisClient.getObject(AppConstant.STATIS_WORKBENCH);
		}else {
			logger.info("=====>从数据库中获取控制台数据");
			data = systemCountService.systemCount();
		}

		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
}
