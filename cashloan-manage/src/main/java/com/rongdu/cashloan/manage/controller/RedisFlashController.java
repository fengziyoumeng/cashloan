package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.constant.AppConstant;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 清除redis缓存中的数据的Controller
 *
 * @author zwk
 * @version 1.0.0
 * @date 2017-11-16 15:54:41
 * Copyright 杭州民华金融信息服务有限公司  arc All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * <p>
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class RedisFlashController {
    @Resource
    private ShardedJedisClient redisClient;

    public static final Logger logger = LoggerFactory.getLogger(RedisFlashController.class);

    @RequestMapping(value = "redis/flowControl/delRedisInfo.htm")
    public void  delRedisInfo(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "type") int type,
            HttpServletResponse response) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        if(Global.getValue("redisFlashKey").equals(code)){
            if(type == 1){
                redisClient.del(AppConstant.REDIS_KEY_PIC_FLOW_INFO);
                logger.info("删除redis中的轮播图信息");
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "删除redis中轮播图信息成功");
            }else if(type ==2){
                redisClient.del(AppConstant.REDIS_KEY_RADIO_FLOW_INFO);
                logger.info("删除redis中的广播信息");
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "删除redis中广播信息成功");
            }else{
                result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "请求数据错误");
            }
            ServletUtils.writeToResponse(response,result);
        }
    }

}
