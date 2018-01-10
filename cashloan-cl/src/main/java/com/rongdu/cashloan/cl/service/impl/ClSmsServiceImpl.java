package com.rongdu.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.cl.domain.Sms;
import com.rongdu.cashloan.cl.domain.SmsTpl;
import com.rongdu.cashloan.cl.mapper.ClBorrowMapper;
import com.rongdu.cashloan.cl.mapper.SmsMapper;
import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.cl.service.ClSmsService;
import com.rongdu.cashloan.cl.service.SmsService;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.mapper.BaseMapper;
import com.rongdu.cashloan.core.common.service.impl.BaseServiceImpl;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.domain.Borrow;
import com.rongdu.cashloan.core.domain.User;
import com.rongdu.cashloan.core.domain.UserBaseInfo;
import com.rongdu.cashloan.core.mapper.UserMapper;
import com.rongdu.cashloan.core.service.UserBaseInfoService;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang3.StringUtils;
import org.h2.value.ValueStringIgnoreCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("clSmsService")
public class ClSmsServiceImpl extends BaseServiceImpl<Sms, Long> implements ClSmsService {

	public ClSmsServiceImpl(String sendUrl, String sendSname, String sendSpwd, String sendScorpid, String sendSpridi){
		this.SENDURL =sendUrl;
		this.SEND_SNAME = sendSname;
		this.SEND_SPWD=sendSpwd;
		this.SEND_SCORPID = sendScorpid;
		this.SEND_SPRDID = sendSpridi;
	}
	public ClSmsServiceImpl(){}

	private  String SENDURL;
	private  String SEND_SNAME;
	private  String SEND_SPWD;
	private  String SEND_SCORPID;
	private  String SEND_SPRDID;

	public static final Logger logger = LoggerFactory.getLogger(ClSmsServiceImpl.class);

    @Resource
    private SmsMapper smsMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
	private ClBorrowMapper clBorrowMapper;

    @Resource
    private UserBaseInfoService userBaseInfoService;
	@Resource
    private SmsService smsService;
    
    private int msgcount;

	@Override
	public BaseMapper<Sms, Long> getMapper() {
		return smsMapper;
	}
	
	@Override
	public long findTimeDifference(String phone, String type) {
		int countdown = Global.getInt("sms_countdown");
		Sms sms = smsService.getSmsFromRedis(phone,type);
		long times = 0;
		if (sms != null) {
			Date d1 = sms.getSendTime();
			Date d2 = DateUtil.getNow();
			long diff = d2.getTime() - d1.getTime();
			if (diff < countdown*1000) {
				times = countdown-(diff/1000);
			}else {
				times = 0;
			}
		}
		return times;
	}
	
	@Override
	public int countDayTime(String phone, String type) {
		String mostTimes = Global.getValue("sms_day_most_times");
		int mostTime = JSONObject.parseObject(mostTimes).getIntValue(type);
		int times = smsService.getSmsCountFromRedis(phone,type);
		//int times = smsMapper.countDayTime(data);
		return mostTime - times;
	}

	@Override
	public long sendSms(String phone, String type) {
		type = StringUtil.upperCase(type);
		SmsTpl tpl = smsService.getSmsTplFromRedis(type);
		if (tpl!=null && "10".equals(tpl.getState())) {
			int vcode = (int) (Math.random() * 9000) + 1000;
			String result = null;
			try {
				String messge = change(type).replaceAll("%s",vcode+"");
				//result = sendCodeForDahantc(phone,change(type)+vcode);
				result = sendCodeForDahantc(phone,messge);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return resultForDahantc(result, phone, type,vcode+"");
		}
        return 0;
	}
	
	@Override
	public int smsBatch(String id,final String type) {
		final long[] ids = StringUtil.toLongs(id.split(","));
		new Thread(){
			public void run() {
				SmsTpl smsTpl = (SmsTpl)smsService.getSmsTplFromRedis(type);
				if (smsTpl!=null && "10".equals(smsTpl.getState())) {
					for (int i = 0; i < ids.length; i++) {
						logger.debug(ids[i]+"---");
//						UrgeRepayOrder uro = urgeRepayOrderMapper.findByPrimary(ids[i]);
//						Map<String,Object> map = new HashMap<>();
//						map.put("platform", uro.getBorrowTime());
//						map.put("loan", uro.getAmount());
//						map.put("time", uro.getRepayTime());
//						map.put("overdueDay", uro.getPenaltyDay());
//						map.put("amercement", uro.getPenaltyAmout());
//						String result = sendCodeForDahantc(uro.getPhone(),changeMessage(type,map));
//						resultForDahantc(result, uro.getPhone(), type,"");
					}
				}else {
					return;
				}
			}
		}.start();
		return 1;
	}
	
    
	
	@Override
	public int verifySms(String phone, String type, String code) {
		if ("dev".equals(Global.getValue("app_environment")) && "0000".equals(code)) {
			return 1;
		}
		if(StringUtil.isBlank(phone) || StringUtil.isBlank(type) || StringUtil.isBlank(code)){
			return 0;
		}

		if (!StringUtil.isPhone(phone)) {
			return 0;
		}
		Sms sms = smsService.getSmsFromRedis(phone,type);
		if (sms != null) {
			String mostTimes = Global.getValue("sms_day_most_times");
			int mostTime = JSONObject.parseObject(mostTimes).getIntValue("verifyTime");
			int times = smsService.updateAndSaveSmsCountFromRedis(phone,type);
			if (times>mostTime) {
				return 0;
			}
			if (sms.getCode().equals(code)) {
				//验证码校验成功就删除，不要删除统计记录
				//smsService.delSmsFromRedis(phone,type);
				return 1;
			}
		}
		return 0;
	}
	
	protected String changeMessage(String smsType, Map<String,Object> map) {
		smsType = StringUtil.upperCase(smsType);
		String message = "";
		if (SmsModel.SMS_TYPE_OVERDUE.equals(smsType)) {    //逾期通知
			message = ret(smsType);
			message = message.replace("{$name}", StringUtil.isNull(map.get("realName")));
			message = message.replace("{$phone}",StringUtil.isNull(map.get("phone")));
		}
		if (SmsModel.SMS_TYPE_LOANINFORM.equals(smsType)) { //放款通知——loanInform
			message = ret(smsType);
			message = message.replace("{$time}",DateUtil.dateStr((Date)map.get("time"),"M月dd日"));
		}
		if (SmsModel.SMS_TYPE_REPAYINFORM.equals(smsType)) { //还款后通知——repayInform
			message = ret(smsType);
			message = message.replace("{$time}",DateUtil.dateStr((Date)map.get("time"),"M月dd日"));
			message = message.replace("{$loan}", StringUtil.isNull(map.get("loan")));
		}
		if (SmsModel.SMS_TYPE_REPAYBEFORE.equals(smsType)) { //还款前通知——repayBefore
			message = ret(smsType);
			message = message.replace("{$name}", StringUtil.isNull(map.get("realName")));
			message = message.replace("{$time}",DateUtil.dateStr((Date)map.get("time"),"M月dd日"));
			message = message.replace("{$phone}", StringUtil.isNull(map.get("phone")));
		}
		if (SmsModel.SMS_TYPE_REFUSE.equals(smsType)) {      //拒绝通知——refuse
			message = ret(smsType);
			message = message.replace("{$name}", StringUtil.isNull(map.get("realName")));
		}
		logger.info("短信发送内容:" + message);
		return message;
	}
	
	public String change(String type){
		String message = null;
		if (SmsModel.SMS_TYPE_REGISTER.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_REGISTER);
		}else if ( SmsModel.SMS_TYPE_FINDREG.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_FINDREG);
		}else if (SmsModel.SMS_TYPE_BINDCARD.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_BINDCARD);
		}else if (SmsModel.SMS_TYPE_FINDPAY.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_FINDPAY);
		}else if (SmsModel.SMS_TYPE_OVERDUE.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_OVERDUE);
		}else if (SmsModel.SMS_TYPE_LOANINFORM.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_LOANINFORM);
		}else if (SmsModel.SMS_TYPE_REPAYINFORM.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_REPAYINFORM);
		}else if (SmsModel.SMS_TYPE_REPAYBEFORE.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_REPAYBEFORE);
		}else if (SmsModel.SMS_TYPE_REFUSE.equals(type)) {
			message = ret(SmsModel.SMS_TYPE_REFUSE);
		}
		return message;
	}
	
	public String ret(String type){
		String tpl = null;
		SmsTpl smsTpl = smsService.getSmsTplFromRedis(type);
		if(smsTpl!=null && "10".equals( smsTpl.getState() )){
			tpl = smsTpl.getTpl();
		}
		return tpl;
	}


	/**
	 * 大汉三通短信返回参数
	 *
	 * json返回样例
	 * {
	 *		"msgid":"f02adaaa99c54ea58d626aac2f4ddfa8",
	 *		"result":"0",
	 *		"desc":"提交成功",
	 *		"failPhones":"12935353535,110,130123123"
	 * }
	 * 
	 *  集结号的返回样例子
	 *  <CSubmitState xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://tempuri.org/">
	 *  <State>0</State>
	 *  <MsgID>1711151918413997262</MsgID>
	 *  <MsgState>提交成功</MsgState>
	 *  <Reserve>1</Reserve>
	 *   </CSubmitState>
	 * @param result
	 * @param phone
	 * @param type
	 * @param verCode
	 * @return
	 */
	private int resultForDahantc(String result,String phone,String type,String verCode){

		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON json = xmlSerializer.read(result);
		System.out.println("=====>");
		JSONObject resultJsonTemp = JSONObject.parseObject(json.toString());
		if (StringUtil.isNotBlank(resultJsonTemp)) {
			logger.info("ClSmsServiceOmpl-resultForDahantc-返回参数:"+result);
			String dahanMsgId = StringUtil.isNull(resultJsonTemp.get("MsgID"));
			String dahanResult = StringUtil.isNull(resultJsonTemp.get("State"));
			String dahanDesc = StringUtil.isNull(resultJsonTemp.get("MsgState"));
			Sms sms = new Sms();
				sms.setPhone(phone);
				sms.setSendTime(DateUtil.getNow());
				sms.setContent(dahanDesc);
				sms.setRespTime(DateUtil.getNow());
				sms.setSmsType(type.toLowerCase());
				if(StringUtils.isNotBlank(verCode)){
					sms.setCode(verCode);
				}
				sms.setOrderNo(dahanMsgId);
				if ("0".equals(dahanResult)) {
					sms.setResp("短信已发送");
					sms.setState("10");
					msgcount = 1;
				}else{
					sms.setResp("短信发送失败");
					sms.setState("20");
					msgcount = 0;
				}
				sms.setVerifyTime(0);
				smsService.saveSmsFromRedis(phone,type,sms);//该方法除了验证码相关的其它都是发送记录
		}else{
			logger.error("ClSmsServiceImpl-resultForDahantc:返回结果为空!"+"-phone:"+phone+"-type:"+type);
		}
		return msgcount;

	}

	/**
	 * 大汉三通短信发送
	 * @author pantheon
	 * @since 20170713
	 * @param mobile 手机号码
	 * @param message 消息内容
	 * @return
	 */
	private String sendCodeForDahantc(String mobile, String message) throws UnsupportedEncodingException {
		message = new String(message.getBytes("UTF-8"),"UTF-8");
		String msg =    URLEncoder.encode(message,"UTF-8");
		String param = "sname="+SEND_SNAME+"&spwd="+SEND_SPWD+"&scorpid=&sprdid="+SEND_SPRDID+"&sdst="+mobile+"&smsg=" +msg;

		return sendSMS(param, SENDURL);


/*		if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(message)){
			logger.error("ClSmsServiceImpl-sendCodeForDahantc-传入参数错误-mobile："+mobile+"-message:"+message);
			return "";
		}

		if("dev".equals(Global.getValue("app_environment"))){
			logger.info("ClSmsServiceImpl-sendCodeForDahantc-测试环境-不发送真实短信-mobile："+mobile+"-message:"+message);
			return "{\"msgid\":\"48d660c4ea7c43b69401ec566e520f7d\",\"result\":\"0\",\"desc\":\"提交成功\",\"failPhones\":\"\"}";
		}

		 String smsURL = Global.getValue("sms_apihost");//大汉三通地址
		 String account = Global.getValue("sms_account");// 用户名（必填）
		 String password = Global.getValue("sms_account_pwd");// 密码（必填）
		 String phone = mobile; // 手机号码（必填,多条以英文逗号隔开）
		 String sign = Global.getValue("sms_sign"); // 短信签名（必填）
		 String subcode = ""; // 子号码（选填）
		 String msgid = UUID.randomUUID().toString().replace("-", ""); // 短信id，查询短信状态报告时需要，（可选）
		 String sendtime = ""; // 定时发送时间（可选）

		String sendhRes = "";
		try {
			String content = message;// 短信内容（必填）

			JSONHttpClient jsonHttpClient = new JSONHttpClient(smsURL);
			jsonHttpClient.setRetryCount(1);
			sendhRes = jsonHttpClient.sendSms(account, password, phone, content, sign, subcode,msgid);
			logger.info("ClSmsServiceImpl-sendCodeForDahantc-提交单条普通短信响应：" + sendhRes);


			// List<SmsData> list = new ArrayList<SmsData>();
			//list.add(new SmsData("13912345678,13112345678",
			//content1, msgid, sign, subcode, sendtime));
			//list.add(new SmsData("18412345678", content2, msgid, sign,
			//subcode, sendtime));
			//String sendBatchRes = jsonHttpClient.sendBatchSms(account,
			//password, list);
			//LOG.info("提交批量短信响应：" + sendBatchRes);
			//
			// String reportRes = jsonHttpClient.getReport(account, password);
			// LOG.info("获取状态报告响应：" + reportRes);
			//
			// String smsRes = jsonHttpClient.getSms(account, password);
			// LOG.info("获取上行短信响应：" + smsRes);

		} catch (Exception e) {
			logger.error("ClSmsServiceImpl-sendCodeForDahantc-应用异常:", e);
		}*/

//		return sendhRes;
	}


	/**
	 * 发送短信
	 * @param postData
	 * @param postUrl
	 * @return
	 */
	public String sendSMS(String postData, String postUrl) {
		try {
			//发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(postData);

			out.flush();
			out.close();

			//获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				logger.debug("connect failed!");
				throw new RuntimeException("短信发送失败");
			}
			//获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			return result;
		} catch (IOException e) {
			//e.printStackTrace(System.out);
			logger.debug("发送短信异常："+e);
			throw new RuntimeException("短信发送失败");
		}
	}

	@Override
	public int findUser(String phone) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginName", phone);
		User user =  userMapper.findSelective(paramMap);
		if (StringUtil.isNotBlank(user)) {
			return 1;
		}
		return 0;
	}

	@Override
	public int loanInform(long userId, long borrowId) {
		Map<String, Object> search = new HashMap<String, Object>();
		User user = userMapper.findByPrimary(userId);
		search.put("borrowId", borrowId);
		Borrow br = clBorrowMapper.findByPrimary(borrowId);
		if (user != null && br != null) {
			SmsTpl tpl = smsService.getSmsTplFromRedis(SmsModel.SMS_TYPE_LOANINFORM);
			if (tpl != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("time", br.getCreateTime());

				String result = null; //loanInform
				try {
					result = sendCodeForDahantc(user.getLoginName(),changeMessage( SmsModel.SMS_TYPE_LOANINFORM, map));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return resultForDahantc(result, user.getLoginName(), SmsModel.SMS_TYPE_LOANINFORM,"");
			}
		}
		return 0;
	}

	@Override
	public int refuse(long userId) {
		SmsTpl smsTpl = smsService.getSmsTplFromRedis(SmsModel.SMS_TYPE_REFUSE); //repayBefore
		if ("10".equals( smsTpl.getState() )) {
			UserBaseInfo bi = userBaseInfoService.findByUserId(userId);
			Map<String,Object> map = new HashMap<>();
			map.put("realName", bi.getRealName());
			String result = null;//refuse
			try {
				result = sendCodeForDahantc(bi.getPhone(),changeMessage( SmsModel.SMS_TYPE_REFUSE,map));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return resultForDahantc(result, bi.getPhone(), SmsModel.SMS_TYPE_REFUSE,"");
		}else {
			logger.error("ClSmsServiceImpl-refuse-未找到对应的模板："+ SmsModel.SMS_TYPE_REFUSE);
		}
		return 0;
	}
	
}