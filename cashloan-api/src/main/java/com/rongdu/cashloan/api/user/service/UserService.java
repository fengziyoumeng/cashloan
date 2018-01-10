package com.rongdu.cashloan.api.user.service;

import com.rongdu.cashloan.api.user.bean.AppDbSession;
import com.rongdu.cashloan.api.util.Des3Util;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.model.SmsModel;
import com.rongdu.cashloan.cl.service.*;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.cl.vo.ChannelForH5;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.util.code.MD5;
import com.rongdu.cashloan.core.domain.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tool.util.BeanUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by lsk on 2016/7/27.
 */
@Service("clUserService_")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AppDbSession appDbSession;

    @Resource
    protected MybatisService mybatisService;

    @Resource
    protected SmsService smsService;

    @Resource
    private UserEquipmentInfoService userEquipmentInfoService;

    @Resource
    private UserAuthService userAuthService;

    @Resource
    private ChannelService channelService;

    @Resource
    private ClFlowInfoMapper clFlowInfoMapper;


    public UserService() {
        super();
    }

    /**
     * 注册controller
     * @param request
     * @param phone
     * @param pwd
     * @param vcode
     * @param regClient
     * @param channelCode
     * @param mobileType
     * @return
     */
    public Map registerUser(HttpServletRequest request, String phone, String pwd, String vcode
            ,String regClient, String channelCode,String mobileType,String byInvitationCode) {
        try {
            if (StringUtil.isEmpty(phone) || !StringUtil.isPhone(phone) || StringUtil.isEmpty(pwd) || StringUtil.isEmpty(vcode) || pwd.length() < 6) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "参数有误");
                return ret;
            }


            //去掉当日最大注册数限制
//            CloanUserService cloanUserService = (CloanUserService) BeanUtil.getBean("cloanUserService");
//            long todayCount = cloanUserService.todayCount();
//            String dayRegisterMax_ = Global.getValue("day_register_max");
//            if(StringUtil.isNotBlank(dayRegisterMax_)){
//                int dayRegisterMax = Integer.parseInt(dayRegisterMax_);
//                if(dayRegisterMax > 0 && todayCount >= dayRegisterMax){
//                    Map ret = new LinkedHashMap();
//                    ret.put("success", false);
//                    ret.put("msg", "今日注册用户数已达上限，请明日再来");
//                    return ret;
//                }
//            }

            ClSmsService clSmsService = (ClSmsService)BeanUtil.getBean("clSmsService");
            int results = clSmsService.verifySms(phone, SmsModel.SMS_TYPE_REGISTER, vcode);
            String vmsg;
            if (results == 1) {
                vmsg = null;
            }else if(results == -1){
                vmsg="验证码已过期";
            }else {
                vmsg="手机号码或验证码错误";
            }
            if (vmsg != null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", vmsg);
                return ret;
            }

            Map old = mybatisService.queryRec("usr.queryUserByLoginName", phone);
            if (old != null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "该手机号码已被注册");
                return ret;
            }

            // 渠道
            Long channelId = channelService.findIdByCode(channelCode);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            Map inserMap = new HashMap<String,Object>();
            inserMap.put("loginName",phone);
            inserMap.put("loginPwd",pwd);
            inserMap.put("uuid",uuid);
            inserMap.put("channelCode",channelCode);
            inserMap.put("invitationCode",randomInvitationCode(6));//随机生成6位用户表里没有的字符串，自己生成的专属邀请码
            inserMap.put("byInvitationCode",byInvitationCode);//被邀请码，上级代理的邀请码
            inserMap.put("registTime",new Date());
//            inserMap.put("uuid",uuid);
//            inserMap.put("level",3);
            inserMap.put("registerClient",regClient);
            inserMap.put("channelId",channelId);
            int count = mybatisService.insertSQL("usr.saveClUser", inserMap);
            long userId = inserMap.get("id")==null?-1L: Long.parseLong( inserMap.get("id").toString());//该id值由
            logger.info("========>注册时插入"+count+"条cl_user条记录，主键="+userId);

            inserMap.clear();
            inserMap.put("userId",userId);
            inserMap.put("phone",phone);
            //将昵称默认设为手机号
            inserMap.put("realName",phone);
            inserMap.put("state","20");
            int count1 = mybatisService.insertSQL("usr.saveClUserBaseInfo", inserMap);


            Map result = new LinkedHashMap();
            result.put("success", true);
            result.put("msg", "注册成功");
            return result;
        } catch (Exception e) {
            logger.error("注册失败", e);
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", "注册失败");
            return ret;
        }
    }
    public  int upName(long userId,String realName){
        Map map=mybatisService.queryRec("usr.queryUserByUserId",userId);
        Map updateMap = new HashMap<String,Object>();
        updateMap.put("userId",userId);
        updateMap.put("realName",realName);
        return mybatisService.updateSQL("usr.updateBaserInfoByUserId",updateMap);
    }



    private String randomInvitationCode(int len) {
        while (true) {
            String str = randomNumAlph(len);
            if (mybatisService.queryRec("usr.queryUserByInvitation", str) == null) {
                return str;
            }
        }
    }

    private static String randomNumAlph(int len) {
        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        byte[][] list = {
                {48, 57},
                {97, 122},
                {65, 90}
        };
        for (int i = 0; i < len; i++) {
            byte[] o = list[random.nextInt(list.length)];
            byte value = (byte) (random.nextInt(o[1] - o[0] + 1) + o[0]);
            sb.append((char) value);
        }
        return sb.toString();
    }


    public Object forgetPwd(String phone, String newPwd, String vcode,String signMsg,String mobileType) {

        if (!StringUtil.isEmpty(vcode)) {
            String msg = smsService.validateSmsCode(phone, SmsModel.SMS_TYPE_FINDREG , vcode);
            if (msg != null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", msg);
                return ret;
            }
        }

//            if (dbService.update(SqlUtil.buildUpdateSql("cl_user", MapUtil.array2Map(new Object[][]{
//                {"login_name", phone},
//                {"login_pwd", newPwd},
//                {"loginpwd_modify_time", new Date()}
//            }), "login_name")) == 1)
        Map updateMap = new HashMap<String,Object>();
        //H5传值过来base64,des解密，再md5加密
        if(Constant.H5_MOBILE_TYPE.equals(mobileType)){
            newPwd= UserService.handlePwd(mobileType,newPwd);
        }
        updateMap.put("loginName",phone);
        updateMap.put("loginPwd",newPwd);
        updateMap.put("loginpwdModifyTime",new Date());
        int modifyCount = mybatisService.updateSQL("usr.updateClUser", updateMap);
        if(modifyCount==1)
        {
            //修改成功后删除
            smsService.delSmsFromRedis(phone, SmsModel.SMS_TYPE_FINDREG);
            Map ret = new LinkedHashMap();
            ret.put("success", true);
            ret.put("msg", "密码已修改");
            return ret;
        } else {
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", "密码修改失败");
            return ret;
        }

    }

    public Map  login(final HttpServletRequest request, final String loginName, final String loginPwd,String signMsg) {
        try {
            String mobileType = request.getParameter("mobileType");
            //如果参数类型为3暂时不走签名验签
//            if(!Constant.H5_MOBILE_TYPE.equals(mobileType)){
//                String _signMsg = MD5.encode(Global.getValue("app_key") + loginName + loginPwd);
//                if (!_signMsg.equalsIgnoreCase(signMsg)) {
//                    Map ret = new LinkedHashMap();
//                    ret.put("success", false);
//                    ret.put("msg", "签名验签不通过");
//                    return ret;
//                }
//            }

            if ( StringUtil.isBlank(mobileType)) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "缺少平台类型参数");
                return ret;
            }

            Map user = mybatisService.queryRec("usr.queryUserByLoginName", loginName);
            if (user == null) {
                Map ret = new LinkedHashMap();
                ret.put("success", false);
                ret.put("msg", "账户不存在");
                return ret;
            }

            String dbPwd = (String) user.get("login_pwd");
            String md5Str=handlePwd(mobileType,loginPwd);
            //md5Str.equalsIgnoreCase(dbPwd)与数据库进行比较，仅针对H5做的一个判断，对其他功能无影响
            if (dbPwd.equalsIgnoreCase(loginPwd)||md5Str.equalsIgnoreCase(dbPwd)) {

                Ticket session = appDbSession.create(request, loginName, Integer.parseInt( mobileType));

                Map map = new HashMap();
                map.put("userId",session.getUserId());
                map.put("token",session.getTicketId());

                //userEquipmentInfoService.save(loginName,blackBox);
                Map ret = new LinkedHashMap();
                ret.put("success", true);
                ret.put("msg", "登录成功");
                ret.put("data", map);
                return ret;
            }
            Map ret = new LinkedHashMap();
            ret.put("success", false);
            ret.put("msg", "密码错误");
            return ret;
        } catch (Exception e) {
            logger.error("登录异常", e);
            Map ret = new LinkedHashMap();
            ret.put("code", 500);
            ret.put("msg", "系统异常");
            return ret;
        }
    }
    public static String handlePwd(String mobileType,String loginPwd){// 3    06C75E043C86CAACE9BCCB283D97730E
        String  md5Str="";
        //根据H5传过来的类型，进行判断，解密后再与数据库中的密码进行比较
        if(Constant.H5_MOBILE_TYPE.equals(mobileType)){
            try {
             Des3Util desObj = new Des3Util();
                System.out.println("loginPwd>>>>>"+loginPwd);
//           //安全key
             String key1 = "pasec23der1e12ecjer3AFRGYD03847@@*^DEJUsdu33";
//           //先Base64解密
            byte[] decodeLoginPwd =  com.rongdu.cashloan.api.util.Base64.decode(loginPwd);
            //再Des3Util解密
                String dec3LoginPwd = desObj.strDec(new String(decodeLoginPwd), key1, null, null);
                System.out.println();
            //最后MD5加密后与数据库密码进行比较
            md5Str= MD5.encode(dec3LoginPwd);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5Str;
    }
}