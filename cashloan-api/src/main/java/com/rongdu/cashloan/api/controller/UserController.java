package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.api.user.bean.AppAbsActionWrapper;
import com.rongdu.cashloan.api.user.bean.AppDbSession;
import com.rongdu.cashloan.api.user.bean.AppLoginedActionWraper;
import com.rongdu.cashloan.api.user.service.UserService;
import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.cl.service.ClSmsService;
import com.rongdu.cashloan.cl.service.SmsService;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.MapUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.domain.Ticket;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tool.util.BeanUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lsk on 2017/2/15.
 */
@Scope("prototype")
@Controller
@RequestMapping("/api/act/user")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController {

	@Resource
	private MybatisService mybatisService;

	@Resource
	private SmsService smsService;

	@Resource
	private AppDbSession appDbSession;

	@Resource(name = "clUserService_")
	private UserService userService;

	@RequestMapping("changeLoginPwd")
	public void changeLoginPwd(final HttpServletRequest request,
							   HttpServletResponse response, final String oldPwd,
							   final String newPwd,final String mobileType) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				Map user = mybatisService.queryRec("usr.queryUser", userId);
				String pwd=(String) user.get("login_pwd");
				//oldPwd.equalsIgnoreCase(pwd)这个是h5判断需要忽略大小写
				//当用H5时用自己的一套加密解密规则
				if(Constant.H5_MOBILE_TYPE.equals(mobileType)){
					String oldPwdH5= UserService.handlePwd(mobileType,oldPwd);
					String pwd1=(String) user.get("login_pwd");
					if (!oldPwdH5.equals(pwd1)) {
						Map ret = new LinkedHashMap();
						ret.put("success", false);
						ret.put("msg", "原密码不正确");
						return ret;
					}
				}else{
					//其他接口的情况
					if (!oldPwd.equals((String) user.get("login_pwd"))) {
						Map ret = new LinkedHashMap();
						ret.put("success", false);
						ret.put("msg", "原密码不正确");
						return ret;
					}
				}



				Map updateMap = new HashMap<String,Object>();
				updateMap.put("id", user.get("id"));
				//h5时修改此
				if(Constant.H5_MOBILE_TYPE.equals(mobileType)){
					String newPwdH5= UserService.handlePwd(mobileType,newPwd);
					updateMap.put("loginPwd", newPwdH5);
				}else {
					updateMap.put("loginPwd", newPwd);
				}
				updateMap.put("loginpwdModifyTime",new Date());
				int modifyCount = mybatisService.updateSQL("usr.updateClUserById", updateMap);
				Map ret = new LinkedHashMap();
				if(modifyCount>0){
					ret.put("success", true);
					ret.put("msg", "登录密码修改成功");
				}else {
					ret.put("success", false);
					ret.put("msg", "登录密码修改失败");
				}
				return ret;
			}
		};
	}

	@RequestMapping("changeTradePwd")
	public void changeTradePwd(final HttpServletRequest request,
							   HttpServletResponse response, final String oldPwd,
							   final String newPwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				Map user = mybatisService.queryRec("usr.queryUser", userId);

				String oldTradePwd = (String) user.get("trade_pwd");
				if (oldTradePwd == null) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "请先上设置初始交易密码");
					return ret;
				}

				if (!oldPwd.equals(oldTradePwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "原密码不正确");
					return ret;
				}

				Map updateMap = new HashMap<String,Object>();
				updateMap.put("id", user.get("id"));
				updateMap.put("tradePwd",newPwd);
				updateMap.put("tradepwdModifyTime",new Date());
				int modifyCount = mybatisService.updateSQL("usr.updateClUserById", updateMap);

				Map ret = new LinkedHashMap();
				if(modifyCount>0){
					ret.put("success", true);
					ret.put("msg", "交易密码修改成功");
				}else {
					ret.put("success", false);
					ret.put("msg", "交易密码修改失败");
				}
				return ret;
			}
		};
	}

	@RequestMapping("setTradePwd")
	public void setTradePwd(final HttpServletRequest request,
							HttpServletResponse response, final String pwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				if (StringUtil.isEmpty(pwd)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}
				Map user = mybatisService.queryRec("usr.queryUser", userId);

				if (!StringUtil.isEmpty((String) user.get("trade_pwd"))) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "交易密码已设置,不能重复设置");
					return ret;
				}


				Map updateMap = new HashMap<String,Object>();
				updateMap.put("id", user.get("id"));
				updateMap.put("tradePwd",pwd);
				updateMap.put("tradepwdModifyTime",new Date());
				int modifyCount = mybatisService.updateSQL("usr.updateClUserById", updateMap);

				Map ret = new LinkedHashMap();
				if(modifyCount>0){
					ret.put("success", true);
					ret.put("msg", "交易密码设置成功");
				}else{
					ret.put("success", false);
					ret.put("msg", "交易密码设置失败");
				}
				return ret;
			}
		};
	}

	@RequestMapping("validateUser")
	public void validateUser(final HttpServletRequest request,
							 HttpServletResponse response, final String idNo,
							 final String realName, final String vcode) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {

				if (StringUtil.isEmpty(idNo) || StringUtil.isEmpty(realName)
						|| StringUtil.isEmpty(vcode)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}

				Map detail = mybatisService
						.queryRec("usr.validateUser", userId);

				if (!idNo.equalsIgnoreCase((String) detail.get("id_no"))
						|| !realName.equals(detail.get("real_name"))) {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "身份证或姓名验证不通过");

					Map data = new LinkedHashMap();
					data.put("pass", false);
					ret.put("data", data);
					return ret;
				}

				ClSmsService clSmsService = (ClSmsService) BeanUtil
						.getBean("clSmsService");
				String loginName = ticket.getLoginName();
				String msg;
				int result = clSmsService.verifySms(loginName,
						SmsModel.SMS_TYPE_FINDPAY, vcode);
				if (result == 1) {
					msg = null;
				} else if (result == -1) {
					msg = "验证码已过期";
				} else {
					msg = "手机号码或验证码错误";
				}

				if (msg != null) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", msg);
					return ret;
				}

				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "验证通过");

				Map data = new LinkedHashMap();
				data.put("pass", true);
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("resetTradePwd")
	public void resetTradePwd(final HttpServletRequest request,
							  HttpServletResponse response, final String newPwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {

				if (StringUtil.isBlank(newPwd) || StringUtil.isBlank(userId)) {
					Map ret = new LinkedHashMap();
					ret.put("success", false);
					ret.put("msg", "参数不能为空");
					return ret;
				}

				Map updateMap = new HashMap<String,Object>();
				updateMap.put("id", userId);
				updateMap.put("tradePwd",newPwd);
				updateMap.put("tradepwdModifyTime",new Date());
				int modifyCount = mybatisService.updateSQL("usr.updateClUserById", updateMap);

				Map ret = new LinkedHashMap();
				if(modifyCount>0){
					ret.put("success", true);
					ret.put("msg", "交易密码重置成功");
				}else {
					ret.put("success", false);
					ret.put("msg", "交易密码重置失败");
				}
				return ret;
			}
		};
	}

	@RequestMapping("logout")
	public void logout(final HttpServletRequest request,
					   HttpServletResponse response) {
		new AppAbsActionWrapper(response) {

			@Override
			public Object doAction() {
				String token = request.getHeader("token");  //放到header中
				if (appDbSession.remove(token)) {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "退出成功");
					return ret;
				} else {
					Map ret = new LinkedHashMap();
					ret.put("success", true);
					ret.put("msg", "token不存在，无需注销");
					return ret;
				}
			}
		};
	}

	@RequestMapping("info")
	public void accountInfo(final HttpServletRequest request,
							HttpServletResponse response) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				Map data = new LinkedHashMap();
				Map ret = new LinkedHashMap();
				Map rec= mybatisService.queryRec("usr.infoH5", userId);
				ret.put("success", true);
				ret.put("msg", "账户信息获取成功");
				data.put("phone", rec.get("phone"));
				data.put("realName", rec.get("real_name"));
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("getTradeState")
	public void getTradeState(final HttpServletRequest request,
							  HttpServletResponse response) {
		new AppLoginedActionWraper(response, request) {

			private boolean containsEmpty(Map rec, String... keys) {
				for (String key : keys) {
					String value = (String) rec.get(key);
					if (StringUtil.isEmpty(value)) {
						return true;
					}
				}
				return false;
			}

			@Override
			public Object doAction(Ticket ticket, String userId) {

				Map user = mybatisService.queryRec("usr.getTradeState", userId);
				boolean infoEmpty = containsEmpty(user, "real_name", "id_no");
				boolean tradeEmpty = StringUtil.isEmpty((String) user
						.get("trade_pwd"));
				Map ret = new LinkedHashMap();
				ret.put("msg", "交易密码是否可设置状态获取成功");

				Map data = new LinkedHashMap();
				data.put("setable", tradeEmpty);
				data.put("forgetable", !infoEmpty && !tradeEmpty);
				data.put("changeable", !tradeEmpty);
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("validateTradePwd")
	public void validateTradePwd(final HttpServletRequest request,
								 HttpServletResponse response, final String tradePwd) {
		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "操作成功");

				Map data = new LinkedHashMap();
				data.put(
						"pass",
						mybatisService.queryRec(
								"usr.validateTradePwd",
								MapUtil.array2Map(new Object[][] {
										{ "userId", userId },
										{ "tradePwd", tradePwd } })) != null);
				ret.put("data", data);
				return ret;
			}
		};
	}

	@RequestMapping("inviteList")
	public void inviteList(final HttpServletRequest request,
						   HttpServletResponse response, final String invitId,
						   final int pageNo, final int pageSize) {

		new AppLoginedActionWraper(response, request) {
			@Override
			public Object doAction(Ticket ticket, String userId) {
				Map ret = new LinkedHashMap();
				ret.put("success", true);
				ret.put("msg", "下级代理商获取成功");

				String _id = StringUtil.isEmpty(invitId) ? userId : invitId;

				Map data = new LinkedHashMap();

				data.put(
						"list",
						mybatisService.querySql(
								"usr.inviteList",
								MapUtil.array2Map(new Object[][] {
										{ "userId", Long.parseLong( _id ) },
										{ "start", (pageNo - 1) * pageSize },
										{ "length", pageSize } })));

				long total = Long.valueOf(mybatisService
						.queryRec("usr.inviteListCnt", Long.parseLong( _id )).get("cnt")
						.toString());

				ret.put("data", data);
				Map page = new LinkedHashMap();
				page.put("current", pageNo);
				page.put("pageSize", pageSize);
				page.put("total", total);
				page.put("pages", total % pageSize == 0 ? total / pageSize
						: total / pageSize + 1);
				ret.put("page", page);
				return ret;
			}
		};
	}

	//修改昵称wl
	@RequestMapping("login/upName.htm")
	public void upName(final HttpServletRequest request,
					   HttpServletResponse response,final long userId,final String realName) {
		new AppAbsActionWrapper(response) {
			@Override
			public Object doAction() {
				Map result = new LinkedHashMap();
				int i=0;
				if(StringUtil.isNotBlank(realName)){
					i= userService.upName(userId,realName);
				}else{
					result.put("msg", "请输入昵称!");
				}
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

}
