package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.cl.service.ClSmsService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.core.service.CloanUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

 /**
 * 短信记录Controller
 * @author lyang
 * @version 1.0.0
 * @date 2017-03-09 14:48:24
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class SmsController extends BaseController {

	@Resource
	private ClSmsService clSmsService;
	@Resource
	private CloanUserService cloanUserService;
	
//	@RequestMapping(value = "/api/smsBatch.htm")
//	public void smsBatch(){
//		String id = request.getParameter("ids");
//		Map<String,Object> result = new HashMap<String,Object>();
//		int r = clSmsService.smsBatch(id);
//		if(r == 1){
//			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//			result.put(Constant.RESPONSE_CODE_MSG, "处理结束");
//		}
//		ServletUtils.writeToResponse(response,result);
//	}

	/**
	 * 探测短信验证码是否可获取
	 * @param phone
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/user/probeSms.htm")
	public void probeSms(@RequestParam(value="phone") String phone, @RequestParam(value="type") String type) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		if(StringUtil.isBlank(phone) || StringUtil.isBlank(type)){
			data.put("message", "参数不能为空");
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "探测失败");
		} else if(!StringUtil.isPhone(phone)){
			data.put("message", "手机号码格式有误");
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "探测失败");
		} else {
			long countDown = clSmsService.findTimeDifference(phone, type);
			data.put("countDown", countDown);
			if (countDown == 0) {
				data.put("state", 10);
			} else {
				data.put("state", 20);
			}
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "探测成功");
		}
		ServletUtils.writeToResponse(response,result);
	}
	 
	/**
	 * 获取短信验证码
	 * @param phone
	 * @param type
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/user/sendSms.htm")
	public void sendSms(@RequestParam(value="phone") String phone, 
			@RequestParam(value="type") String type) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();

		type = StringUtil.upperCase(type);

		String message = this.check(phone, type);
		if (message==null) {
			long countDown = clSmsService.findTimeDifference(phone, type);
			if (countDown != 0) {
				data.put("countDown", countDown);
				data.put("state", "20");
				message = "获取短信验证码过于频繁，请稍后再试";
			} else {
				long msg = clSmsService.sendSms(phone, type);
				if (msg == 1) {
					data.put("state", "10");
					result.put(Constant.RESPONSE_DATA, data);
					result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
					result.put(Constant.RESPONSE_CODE_MSG, "发送成功");
				} else {
					result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
					result.put(Constant.RESPONSE_CODE_MSG, "发送失败");
				}
			}
			
		}
		if (message!=null) {
			data.put("state", "20");
			data.put("message", message);
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "发送失败");
		}
		
		
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 短信验证
	 * @param phone
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value = "/api/user/verifySms.htm")
	public void verifyMsg(
			@RequestParam(value="phone") String phone,
			@RequestParam(value="type") String type,
			@RequestParam(value="vcode") String code
			) throws Exception {
		int msg = clSmsService.verifySms(phone, type, code);
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> data = new HashMap<String,Object>();
		if (msg == 1) {
			data.put("state", "10");
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "验证成功");
		} else if(msg == -1){
			data.put("message", "验证码已过期");
			data.put("state", "20");
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "验证失败");
		} else {
			data.put("message", "手机号码或验证码错误");
			data.put("state", "20");
			result.put(Constant.RESPONSE_DATA, data);
			result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			result.put(Constant.RESPONSE_CODE_MSG, "验证失败");
		}
		ServletUtils.writeToResponse(response,result);
	}
	
	/**
	 * 网页注册发送短信(含验证图片验证码)
	 */
	@RequestMapping(value = "/api/user/h5SendSms.htm")
	public void h5SendSms(){
		String code = request.getParameter("code");
		String phone = request.getParameter("phone");
		String type = request.getParameter("type");
		type = StringUtil.upperCase(type);
		long countDown = 0;
		HttpSession session = request.getSession();  
		String sessionCode = (String) session.getAttribute("code"); 
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String result = null;
		if (StringUtil.isNotBlank(code)&&code.length()==4&&code.equals(sessionCode)) {
			result = this.check(phone, type);
			
	        if (result==null) {
				if (type.equalsIgnoreCase("register")) {
					countDown = clSmsService.findTimeDifference(phone, type);
					if (countDown != 0) {
						result = "获取短信验证码过于频繁，请稍后再试";
					} else {
						long msg = clSmsService.sendSms(phone, type);
						if (msg == 1) {
							result = "10";
						} else {
							result = "短信发送失败";
						}
					}
					
				}else {
					result = "短信类型错误";
				}
			}
	        resultMap.put("countDown", countDown);
			if (result.equals("10")) {
				resultMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, "短信发送成功");
			}else {
				resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
				resultMap.put(Constant.RESPONSE_CODE_MSG, result);
			}
		}else {
			resultMap.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
			resultMap.put(Constant.RESPONSE_CODE_MSG, "图片验证码错误");
		}
		
		ServletUtils.writeToResponse(response,resultMap);
	}
	
	private String check(String phone,String type){
		String msg = null;
		if(StringUtil.isBlank(phone) || StringUtil.isBlank(type)){
			msg = "参数不能为空";
		} else if(!StringUtil.isPhone(phone)){
			msg = "手机号码格式有误";
		} else {
			// 当日最大注册用户数
			long todayCount = cloanUserService.todayCount();
            String dayRegisterMax_ = Global.getValue("day_register_max");
            if(StringUtil.isNotBlank(dayRegisterMax_)){
            	int dayRegisterMax = Integer.parseInt(dayRegisterMax_);
            	if(dayRegisterMax > 0 && todayCount >= dayRegisterMax){
					msg = "今天注册人数已达到上限";
            	}
            }
			
			if(StringUtil.equals(SmsModel.SMS_TYPE_REGISTER, type)){ //register
				if (clSmsService.findUser(phone)>0) {
					msg = "该手机号已经注册";
				}
			}
			
			if (StringUtil.equals(SmsModel.SMS_TYPE_FINDREG, type)) {//findReg
				if (clSmsService.findUser(phone)<1) {
					msg = "该手机号不存在";
				}
			}
			
			if (msg==null&&clSmsService.countDayTime(phone, type) <= 0) {
				msg = "获取短信验证码过于频繁，请明日再试";
			}
		}	
		return msg;
	}

	 /**
	  * 获取短信验证码,不验签
	  * @param phone
	  * @param type
	  * @throws Exception
	  */
	 @RequestMapping(value = "/user/sendSms.htm")
	 public void sendSmsNoApi(@RequestParam(value="phone") String phone, @RequestParam(value="type") String type) throws Exception {
		 Map<String,Object> result = new HashMap<String,Object>();
		 Map<String,Object> data = new HashMap<String,Object>();
		 type = StringUtil.upperCase(type);

		 String message = this.check(phone, type);
		 if (message==null) {
			 long countDown = clSmsService.findTimeDifference(phone, type);
			 if (countDown != 0) {
				 data.put("countDown", countDown);
				 data.put("state", "20");
				 message = "获取短信验证码过于频繁，请稍后再试";
			 } else {
				 long msg = clSmsService.sendSms(phone, type);
				 if (msg == 1) {
					 data.put("state", "10");
					 result.put(Constant.RESPONSE_DATA, data);
					 result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
					 result.put(Constant.RESPONSE_CODE_MSG, "发送成功");
				 } else {
					 result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
					 result.put(Constant.RESPONSE_CODE_MSG, "发送失败");
				 }
			 }
		 }
		 if (message!=null) {
			 data.put("state", "20");
			 data.put("message", message);
			 result.put(Constant.RESPONSE_DATA, data);
			 result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
			 result.put(Constant.RESPONSE_CODE_MSG, "发送失败");
		 }
		 ServletUtils.writeToResponse(response,result);
	 }
}
