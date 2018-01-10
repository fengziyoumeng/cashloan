package rdb;



import base.BaseJunit4Test;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.mapper.SmsMapper;
import com.rongdu.cashloan.cl.mapper.SystemCountMapper;
import com.rongdu.cashloan.cl.mapper.UserAuthMapper;

import com.rongdu.cashloan.cl.service.*;

import com.rongdu.cashloan.core.redis.ShardedJedisClient;

import com.rongdu.cashloan.core.service.CloanUserService;
import com.rongdu.cashloan.core.service.impl.CloanUserServiceImpl;
import com.rongdu.cashloan.system.model.SysAccessCodeModel;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.fop.fo.pagination.Flow;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class SpringRDBTest extends BaseJunit4Test {

    private Logger logger = LoggerFactory.getLogger(SpringRDBTest.class);



    @Autowired
    private UserAuthMapper UserAuthMapper;



    @Autowired
    private SystemCountMapper systemCountMapper;




    @Autowired
    private ClBorrowService clBorrowService;


    @Autowired
    private ShardedJedisClient redisClient;

    @Autowired
    private SmsMapper smsMapper;


    @Autowired
    private ClSmsService clSmsService;

    @Autowired
    private CloanUserService cloanUserService;

//    @Autowired
//    private SysConfigService sysConfigService;


    @Before
    public void initConfig(){
//        Map<String, Object> configMap = new HashMap<String, Object>();
//        List<SysConfig> sysConfigs = sysConfigService.findAll();
//        for (SysConfig sysConfig : sysConfigs) {
//            if (null != sysConfig && StringUtil.isNotBlank(sysConfig.getCode())) {
//                configMap.put(sysConfig.getCode(), sysConfig.getValue());
//            }
//        }
//        Global.configMap = new HashMap<String, Object>();
//        Global.configMap.putAll(configMap);

    }


    @Test
    public void test(){





//        String xmlString = "<CSubmitState xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://tempuri.org/\">" +
//                "<State>0</State>" +
//                "<MsgID>1711151918413997262</MsgID>" +
//                "<MsgState>提交成功</MsgState>" +
//                "<Reserve>1</Reserve>" +
//                "</CSubmitState>";
//
//        XMLSerializer xmlSerializer = new XMLSerializer();
//        JSON json = xmlSerializer.read(xmlString);
//        System.out.println("=====>");

//       String ss =  InviteCodeUtil.toSerialCode(12345671L);
//        System.out.println("=================================>"+ss);


//        long leftSeconds = DateUtil.getLeftSeconds();
//        //redisClient.set(AppConstant.BORROW_DAY_TIMES+1111111,"3",(int)leftSeconds);
//        long tempLong = redisClient.decr(AppConstant.BORROW_DAY_TIMES+1111111,(int)leftSeconds);
//        System.out.println("=========>"+tempLong);


    }


    @Test
    public void MxBasic() {
        Long CallCntCount = 10L;
        Integer contactCallCnt=0;
        Integer contactCallCnt0=10;



//        Map<String, Object> orderMap = new HashMap<>();
//        orderMap.put("borrowId",113443  );
//        UrgeRepayOrder order = urgeRepayOrderService.findOrderByMap(orderMap);

        System.out.println("推送给闲钱宝成功");



//        for(int i =0 ;i<5;i++){
//            try {
//                TimeUnit.SECONDS.sleep(3);
//                boolean ishandle = clBorrowService.borrowLoanSentryFundsLock(null,new Date());
//                System.out.println("处理结果："+ishandle);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

//        ApplicationContext ctx = getApplicationContext();
//        String[] beanNames =  ctx.getBeanDefinitionNames();
//        System.out.println("所以beanNames个数："+beanNames.length);
//        for(String bn:beanNames){
//            System.out.println("=======>"+bn);
//        }

//        Map inserMap = new HashMap<String,Object>();

//        rDBUserMapper.insertOne(new UserEntity(1, "user1", 1));  //db_1.user_1
//        rDBUserMapper.insertOne(new UserEntity(2, "user2", 2));  //db_0.user_0
//
//        rDBUserMapper.insertOne(new UserEntity(3, "user3", 3));
//        rDBUserMapper.insertOne(new UserEntity(4, "user4", 4));
//        rDBUserMapper.insertOne(new UserEntity(5, "user5", 5));
//        rDBUserMapper.insertOne(new UserEntity(6, "user6", 6));
//        rDBUserMapper.insertOne(new UserEntity(7, "user7", 7));
//        rDBUserMapper.insertOne(new UserEntity(8, "user8", 8));
//
//        rDBUserMapper.insertOne(new UserEntity(9, "user9", 9));
//        rDBUserMapper.insertOne(new UserEntity(10, "user10", 10));
//        rDBUserMapper.insertOne(new UserEntity(11, "user11", 11));
//        rDBUserMapper.insertOne(new UserEntity(12, "user12", 12));
//        rDBUserMapper.insertOne(new UserEntity(13, "user13", 13));
//        rDBUserMapper.insertOne(new UserEntity(14, "user14", 14));
//
//
//        rDBUserItemMapper.insertOne(new UserItemEntity(1, "useritem1", 11));  //db_1.user_1
//        rDBUserItemMapper.insertOne(new UserItemEntity(2, "useritem2", 12));  //db_0.user_0

//          System.out.println(rDBUserMapper.selectJoin(1));
//        System.out.println(rDBUserMapper.selectByPk(2));
//
//          List<UserEntity> list = rDBUserMapper.selectMap();
//          for (UserEntity u : list){
//              System.out.println(u.getName());
//          }

//        List<UserAuthModel> userAuthList = UserAuthMapper.listUserAuthModel(new HashMap<String, Object>());
//        System.out.println("=====>"+userAuthList.size());
//        for (UserAuthModel u : userAuthList){
//            System.out.println(u.getLoginName());
//        }

//        List<Map<String, Object>> rtValue = systemCountMapper.countMonthRegisterByProvince();
//        System.out.println("=======>"+rtValue);

        //Page<UrgeRepayCountModel> page =urgeRepayOrderService.urgeCount(new HashMap<String, Object>(),1,10);

//        List<Map<String, Object>> list = infoMapper.findTable();

//
//        List<Map<String, Object>> list = infoService.findTable();



//        long startTime=System.currentTimeMillis();   //获取开始时间
//        List data = clBorrowService.listIndex();
//        long endTime=System.currentTimeMillis(); //获取结束时间
//        System.out.println("wwpwan=========>clBorrowService.findIndex运行时间="+(endTime-startTime)+"ms");





//        //long id = clUserGenerator.generateKey().longValue();
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        Map insertUserMap = new HashMap<String,Object>();
//        insertUserMap.put("loginName","1355555555");
//        insertUserMap.put("loginPwd","12345678");
//        insertUserMap.put("invitationCode","ASDEFG");
//        insertUserMap.put("registTime",new Date());
//        insertUserMap.put("uuid",uuid);
//        insertUserMap.put("level","3");
//        insertUserMap.put("registerClient","ios");
//        insertUserMap.put("channelId",3L);
//        int count = mybatisService.insertSQL("usr.saveClUser", insertUserMap);
//        long userId = insertUserMap.get("id")==null?-1L: Long.parseLong( insertUserMap.get("id").toString());
//        logger.info("注册时插入"+count+"条cl_user条记录，主键="+userId);






//        inserMap.clear();
//        inserMap.put("userId",10000L);
//        inserMap.put("phone","1355555555");
//        inserMap.put("registerCoordinate","114.350456,28.510725");
//        inserMap.put("registerAddr","江西省宜春市铜鼓县定江西路靠近铜鼓县凌峰电脑学校");
//        inserMap.put("state","20");
//        mybatisService.insertSQL("usr.saveClUserInfo", inserMap);


//        inserMap.clear();
//        inserMap.put("consumerNo",10000);
//        inserMap.put("total",1000);
//        inserMap.put("unuse",1000);
//        inserMap.put("state","10");
//        mybatisService.insertSQL("usr.saveArcCredit", inserMap);


//        inserMap.clear();
//        inserMap.put("userId",10000L);
//        inserMap.put("state","10");
//        mybatisService.insertSQL("usr.saveClProfitAmount", inserMap);


//        inserMap.clear();
//        inserMap.put("userId",1000L);
//        inserMap.put("idState","10");
//        inserMap.put("zhimaState","10");
//        inserMap.put("phoneState","10");
//        inserMap.put("contactState","10");
//        inserMap.put("bankCardState","10");
//        inserMap.put("workInfoState","10");
//        inserMap.put("otherInfoState","10");
//        inserMap.put("livingIdentifyState","10");
//        inserMap.put("tongdunState","10");
//        inserMap.put("newVersion","0");
//        mybatisService.insertSQL("usr.saveClUserAuth", inserMap);


//        inserMap.clear();
//        inserMap.put("inviteTime",new Date());
//        inserMap.put("inviteId",10000L);
//        inserMap.put("inviteName","wwpwan");
//        inserMap.put("userId",10000L);
//        inserMap.put("userName","ffff");
//        mybatisService.insertSQL("usr.clUserInvite", inserMap);



//        inserMap.put("loginName","18042465087");
//        inserMap.put("loginPwd","1111");
//        inserMap.put("loginpwdModifyTime",new Date());
//        int modifyCount = mybatisService.updateSQL("usr.updateClUser", inserMap);
//        logger.info("=====>"+modifyCount);



//        Map paramMap = new HashMap<String,Object>();
//        paramMap.put("phone","15366322360");
//        paramMap.put("smsType","register");
//        paramMap.put("validateSmsCode","1");  //
//        Sms sms = smsMapper.findSelective(paramMap);


//        Map updateMap = new HashMap<String,Object>();
//        updateMap.put("id", 80533);
//        updateMap.put("loginPwd","111111");
//        updateMap.put("loginpwdModifyTime",new Date());
//        int modifyCount = mybatisService.updateSQL("usr.updateClUserById", updateMap);



//        List<UserMessages> insertList = new ArrayList<UserMessages>();
//        UserMessages user1 = new UserMessages();
//        //user1.setId(1L);
//        user1.setType("10");
//        user1.setName("www");
//        user1.setTime(new Date());
//        user1.setPhone("1355555555");
//        user1.setUserId(1L);
//        insertList.add(user1);
//
//
//        UserMessages user2 = new UserMessages();
//        //user2.setId(2L);
//        user2.setType("10");
//        user2.setName("www");
//        user2.setTime(new Date());
//        user2.setPhone("1344444444");
//        user2.setUserId(2L);
//        insertList.add(user2);
//       int ss =  userMessagesMapper.inserBatch(insertList);


//        String ss = "12222";
//       System.out.println(StringUtils.isNotBlank(ss)  ) ;
//
//
//        System.out.println("=======>end");

//        clSmsService.overdue(117125);//逾期第一天发送短信通知


    }

}
