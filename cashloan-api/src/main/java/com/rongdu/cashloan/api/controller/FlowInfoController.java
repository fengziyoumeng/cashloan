package com.rongdu.cashloan.api.controller;


import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.ClFlowInfo;
import com.rongdu.cashloan.cl.domain.FlowPic;
import com.rongdu.cashloan.cl.domain.FlowRadio;
import com.rongdu.cashloan.cl.domain.HomeSort;
import com.rongdu.cashloan.cl.page.PageResult;
import com.rongdu.cashloan.cl.service.ClFlowInfoService;
import com.rongdu.cashloan.cl.service.FlowPicService;
import com.rongdu.cashloan.cl.service.FlowRadioService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
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
 * 借贷产品数据
 * @author caitt
 * @version 1.0
 * @date 2017年11月09日下午21:10:14
 * Copyright 杭州民华金融信息服务有限公司 现金贷  All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
@Scope("prototype")
@Controller
public class FlowInfoController {

    @Resource
    private ClFlowInfoService clFlowInfoService;

    @Resource
    private FlowPicService flowPicService;

    @Resource
    private FlowRadioService flowRadioService;

    /**
     * 热门推荐列表,兼容旧版app应用
     * @param response
     */
    @RequestMapping("/api/info/showHotRecommend.htm")
    public void showHotRecommend( HttpServletResponse response ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<ClFlowInfo> infos = clFlowInfoService.getHot();
            result.put(Constant.RESPONSE_DATA, infos);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 热门推荐列表,加入分页功能,用于新版app调用
     * @param response
     */
    @RequestMapping("/api/info/showHotRecommendList.htm")
    public void showHotRecommendList( HttpServletResponse response ,int currentPage, int pageSize){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            PageResult pageResult = clFlowInfoService.getHotList(currentPage,pageSize);

            result.put(Constant.RESPONSE_DATA, pageResult);
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
     * 获取首页分类列表信息
     * @param response
     */
    @RequestMapping("/api/info/showByType.htm")
    public void showByType( HttpServletResponse response,Integer type){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<ClFlowInfo> infos = clFlowInfoService.getListByType(type);
            result.put(Constant.RESPONSE_DATA, infos);
            result.put(Constant.RESPONSE_CONNECT, type);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 获取首页分类图标信息
     * @param response
     */
    @RequestMapping("/api/info/sortPicList.htm")
    public void sortPicList( HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<HomeSort> homeSort = clFlowInfoService.getShowPicList();
            result.put(Constant.RESPONSE_DATA, homeSort);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }


    /**
     * 贷款所有列表
     * @param current
     * @param limit
     * @param day
     * @param response
     */
    @RequestMapping("/api/info/showAll.htm")
    public void showAll(
            @RequestParam(value="current") int current,
            @RequestParam(value="limit") int limit,
            @RequestParam(value="day") int day,
            HttpServletResponse response ){
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> param = new HashMap<>();
        try {
            param.put("limit",limit);
            param.put("day",day);
            List<ClFlowInfo> infos = clFlowInfoService.getAll(current,param);
            result.put(Constant.RESPONSE_DATA, infos);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 详情页
     * @param id
     * @param pCode
     * @param response
     */
    @RequestMapping("/api/info/showDetail.htm")
    public void showDetail(
            @RequestParam(value="id") Long id,
            @RequestParam(value="pCode") String pCode,
            HttpServletResponse response
    ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            ClFlowInfo info = clFlowInfoService.getDetail(id,pCode);
            result.put(Constant.RESPONSE_DATA, info);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 立即申请点击记录
     * @param id
     * @param pCode
     * @param response
     */
    @RequestMapping("/api/act/info/getUrl.htm")
    public void getUrl(
            @RequestParam(value="id") Long id,
            @RequestParam(value="pCode") String pCode,
            HttpServletResponse response
    ){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            clFlowInfoService.getUrl(id,pCode);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * 首页banner图接口
     * @param response
     */
    @RequestMapping("/api/info/getPicUrl.htm")
    public void getPicUrl(HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> data = new HashMap<>();
        try {
            List<FlowPic> pics = flowPicService.getPic(1);
            List<FlowRadio> radios = flowRadioService.getRadio();
            data.put("pics",pics);
            data.put("radios",radios);
            result.put(Constant.RESPONSE_DATA, data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
