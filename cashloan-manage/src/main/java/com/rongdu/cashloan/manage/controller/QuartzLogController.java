package com.rongdu.cashloan.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tool.util.BeanUtil;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.manage.model.QuartzLogModel;
import com.rongdu.cashloan.manage.service.QuartzLogService;

 /**
 * 定时任务记录Controller
 *
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-15 13:38:29
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 *
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class QuartzLogController extends BaseController {

	@Resource
	private QuartzLogService quartzLogService;

	/**
	 * 定时日志列表
	 * @param search
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/modules/quartzLog/page.htm")
	public void quartzLog(
			@RequestParam(value="search") String search,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		QuartzLogService quartzLogService = (QuartzLogService)BeanUtil.getBean("quartzLogService");
		Map<String,Object> searchMap = JsonUtil.parse(search, Map.class);
		Page<QuartzLogModel> page = quartzLogService.page(searchMap,current, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
}
