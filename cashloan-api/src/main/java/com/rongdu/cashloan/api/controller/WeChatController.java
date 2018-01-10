package com.rongdu.cashloan.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.api.util.SecurityUtil;
import com.rongdu.cashloan.api.util.WeixinUtil;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.rongdu.cashloan.api.util.WeixinUtil.*;

/**
 * Created by admin on 2017/9/3.
 */

/**
 * url验证采用get请求
 * 消息处理采用post请求
 */
@Scope("prototype")
@Controller
public class WeChatController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatController.class);


    //生成微信权限验证的参数
    @RequestMapping("/api/info/getwxticket.htm")
    public void makeWXTicket(HttpServletResponse response, String url) {
        WeixinUtil.getAccessToken();
        JSONObject jsApiTicket1 = WeixinUtil.getJsApiTicket();
        System.out.println(">>>>>>>>>>>>>>>>"+jsApiTicket1.getString("ticket"));

        Map<String, String> ret = new HashMap<String, String>();
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";
        String jsApiTicket = jsApiTicket1.getString("ticket");
        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        logger.info("String1=====>"+string1);
        try{
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
            logger.info("signature=====>"+signature);
        }
        catch (NoSuchAlgorithmException e) {
            logger.error("WeChatController.makeWXTicket=====Start");
            logger.error(e.getMessage(),e);
            logger.error("WeChatController.makeWXTicket=====End");
        }
        catch (UnsupportedEncodingException e){
            logger.error("WeChatController.makeWXTicket=====Start");
            logger.error(e.getMessage(),e);
            logger.error("WeChatController.makeWXTicket=====End");
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("appid", WeixinUtil.APPID);

        Map<String,Object> result = new HashMap<String,Object>();
        try {
            result.put(Constant.RESPONSE_DATA, ret);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }


    //微信接入
    /*@RequestMapping(value = "/weixin", method = RequestMethod.GET)
    @ResponseBody
    public String checkSignature(String signature, String timestamp, String nonce, String echostr) {
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        //加密/校验流程如下
        String[] arr = {WeixinUtil.TOKEN, timestamp, nonce};
        //将token,timestamp,nonce三个参数进行字典序排序
        Arrays.sort(arr);
        //将三个参数字符串拼接成一个字符串进行sha1加密
        String str = "";
        for (String temp : arr) {
            str += temp;
        }
        //开发者获取加密后的字符串可与signature对比标识该请求来源于微信
        if (signature.equals(SecurityUtil.SHA1(str))) {
            System.out.println("接入成功");
            return echostr;
        }
        System.out.println("接入失败");
        return null;
    }*/

}
