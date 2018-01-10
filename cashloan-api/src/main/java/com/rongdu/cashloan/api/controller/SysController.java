package com.rongdu.cashloan.api.controller;

import java.util.HashMap;
import java.util.Map;

import com.rongdu.cashloan.core.common.context.Global;
import com.rongdu.cashloan.core.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.CacheUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import com.rongdu.cashloan.system.permission.annotation.RequiresPermission;

import javax.servlet.http.HttpServletRequest;

/**
 * API System Controller
 * 
 * @author gc
 * @version 1.0.0
 * @date 2017年4月29日 下午3:47:24
 * Copyright 杭州民华金融信息服务有限公司 All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */

@Controller
@Scope("prototype")
public class SysController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysController.class);

    /**
     * 重加载系统配置数据，建议每次部署系统时修改下arc_sys_config表中的CacheSecurityCode
     * 如：api.jijiehao.com/system/config/reload.htm?code=iD7pghnuAN6RjeBVFO45B2JdiL956ciz
     * @throws Exception
     */
    @RequestMapping("/system/config/reload.htm")
    public void reload(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        //每次发布系统时更改一次code
        if(!StringUtil.isBlank(code) && Global.getValue("CacheSecurityCode").equals(code)){
            // 调用缓存辅助类 重加载系统配置数据
            CacheUtil.initSysConfig();
            logger.info("==========>缓存刷新成功！");
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            returnMap.put(Constant.RESPONSE_CODE_MSG, Constant.OPERATION_SUCCESS);
            ServletUtils.writeToResponse(response, returnMap);
        }
    }
    
}
