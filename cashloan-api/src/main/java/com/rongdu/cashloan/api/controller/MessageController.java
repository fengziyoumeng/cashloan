package com.rongdu.cashloan.api.controller;

import com.rongdu.cashloan.cl.domain.Message;
import com.rongdu.cashloan.cl.serviceNoSharding.MessageService;
import com.rongdu.cashloan.cl.vo.MessageVo;
import com.rongdu.cashloan.core.common.context.Constant;
import com.rongdu.cashloan.core.common.util.ServletUtils;
import com.rongdu.cashloan.core.common.web.controller.BaseController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
public class MessageController extends BaseController{

    @Resource
    private MessageService messageService;

    @RequestMapping("/api/message/getall/byuserid.htm")
    public void getMessageAll(Long userId){
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            List<MessageVo> dataList = messageService.selectAllMessage(userId);
            result.put(Constant.RESPONSE_DATA, dataList);
            result.put(Constant.RESPONSE_CODE, Constant.SUCCEED_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "查询成功");
        }catch (Exception e){
            result.put(Constant.RESPONSE_CODE, Constant.OTHER_CODE_VALUE);
            result.put(Constant.RESPONSE_CODE_MSG, "服务异常，请重试");
        }
        ServletUtils.writeToResponse(response,result);
    }
}
