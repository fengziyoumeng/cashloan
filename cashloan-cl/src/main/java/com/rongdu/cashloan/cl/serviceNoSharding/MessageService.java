package com.rongdu.cashloan.cl.serviceNoSharding;

import com.rongdu.cashloan.cl.vo.MessageVo;
import com.rongdu.cashloan.core.common.service.BaseService;
import com.rongdu.cashloan.cl.domain.Message;

import java.util.List;
import java.util.Map;

/**
 * 用户消息Service
 * 
 * @author Yang
 * @version 1.0.0
 * @date 2018-01-26 13:31:57
 * Copyright 杭州民华金融信息服务有限公司  cashloan All Rights Reserved
 * 官方网站：www.yongqianbei.com
 * 未经授权不得进行修改、复制、出售及商业使用
 */
public interface MessageService extends BaseService<Message, Long>{
    List<MessageVo> selectAllMessage(Long userId) throws Exception;

    void sendMessage(String parentCode,String childenCode1,String childenCode2,Long receiving,Integer msgType,Long ...procId);

    List<Message> messageList() throws Exception;

    void saveOrUpdateMessage(String data);

    void deleteMessage(Long id);
}
