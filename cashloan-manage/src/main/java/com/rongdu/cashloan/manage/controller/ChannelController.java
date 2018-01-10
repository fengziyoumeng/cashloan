package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.Channel;
import com.rongdu.cashloan.cl.model.ChannelCountModel;
import com.rongdu.cashloan.cl.model.ChannelModel;
import com.rongdu.cashloan.cl.service.ChannelService;
import com.rongdu.cashloan.cl.vo.ChannelForH5;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* 渠道信息Controller
*
* @author gc
* @version 1.0.0
* @date 2017-03-03 10:52:07
* Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
* 官方网站：www.yongqianbei.com
*
* 未经授权不得进行修改、复制、出售及商业使用
*/
@Scope("prototype")
@Controller
public class ChannelController extends ManageBaseController {

   @Resource
   private ChannelService channelService;
    @Autowired
    private ShardedJedisClient redisClient;
   /**
    * 保存
    * @param code
    * @param name
    * @param linker
    * @param phone
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/save.htm", method = RequestMethod.POST)
   public void save(@RequestParam(value="code") String code,
           @RequestParam(value="name") String name,
           @RequestParam(value="linker") String linker,
           @RequestParam(value="phone") String phone,
           @RequestParam(value="apkUrl") String apkUrl) throws Exception {
        Channel channel=new Channel();
        channel.setCode(code);
        channel.setLinker(linker);
        channel.setName(name);
        channel.setPhone(phone);
        boolean flag = channelService.save(channel);
        //如果不为空保存到redis里
        if(StringUtil.isNotEmpty(apkUrl)){
            redisClient.setObject(AppConstant.APK_DOWNLOAD+ code,apkUrl);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (flag) {
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
        } else {
            result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
        }
        ServletUtils.writeToResponse(response, result);
    }
    /**
     * 通过ajax异步获取apk下载地址
     * @param code
     * @throws Exception
     */
    @RequestMapping(value = "/modules/manage/promotion/channel/getUrl.htm", method = RequestMethod.POST)
    public void test(@RequestParam(value="code") String code) throws Exception {
        String apkUrl ="";
        if(redisClient.getObject(AppConstant.APK_DOWNLOAD+ code)!=null) {
           apkUrl = redisClient.getObject(AppConstant.APK_DOWNLOAD + code).toString();
       }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("apkUrl", apkUrl);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }

    @RequestMapping("registerCountListData")
    public void registerCountList(final HttpServletRequest request) {

    }

   /**
    * 查询所有渠道信息
    *
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/listChannel.htm")
   public void listChannel() throws Exception {
       List<Channel> list = channelService.listChannel();

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, list);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 查询推广渠道注册数量统计
    *
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/registerCountList.htm")
   public void registerCountListData(@RequestParam(value="searchParams",required=false) String searchParams,
                                     @RequestParam(value = "current") int current,
                                     @RequestParam(value = "pageSize") int pageSize) throws Exception {


       Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
       Date beginTime = null;
       Date endTime = null;
       if (params!=null){
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           beginTime =sdf.parse((String)params.get("beginTime"));
           endTime =sdf.parse((String)params.get("endTime"));
       }

       List<ChannelForH5> list = channelService.registerCountList(beginTime,endTime);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, list);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }
   /**
    * 推广渠道注册数量统计80%
    *
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/for80/registerCountList.htm")
   public void registerCountList80(@RequestParam(value="searchParams",required=false) String searchParams,
                                     @RequestParam(value = "current") int current,
                                     @RequestParam(value = "pageSize") int pageSize) throws Exception {


       Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
       Date beginTime = null;
       Date endTime = null;
       if (params!=null){
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           beginTime =sdf.parse((String)params.get("beginTime"));
           endTime =sdf.parse((String)params.get("endTime"));
       }
       double ratio = 0.8;
       List<ChannelForH5> list = channelService.registerCountFor80(beginTime,endTime);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, list);
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 渠道信息列表页查看
    *
    * @param searchParams
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/promotion/channel/page.htm", method = {RequestMethod.POST,RequestMethod.GET})
   public void page(
           @RequestParam(value="searchParams",required=false) String searchParams,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String, Object> searchMap = new HashMap<>();
       if (!StringUtils.isEmpty(searchParams)) {
           searchMap = JsonUtil.parse(searchParams, Map.class);
       }

       Page<ChannelModel> page = channelService.page(current, pageSize,searchMap);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 修改
    * @param id
    * @param code
    * @param name
    * @param linker
    * @param phone
    * @throws Exception
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/update.htm", method = RequestMethod.POST)
   public void update(
           @RequestParam(value="id") Long id,
           @RequestParam(value="code") String code,
           @RequestParam(value="name") String name,
           @RequestParam(value="linker") String linker,
           @RequestParam(value="phone") String phone,
           @RequestParam(value="apkUrl") String apkUrl) throws Exception {
        //将数据放在redis中,如果不为空修改，为空则删除
       if(!StringUtils.isEmpty(apkUrl)){
           redisClient.setObject(AppConstant.APK_DOWNLOAD+ code,apkUrl);
       }else {
           redisClient.delObject(AppConstant.APK_DOWNLOAD+ code);
       }

       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("id", id);
       paramMap.put("code", code);
       paramMap.put("name", name);
       paramMap.put("linker", linker);
       paramMap.put("phone", phone);
       paramMap.put("apkUrl", apkUrl);
       boolean flag = channelService.update(paramMap);
       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
       }
       ServletUtils.writeToResponse(response, result);
   }


   /**
    * 渠道信息修改状态
    */
   @RequestMapping(value = "/modules/manage/promotion/channel/updateState.htm", method = RequestMethod.POST)
   public void updateState(@RequestParam(value="id") Long id,
                   @RequestParam(value="state") String state) throws Exception {
       Map<String, Object> paramMap = new HashMap<String, Object>();
       paramMap.put("id", id);
       paramMap.put("state", state);
       boolean flag = channelService.update(paramMap);
       Map<String, Object> result = new HashMap<String, Object>();
       if (flag) {
           result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
       } else {
           result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
           result.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_FAIL);
       }
       ServletUtils.writeToResponse(response, result);
   }

   /**
    * 统计渠道用户信息
    *
    * @param searchParams
    * @param current
    * @param pageSize
    * @throws Exception
    */
   @SuppressWarnings("unchecked")
   @RequestMapping(value = "/modules/manage/promotion/channel/channelUserList.htm", method = {RequestMethod.POST,RequestMethod.GET})
   public void channelUserList(
           @RequestParam(value="searchParams",required=false) String searchParams,
           @RequestParam(value = "current") int current,
           @RequestParam(value = "pageSize") int pageSize) throws Exception {
       Map<String, Object> searchMap = new HashMap<>();
       if (!StringUtils.isEmpty(searchParams)) {
           searchMap = JsonUtil.parse(searchParams, Map.class);
       }
       Page<ChannelCountModel> page = channelService.channelUserList(current, pageSize,searchMap);

       Map<String, Object> result = new HashMap<String, Object>();
       result.put(Constant.RESPONSE_DATA, page);
       result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(page));
       result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
       result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
       ServletUtils.writeToResponse(response, result);
   }




}
