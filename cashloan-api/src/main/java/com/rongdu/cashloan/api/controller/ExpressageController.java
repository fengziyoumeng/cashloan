package com.rongdu.cashloan.api.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rongdu.cashloan.cl.domain.Expressage;
import com.rongdu.cashloan.cl.service.ExpressageService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.redis.ShardedJedisClient;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class ExpressageController {

    @Resource
    private ExpressageService expressageService;

    @Resource
    private ShardedJedisClient redisClient;
    /**
     * 收货信息查询
     * @param response
     */
    @RequestMapping("/api/info/selectbytime/expressage.htm")
    public void selectExpressageByTime( HttpServletResponse response ,@DateTimeFormat(pattern="yyyy-MM-dd") Date creatTime){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<Expressage> list = expressageService.getListByCreatTime(creatTime);
            result.put(Constant.RESPONSE_DATA, list);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }


    /**
     * 保存收货信息
     * @param response
     */
    @RequestMapping("/api/info/insertOrUpdate/expressage.htm")
    public void saveOrUpdateExpressage( HttpServletResponse response ,Expressage expressage){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            // 校验该手机号码是否已经收货
            String tel = redisClient.get(expressage.getTel());
            if(!StringUtil.isEmpty(tel)){ //tel不为空，返回
                result.put(Constant.RESPONSE_CODE, Constant.ALREADY_EXIST_CODE); //10
                result.put(Constant.RESPONSE_CODE_MSG, "同一手机号不能多次收货");
            }else{
                // tel缓存中找不到，去数据库找
                boolean flag = expressageService.isExistByTel(expressage.getTel());
                if(flag){ //已存在
                    result.put(Constant.RESPONSE_CODE, Constant.ALREADY_EXIST_CODE); //10
                    result.put(Constant.RESPONSE_CODE_MSG, "同一手机号不能多次收货");
                }else{ //不存在，便保存
                    expressageService.saveOrUpdate(expressage);
                    redisClient.set(expressage.getTel(),expressage.getTel());
                    result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                    result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 删除收货信息
     * @param response
     */
    @RequestMapping("/api/info/remove/expressage.htm")
    public void deleteExpressage( HttpServletResponse response ,Long id){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            expressageService.deleteById(id);

            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 根据id获取收货信息
     * @param response
     */
    @RequestMapping("/api/info/selectById/expressage.htm")
    public void selectExpressageById( HttpServletResponse response ,Long id){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Expressage expressage = expressageService.getExpressageById(id);
            result.put(Constant.RESPONSE_DATA, expressage);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
