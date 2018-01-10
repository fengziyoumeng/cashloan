package com.rongdu.cashloan.api.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rongdu.cashloan.api.controller.WeChatController;

import java.util.*;

/**
 * Created by admin on 2017/9/3.
 */
public class WeixinUtil {
    public static       String TOKEN               = "lqq566";
    //获取基础ACCESSTOKEN的url
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //APPID
    public static final String APPID               = "wx00841e8ba2cff430";
    //SECRET
    public static final String SECRET              = "a8ce65f1b196f440ec426b7bbbdbde47";
    //对全局接口凭证进行缓存,不用每次都去获取
    public static String accessToken;
    //失效时长
    public static       Long   expiresTime     = 0L;
    //创建菜单的utl
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //获取用户信息的URL
    public static final String GET_USERINFO_URL ="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //发送模板消息的URL
    public static final String SEND_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    //网页授权
    public static final String  SNSAPI_USERINFO = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //通过网页获取用户信息的URL(不需要关注公众号)
    public static final String GET_WEB_USERINFO_URL ="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    //获取jssdk使用的ticket
    public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    public static final String GET_IMAGEURL_RUL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";




    //创建菜单之前必须先获取Access_Token,失效时间为两个小时
    public static String getAccessToken() {
        System.out.println("accessToken>>>>"+accessToken);
        //如果AccessToken为null或失效时间小于当前时间,则需要重新更新accessToken
        if (accessToken == null || expiresTime < new Date().getTime()) {
            //获取access_Token字符串
            String result = HttpUtil.get(GET_ACCESSTOKEN_URL.replace("APPID", APPID).replace("APPSECRET", SECRET));
            //转为json对象
            JSONObject jsonObject = JSON.parseObject(result);
            //获取全局接口调用凭证
            accessToken = jsonObject.getString("access_token");
            //有效时长
            expiresTime = jsonObject.getLong("expires_in");
            //失效时间
            expiresTime = new Date().getTime() + (expiresTime - 10) * 1000;
        }
        return accessToken;

    }

    //获取ticket
    public static JSONObject getJsApiTicket(){
        String requestUrl = GET_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
        String jsApiTicket = HttpUtil.get(requestUrl);
        JSONObject result = JSONObject.parseObject(jsApiTicket);
        return result;
    }

    //字节数组转换为十六进制字符串
    public static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    //生成随机字符串
    public static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    //生成时间戳
    public static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /*自定义创建菜单*/
    public static void createMenu2(Object buttonResult) {
        String s = JSON.toJSONString(buttonResult);
        System.out.println(s);
        HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN", getAccessToken()), s);
    }

    //创建菜单
    public static void createMenu() {
        //发送post请求,获取菜单
        String result = HttpUtil.post(CREATE_MENU_URL.replace("ACCESS_TOKEN", getAccessToken())," {\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"官网\",\n" +
                "               \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx08601114c18175fd&redirect_uri=http://chenyongqiang.nat300.top/index.do&response_type=code&scope=snsapi_userinfo#wechat_redirect\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }");
        System.out.println(result);

    }
    //发送模板消息
    public static void sendTemplate() {
        //发送post请求,发送模板消息
        String result = HttpUtil.post(SEND_TEMPLATE.replace("ACCESS_TOKEN", getAccessToken())," {\n" +
                "           \"touser\":\"oZUHmwgKvZSqayEVhfFhw-Zyhkg4\",\n" +
                "           \"template_id\":\"LF9J0xRV33DZPlUy75VyXF7sJ3Dh0I3ww8wT7Dzl-FI\",\n" +
                "           \"url\":\"http://www.520it.com\", \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"巧克力\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"39.8元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }");
        System.out.println(result);

    }

    public static void main(String[] args) {
        accessToken = getAccessToken();
        System.out.println("accessToken>>>>>>>>>>>>"+accessToken);
       /* Map<String, String> stringStringMap = new WeChatController().makeWXTicket(null);
        Collection<String> values = stringStringMap.values();
        for (String value : values) {
            System.out.println(value);
        }*/

//        JSONObject jsApiTicket = getJsApiTicket();
//        System.out.println(accessToken);
//        System.out.println("jsApiTicket>>"+jsApiTicket.getString("ticket"));
//        createMenu();
//        String temp = HttpUtil.get(WeixinUtil.GET_TICKET_URL.replace("ACCESS_TOKEN", getAccessToken()));
//        System.out.println(temp);

    }
}
