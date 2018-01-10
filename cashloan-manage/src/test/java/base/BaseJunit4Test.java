package base;

import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.mapper.ChannelMapper;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.mapper.ClickTrackMapper;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.cl.util.DateTools;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.mapper.UserMapper;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.manage.job.SftpPropertiesUtil;
import com.rongdu.cashloan.manage.job.SftpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Protocol;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@ContextConfiguration(locations={"classpath:/config/spring/*-beans.xml"})//加载spring配置文件

public class BaseJunit4Test {
    @Resource
    private ClFlowInfoMapper clFlowInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private ClickTrackMapper clickTrackMapper;

    @Autowired
    private ShardedJedisClient redisClient;

    @Autowired
    IClickTrackService clickTrackService;

    @Resource
    private ClFlowUVService clFlowUVService;


    @Test
    public  void test1(){
        List<String> lsr = DateTools.getSdateToEdate("2018-01-06","2018-01-07");
        List<ClFlowUV> clFlowUVList = clFlowUVService.listFlowUv();
        if(clFlowUVList!=null && clFlowUVList.size()>0){
            for(ClFlowUV clFlowUV:clFlowUVList){
                System.out.println(String.format("产品编码【%s】",clFlowUV.getPCode()));
                for(String str:lsr){
                    long i = redisClient.del(clFlowUV.getPCode()+":excel_uv:"+str);
                    System.out.println(String.format("缓存数据【%s】",clFlowUV.getPCode()+":excel_uv:"+str));
                    System.out.println(String.format("结果【%s】",i));
                }
            }
        }
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
            aa[i%m].add(list.get(i)); //平均分配到m个list
        }

        for (int i = 0; i <aa.length ; i++) {
            System.out.println("分配"+aa[i].size());//打印出m个list的值
        }
    }

    @Test
    public void testftp(){
        String addr = SftpPropertiesUtil.getVal("addr");
        String username = SftpPropertiesUtil.getVal("username");
        String password = SftpPropertiesUtil.getVal("password");

        String localDir = "D:/home/jjhao/uploadFiles/" + "POS_DATA/" + "test";
        String localFile = "POS_DATA_" + "20171219" + ".txt";
        localFile = localDir+"/"+localFile;
        String remoteDir = "/home/ftp/test";
        SftpUtils.listFileNames(addr, 22, username, password, localFile,remoteDir);

//        String addr = SftpPropertiesUtil.getVal("addr");
//        String username = SftpPropertiesUtil.getVal("username");
//        String password = SftpPropertiesUtil.getVal("password");
//        FtpUtils f = new FtpUtils(addr, 22, username, password);
//        if (f.open()) {
//            System.out.println("====================连接FTP成功！！！====================");
//            f.changeToParentDir();
//            f.close();
//            System.out.println("====================上传到FTP文件成功====================");
//        } else {
//            System.out.println("====================连接FTP失败！！！====================");
//        }
    }

    @Test
    public void testSetNx(){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MONTH, 0);
//        String s = format.format(calendar.getTime());
//        boolean b = redisClient.setnx("monthQuartz","MonthQuartz:"+s);

//        String numString = redisClient.get(" nuanshd001_excel_uv:2017-12-01");
//        System.out.println("前numString："+numString+"=====");
        try {
            redisClient.set("wbaaa","23");
            redisClient.set("wbaaa","24");
            String numtest = redisClient.get("wbaaa");
            System.out.println("numtest："+numtest+"=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisClient.setnx("wubin111","24");
    }

    @Test
    public void testTrack(){
        List<ClickTrack> clickTrackList =  clickTrackMapper.queryTrailsByDate("2017-12-31");
//        if(clickTrackList!=null && clickTrackList.size()>0){
//            for(ClickTrack clickTrack : clickTrackList){
//                String channelName = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"channel:"+clickTrack.getUserId());
//                if(StringUtil.isNotBlank(channelName)){
//                    clickTrack.setChannelName(channelName);
//                }else{
//                    Long channelId = userMapper.findChannelId(clickTrack.getUserId());
//                    channelName = channelMapper.findName(channelId);
//                    if(StringUtil.isNotBlank(channelName)){
//                        redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"channel:"+clickTrack.getUserId(),channelName);
//                        clickTrack.setChannelName(channelName);
//                    }
//                }
//                int i = clickTrackMapper.update(clickTrack);
//                System.out.println("====："+i+"===");
//            }
//        }

//        ClickTrack clickTrack = new ClickTrack();
//        clickTrack.setName("test");
//        clickTrack.setChannelName("test");
//        clickTrack.setClickTime(new Date());
//        clickTrack.setFlag(0l);
//        clickTrack.setPositionMark("test");
//        clickTrack.setUserId(11111l);
//        clickTrackMapper.save(clickTrack);
    }
}