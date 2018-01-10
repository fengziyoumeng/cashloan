package com.rongdu.cashloan.api.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.model.ClBorrowModel;
import com.rongdu.cashloan.cl.model.IndexModel;
import com.rongdu.cashloan.cl.service.ClBorrowService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.StringUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借款申请风控接口执行记录
 * @author caitt
 * @version 1.0
 * @date 2017年4月11日下午5:40:14
 * Copyright 杭州民华金融信息服务有限公司 现金贷  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class ClBorrowController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(ClBorrowController.class);
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private UserMapper userMapper;

	
	/**
	 * 查询借款列表
	 * @param userId
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/act/borrow/findAll.htm")
	public void findAll(
			@RequestParam(value="userId") long userId) throws Exception {
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("userId", userId);
//		List<RepayModel> list = clBorrowService.findRepay(searchMap);
		Map<String,Object> data = new HashMap<String,Object>();
		List list = null ;
		data.put("list", list);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data); 
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 查询借款
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/act/mine/borrow/page.htm", method = RequestMethod.POST)
	public void page(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String,Object> searchMap = new HashMap<>();
		searchMap.put("userId", userId);
		Page<ClBorrowModel> page = clBorrowService.page(searchMap,current, pageSize);
		Map<String, Object> data = new HashMap<>();
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 查询订单
	 * @param borrowId
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/act/borrow/findBorrow.htm", method = RequestMethod.POST)
	public void findBorrow(
			@RequestParam(value="borrowId",required=false) Long borrowId) throws Exception {
		Borrow borrow = clBorrowService.getById(borrowId);
		ClBorrowModel data = new ClBorrowModel();
		BeanUtils.copyProperties(borrow, data);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 首页信息查询
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/borrow/findIndex.htm", method = RequestMethod.POST)
	public void findIndex() throws Exception {
		String userId = request.getParameter("userId");

		Map<String,Object> data = null;
//		Map<String,Object> data = clBorrowService.findIndex(userId);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 首页轮播信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/borrow/listIndex.htm", method = RequestMethod.POST)
	public void listIndex() throws Exception {
		List<IndexModel> list = clBorrowService.listIndex();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("list", list);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 选择借款金额和期限
	 * app里选择借款金额和期限，返回实际到账金额、服务费、服务费明细
	 * @param amount
	 * @param timeLimit
	 */
	@RequestMapping(value = "/api/borrow/choice.htm")
	public void choice(@RequestParam(value = "amount") double amount,
			@RequestParam(value = "timeLimit") String timeLimit) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(!(amount > 0) || StringUtil.isBlank(timeLimit)){
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询失败，请核对参数");
		} else {
			result.put(Constant.RESPONSE_DATA, clBorrowService.choice(amount, timeLimit));
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		}
		
		ServletUtils.writeToResponse(response, result);
	}

}
