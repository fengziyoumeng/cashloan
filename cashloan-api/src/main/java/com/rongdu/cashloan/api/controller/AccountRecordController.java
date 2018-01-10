package com.rongdu.cashloan.api.controller;


import com.rongdu.cashloan.cl.util.FileUtil;
import com.rongdu.cashloan.api.util.ParamterUtil;
import com.rongdu.cashloan.cl.domain.AccountRecord;
import com.rongdu.cashloan.cl.service.IAccountRecordService;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.DateUtil;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Scope("prototype")
@Controller
@CrossOrigin
@RequestMapping("/api/account")
public class AccountRecordController {

    public static final Logger logger = LoggerFactory.getLogger(AccountRecordController.class);

    @Resource
    private IAccountRecordService accountRecordService;

    @RequestMapping("/query/records")
    public void queryRecords(HttpServletResponse response,String userId){
        logger.info(String.format("查询用户记账账单>>前端传入的参数userId【%s】",userId));
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> paramsMap = new HashMap<>();
        try {
            paramsMap.put("userId",userId);
            Map<String, Object> resultParamMap = accountRecordService.totalAccRecords(paramsMap);
            result.put(Constant.RESPONSE_DATA, resultParamMap);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/query/detail/record")
    public void queryRecordsDetail(HttpServletResponse response,String id){
        logger.info(String.format("查询用户记账账单详情>>前端传入的参数id【】",id));
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String,Object> paramsMap = new HashMap<>();
        try {
            paramsMap.put("id",id);
            AccountRecord accountRecord = accountRecordService.queryRecordsDetail(paramsMap);
            result.put(Constant.RESPONSE_DATA, accountRecord);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/save/records")
    public void saveRecords(HttpServletResponse response,AccountRecord accountRecord){
        logger.info(String.format("保存记账记录>>前端传入的参数【%s】",accountRecord.toString()));
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            int repayDay = Integer.parseInt(FileUtil.StringToNumber(accountRecord.getRepayDay()));//还款日
            int currentDay = DateUtil.getDay(new Date());
            if(currentDay>repayDay){//当前日期>还款日
                accountRecord.setRemainRepayDay("当前已超出预期还款日，请还款！");
            }else{
                accountRecord.setRemainRepayDay(String.valueOf(repayDay-currentDay));
            }
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            accountRecord.setLastRepayDay(month+"."+repayDay);
            accountRecord.setRepayRemind(accountRecord.getRepayRemind());
            accountRecord.setIsEnabled(1);
            accountRecord.setCreateTime(new Date());
            int i = accountRecordService.insert(accountRecord);
            if(i==1){
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "记账成功");
            }else{
                result.put(Constant.RESPONSE_CODE, Constant.FAIL_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "记账失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/update/repayStatus")
    public void updateRepayStatus(HttpServletResponse response, Long id,Integer repayStatus){
        logger.info(String.format("更新记账已还>>前端传入的参数id【%s】、repayStatus【%s】",id,repayStatus));
        Map<String,Object> result = new HashMap<String,Object>();
        Map<String, Object> paramsMap = new HashMap<>();
        try {
            paramsMap.put("id",id);
            paramsMap.put("repayStatus",repayStatus);
            int i =  accountRecordService.updateRepayStatus(paramsMap);
            if(i==1){
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "更新记账已还成功");
            }else{
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "更新记账已还失败");
            }
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/delete/status")
    public void deleteStatus(HttpServletResponse response, Long id){
        logger.info(String.format("删除记账账单>>前端传入的参数id【%s】",id));
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            int i =  accountRecordService.deleteStatus(id);
            if(i==1){
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "删除记账账单成功");
            }else{
                result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
                result.put(Constant.RESPONSE_CODE_MSG, "删除记账账单失败");
            }
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
