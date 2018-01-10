package com.rongdu.cashloan.manage.controller;

import com.github.pagehelper.Page;
import com.rongdu.cashloan.cl.domain.ClickTrack;
import com.rongdu.cashloan.cl.service.IClickTrackService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.RdPage;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
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
 * 点击轨迹记录Controller
 */
@Scope("prototype")
@Controller
public class TrackController extends BaseController {
    @Resource
    private IClickTrackService clickTrackService;

    @RequestMapping("/act/query/trail/recode.htm")
    public void  queryTrailRecodes(@RequestParam(value="searchParams",required=false) String searchParams,
                             @RequestParam(value = "current") int current,
                             @RequestParam(value = "pageSize") int pageSize ) throws Exception {
        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        if(params!=null){
            params.put("userId",(String)params.get("userId"));
            params.put("beginTime",(String)params.get("beginTime"));
            params.put("endTime",(String)params.get("endTime"));
            params.put("channelName",(String)params.get("channelName"));
        }
        Page<ClickTrack> clickTrackList = clickTrackService.queryTrailRecodes(params,current,pageSize);
        Map<String,Object> result = new HashMap<String,Object>();
        result.put(Constant.RESPONSE_DATA, clickTrackList);
        result.put(Constant.RESPONSE_DATA_PAGE, new RdPage(clickTrackList));
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "获取成功");
        ServletUtils.writeToResponse(response,result);
    }
}