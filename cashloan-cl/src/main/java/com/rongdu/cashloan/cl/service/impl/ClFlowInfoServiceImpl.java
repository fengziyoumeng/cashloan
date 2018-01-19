package com.rongdu.cashloan.cl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.ClFlowUV;
import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.domain.HomeSort;
import com.rongdu.cashloan.cl.mapper.ClFlowInfoMapper;
import com.rongdu.cashloan.cl.mapper.FlowPicMapper;
import com.rongdu.cashloan.cl.page.PageResult;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.ClFlowUVService;
import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.cl.util.ImageUploadUtil;
import com.rongdu.cashloan.cl.util.SeqUtil;
import com.rongdu.cashloan.cl.util.StateUtil;
import com.rongdu.cashloan.core.aliyun.AliYunUtil;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.exception.ServiceException;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import com.rongdu.cashloan.core.redisson.RedisUtils;
import com.rongdu.cashloan.system.domain.SysDictDetail;
import com.rongdu.cashloan.system.mapper.SysDictDetailMapper;
import com.rongdu.cashloan.system.serviceNoSharding.SysDictDetailService;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service("clFlowInfoService")
public class ClFlowInfoServiceImpl implements ClFlowInfoService {

    public static final Logger logger = LoggerFactory.getLogger(ClFlowInfoServiceImpl.class);

    @Resource
    private ShardedJedisClient redisClient;

    @Resource
    private ClFlowInfoMapper clFlowInfoMapper;

    @Resource
    private SysDictDetailMapper sysDictDetailMapper;

    @Resource
    private SysDictDetailService sysDictDetailService;

    @Resource
    private ClFlowUVService clFlowUVService;

    @Resource
    private FlowPicMapper flowPicMapper;

    @Value("${redis-ip}")
    private String redisIp;
    @Value("${redis-port}")
    private String redisPort;
    @Value("${redis-passwd}")
    private String redisPasswd;


    /**
     * 所有渠道列表  getInfoManage
     *
     * @throwsxception
     */
    @Override
    public List<ClFlowInfo> getAllProdctList(Map<String, Object> params) throws Exception {
        List<ClFlowInfo> list = (List<ClFlowInfo>) clFlowInfoMapper.listSelective(params);
        return list;
    }


    /**
     * 显示所有商品信息供UV统计用
     *
     * @throwsxception
     */
    @Override
    public Page<ClFlowInfo> getAllProdctListForUV(Map<String, Object> params, int currentPage, int pageSize) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<ClFlowInfo> list = clFlowInfoMapper.listSelectiveForUV(params);
        return (Page<ClFlowInfo>) list;
    }

    /**
     * 保存流量平台信息
     *
     * @param flowInfoModel
     * @param imgPath       图片文件路径
     * @param imgName       图片名称
     * @param tempImgPath   临时服务器目录
     * @param opType        更新类型
     * @return
     * @throws Exception
     */
    @Override
    public String saveOrUpdate(ClFlowInfo flowInfoModel, String imgPath, String imgName, String tempImgPath, String opType) throws Exception {
        String picUrls = "";
        String numberStr = SeqUtil.randomInvitationCode(6);
        redisClient.set(flowInfoModel.getPCode() + ":number", numberStr);
        try {
            //把路径中/后面的字符串作为图片名
            String imageName = imgPath.substring(imgPath.lastIndexOf(File.separatorChar) + 1);
            flowInfoModel.setPicName(imageName);
            picUrls = ImageUploadUtil.uploadOSSDeleteTemp(imgPath, "image");
            //上传的文件不为空，文件保存至Oss上
           /* if(StringUtil.isNotEmpty(tempImgPath)){
                File file =new File(imgPath);
                if(file.exists()){//判断服务器上是否已经有图片，有则认为是重新上传，无则认为没有修改图片，不再上传oss
                    picUrls = AliYunUtil.uploadH5File("image/",imgName,file);
                    logger.info("===>OSS图片地址："+picUrls);
                }
                //上传成功后删除本地文件
                //String path = request.getRealPath("/")+"flowPlatFormImg";
                File file1 =new File(imgPath);
                if (FileUtil.deleteFile(file1)) {
                    logger.info("===>上传后删除本地文件成功" + file1);
                }
            }*/
            flowInfoModel.setPicUrl(picUrls);
            int x = 0;
            if ("update".equals(opType)) {
                x = clFlowInfoMapper.update(flowInfoModel);
                System.out.println("x = " + x);

            } else {
                x = clFlowInfoMapper.save(flowInfoModel);
            }

            if (x > 0) {
                if (flowInfoModel.getPType() != null && flowInfoModel.getPType() == 1) {
                    delRedisHot();
                }
                delRedisALL();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picUrls;
    }

    private void delRedisALL() {
        Iterator<String> it = redisClient.keys(AppConstant.REDIS_KEY_CASH_FLOW_INFO_ALL + "*").iterator();
        while (it.hasNext()) {
            redisClient.del(it.next());
        }
    }

    private void delRedisHot() {
        redisClient.del(AppConstant.REDIS_KEY_CASH_FLOW_INFO_HOT);
    }


    /**
     * 获取本月的点击数
     */
    @Override
    public void getPlatFormClick(String code) throws Exception {
        int totalAmount = 0;
        int todayAmount = 0;
        int yestadayAmount = 0;
        int afterTomorrowAmount = 0;
        Map<String, Integer> amountStatic = new HashMap<String, Integer>();
        //将点击数量放入redis中保存
        if (StringUtil.isNotEmpty(code)) {
            //将当前日期的点击数量保存至redis,获取当前的次数，再加一即可
            String day = DateTime.now().toString("dd");
            //目前未做按时间查询，暂时先将近三日数据展示出
            todayAmount = getThisAmount(0, code);
            yestadayAmount = getThisAmount(1, code);
            afterTomorrowAmount = getThisAmount(2, code);
            //将今日之前的月份遍历查出每日的点击数
            for (int i = 0; i <= Integer.parseInt(day); i++) {
                int amount = 0;
                String yearMonth = DateTime.now().toString("yyyy-MM-");
                if (i < 10) {
                    yearMonth = yearMonth + "0" + i;
                } else {
                    yearMonth = yearMonth + i;
                }
                if (redisClient.hget(AppConstant.FLOW_PALTFORM_AMOUNT + code, yearMonth) != null) {
                    amount = Integer.parseInt(redisClient.hget(AppConstant.FLOW_PALTFORM_AMOUNT + code, yearMonth));
                }
                totalAmount += amount;
                amountStatic.put(yearMonth + i, amount);
            }
        }
//        Map<String, Object> result = new HashMap<String, Object>();
//        //每日点击数量
//        result.put("amount", amountStatic);
//        result.put("todayAmount", todayAmount);
//        result.put("yestadayAmount", yestadayAmount);
//        result.put("afterTomorrowAmount", afterTomorrowAmount);
//        //本月点击总数
//        result.put("totalAmount", totalAmount);
//        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
//        result.put(Constant.RESPONSE_CODE_MSG, "处理成功");
//        //加上返回参数
//        ServletUtils.writeToResponse(response, result);
    }


    /**
     * 平台信息删除（物理删除），此步骤涉及删除redis中的平台地址及平台点击数等数据
     */
    @Override
    public boolean channelDelete(Long id,
                                 String code,
                                 String imageName) throws Exception {
        boolean result = false;
        int count = clFlowInfoMapper.deleteById(id);
        if (count > 0) {
            //删除redis中的点击数量
            delRedisHot();
            delRedisALL();

            //删除OSS上的图片
            if (imageName != null && !"".equals(imageName)) {
                AliYunUtil.deleteH5Object("image" + File.separatorChar + imageName);
            }
            result = true;
        }
        return result;
    }

    @Override
    public String savePic(String realPath,MultipartFile image,String fileName) {
        return ImageUploadUtil.tempSave(realPath,"flowPlatFormImg",image,fileName);
    }

    /**
     * 获取每日平台的点击数
     *
     * @throws ServiceException
     * @throws Exception        异常
     */
    @Override
    public int getThisAmount(int i, String code) {
        int amount = 0;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -i);
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        if (redisClient.hget(AppConstant.FLOW_PALTFORM_AMOUNT + code, dayBefore) != null) {
            amount = Integer.parseInt(redisClient.hget(AppConstant.FLOW_PALTFORM_AMOUNT + code, dayBefore));
        }
        return amount;
    }


    /**
     * 获取流量商品信息
     * param searchMap
     *
     * @throwsxception
     */
    @Override
    public List<ClFlowInfo> getProdctList(Map<String, Object> searchMap) throws Exception {
        List<ClFlowInfo> list = (List<ClFlowInfo>) clFlowInfoMapper.listSelective(searchMap);
        return list;
    }

    /**
     * 获取热门推荐列表,兼容旧版app应用
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<ClFlowInfo> getHot() throws Exception {
        List<ClFlowInfo> infos = null;
        try {
            infos = (List<ClFlowInfo>) redisClient.getObject(AppConstant.REDIS_KEY_CASH_FLOW_INFO_HOT);
            if (infos != null && !infos.isEmpty()) {
                for (ClFlowInfo info : infos) {
                    if (info.getPTag() != null) {
                        String tagStr = handleDict(info.getPTag(), "FLOWINFO_P_TAG");
                        info.setPTag(tagStr);
                    }
                }
                return infos;
            }
        } catch (Exception e) {
            logger.info("redis获取首页信息错误" + e);
        }

        try {
            infos = clFlowInfoMapper.getHot();
            for (ClFlowInfo info : infos) {
                if (info.getPTag() != null) {
                    String tagStr = handleDict(info.getPTag(), "FLOWINFO_P_TAG");
                    info.setPTag(tagStr);
                }
            }
        } catch (Exception e) {
            logger.info("数据库查询失败" + e);
        }

        try {
            if (infos != null && !infos.isEmpty()) {
                redisClient.setObject(AppConstant.REDIS_KEY_CASH_FLOW_INFO_HOT, infos, Global.getInt("appListExpire") * 3600);
            }
        } catch (Exception e) {
            logger.info("存储首页redis数据" + e);
        }
        return infos;
    }

    /**
     * 获取热门推荐列表,加入分页,用于新版app和h5
     *
     * @return
     * @throws Exception
     */
    @Override
    public PageResult getHotList(int currentPage, int pageSize) throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("current",(currentPage-1)*pageSize);
        param.put("pageSize",pageSize);
        PageResult pageResult = null;
        try {
            pageResult= (PageResult)redisClient.getObject(AppConstant.REDIS_KEY_CASH_FLOW_INFO_HOT_PAGERESULT+currentPage + pageSize);
            if(pageResult == null){
                List<ClFlowInfo> infos = clFlowInfoMapper.getAllHot(param);
                for (ClFlowInfo info : infos) {
                    if (info.getPTag() != null) {
                        String tagStr = handleDict(info.getPTag(), "FLOWINFO_P_TAG");
                        info.setPTag(tagStr);
                    }
                }
                Long total = clFlowInfoMapper.getTotal();
                pageResult = new PageResult(infos, total,currentPage,pageSize);
                redisClient.setObject(AppConstant.REDIS_KEY_CASH_FLOW_INFO_HOT_PAGERESULT+currentPage + pageSize, pageResult, Global.getInt("appListExpire") * 3600);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取热门推荐列表失败" + e);
        }
        return pageResult;
    }

    @Override
    public Page<ClFlowInfo> getAll(int current, Map param) throws Exception {
        List<ClFlowInfo> infos = null;
        String key = null;
        try {
            // 数据非空判断
            if (param.get("limit") == null) {
                param.put("limit", 0);
            }

            if (param.get("day") == null) {
                param.put("day", 0);
            }
            key = AppConstant.REDIS_KEY_CASH_FLOW_INFO_ALL + "_" + param.get("limit") + "_" + param.get("day") + ":" + current;
            infos = (List<ClFlowInfo>) redisClient.getObject(key);
            if (infos != null && !infos.isEmpty()) {
                return (Page<ClFlowInfo>) infos;
            }
        } catch (Exception e) {
            logger.info("获取redis全部信息数据" + e);
        }
        int pageSize = Global.getInt("appPageSize");
        PageHelper.startPage(current, pageSize, false);
        infos = clFlowInfoMapper.getAll(param);
        try {
            if (infos != null && !infos.isEmpty() && key != null) {
                redisClient.setObject(key, infos, Global.getInt("appListExpire") * 3600);
            }
        } catch (Exception e) {
            logger.info("获取redis全部信息数据" + e);
        }

        return (Page<ClFlowInfo>) infos;
    }

    @Override
    public ClFlowInfo getDetail(Long id, String pCode) throws Exception {
        ClFlowInfo info;
        try {
            info = (ClFlowInfo) redisClient.getObject(AppConstant.REDIS_KEY_DETAIL_FLOW_INFO + pCode);
            if (info != null) {
                return info;
            }
        } catch (Exception e) {
            logger.info(pCode + "=========》从redis中取出数据时发生错误" + e);
        }

        info = clFlowInfoMapper.getDetail(id);
        if (info.getPProcess() != null) {
            String processStr = handleDict(info.getPProcess(), "FLOWINFO_P_PROCESS");
            info.setPProcess(processStr);
        }

        if (info.getPTag() != null) {
            String tagStr = handleDict(info.getPTag(), "FLOWINFO_P_TAG");
            info.setPTag(tagStr);
        }

        try {
            if (info != null) {
                redisClient.setObject(AppConstant.REDIS_KEY_DETAIL_FLOW_INFO + pCode, info, Global.getInt("appDetailExpire") * 3600);
            }
        } catch (Exception e) {
            logger.info(id + "=========》商品详细信息存储到redis时发生错误" + e);
        }
        return info;
    }

    @Override
    public boolean getUrl(long id, String pCode) throws Exception {
//        try{
//            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
//            int expire = Global.getInt("appClickExpire");
//            redisClient.incr(AppConstant.REDIS_KEY_CLICK_FLOW_INFO + pCode + ":" + date, expire*86400);
//            return true;
//        }catch (Exception e){
//            logger.info(id+"=========》增加redis中的点击数时发生错误"+e);
//            return false;
//        }
        final RedissonClient redisson = RedisUtils.getInstance().getRedisson(redisIp, redisPort, redisPasswd);
        final RLock bLock = redisson.getLock(AppConstant.LOCK_FLOWINFO_COUNT + pCode);
        try {
            bLock.lock(20, TimeUnit.SECONDS); //20秒后自动释放
            logger.info("====>设置分布式锁，key=" + AppConstant.LOCK_FLOWINFO_COUNT + pCode);
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int expire = Global.getInt("appClickExpire");
            redisClient.incr(AppConstant.REDIS_KEY_CLICK_FLOW_INFO + pCode + ":" + date, expire * 86400);
        } catch (Exception e) {
            logger.info(id + "=========》增加redis中的点击数时发生错误" + e);
            return false;
        } finally {
            logger.info("====>释放分布式锁，key=" + AppConstant.LOCK_FLOWINFO_COUNT + pCode);
            bLock.unlock();
        }
        return true;
    }

   /* @Override
    public int getNum(String pCode) {
        int num = 0;
        try{
            String jNum = redisClient.get(AppConstant.REDIS_KEY_BORROWNUM_FLOW_INFO + pCode);
            if(StringUtil.isNotBlank(jNum)){
                num = Integer.parseInt(jNum);
            }
        }catch (Exception e){
            num = 17613;
        }
        return num;
    }*/

    //处理字典字符串拼接
    private String handleDict(String pProcess, String itemCode) {
        String[] process = pProcess.split(",");
        String processStr = "";
        for (int i = 0; i < process.length; i++) {
            try {
                SysDictDetail itemDict = sysDictDetailService.findDetail(process[i], itemCode);
                if (i < process.length - 1) {
                    processStr += itemDict.getItemValue() + "|";
                } else {
                    processStr += itemDict.getItemValue();
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        return processStr;
    }

    @Override
    public List<ClFlowInfo> getAllPCode() {
        return clFlowInfoMapper.getAllPCode();
    }

    @Override
    public void update(ClFlowInfo info) {
        clFlowInfoMapper.update(info);
    }

    @Override
    public List<ClFlowInfo> getAllPCodeForYesterday() {
        return clFlowInfoMapper.getAllPCodeForYesterday();
    }

    @Override
    public List<ClFlowInfo> getAllInfo() {
        return clFlowInfoMapper.getAllToMonth();
    }

    /**
     * 查询所有列表信息,根据类型获取
     *
     * @param type
     * @return
     */
    @Override
    public List<ClFlowInfo> getListByType(Integer type) {
        List<ClFlowInfo> infos = null;
        List<ClFlowInfo> resultList = null;
        //从redis中取值
        try {
            resultList = (List<ClFlowInfo>) redisClient.getObject(AppConstant.REDIS_KEY_LIST_ALL + type.toString());
            if (resultList != null && !resultList.isEmpty()) {
                return resultList;
            }
        } catch (Exception e) {
            logger.info("redis获取分类列表" + e);
        }

        if (resultList == null) {
            infos = clFlowInfoMapper.getAllListOrderByTypeSort();
        }
        List<ClFlowInfo> listInfo = new ArrayList<>();
        try {
            for (ClFlowInfo info : infos) {
                boolean flag = StateUtil.hasState(info.getPType(), type);
                if (info.getPType() != null && flag) {
                    String tagStr = handleDict(info.getPTag(), "FLOWINFO_P_TAG");
                    info.setPTag(tagStr);
                    listInfo.add(info);
                }
            }
            redisClient.setObject(AppConstant.REDIS_KEY_LIST_ALL + type.toString(), listInfo, Global.getInt("appListExpire") * 3600);
            return listInfo;
        } catch (Exception e) {
            logger.info("获取分类信息失败" + e);
        }
        return resultList;
    }

    @Override
    public List<HomeSort> getShowPicList() {
        List<HomeSort> homeSorts = new ArrayList<>();
        List<SysDictDetail> Sorts = sysDictDetailMapper.findAllDetail("FLOWINFO_SHOW_TYPE");
        List<FlowPic> picList = flowPicMapper.getPic(2);
        for (FlowPic pic : picList) {
            for (SysDictDetail sort : Sorts) {
                String title = sort.getItemValue();
                String pid = sort.getItemCode();
                String id = sort.getItemCode();
                if (pic.getPId().toString().equals(pid)) {
                    HomeSort homeSort = new HomeSort();
                    homeSort.setPicUrl(pic.getUrl());
                    homeSort.setTitle(title);
                    homeSort.setId(Long.parseLong(id));
                    homeSorts.add(homeSort);
                }
            }
        }

       /* for (SysDictDetail sort : Sorts) {
            String title = sort.getItemValue();
            String pid = sort.getItemCode();
            for (FlowPic pic : picList) {
                if (pic.getPId().toString().equals(pid)){
                    HomeSort homeSort = new HomeSort();
                    homeSort.setPicUrl(pic.getUrl());
                    homeSort.setTitle(title);
                    homeSorts.add(homeSort);
                }
            }
        }*/
        return homeSorts;
    }


    @Override
    public String getTagByType(Integer type) {
        if (type != null) {
            return handleDict(type.toString(), "FLOWINFO_SHOW_TYPE");
        }
        return null;
    }

    @Override
    public Map<String, Object> getAmountClick(String code) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pCode", code);
        ClFlowInfo clFlowInfo = clFlowInfoMapper.getAmountClick(paramMap);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String countDate1 = format.format(calendar.getTime());
        paramMap.put("countDate", countDate1);
        ClFlowUV clFlowUV = clFlowUVService.getClFlowUVByDate(paramMap);
        resultMap.put("yestadayAmount", (clFlowUV == null ? 0 : clFlowUV.getClickCount())); //昨日

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String countDate2 = format.format(calendar.getTime());
        paramMap.put("countDate", countDate2);
        clFlowUV = clFlowUVService.getClFlowUVByDate(paramMap);
        resultMap.put("afterTomorrowAmount", (clFlowUV == null ? 0 : clFlowUV.getClickCount())); //前日

        if (clFlowInfo == null || clFlowInfo.getPTodayClickCount() == null) {
            resultMap.put("todayAmount", 0); //今日
        } else {
            resultMap.put("todayAmount", clFlowInfo.getPTodayClickCount()); //今日
        }

        if (clFlowInfo == null || clFlowInfo.getpPreMonthClickCount() == null) {
            resultMap.put("totalAmount", 0); //当月
        } else {
            resultMap.put("totalAmount", clFlowInfo.getpPreMonthClickCount()); //当月
        }

        return resultMap;
    }
}
