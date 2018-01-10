package com.rongdu.cashloan.cl.service;

import com.rongdu.cashloan.cl.domain.Sms;
import com.rongdu.cashloan.cl.domain.SmsTpl;
import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.exception.BaseRuntimeException;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tool.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by lsk on 2017/2/15.
 */
@Service
public class SmsService {

    @Autowired
    private ShardedJedisClient redisClient;


    /**
     * 短信验证
     * @param phone
     * @param type
     * @param vcode
     * @return
     */
    public String validateSmsCode(String phone, String type, String vcode) {
    	if("dev".equals(Global.getValue("app_environment")) && "0000".equals(vcode)){
            return null;
        }

        Sms sms = getSmsFromRedis(phone,type);
        if (sms == null) {
            return "验证码已失效";
        }

        //限制只能发送10次
        int verifyTime = sms.getVerifyTime();     //sms.get("verify_time").toString();
        if (verifyTime>10) {
        	return "验证码已失效";
		}

        //验证码的失效时间靠redis控制
        if (!vcode.equals(sms.getCode())) {
            return "验证码错误";
        }
        return null;
    }

    public String sendSmsByTpl(HttpServletRequest request, String phone, String smsType, Object... params) {
        SmsTpl smsTpl = getSmsTplFromRedis(smsType);
        if (smsTpl == null) {
            throw new BaseRuntimeException("没有找到短信模板:" + smsType);
        }
        String templ = null;
        //只有启用的模版才发送短信
        if("10".equals(smsTpl.getState() )){
            templ= smsTpl.getTpl();
            for (int i = 0; i < params.length; i++) {
                Object value = params[i];
                if (value == null) {
                    value = "";
                } else {
                    value = value.toString();
                }
                templ = templ.replaceFirst("\\{}", (String) value);
            }
            sendSms(phone, templ, smsType);
        }
        return templ;
    }

    @SuppressWarnings("unchecked")
	public void sendSms(String phone, String content, String smsType) {
        String result = "短信已发送";
        Sms sms = new Sms();
        sms.setSendTime(new Date());
        sms.setPhone(phone);
        sms.setContent(content);
        sms.setRespTime(new Date());
        sms.setResp(result);
        sms.setSmsType(smsType.toLowerCase());
        saveSmsFromRedis(phone,smsType,sms);
    }


   public Sms getSmsFromRedis(String phone, String type){
       type = StringUtil.upperCase(type);
       String key = AppConstant.SMS_RECORD+type+":"+ phone;
       Sms sms = null;
       //查找该用用户的sms记录
       if( redisClient.exists(key)){
           sms = (Sms) redisClient.getObject( key);
       }
       return sms;
   }

    /**
     * 删除验证码
     * @param phone
     * @param type
     */
    public void delSmsFromRedis(String phone, String type){
        type = StringUtil.upperCase(type);
        String key = AppConstant.SMS_RECORD+type+":"+ phone;
        type = StringUtil.upperCase(type);
        Sms sms = null;
        //查找该用用户的sms记录
        if( redisClient.exists(key)){
             redisClient.delObject( key);
        }
    }


    /**
     * 获取某种类型短信发送的次数
     * @param phone
     * @param type
     * @return
     */
    public int getSmsCountFromRedis(String phone, String type){
        type = StringUtil.upperCase(type);
        int count = 0;
        String key = AppConstant.SMS_COUNT+type+":"+ phone;
        //该用户某种短信类型一天内调用次数（以第一次调用时间为起始）
        if( redisClient.exists(key)){
            String tempCount  = redisClient.get(key);
            if(!StringUtils.isBlank(tempCount)){
                count = Integer.parseInt(tempCount);
            }
        }
        return count;
    }

    /**
     * 更新或者插入每天验证码失效时间
     * @param phone
     * @param type
     * @return
     */
    public int updateAndSaveSmsCountFromRedis(String phone, String type){
        type = StringUtil.upperCase(type);
        int count = 1;
        String key = AppConstant.SMS_COUNT+type+":"+ phone;
        int smsTimeLimit =  getsmsTimeLimit();
        //该用户某种短信类型一天内调用次数（以第一次调用时间为起始）
        if( redisClient.exists(key)){
            long ttl = redisClient.ttl(key);
            long tempCount = redisClient.incr(key,smsTimeLimit-Integer.parseInt(ttl+""));
            count =  Integer.parseInt(tempCount+"");
        }else{
            redisClient.set(key,"1",smsTimeLimit);
            count = 1;
        }
        return count;
    }

    /**
     * 插入sms记录
     * @param phone
     * @param type
     * @param sms
     */
    public void saveSmsFromRedis(String phone, String type,Sms sms){
        type = StringUtil.upperCase(type);
        int smsTimeLimit =  getsmsTimeLimit();
        int verifyTime = 0;
        if(redisClient.exists(AppConstant.SMS_RECORD+type+":"+ phone)){
            Sms oldSms =(Sms)redisClient.getObject(AppConstant.SMS_RECORD+type+":"+ phone);
            verifyTime = oldSms.getVerifyTime()+1;
        }
        sms.setVerifyTime(verifyTime);
        if(SmsModel.SMS_TYPE_REGISTER.equals(type) || SmsModel.SMS_TYPE_FINDPAY.equals(type) || SmsModel.SMS_TYPE_FINDREG.equals(type)){
            //注册、找回登录密码、找回支付密码一般几分钟就失效了
            redisClient.setObject(AppConstant.SMS_RECORD+type+":"+ phone,sms,smsTimeLimit);
        }else { //非验证码类型短信
            int expireTime = Global.getInt("sms_record_expire_time"); //短信记录过期时间
            expireTime = expireTime*84600;  //一天84600秒
            redisClient.setObject(AppConstant.SMS_RECORD+type+":"+ phone,sms,expireTime);
        }


    }

    /**
     * 获取sms模版
     * @param type
     * @return
     */
    public SmsTpl getSmsTplFromRedis(String type){
        type = StringUtil.upperCase(type);
        SmsTpl smsTpl = null;
       //查找该用用户的sms记录
       if( redisClient.exists(AppConstant.SMS_TEMPLATE+ type)){
           //因为sms模版会经常变动，为了在redis中可以方便更改所以smstpl在redis中定义为hash类型
           String typeName = redisClient.hget(AppConstant.SMS_TEMPLATE+ type,"type_name");
           String tpl = redisClient.hget(AppConstant.SMS_TEMPLATE+ type,"tpl");
           String number = redisClient.hget(AppConstant.SMS_TEMPLATE+ type,"number");
           String state = redisClient.hget(AppConstant.SMS_TEMPLATE+ type,"state");
           smsTpl = new SmsTpl();
           smsTpl.setType(type);
           smsTpl.setTpl(tpl);
           smsTpl.setNumber(number);
           smsTpl.setState(state);
           smsTpl.setTypeName(typeName);
       }
       return smsTpl;
   }

    /**
     * 验证码失效时间
     * @return
     */
   public int getsmsTimeLimit(){
      int smsTimeLimit =  Global.getInt("sms_time_limit");
      if(smsTimeLimit == 0){
          smsTimeLimit = 300; //默认5分钟失效
      }else {
          smsTimeLimit = smsTimeLimit * 60;
      }
      return smsTimeLimit;
   }


}
