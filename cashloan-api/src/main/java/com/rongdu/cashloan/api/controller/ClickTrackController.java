package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 点击轨迹记录Controller
 */
@Scope("prototype")
@Controller
public class ClickTrackController {
    @Resource
    private IClickTrackService clickTrackService;


    @RequestMapping("/api/act/trail/recode.htm")
    public void trailRecode(HttpServletResponse response, ClickTrack clickTrack){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            clickTrackService.saveInRedis(clickTrack);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存到缓存成功");
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常,保存失败");
        }
        ServletUtils.writeToResponse(response,result);
    }
}