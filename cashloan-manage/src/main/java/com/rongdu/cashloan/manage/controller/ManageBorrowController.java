package com.rongdu.cashloan.manage.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rongdu.cashloan.cl.model.ManageBorrowModel;
import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.cl.service.*;
import com.rongdu.cashloan.core.common.context.ExportConstant;
import com.rongdu.cashloan.core.common.util.excel.JsGridReportBase;
import com.rongdu.cashloan.system.domain.SysUser;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tool.util.DateUtil;
import tool.util.StringUtil;

import com.github.pagehelper.Page;

import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.model.BorrowModel;
import com.rongdu.cashloan.system.permission.annotation.RequiresPermission;

 /**
 * 借款信息表Controller
 * 
 * @author jdd
 * @version 1.0.0 
 * @date 2017-02-23 16:26:19
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ManageBorrowController extends ManageBaseController {

	private static final Logger logger = Logger.getLogger(ManageBorrowController.class);
	
	@Resource
	private ClBorrowService clBorrowService;
	@Resource
	private ClSmsService clSmsService;


	 /**
	  * 借款信息列表
	  * @param searchParams
	  * @param current
	  * @param pageSize
	  */
	@RequestMapping(value="/modules/manage/borrow/list.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:list",name = "借款信息列表")
	public void list(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<ManageBorrowModel> page =clBorrowService.listModel(params,current,pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}

	
	/**
	 * 人工复审查询
	 * @param searchParams
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value="/modules/manage/borrow/reviewList.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:reviewList",name = "人工复审通过列表")
	public void reviewList(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
	
		Page<ManageBorrowModel> page = null;
		if (params != null) {
			List<String> stateList =  new ArrayList<String>();
			String state = StringUtil.isNull(params.get("state"));
			if (StringUtil.isNotBlank(state)) {
				stateList = Arrays.asList(state);
				// 如果是待人工复审及人工复审不通过 ，则判断borrow状态
				if (BorrowModel.STATE_NEED_REVIEW.equals(state) || state.contains(BorrowModel.STATE_REFUSED)) {
					params.put("state", state);
				}else if(BorrowModel.STATE_PASS.equals(state)){//pantheon 20170802 查询人工复审通过的订单
					params.put("stateSuccess", BorrowModel.STATE_SUCCESS_PASS);
					stateList = null;
					params.put("state", "");
				}else{
					params.put("state", "");
				}
			} else {
				//查询时将评审的状态集合保存
				stateList = Arrays.asList(BorrowModel.STATE_NEED_REVIEW, BorrowModel.STATE_PASS,BorrowModel.STATE_REFUSED);
//						BorrowModel.STATE_REFUSED1,BorrowModel.STATE_REFUSED2,BorrowModel.STATE_REFUSED3,BorrowModel.STATE_REFUSED4,BorrowModel.STATE_REFUSED5,
//						BorrowModel.STATE_REFUSED6,BorrowModel.STATE_REFUSED7,BorrowModel.STATE_REFUSED8,BorrowModel.STATE_REFUSED9,BorrowModel.STATE_REFUSED10,
//						BorrowModel.STATE_REFUSED11,BorrowModel.STATE_REFUSED12,BorrowModel.STATE_REFUSED13,BorrowModel.STATE_REFUSED14);
//				stateList.add(BorrowModel.STATE_REFUSED1);
//				stateList.add(BorrowModel.STATE_REFUSED2);
//				stateList.add(BorrowModel.STATE_REFUSED3);
//				stateList.add(BorrowModel.STATE_REFUSED4);
//				stateList.add(BorrowModel.STATE_REFUSED5);
//				stateList.add(BorrowModel.STATE_REFUSED6);
//				stateList.add(BorrowModel.STATE_REFUSED7);
//				stateList.add(BorrowModel.STATE_REFUSED8);
//				stateList.add(BorrowModel.STATE_REFUSED9);
//				stateList.add(BorrowModel.STATE_REFUSED10);
//				stateList.add(BorrowModel.STATE_REFUSED11);
//				stateList.add(BorrowModel.STATE_REFUSED12);
//				stateList.add(BorrowModel.STATE_REFUSED13);
//				stateList.add(BorrowModel.STATE_REFUSED14);
			}
			params.put("stateList", stateList);
			page = clBorrowService.listReview(params, currentPage, pageSize);
		}

		//TODO 该逻辑移到点击人工复审和和关闭详情中。修改订单审批中逻辑为：(1)使用锁防止竞争修改状态为审批中，当退出时立即修改单个订单
//		for(ManageBorrowModel borrowModel:page){
//			clBorrowService.verifyAudit(borrowModel);
//		}

		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 借款审核列表
	 * @param currentPage
	 * @param pageSize
	 */
	@RequestMapping(value="/modules/manage/borrow/borrowList.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:borrowList",name = "借款审核状态列表")
	public void borrowList(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List stateList;
		if (params != null) {
			String state = StringUtil.isNull(params.get("state"));
			if (null != state &&!StringUtil.isBlank(state)) {
				//待自动审核
				if(state.equals(BorrowModel.STATE_PRE)){//10
					stateList = Arrays.asList(BorrowModel.STATE_PRE);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				//自动审核失败
				if(state.equals(BorrowModel.STATE_AUTO_REFUSED)){//21
					stateList = Arrays.asList(BorrowModel.STATE_AUTO_REFUSED);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				//人工复审  
				if(state.equals(BorrowModel.STATE_NEED_REVIEW)){//22
					stateList = Arrays.asList(BorrowModel.STATE_NEED_REVIEW,
							BorrowModel.STATE_REFUSED,BorrowModel.STATE_PASS);
				    params.put("stateList", stateList);
					params.put("state", "");
				}
				//自动审核通过
				if(state.equals(BorrowModel.STATE_AUTO_PASS)){//20
//					stateList = Arrays.asList(BorrowModel.STATE_AUTO_PASS);
//				    params.put("stateList", stateList);
//					params.put("state", "");
					//pantheon 20170802 自动审核通过
					params.put("stateSuccess", BorrowModel.STATE_SUCCESS_AUTO_PASS);
					params.put("stateList", null);
					params.put("state", "");
				}
			}
		}
		Page<ManageBorrowModel> page = clBorrowService.listModel(params,currentPage,pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}


	 /**
	  * 机审拒绝导出
	  *
	  * @throws Exception
	  */
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	 @RequestMapping(value = "/modules/manage/borrow/autoCheck/export.htm")
	 public void export(@RequestParam(value="searchParams",required = false) String searchParams) throws Exception {
		 Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		 SysUser user = (SysUser) request.getSession().getAttribute("SysUser");
		 if(user!=null){
			 List list = clBorrowService.listByMap(params);
			 response.setContentType("application/msexcel;charset=UTF-8");
			 String title = "机审拒绝记录";
			 String[] hearders =  ExportConstant.EXPORT_TONGDUNLOG_LIST_HEARDERS;
			 String[] fields = ExportConstant.EXPORT_TONGDUNLOG_LIST_FIELDS;
			 JsGridReportBase report = new JsGridReportBase(request, response);
			 report.exportExcel(list,title,hearders,fields,user.getName());
		 }else{
			 Map<String,Object> result = new HashMap<String,Object>();
			 result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			 result.put(Constant.RESPONSE_CODE_MSG, "登录超时,请重新登录");
			 ServletUtils.writeToResponse(response,result);
		 }
	 }


	 /**
	 * 借款还款信息列表
	 */
	@RequestMapping(value="/modules/manage/borrow/borrowRepayList.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:borrow:borrowRepayList",name = "借款还款信息列表 ")
	public void borrowRepayList(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		List stateList;
		if (params != null) {
			//放款列表
			String type= StringUtil.isNull(params.get("type"));
			if("repay".equals(type)){
				stateList = Arrays.asList(
						BorrowModel.STATE_AUTO_PASS,
						BorrowModel.STATE_PASS,
						
						BorrowModel.STATE_REPAY_FAIL,
						BorrowModel.STATE_REPAY,
						
						BorrowModel.STATE_REMISSION_FINISH,
						BorrowModel.STATE_DELAY,
						BorrowModel.STATE_BAD,
						BorrowModel.STATE_FINISH);
			    params.put("stateList", stateList);
				String state = StringUtil.isNull(params.get("state"));
				if (null != state &&!StringUtil.isBlank(state)) {
					params.put("state", state);
				}
			}
			String state = StringUtil.isNull(params.get("state"));
			if (null != state &&!StringUtil.isBlank(state)) {
				//还款列表
				if(state.equals(BorrowModel.STATE_FINISH)){//40
					stateList = Arrays.asList(BorrowModel.STATE_FINISH,
							BorrowModel.STATE_REMISSION_FINISH);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				//逾期中列表  
				if(state.equals(BorrowModel.STATE_DELAY)){//50
					stateList = Arrays.asList(BorrowModel.STATE_DELAY);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				//坏账列表  
				if(state.equals(BorrowModel.STATE_BAD)){//90
					stateList = Arrays.asList(BorrowModel.STATE_BAD);
					params.put("stateList", stateList);
					params.put("state", "");
				}
				
			}
		}
		Page<ManageBorrowModel> page = clBorrowService.listBorrowModel(params,current,pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}

	
	/**
	 * 查询借款记录 
	 * @param userId
	 * @param current
	 * @param pageSize
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/borrow/listBorrowLog.htm")
	public void listBorrowLog(
			@RequestParam(value="userId") long userId,
			@RequestParam(value = "current") int current,
			@RequestParam(value = "pageSize") int pageSize) throws Exception {
		Map<String,Object> params = new HashMap<>();
		params.put("userId", userId);
		Page<ManageBorrowModel> page = clBorrowService.listBorrowModel(params, current, pageSize);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", page.getResult());
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, data);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
		ServletUtils.writeToResponse(response,result);
	}

	/**
	 * 催收群发短信
	 */
	@RequestMapping(value = "/api/smsBatch.htm")
	public void smsBatch(){
		String id = request.getParameter("ids");
		Map<String,Object> result = new HashMap<String,Object>();
		int r = clSmsService.smsBatch(id, SmsModel.SMS_TYPE_OVERDUE);
		if(r == 1){
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
		}
		ServletUtils.writeToResponse(response,result);
	}
}
