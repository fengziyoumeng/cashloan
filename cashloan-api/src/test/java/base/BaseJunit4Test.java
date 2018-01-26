package base;

import com.rongdu.cashloan.api.user.service.UserService;
import com.rongdu.cashloan.api.util.Des3Util;
import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.domain.CompanyInformation;
import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.service.ICompanyInfomationService;
import com.rongdu.cashloan.cl.service.FlowPicService;
import com.rongdu.cashloan.cl.service.IBankInfoService;
import com.rongdu.cashloan.cl.service.impl.JipushService;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.core.aliyun.AliYunUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@ContextConfiguration(locations={"classpath:/config/spring/*-beans.xml"})//加载spring配置文件
public class BaseJunit4Test implements ApplicationContextAware {

    public ApplicationContext  applicationContext;
    @Resource(name = "clUserService_")
    private UserService userService;
    @Autowired
    private ShardedJedisClient redisClient;
    @Resource
    private FlowPicService flowPicService;
    @Resource
    private IBankInfoService bankInfoService;
    @Resource
    protected MybatisService mybatisService;
    @Resource
    protected ICompanyInfomationService companyInfoService;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
   @Test
    public void test1() {
      int i=  userService.upName(80526,"2张三");
        System.out.println("i = " + i);
    }
    @Test
    public void test2(){
        List<FlowPic> pics = flowPicService.getPic(1);
        System.out.println("pics = " + pics);
    }
    @Test
    public void test3(){
        String md5Str= UserService.handlePwd("3","");
        System.out.println("md5Str = " + md5Str);
    }
    @Test
    public  void test4(){
        File file = new File("D:\\yongqianbei_workspace\\cashloan_rongdu\\testWl.png");
        //AliYunUtil.uploadFile(MediaType.IMG,file);
        String x = AliYunUtil.uploadH5File("image/", "ssss.png", file);
        System.out.println("x = " + x);
    }
    @Test
    public void test5() {
        int amount = 0;
        String now = DateTime.now().toString("yyyy-MM-dd");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "FINDREG", "number", "SMS2077735631");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "FINDREG", "tpl",   "您的找回登陆密码的验证码是:%s请在1分钟内输入【急借号】");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "FINDREG", "state",   "10");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "FINDREG", "type_name",   "找回登陆密码");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "REGISTER", "number", "SMS0012139737");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "REGISTER", "tpl",   "您的注册验证码是:%s请在1分钟内输入【急借号】");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "REGISTER", "state",   "10");
        redisClient.hset(AppConstant.SMS_TEMPLATE + "REGISTER", "type_name",   "注册验证码");
    }
    @Test
    public void test6() {
        try {
            Des3Util desObj = new Des3Util();
            String str = "06C75E043C86CAACE9BCCB283D97730E";
            String key1 = "pasec23der1e12ecjer3AFRGYD03847@@*^DEJUsdu33";
            byte[] decodeLoginPwd =  com.rongdu.cashloan.api.util.Base64.decode(str);
            System.out.println(decodeLoginPwd);
            String ss = new String(decodeLoginPwd,"utf-8");
            System.out.println(ss);
//            String dec3LoginPwd = desObj.strDec(ss, key1, null, null);
//            System.out.println(dec3LoginPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBankInfo() {
        try {
            List<BankInfo> bankInfoList = bankInfoService.getListByType(1L);
            for (BankInfo bankInfo : bankInfoList) {
                System.out.println(bankInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegister(){
        Map inserMap = new HashMap<String,Object>();
        inserMap.put("loginName","987654321");
        inserMap.put("loginPwd","123");
        inserMap.put("uuid","77777777");
        inserMap.put("channelCode","1");
//            inserMap.put("invitationCode",randomInvitationCode(6));
        inserMap.put("registTime",new Date());
//            inserMap.put("uuid",uuid);
//            inserMap.put("level",3);
        inserMap.put("registerClient","测试");
        inserMap.put("channelId","1");
        int count = mybatisService.insertSQL("usr.saveClUser", inserMap);
    }

    @Test
    public void testSetNx(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        String s = format.format(calendar.getTime());
        boolean b = redisClient.setnx("monthQuartz","MonthQuartz:"+s);
    }

    @Test
    public void testSend(){
        JipushService jipushService = new JipushService();
//        int i = jipushService.sendToAllAndroid
//                ("所有安卓","所有安卓副标题","所有安卓接受内容","所有安卓扩展字段");
        int j = jipushService.sendToAll
                ("所有平台","所有平台副标题","所有平台接受内容","所有平台扩展字段");
    }

    public static void main(String[] args) {
        JipushService jipushService = new JipushService();

        // 发送给所有用户
        jipushService.sendToAll("所有平台","所有平台副标题","所有平台接受内容","所有平台扩展字段");

//        // 推送给设备标识参数的用户
//        List<String> aliasList1 = new ArrayList<>();
//        aliasList1.add("");
//        jipushService.sendToAliasList(aliasList1,"所有平台","所有平台副标题","所有平台接受内容","所有平台扩展字段");
//
//        // 推送给设备标识参数的用户
//        List<String> aliasList2 = new ArrayList<>();
//        aliasList2.add("");
//        jipushService.sendToTagList(aliasList2,"所有平台","所有平台副标题","所有平台接受内容","所有平台扩展字段");
    }

    @Test
    public void testvd() {
        ArrayList<BankInfo> list = new ArrayList<>();
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        list.add(new BankInfo());
        System.out.println("原始数量"+list.size());

        int m = 4;

        List<BankInfo>[] aa = new List[m];
        for (int i = 0; i < aa.length; i++) {
            aa[i] = new ArrayList<>();
        }

        for (int i = 0; i <list.size() ; i++) {
            aa[i%m].add(list.get(i));
        }

        for (int i = 0; i <aa.length ; i++) {
            System.out.println("分配"+aa[i].size());
        }
    }

    @Test
    public void testDeleteRedis(){
        String loginName = "18071418791";
        System.out.println(loginName);

        String TicketId = redisClient.get(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA+loginName);
        System.out.println("ticketid>>>>>>>>>"+TicketId);

        boolean exists = redisClient.exists(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + loginName);
        System.out.println("是否存在: "+exists);
        if(exists){
            long del = redisClient.del(AppConstant.REDIS_KEY_LOGIN_PHONE_FOR_TICKETDATA + loginName);
            System.out.println("del>>>>>>>>>>>"+del);
        }

        boolean obj = redisClient.exists(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + TicketId);
        System.out.println("是否存在: "+obj);
        if(obj){
            long l = redisClient.delObject(AppConstant.REDIS_KEY_LOGIN_TICKETID_FOR_TICKETDATA + TicketId);
            System.out.println("l>>>>>>>>>>>"+l);
        }
    }

    @Test
    public void testauditCompany(){
        CompanyInformation companyInfo = new CompanyInformation();
//        companyInfo.setId(3L);
        companyInfo.setCompanyName("急借号");
        companyInfo.setCompanyAddress("ksadfkajjdfa杭州...送");
        companyInfo.setRegisteredCapital("10000万");
        companyInfo.setUserId(11L);
        companyInfo.setLegalPersonName("skdfjk");
        companyInfo.setIDNumber("362228199308250317");
        companyInfo.setContactPerson("qiao");
        companyInfo.setContactTel("17752558907");
        companyInfo.setIntroduction("加上地方就会时代峻峰好,阿萨德快放假啊按时打卡交罚款,阿斯顿开飞机.");
        companyInfo.setLicensePic("http://116.62.174.111:8885/static/pic/banner2.jpg");
        companyInfo.setIdentityFrontPic("http://116.62.174.111:8885/static/pic/banner2.jpg");
        companyInfo.setIdentityReversePic("http://116.62.174.111:8885/static/pic/banner2.jpg");
        companyInfo.setHoldCardPic("http://116.62.174.111:8885/static/pic/banner2.jpg");
        try {
            companyInfoService.saveOrUpdate(companyInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}