package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.BankInfo;
import com.rongdu.cashloan.cl.service.IBankInfoService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 信用卡模块
 */
@Scope("prototype")
@Controller
public class BankInfoController {
    @Resource
    private IBankInfoService bankInfoService;


    @RequestMapping("/api/credit/list.htm")
    public void showDetail(HttpServletResponse response,Long showType){

        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<BankInfo> bankInfoList = bankInfoService.getListByType(showType);
            result.put(Constant.RESPONSE_DATA, bankInfoList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

}
