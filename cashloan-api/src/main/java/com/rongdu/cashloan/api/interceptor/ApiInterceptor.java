package com.rongdu.cashloan.api.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rongdu.cashloan.api.util.MD5Util;
import com.rongdu.cashloan.core.domain.Ticket;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.api.user.bean.AppDbSession;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.MapUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;

//import tool.util.StringUtil;

/**
 * Created by lsk on 2017/2/14.
 */
@SuppressWarnings({ "rawtypes" })
public class ApiInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(ApiInterceptor.class);

	@Autowired
	private AppDbSession session;




	public static Map<String,Object> getParams(HttpServletRequest request) {
		Map<String, String[]> rec = request.getParameterMap();
		Map<String, Object> result = new LinkedHashMap<String, Object>();

		for (Map.Entry<String, String[]> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue()[0];
			result.put(name, value);
		}
		return result;
	}

	public static String paramsString(Map<String,Object> map) {
		Map<String, Object> rec = MapUtil.simpleSort(map);
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Object> entry : rec.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			sb.append(name + "=" + value).append("|");
		}

		if (sb.length() > 1)
			sb.deleteCharAt(sb.length() - 1);
		logger.debug("***********参数拼接后： " + sb.toString()+"***********");
		return sb.toString();
	}

	private static String md5(String data) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		return Hex.encodeToString(MessageDigest.getInstance("MD5").digest(
				data.getBytes("utf8")));

	}

	public static String getBodyString(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
//			br.close();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return str;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		Map< String, Object>  requestMap = getParams(request);


		// 版本控制
		String check_version_android = Global.getValue("check_version_android");
		String check_version_IOS = Global.getValue("check_version_ios");

		Map<String, Object> m_android = JSONObject.parseObject(check_version_android, Map.class);
		Map<String, Object> m_ios = JSONObject.parseObject(check_version_IOS, Map.class);


		String mobileType = StringUtil.isNull(requestMap.get("mobileType"));
		if( !StringUtil.isBlank(mobileType) ){
			//IOS版本检测
			if("1".equals(mobileType)) {
				// 开关状态
				if (m_ios.get("state") != null && m_ios.get("state").equals("10")) {
					// String[] sys_vers=(m.get("version")+"").split("\\.");
					String versionNumbe = StringUtil.isNull(requestMap.get("versionNumber"));
					String user_version = StringUtil.isBlank(versionNumbe) ? "1.0.0" : versionNumbe;
					String sys_version = m_ios.get("version") + "";
					String iosMsg = m_ios.get("msg")+"";
					String iosDownloadUrl = m_ios.get("iosDownloadUrl")+"";
					String forceUpdate = m_ios.get("forceUpdate")+"";
					int result = StringUtil.compareVersion(sys_version, user_version);
					if (result > 0) {
						m_ios = new HashMap<String, Object>();
						m_ios.put("code", 400);
						m_ios.put("msg",iosMsg);
						m_ios.put("iosDownloadUrl",iosDownloadUrl);
						m_ios.put("forceUpdate",forceUpdate);
						JsonUtil.writeJson(m_ios, response);
						return false;
					}
				}
			}

			//android版本检测
			if ("2".equals(mobileType)) {
				// 开关状态
				if (m_android.get("state") != null && m_android.get("state").equals("10")) {
					// String[] sys_vers=(m.get("version")+"").split("\\.");
					String versionNumbe = StringUtil.isNull(requestMap.get("versionNumber"));
					String user_version = StringUtil.isBlank(versionNumbe) ? "1.0.0" : versionNumbe;
					String sys_version = m_android.get("version") + "";
					String androidMsg = m_android.get("msg")+"";
					String forceUpdate = m_android.get("forceUpdate")+"";
					// begin pantheon 20170606 新增安卓下载版本
					String sys_androidDownloadUrl = m_android.get("androidDownloadUrl") + "";
					int result = StringUtil.compareVersion(sys_version, user_version);
					if (result > 0) { //需要跟新版本
						String appUpdateWay = "oss";
						String androidMustOss = Global.getValue("android_must_oss");
						if(androidMustOss!=null && "2".equals(androidMustOss)){
							String channelId = StringUtil.isNull(requestMap.get("update_channel"));
							//从redis中获取下载地址
							String apkUrl = session.getApkUrl(channelId);
							if(!StringUtils.isBlank(apkUrl)){  //当redis中有该渠道的地址时使用渠道中的下载地址
								sys_androidDownloadUrl = apkUrl;
								appUpdateWay = "web";
							}
						}
						m_android = new HashMap<String, Object>();
						m_android.put("code", 400);
						m_android.put("msg",androidMsg);
						m_android.put("androidDownloadUrl", sys_androidDownloadUrl);
						m_android.put("forceUpdate",forceUpdate);
						m_android.put("appUpdateWay",appUpdateWay);
						JsonUtil.writeJson(m_android, response);
						return false;
					}
				}
			}

			if("3".equals(mobileType)) {
				return true;
			}

		}

		String token = request.getHeader("token");
		String signMsg = request.getHeader("signMsg");

		Map<String, Object> rec = new LinkedHashMap<String, Object>();
		String _signMsg;
		// 登录后的请求地址都带有/act/
		boolean flag;
		if (uri.contains("/act/")) {
			if (StringUtil.isEmpty(token) || StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "没有token或signMsg");
				JsonUtil.writeJson(rec, response);
				return false;
			}

			_signMsg = md5(Global.getValue("app_key") + token + paramsString(requestMap));
			logger.info("*******md5加密后的_signMsg："+_signMsg+"**********");
			flag = _signMsg.equalsIgnoreCase(signMsg);

//			// 不需要登录的地址可能没有token
		} else {
			if (StringUtil.isEmpty(signMsg)) {
				rec.put("code", 400);
				rec.put("msg", "没有signMsg");
				JsonUtil.writeJson(rec, response);
				return false;
			}
			_signMsg = md5(Global.getValue("app_key") + (token == null ? "" : token) + paramsString(requestMap));
			flag = _signMsg.equalsIgnoreCase(signMsg);
		}
		logger.info("*******传入的token："+token+"**********"+"传入的signMsg："+signMsg+"********");
		logger.info("*******md5加密生成的_signMsg>>"+_signMsg+"*******");

		// 根据地址是否带/act/生成的_signMsg，校验
		if (!flag) {
			rec.put("code", 400);
			rec.put("msg", "验签不通过");
			JsonUtil.writeJson(rec, response);
			return false;
		}

		// 如果带有token，则说明已经登陆，将用户数据放入session中
		if (StringUtil.isNotBlank(token) && uri.contains("/act/")) {
			Object result = session.access(token);
			if (result instanceof Ticket) {
				Ticket sessionBean = (Ticket) result;
				request.getSession().setAttribute("userData", sessionBean);  //放置的是tiket
				request.getSession().setAttribute("userId", sessionBean.getUserId());
			} else {
				Map json = (result instanceof Map) ? (Map) result : MapUtil.array2Map((Object[][]) result);
				JsonUtil.writeJson(json, response);
				return false;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
