package com.rongdu.cashloan.manage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.rongdu.cashloan.cl.domain.*;
import com.rongdu.cashloan.cl.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tool.util.StringUtil;
import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.model.UserAuthModel;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.domain.User;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.domain.UserOtherInfo;
import com.rongdu.cashloan.core.model.CloanUserModel;
import com.rongdu.cashloan.core.model.ManagerUserModel;
import com.rongdu.cashloan.core.service.CloanUserService;
import com.rongdu.cashloan.core.service.UserBaseInfoService;
import com.rongdu.cashloan.system.permission.annotation.RequiresPermission;


 /**
 * 用户记录Controller
 * 
 * @author jdd
 * @version 1.0.0
 * @date 2017-02-21 13:39:06
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Controller
@Scope("prototype")
public class ManageUserController extends ManageBaseController {
	 private static final Logger logger = LoggerFactory.getLogger(ManageUserController.class);
	 @Resource
	 private CloanUserService cloanUserService;
	 @Resource
	 private UserAuthService authService;
	 @Resource
	 private UserBaseInfoService userBaseInfoService;
	 @Resource
	 private ChannelService channelService;

	 /**
	  * 用户信息列表
	  *
	  * @param searchParams
	  * @param currentPage
	  * @param pageSize
	  */
	 @SuppressWarnings("unchecked")
	 @RequestMapping(value = "/modules/manage/cl/cluser/list.htm", method = {RequestMethod.GET, RequestMethod.POST})
	 @RequiresPermission(code = "modules:manage:cl:cluser:list", name = "用户信息列表")
	 public void list(@RequestParam(value = "searchParams", required = false) String searchParams,
					  @RequestParam(value = "current") int currentPage,
					  @RequestParam(value = "pageSize") int pageSize) {
		 Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		 Page<User> page = cloanUserService.listUser(params, currentPage, pageSize);
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put(Constant.RESPONSE_DATA, page);
		 result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		 result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		 result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		 ServletUtils.writeToResponse(response, result);
	 }

	 /**
	  * 修改用户信息
	  *
	  */
	 @RequestMapping("/modules/manage/cl/cluser/save.htm")
	 @RequiresPermission(code = "modules:manage:system:user:save", name = "修改用户信息")
	 public void saveOrUpdateUserInfo(HttpServletResponse response,
									  @RequestParam(value = "form", required = false) String form,
									  @RequestParam(value = "status", required = false) String status)
			 throws Exception {
		 UserBaseInfo userModel = JsonUtil.parse(form, UserBaseInfo.class);
		 Map<String, Object> res = new HashMap<String, Object>();
		 userModel.setId(userModel.getBaseInfoId());
		 boolean backstauts = userBaseInfoService.addOrModify(userModel, status);
		 //boolean backstauts = sysDictService.addOrModify(sysDict, status);
		 if (backstauts) {
			 res.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			 if (Constant.INSERT.equals(status)) {
				 res.put(Constant.RESPONSE_CODE_MSG, "插入成功");
			 } else {
				 res.put(Constant.RESPONSE_CODE_MSG, "更新成功");
			 }
		 }
		 ServletUtils.writeToResponse(response, res);
	 }


	/**
	 * 用户详细信息
	 * @param userId
	 */
	@RequestMapping(value="/modules/manage/cl/cluser/detail.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:cl:cluser:detail",name = "用户详细信息")
	public void detail(@RequestParam(value = "userId") Long userId){
		String serverHost = Global.getValue("server_host");
		HashMap<String, Object> map = new HashMap<String,Object>();
		User user = cloanUserService.getById(userId);
		if (user != null && user.getId() != null) {
			//用户基本信息
			ManagerUserModel model = userBaseInfoService.getBaseModelByUserId(userId);
			model.setLivingImg(model.getLivingImg()!=null?serverHost +"/readFile.htm?path="+ model.getLivingImg():"");
			model.setFrontImg(model.getFrontImg()!=null?serverHost +"/readFile.htm?path="+ model.getFrontImg():"");
			model.setBackImg(model.getBackImg()!=null?serverHost +"/readFile.htm?path="+ model.getBackImg():"");
			model.setOcrImg(model.getOcrImg()!=null?serverHost +"/readFile.htm?path="+ model.getOcrImg():"");
			
			if (StringUtil.isNotBlank(model.getWorkingImg())) {
				String workImgStr = model.getWorkingImg();
				List<String> workImgList = Arrays.asList(workImgStr.split(";"));
				for (int i = 0; i < workImgList.size(); i++) {
					String workImg = workImgList.get(i);
					workImgList.set(i, serverHost +"/readFile.htm?path="+ workImg);
				}
				map.put("workImgArr", workImgList);
			}

			Channel cl = channelService.getById(user.getChannelId());
			if (cl!=null) {
				model.setChannelName(cl.getName());
			}
			map.put("userbase", model);
			
			// 构造查询条件Map
			HashMap<String, Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId",user.getId());
			
			// 认证信息
			UserAuth authModel = authService.getUserAuth(paramMap);
			map.put("userAuth", authModel);

		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, map);
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 用户认证信息列表
	 * @param currentPage
	 * @param pageSize
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/modules/manage/cl/cluser/authlist.htm",method={RequestMethod.GET,RequestMethod.POST})
	@RequiresPermission(code = "modules:manage:cl:cluser:authlist",name = "用户认证信息列表")
	public void authlist(@RequestParam(value="searchParams",required=false) String searchParams,
			@RequestParam(value = "current") int currentPage,
			@RequestParam(value = "pageSize") int pageSize){
		Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
		Page<UserAuthModel> page = authService.listUserAuth(params, currentPage, pageSize);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put(Constant.RESPONSE_DATA, page);
		result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
		result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
		result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
		ServletUtils.writeToResponse(response,result);
	}

	
	/**
	 * 添加和取消黑名单
	 * @param id
	 * @param state
	 * @throws Exception
	 */
	@RequestMapping(value = "/modules/manage/user/updateState.htm")
	public void updateState(
			@RequestParam(value="id") long id,
			@RequestParam(value="state") String state) throws Exception {
		int msg = userBaseInfoService.updateState(id,state);
		Map<String,Object> result = new HashMap<String,Object>();
		if (msg<0) {
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "修改失败");
		}else {
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "修改成功");
			
		}
		ServletUtils.writeToResponse(response,result);
	}
}
