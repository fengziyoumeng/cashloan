package com.rongdu.cashloan.cl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.mapper.BankInfoMapper;
import com.rongdu.cashloan.cl.mapper.ChannelMapper;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.mapper.ClickTrackMapper;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.cl.threads.SingleThreadPool;
import com.rongdu.cashloan.cl.util.DateTools;
import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.mapper.UserMapper;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.core.redisson.RedisUtils;
import com.rongdu.cashloan.core.service.CloanUserService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

@Service("clickTrackService")
public class ClickTrackServiceImpl implements IClickTrackService {

    public static final Logger logger = LoggerFactory.getLogger(ClickTrackServiceImpl.class);

    @Resource
    private ClickTrackMapper clickTrackMapper;

    @Resource
    private ShardedJedisClient redisClient;

    @Resource
    private ClFlowInfoMapper clFlowInfoMapper;

    @Resource
    private ChannelMapper channelMapper;

    @Resource
    private BankInfoMapper bankInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int save(ClickTrack clickTrack) {
        int ret =0;
        try {
             ret = clickTrackMapper.save(clickTrack);
        }catch (Exception e){
            logger.info("保存失败:"+e);
        }
        return ret;
    }

    @Override
    public void saveInRedis(ClickTrack clickTrack) {
        try {
            if(clickTrack.getUserId()!=null) {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                clickTrack.setClickTime(now);
                getChannelName(clickTrack);
                redisClient.hset(AppConstant.REDIS_KEY_CLICK_TRACK + DateUtil.getNowDate(), sdf.format(now), JSONObject.toJSONString(clickTrack), 172800);
            }
        }catch(Exception e){
            logger.info("点击轨迹保存失败"+e);
        }
    }

    @Override
    public Page<ClickTrack> queryTrailRecodes(Map<String, Object> params, int currentPage, int pageSize) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<ClickTrack> clickTrackList  = getClickTracks(params);
        return (Page<ClickTrack>)clickTrackList;
    }

    private void getChannelName(ClickTrack clickTrack){
        //查询并设置渠道名称
        String channelName = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"channel:"+clickTrack.getUserId());
        if(StringUtil.isNotBlank(channelName)){
            clickTrack.setChannelName(channelName);
        }else{
            Long channelId = userMapper.findChannelId(clickTrack.getUserId());
            channelName = channelMapper.findName(channelId);
            if(StringUtil.isNotBlank(channelName)){
                redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"channel:"+clickTrack.getUserId(),channelName);
                clickTrack.setChannelName(channelName);
            }
        }
    }

    private List<ClickTrack> getClickTracks(Map<String, Object> params){

        List<ClickTrack> clickTrackList =  clickTrackMapper.queryTrailRecodes(params);
        if(clickTrackList!=null && clickTrackList.size()>0){
            for(ClickTrack clickTrack : clickTrackList){
                if(StringUtil.isBlank(clickTrack.getChannelName())){
                    getChannelName(clickTrack);
                }

                //设置（产品、信用卡）页面名称，渠道名称
                if("platDetail".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platDetail:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"详情页");
                    }else{
                        name = clFlowInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"详情页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platDetail:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("platRegister".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platRegister:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"注册页");
                    }else{
                        name = clFlowInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"注册页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platRegister:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("card".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"card:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"信用卡页");
                    }else{
                        name = bankInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"信用卡页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"card:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("sortPic".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"sortPic:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name);
                    }else{
                        if(2==clickTrack.getFlag()){
                            name = "微额贷";
                        }else if(4==clickTrack.getFlag()){
                            name = "大额贷";
                        }else if(8==clickTrack.getFlag()){
                            name = "分期贷";
                        }else if(16==clickTrack.getFlag()){
                            name = "定制款";
                        }else if(32==clickTrack.getFlag()){
                            name = "POS机";
                        }else if(64==clickTrack.getFlag()){
                            name = "信用卡";
                        }else if(128==clickTrack.getFlag()){
                            name = "征信";
                        }
                        clickTrack.setName(name);
                        redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"sortPic:"+clickTrack.getFlag(),name);
                    }
                }else if("loansTabs".equals(clickTrack.getPositionMark())){
                    clickTrack.setName("贷款列表页");
                }else if("creditTabs".equals(clickTrack.getPositionMark())){
                    clickTrack.setName("信用卡列表页");
                }
            }
        }
        return clickTrackList;
    }

    @Override
    public List<ClickTrack> getTrackExcel(String beginTime, String endTime, String userId, String channelName) throws Exception {

        final Map<String, Object> params = new HashMap<String,Object>();
        params.put("userId",userId);
        params.put("beginTime",beginTime);
        params.put("endTime",endTime);
        params.put("channelName",channelName);
        int pageNum = 1;
        int num = 1;
        int pageSize = 1000;
        String start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        logger.info("=start=="+start);
        PageHelper.startPage(pageNum, pageSize);
        List<ClickTrack> clickTrackList  = getClickTracks(params);
        String end = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        logger.info("=end=="+end);
        final List<ClickTrack> list = new ArrayList<ClickTrack>();
        final List<Integer> flagList = new ArrayList<Integer>();
        if(clickTrackList!=null && clickTrackList.size()>0){
            list.addAll(clickTrackList);
        }
        Page<ClickTrack> page = (Page<ClickTrack>)clickTrackList;
        RdPage rdPage = new RdPage(page);
        long pages = rdPage.getPages();
        logger.info("=pages："+pages);
        start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        logger.info("=for start=="+start);
        long i = pages/5;
        logger.info("pages除以10的数："+i);
        int b = 0;//标识
        if(i>=2){// 超过10个
            long j = i/2; //j>=1，超过10
            logger.info("生成的线程数："+(5*i-1));
            for(int d=1;d<5*i;d++){
                final Long aLong = new Long(d);
                SingleThreadPool.getThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        PageHelper.startPage(aLong.intValue()+1, 1000);
                        List<ClickTrack> clickTrackList = getClickTracks(params);
                        list.addAll(clickTrackList);
                        flagList.add(aLong.intValue());
                        logger.info("进入线程"+aLong.intValue());
                    }
                });
            }
            for(pageNum=10;pageNum<pages;pageNum++){
                PageHelper.startPage(pageNum, pageSize);
                clickTrackList = getClickTracks(params);
                list.addAll(clickTrackList);
            }
            Thread.sleep(10000);
        }else{
            b = 1;
            for(num=1;pageNum<pages;pageNum++){
                num++;
                PageHelper.startPage(num, pageSize);
                clickTrackList = getClickTracks(params);
                list.addAll(clickTrackList);
            }
        }
        while(flagList.size()!=(5*i-1) && b==0){
            logger.info("=flagList长度没有9或者b不等于1，进入休眠等待，休眠时间5s==");
            Thread.sleep(5000);
        }
        end = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        logger.info("=for end=="+end);
        return list;
    }

    private List<ClickTrack> getTracksByDate(String str){

        List<ClickTrack> clickTrackList =  clickTrackMapper.queryTrailsByDate(str);
        if(clickTrackList!=null && clickTrackList.size()>0){
            for(ClickTrack clickTrack : clickTrackList){
                if(StringUtil.isBlank(clickTrack.getChannelName())){
                    getChannelName(clickTrack);
                }

                //设置（产品、信用卡）页面名称，渠道名称
                if("platDetail".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platDetail:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"详情页");
                    }else{
                        name = clFlowInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"详情页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platDetail:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("platRegister".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platRegister:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"注册页");
                    }else{
                        name = clFlowInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"注册页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"platRegister:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("card".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"card:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name+"信用卡页");
                    }else{
                        name = bankInfoMapper.findName(clickTrack.getFlag());
                        clickTrack.setName(name+"信用卡页");
                        if(StringUtil.isNotBlank(name)){
                            redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"card:"+clickTrack.getFlag(),name);
                        }
                    }
                }else if("sortPic".equals(clickTrack.getPositionMark())){
                    String name = redisClient.get(AppConstant.REDIS_KEY_LIAN_PRODUCT+"sortPic:"+clickTrack.getFlag());
                    if(StringUtil.isNotBlank(name)){
                        clickTrack.setName(name);
                    }else{
                        if(2==clickTrack.getFlag()){
                            name = "微额贷";
                        }else if(4==clickTrack.getFlag()){
                            name = "大额贷";
                        }else if(8==clickTrack.getFlag()){
                            name = "分期贷";
                        }else if(16==clickTrack.getFlag()){
                            name = "定制款";
                        }else if(32==clickTrack.getFlag()){
                            name = "POS机";
                        }else if(64==clickTrack.getFlag()){
                            name = "信用卡";
                        }else if(128==clickTrack.getFlag()){
                            name = "征信";
                        }
                        clickTrack.setName(name);
                        redisClient.set(AppConstant.REDIS_KEY_LIAN_PRODUCT+"sortPic:"+clickTrack.getFlag(),name);
                    }
                }else if("loansTabs".equals(clickTrack.getPositionMark())){
                    clickTrack.setName("贷款列表页");
                }else if("creditTabs".equals(clickTrack.getPositionMark())){
                    clickTrack.setName("信用卡列表页");
                }
            }
        }
        return clickTrackList;
    }

    @Override
    public List<ClickTrack> makeTrackExcel(String beginTime, String endTime, String userId, String channelName) throws Exception {
        final List<String> lsr = DateTools.getSdateToEdate(beginTime,endTime);
        final List<ClickTrack> list = new ArrayList<ClickTrack>();
        final List<Integer> flagList = new ArrayList<Integer>();
        logger.info("生成的线程数："+lsr.size());
        for(int d=0;d<lsr.size();d++){
            final Integer integer = new Integer(d);
            SingleThreadPool.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("时间"+lsr.get(integer));
                    List<ClickTrack> clickTrackList = (List<ClickTrack>)redisClient.getObject("user_clickTrackList:"+lsr.get(integer));
                    if(clickTrackList!=null && clickTrackList.size()>0){
                        logger.info(lsr.get(integer)+":缓存中有数据");
                        list.addAll(clickTrackList);
                    }else{
                        clickTrackList = getTracksByDate(lsr.get(integer));
                        if(clickTrackList!=null && clickTrackList.size()>0){
                            list.addAll(clickTrackList);
                            redisClient.setObject("user_clickTrackList:"+lsr.get(integer),clickTrackList);
                            logger.info(lsr.get(integer)+":设置缓存成功");
                        }
                    }
                    flagList.add(integer);
                    logger.info("进入线程"+integer);
                }
            });
        }
        Thread.sleep(5000);
        while(flagList.size()!=lsr.size()){
            logger.info("=flagList长度没有达到目标，进入休眠等待，休眠时间5s==");
            Thread.sleep(3000);
        }
        return list;
    }

    @Override
    public Map<String,Object> channelSurvey(Map<String,Object> map)throws Exception{
        Long tUV = clickTrackMapper.totalUV(map);
        Long dUV = clickTrackMapper.duplicateUV(map);
        Long rUV = clickTrackMapper.registerUV(map);
        long totalUV = tUV==null?0:tUV;//总UV
        long duplicateUV = dUV==null?0:dUV;//去重后总UV
        long registerUV = rUV==null?0:rUV;//去重后注册UV
        long zcl = (Long) map.get("zcl");//注册量
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String bl = zcl==0?"0":numberFormat.format((float) totalUV / (float) zcl);//倍率（总UV/注册量）
        String qcl = totalUV==0?"0":numberFormat.format((1-(float) duplicateUV / (float) totalUV)*100);//去重率（总UV/注册量）
        String qch_bl = zcl==0?"0":numberFormat.format((float) duplicateUV / (float) zcl);//去重后倍率（去重后总UV/注册量）
        String xq_zc = (duplicateUV-registerUV)==0?"0":numberFormat.format((float) registerUV / (float) (duplicateUV-registerUV) * 100);//详情注册转化率（去重后注册UV/（去重后总UV-去重后注册UV））
        String zc_zh = totalUV==0?"0":numberFormat.format((float) registerUV / (float) totalUV * 100);//最终转化率（去重后注册UV/totalUV）
        map.put("totalUV",totalUV);
        map.put("duplicateUV",duplicateUV);
        map.put("registerUV",registerUV);
        map.put("bl",bl);
        map.put("qcl",qcl+"%");
        map.put("qch_bl",qch_bl);
        map.put("xq_zc",xq_zc+"%");
        map.put("zc_zh",zc_zh+"%");
        return map;
    }
}
