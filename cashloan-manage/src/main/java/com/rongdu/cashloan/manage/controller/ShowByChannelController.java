package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.CompanyProdDetail;
import com.rongdu.cashloan.cl.service.ChannelService;
import com.rongdu.cashloan.cl.vo.ChannelForH5;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.JsonUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.util.StringUtil;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.system.domain.SysUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
public class ShowByChannelController extends BaseController {

    @Resource
    private ChannelService channelService;

    /**
     * 根据渠道用户查询对应的注册数
     *
     * @throws Exception
     */
    @RequestMapping("/modules/manage/channel/showregister.htm")
    public void registerCountList80(@RequestParam(value="searchParams",required=false) String searchParams,
                                    @RequestParam(value = "current") int current,
                                    @RequestParam(value = "pageSize") int pageSize) throws Exception {

        Map<String, Object> params = JsonUtil.parse(searchParams, Map.class);
        Date beginTime = null;
        Date endTime = null;
        if (params!=null && StringUtil.isNotBlank(params.get("beginTime")) &&
                StringUtil.isNotBlank(params.get("endTime"))){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            beginTime =sdf.parse((String)params.get("beginTime"));
            endTime =sdf.parse((String)params.get("endTime"));
        }

        SysUser loginUser = this.getLoginUser(request);
        String number = loginUser.getNumber();

        List<ChannelForH5> channelForH5 = new ArrayList<>();
        List<ChannelForH5> list = channelService.registerCountFor80(beginTime,endTime);

        if(StringUtil.isNotBlank(number)){
            for (ChannelForH5 channel : list) {
                if(channel.getChannelCode().equals(number)){
                    channelForH5.add(channel);
                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put(Constant.RESPONSE_DATA, channelForH5);
        result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
        result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        ServletUtils.writeToResponse(response, result);
    }
}
