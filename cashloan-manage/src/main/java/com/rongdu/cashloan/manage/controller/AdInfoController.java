package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.AdInfo;
import com.rongdu.cashloan.cl.service.AdInfoService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class AdInfoController extends BaseController{
    @Resource
    private AdInfoService adInfoService;

    @RequestMapping("/act/adinfo/getall.htm")
    public void getAllAdInfoList(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<AdInfo> adInfos = adInfoService.selectAll();
            result.put(Constant.RESPONSE_DATA, adInfos);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/act/adinfo/delete.htm")
    public void deleteAdInfo(@RequestParam(value ="id") Long id,
                             @RequestParam(value = "adUrl") String adUrl){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            adInfoService.deleteAdInfoByid(id,adUrl);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/act/adinfo/saveorupdate.htm")
    public void saveOrUpdate(@RequestParam(value = "form") String data){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            adInfoService.saveOrUpdate(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "操作成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请稍后重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
