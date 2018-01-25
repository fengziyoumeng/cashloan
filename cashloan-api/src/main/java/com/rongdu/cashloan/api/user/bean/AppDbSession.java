package com.rongdu.cashloan.api.user.bean;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.cl.threads.SingleThreadPool;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.domain.Ticket;
import com.rongdu.cashloan.core.domain.User;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AppDbSession{

	private Logger logger = LoggerFactory.getLogger(AppDbSession.class);

	@Autowired
	private ShardedJedisClient redisClient;

	@Resource
	protected MybatisService mybatisService;

	//会话失效
	public boolean remove(String ticketId) {
		//删除该用户的ticketId对应的数据
		if( redisClient.exists(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + ticketId)){
			Ticket ticket = (Ticket) redisClient.getObject( AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA+ticketId);
			//删除该用户的手机号对应的数据
			if(ticket!= null && redisClient.exists(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + ticket.getLoginName()) ){
				redisClient.del(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + ticket.getLoginName());  //删除phone-ticketid对应的数据
			}
			redisClient.delObject(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + ticketId);
		}
		return true;
	}

	public Object access(String ticketId) {
		//判断session是否存在
		Ticket ticket = null;
		if( redisClient.exists(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + ticketId)){
			ticket = (Ticket) redisClient.getObject( AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA+ticketId);
		}

		//为了不查询数据库，当没有session时不判断是否用户被锁定
		if (ticket == null){
			return new Object[][] { { "code", 413 }, { "msg", "会话失效请登录" } };
		}

		//更新最新访问时间和重置失效时间
		Date now = new Date();
		ticket.setLastAccessTime(now);

		//更新上一次登录时间到缓存
		redisClient.hset(AppConstant.REDIS_KEY_LOGIN_TIME + DateUtil.getNowDate(), ticket.getUserId(), new SimpleDateFormat("yyyyMMddHHmmss").format(now), 172800);

		int expireTime = Global.getInt("session_expire");
		if(expireTime ==0){
			expireTime = 604800;
		}
		redisClient.setObject(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + ticketId,ticket, expireTime);
		redisClient.set(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + ticket.getLoginName(),ticket.getTicketId(),expireTime);
		return ticket;
	}


	public Ticket create(HttpServletRequest request, String loginname, int platformtype) {

		Ticket ticket = null;
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		//判断是否在别的平台登录过，如果在别的平台登录过删除redis中对应的数据（平台间互踢）
		if(redisClient.exists(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + loginname) ){
			logger.info("loginname在别的平台登录过=======>REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA="+AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + loginname);
			String oldTicketId = redisClient.get(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA+loginname);
			if(!StringUtil.isBlank(oldTicketId) && redisClient.exists(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + oldTicketId)){
				logger.info("loginname在别的平台登录过=======>oldTicketId="+oldTicketId);
				ticket = (Ticket) redisClient.getObject( AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA+oldTicketId);
				ticket.setLastAccessTime(new Date());
				ticket.setTicketId(token);
				logger.info("loginname在别的平台登录过=======>oldTicketId="+oldTicketId+",设置成功");
			}


			//删除老的redis中信息
			redisClient.del(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA+ticket.getLoginName());
			redisClient.delObject(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + oldTicketId);
		}else {
			ticket = new Ticket();
			ticket.setTicketId(token);
			Map user = mybatisService.queryRec("usr.queryUserByLoginName", loginname);
			//这里不会判断是否用户锁定
			if(user!=null){
				ticket.setLastAccessTime(new Date());
				ticket.setUserId(user.get("id").toString());
				ticket.setLoginName(user.get("login_name")==null?"":user.get("login_name").toString());
				ticket.setChanneId(user.get("channel_id")==null?-1:Long.parseLong(user.get("channel_id").toString()));
				ticket.setLevel(user.get("level")==null?"":user.get("level").toString());
				ticket.setLoginPwd(user.get("login_pwd")==null?"":user.get("login_pwd").toString());
				ticket.setTrade_pwd(user.get("trade_pwd")==null?"":user.get("trade_pwd").toString());
				ticket.setPlatformType(platformtype);
				ticket.setInvitationCode(user.get("invitation_code")==null?"":user.get("invitation_code").toString());
				ticket.setRegisterClient(user.get("register_client")==null?"":user.get("register_client").toString());
				ticket.setRegistTime(user.get("regist_time")==null?null:DateUtil.valueOf(user.get("regist_time").toString() ,"yyyy-MM-dd HH:mm:ss"));
			}
		}
		//session放到redis中
		int expireTime = Global.getInt("session_expire");
		if(expireTime ==0){
			expireTime = 604800;
		}
		redisClient.setObject(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + ticket.getTicketId(), ticket,expireTime);
		redisClient.set(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA+loginname,ticket.getTicketId(),expireTime);
		return ticket;

	}


	public Ticket autoLogin(HttpServletRequest request, String loginName,String mobileType) {
		return  create(request, loginName, Integer.parseInt( mobileType));
	}


	private Map toMap(String data) {
		return JSONObject.parseObject(data, LinkedHashMap.class);
	}


	public String getApkUrl(String channelId){
		String key = AppConstant.APK_DOWNLOAD+channelId;
		return redisClient.get(key);
	}
}
