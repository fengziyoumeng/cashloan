package com.rongdu.cashloan.core.constant;


public class AppConstant {

    /**
     * session相关
     */
    //用于结合手机号(用户名)获取ticketId
    public static final String REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA = "APP_LOGIN:TICKET:PHONE_FOR_TICKETDATA_";

    //这是ticketId,用于获取ticketData
    public static final String REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA = "APP_LOGIN:TICKETID:TICKET_FOR_TICKETDATA_";

    /**
     *  通过CODE获取点击流量平台链接的数量
     */
    public static final String FLOW_PALTFORM_AMOUNT = "FLOW:PALTFORM:AMOUNT:";

    /**
     *  CODE获取流量平台链接的地址
     */
    public static final String FLOW_PALTFORM_HTTP = "FLOW:PALTFORM:HTTP:";
    /**
     *  短信相关
     */
    public static final String SMS_TEMPLATE = "SMS:TEMPLATE:";  //短信模版,如：SMS:TEMPLATE:FINDPAY
    public static final String SMS_RECORD = "SMS:RECORD:";      //短信记录，如：SMS:RECORD:REGISTER:18658821091
    public static final String SMS_COUNT = "SMS:COUNT:";        //记录用户一天某种类型短信的请求次数(从第一次该类型短信发送起开始计数)，如SMS:COUNT:REGISTER:18658821091


    /**
     *  APK下载相关
     */
    public static final String APK_DOWNLOAD = "APK:DOWNLOAD:";

    /**
     *  报表相关
     */
    public static final String STATIS_WORKBENCH = "STATIS:WORKBENCH_JJH";   //工作台缓存


    /**
     *  分布式锁相关
     */
    public static final String LOCK_FLOWINFO_COUNT = "LOCK:FLOWINFO:COUNT:";   //工作台缓存


    /**
     *  自定义主键
     */
    public static final String ID_CLUSER = "ID:CLUSER";//自定义主键


    /**
     *  微信相关
	 */
	// 用户微信账户信息在redis中的键值
	public static final String REDIS_KEY_WX_LOGIN_TICKET = "APP_LOGIN_WX:WX_TICKET:TICKET_";

    //ACCESS_TOKEN在redis中的键值
	public static final String REDIS_KEY_WX_LOGIN_TOKEN = "APP_LOGIN_WX:WX_ACCESS_TOKEN:ACCESS_TOKEN_";

	//登录手机验证码(不能用hset,会一起失效的)
	public static final String REDIS_KEY_MOBILE_LOGIN_CHECKCODE = "APP_LOGIN_PHONE:PHONE_CHECKCODE:PHONE_CHECKCODE_";

	//手机注册验证码(不能用hset,会一起失效的)
	public static final String REDIS_KEY_MOBILE_REGISTER_CHECKCODE = "APP_REGISTER_PHONE:PHONE_CHECKCODE:PHONE_CHECKCODE_";


	//每天实名认证统一个IP只能20次
    public static final String REDIS_KEY_LOCK_REALNAME_IP = "LOCK:REALNAME:IP_";

    //每天实名认证统一个手机号只能20次
    public static final String REDIS_KEY_LOCK_REALNAME_MOBILE = "LOCK:REALNAME:MOBILE_";

    //每天短信验证码一个IP只能20次
    public static final String REDIS_KEY_LOCK_CHECKCODE_IP = "LOCK:CHECKCODE:IP_";

    //每天短信验证码一个手机号只能20次
    public static final String REDIS_KEY_LOCK_CHECKCODE_MOBILE = "LOCK:CHECKCODE:MOBILE_";

    //每分钟短信验证码一个手机号只能发送一次
    public static final String REDIS_KEY_LOCK_CHECKCODE_MINUTE_MOBILE = "LOCK:CHECKCODE_MINUTE:MOBILE_";

    //加入计划的锁判断，身份证纬度
    public static final String REDIS_KEY_LOCK_SAVEORDERFUNDS_CARDID = "LOCK:SAVEORDERFUNDS:CARDID:";

    //加入计划的锁判断，手机号纬度
    public static final String REDIS_KEY_LOCK_SAVEORDERFUNDS_MOBILE = "LOCK:SAVEORDERFUNDS:MOBILE:";

    /**
     * app首页火热借款产品缓存页面
     */
    public static final String REDIS_KEY_CASH_FLOW_INFO_HOT ="FLOW:PALTFORM:CACHE:INDEX_HOT";
    /**
     * app热门推荐分页结果数据
     */
    public static final String REDIS_KEY_CASH_FLOW_INFO_HOT_PAGERESULT ="FLOW:PALTFORM:CACHE:INDEX_HOT_PAGE:";

    /**
     * app所有商品缓存（key：FLOW_INFO_ALL_limit值_day值:页码）
     */
    public static final String REDIS_KEY_CASH_FLOW_INFO_ALL = "FLOW:PALTFORM:CACHE:INDEX_ALL";

    /**
     * 借款软件详情redis的key
     */
    public static final String REDIS_KEY_DETAIL_FLOW_INFO = "FLOW:PALTFORM:DETAIL:";

    /**
     * 借款软件借款数（是假的）
     */
    public static final String REDIS_KEY_BORROWNUM_FLOW_INFO = "FLOW:PALTFORM:BORROWNUM:";

    /**
     * 所有首页轮播图
     */
    public static final String REDIS_KEY_PIC_FLOW_INFO = "FLOW:PALTFORM:PIC";

    /**
     * 统计点击次数
     */
    public static final String REDIS_KEY_CLICK_FLOW_INFO = "FLOW:PALTFORM:CLICK:";

    /**
     * 获取产品用户浏览数
     */
    public static final String REDIS_KEY_CLICK_BDATA_PROD_INFO = "BDATA:PRODFORM:CLICK:";

    /**
     * 广播信息
     */
    public static final String REDIS_KEY_RADIO_FLOW_INFO = "FLOW:PALTFORM:RADIO:";

    /**
     * 所有列表信息缓存
     */
    public static final String REDIS_KEY_LIST_ALL ="FLOW:PALTFORM:LIST:";

    /**
     * 点击记录轨迹
     */
    public static final String REDIS_KEY_CLICK_TRACK = "FLOW:TRACK:CLICK:";

    /**
     * 产品id和name关联/
     */
    public static final String REDIS_KEY_LIAN_PRODUCT = "FLOW:LIAN:PRODUCT:";
}
