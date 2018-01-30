package com.rongdu.cashloan.manage.controller;

import com.rongdu.cashloan.cl.domain.Message;
import com.rongdu.cashloan.cl.serviceNoSharding.MessageService;
import com.rongdu.cashloan.cl.vo.MessageVo;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class MessageManageController extends BaseController{

    @Resource
    private MessageService messageService;

    @RequestMapping("/act/message/getall.htm")
    public void getMessages(){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<Message> dataList = messageService.messageList();
            result.put(Constant.RESPONSE_DATA, dataList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/act/message/saveorupdate.htm")
    public void saveMessage(@RequestParam(value = "form") String data){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            messageService.saveOrUpdateMessage(data);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "保存成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }

    @RequestMapping("/act/message/delete.htm")
    public void deleteMessageById(@RequestParam(value = "id") Long id){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            messageService.deleteMessage(id);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "删除成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
