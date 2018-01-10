package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.api.user.bean.AppAbsActionWrapper;
import com.rongdu.cashloan.api.user.bean.AppDbSession;
import com.rongdu.cashloan.api.user.service.UserService;
import com.rongdu.cashloan.cl.service.SmsService;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.util.code.MD5;
import com.rongdu.cashloan.core.domain.Ticket;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lsk on 2016/7/27.
 */
@Scope("prototype")
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping("/api/user")
public class LoginController {

	@Resource
	private MybatisService mybatisService;
	@Resource
	private AppDbSession appDbSession;

	@Resource(name = "clUserService_")
	private UserService userService;

	@Resource
	private SmsService smsService;
	protected HttpServletResponse response;

	@RequestMapping("login")
	public void login(final HttpServletRequest request,
					  HttpServletResponse response, final String loginName,
					  final String loginPwd, final String signMsg) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				return userService.login(request, loginName, loginPwd, signMsg);
			}
		};
	}

	@RequestMapping("autoLogin")
	@Deprecated
	//20170828 pantheon app没有请求该接口
	//public void autoLogin(final HttpServletRequest request, final HttpServletResponse response, final String refresh_token) {
	public void autoLogin(final HttpServletRequest request, final HttpServletResponse response, final String token,final String loginName,final String mobileType) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = new LinkedHashMap();

				if (StringUtil.isEmpty(token) || StringUtil.isEmpty(loginName) || StringUtil.isEmpty(mobileType)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "非法请求");
					return ret;
				}

//				if (Math.abs(new Date().getTime() - subtime) > 60000) {
//					Map ret = new LinkedHashMap();
//					ret.put("success", false);
//					ret.put("msg", "请确认参数subtime是否正确");
//					return ret;
//				}

				/*
				 * String verify = MD5.encode(Global.getValue("app_key") +
				 * refreshToken + subtime); if
				 * (!verify.equalsIgnoreCase(signMsg)) { Map ret = new
				 * LinkedHashMap(); ret.put("success", false); ret.put("msg",
				 * "签名未通过"); return ret; } else {
				 */
				Ticket ticket = appDbSession.autoLogin(request, token,mobileType);
				result.put("success", true);
				result.put("data",ticket.getTicketId());
				result.put("msg", "成功自动登录");
				return result;
			}
		};
	}


	/**
	 * 该方法没有用到
	 * @param request
	 * @param response
	 * @param phone
	 */
	@RequestMapping("fetchSmsVCode")
	public void fetchSmsVCode(final HttpServletRequest request, final HttpServletResponse response, final String phone) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				if (StringUtil.isEmpty(phone)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "手机号码不能为空");
					return ret;
				}
				smsService.sendSmsByTpl(request, phone, "vcode", randomNum(4));
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "验证码已发送");
				return ret;
			}
		};
	}

	private static String randomNum(int len) {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	//final String invitationCode,
	//final String registerCoordinate,
	//final String registerAddr,
	//final String blackBox,
	//final String signMsg, //加签值

	@RequestMapping("register")
	public void register(final HttpServletRequest request,
			final HttpServletResponse response,
			final String loginName,
			final String loginPwd,
			final String vcode,
			final String client,
			final String channelCode,
		    final String invitationCode) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = userService.registerUser(request, loginName,
						loginPwd, vcode, client,channelCode,"",invitationCode);
				if ((Boolean) result.get("success")) {
					result = userService.login(request, loginName, loginPwd,"");
					result.put("msg", result.get("msg"));
				}
				return result;
			}
		};
	}


	@RequestMapping("wxRegister")
	public void wxRegister(final HttpServletRequest request,
						   final HttpServletResponse response, final String loginName,
						   final String loginPwd, final String vcode,
						   final String channelCode, final String signMsg,
						   final String blackBox,final String mobileType,
						   final String invitationCode) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				//注册H5与app端密码加密规则不一致
				String newPwd=loginPwd.toUpperCase();
				if(Constant.H5_MOBILE_TYPE.equals(mobileType)){
					newPwd = UserService.handlePwd(mobileType, loginPwd);
				}
				if (Constant.MOBILE_TYPE_NO_LOGIN.equals(mobileType)){
					newPwd = MD5.encode(newPwd);
				}
				Map result = userService.registerUser(request, loginName,newPwd, vcode, "h5",channelCode,mobileType,invitationCode);
				if(!Constant.MOBILE_TYPE_NO_LOGIN.equals(mobileType)){
					if ((Boolean) result.get("success")) {
						result = userService.login(request, loginName, newPwd,
								signMsg);
						result.put("msg", "注册成功!");
					}
				}
				return result;
			}
		};
	}


	@RequestMapping("login/forgetPwd.htm")
	public void forgetPwd(final HttpServletRequest request,
			HttpServletResponse response, final String phone,
			final String newPwd, final String vcode, final String signMsg,final String mobileType) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				return userService.forgetPwd(phone, newPwd, vcode, signMsg,mobileType);
			}
		};
	}
	//修改昵称wl
	@RequestMapping("/api/act/user/login/upName.htm")
	public void upName(final HttpServletRequest request,
					   HttpServletResponse response,final long userId,final String realName) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = new LinkedHashMap();
				int i=0;
				i= userService.upName(userId,realName);
				Map data = new LinkedHashMap();
				if(i>0){
					result.put("data", data);
					result.put("success", true);
					result.put("msg", "修改昵称成功!");
				}else{
					result.put("msg", "修改昵称失败!");
				}
				return result;
			}
		};
	}
	@RequestMapping("validateSmsCode")
	public void validateSmsCode(final HttpServletRequest request,
			HttpServletResponse response, final String phone,
			final String type, final String vcode, final String signMsg) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(vcode)
						|| StringUtil.isEmpty(signMsg)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "非法参数");
					return ret;
				}

				/*
				 * String _signMsg = MD5.encode(Global.getValue("app_key") +
				 * phone + vcode); if (!_signMsg.equalsIgnoreCase(signMsg)) {
				 * Map ret = new LinkedHashMap(); ret.put("success", false);
				 * ret.put("msg", "签名验签不通过"); return ret; }
				 */

				String msg = smsService.validateSmsCode(phone, type, vcode);
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", msg);

				Map data = new LinkedHashMap();
				data.put("pass", msg == null);
				ret.put("data", data);
				return ret;
			}
		};

	}

	@RequestMapping("isPhoneExists")
	public void isPhoneExists(final HttpServletRequest request,
			HttpServletResponse response, final String phone) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				boolean exists = mybatisService.queryRec("usr.isPhoneExists",
						phone) != null;
				Map ret = new LinkedHashMap();
				ret.put("success", true);

				Map data = new LinkedHashMap();
				data.put("isExists", exists ? "20" : "10");
				ret.put("data", data);
				ret.put("msg", exists ? "该手机号码已存在" : "该手机不存在，可注册");
				return ret;
			}
		};
	}
}
