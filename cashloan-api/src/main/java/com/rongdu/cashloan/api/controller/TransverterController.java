package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.Transverter;
import com.rongdu.cashloan.cl.service.TransverterService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Scope("prototype")
@Controller
public class TransverterController {

    @Resource
    private TransverterService transverterService;

    /**
     * ios原始开关接口
     * @param response
     */
    @RequestMapping("/api/info/transverter.htm")
    public void transverter( HttpServletResponse response){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Transverter transverter = transverterService.getSwitchCode("appstore");

            result.put(Constant.RESPONSE_DATA, transverter.getSwitchCode());
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);

            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * ios按版本号获取开关接口
     * @param response
     */
    @RequestMapping("/api/ios/version/transverter.htm")
    public void transverterByIosVersion( HttpServletResponse response,String iosVersion){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Transverter transverter = transverterService.getSwitchCode(iosVersion);
            result.put(Constant.RESPONSE_DATA, transverter.getSwitchCode());
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    /**
     * android开关接口
     * @param response
     */
    @RequestMapping("/api/info/androidTransverter.htm")
    public void transverterAndroid( HttpServletResponse response,String channel){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            Transverter transverter = transverterService.getSwitchCode(channel);
            result.put(Constant.RESPONSE_DATA, transverter.getSwitchCode());
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
