package rdb;

import base.BaseJunit4Test;
import com.rongdu.cashloan.api.user.bean.ClUserGenerator;
import com.rongdu.cashloan.cl.mapper.ClBorrowMapper;
import com.rongdu.cashloan.cl.mapper.SmsMapper;
import com.rongdu.cashloan.cl.mapper.SystemCountMapper;
import com.rongdu.cashloan.cl.mapper.UserAuthMapper;
import com.rongdu.cashloan.cl.service.ClBorrowService;
import com.rongdu.cashloan.cl.service.impl.MybatisService;
import com.rongdu.cashloan.core.mapper.RDBUserItemMapper;
import com.rongdu.cashloan.core.mapper.UserMapper;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
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


    @Resource
    protected MybatisService mybatisService;

    @Autowired
    private ShardedJedisClient redisClient;

    @Autowired
    private ClUserGenerator clUserGenerator;

    @Autowired
    private SmsMapper smsMapper;

    @Autowired
    private ClBorrowMapper clBorrowMapper;

    @Autowired
    private RDBUserItemMapper rDBUserItemMapper;

    @Autowired
    private UserMapper userMapper;




    @Test
    public void test01() {
        System.out.println("begin....");

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Map inserMap = new HashMap<String,Object>();
        inserMap.put("loginName","13555055555");
        inserMap.put("loginPwd","1111111");
        inserMap.put("uuid",uuid);
        inserMap.put("channelCode","xxxx");
        inserMap.put("registerClient","");
        inserMap.put("channelId",1);
        int count = mybatisService.insertSQL("usr.saveClUser", inserMap);

//        Map rec = mybatisService.queryRec("usr.info", 81138L);


//        ApplicationContext ctx = getApplicationContext();
//        String[] beanNames =  ctx.getBeanDefinitionNames();
//        System.out.println("所以beanNames个数："+beanNames.length);
//        for(String bn:beanNames){
//            System.out.println("=======>"+bn);
//        }



        //borrowRepayService.authSignApply(200516L);



//        Map<String, Object> paramMap = new HashMap<String, Object>();
//        paramMap.put("userId", 88737L);
//        paramMap.put("state", BorrowRepayModel.STATE_REPAY_NO);
//        List<BorrowRepay> borrowRepayList = borrowRepayMapper.findUnRepay(paramMap);


//          SceneBusiness bus = sceneBusinessMapper.findByPrimary(2L);


//        User user = userMapper.findByPrimary(200516L); // 用户UUID
//        Borrow borrow = clBorrowMapper.findByPrimary(113114L ); // 借款标识 OrderNo 作为还款编号
        System.out.println("ok");


//        Borrow borrow = new Borrow();
//        borrow.setUserId(9999999L);
//        borrow.setOrderNo("668899");
//        borrow.setAmount(1000.0);
//        borrow.setRealAmount(1000.0);
//        borrow.setCreateTime(new Date());
//
//        borrow.setFee(140.0);
//        borrow.setTimeLimit("3");
//        borrow.setState("10");
//
//
//        clBorrowMapper.save(borrow);





//        inserMap.clear();
//        inserMap.put("consumerNo","999988877");
//        inserMap.put("total",1000);
//        inserMap.put("unuse",1000);
//        inserMap.put("state","10");
//        mybatisService.insertSQL("usr.saveArcCredit", inserMap);


//        for(int i=0;i<10;i++)
//        {
//        UserItemEntity userItemEntity = new UserItemEntity("useritem1", 11);
//           int key =  rDBUserItemMapper.insertOne(userItemEntity);  //db_1.user_1
//        System.out.println("id=====》"+userItemEntity.getId());
////        }



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
//        System.out.println("=========>clBorrowService.findIndex运行时间="+(endTime-startTime)+"ms");





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
//        inserMap.put("inviteName","wangwu");
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

        System.out.println("=======>end");


    }

}
