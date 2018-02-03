package com.rongdu.cashloan.cl.WebSocketChat;

import org.apache.catalina.websocket.MessageInbound;

import java.util.ArrayList;
import java.util.List;

/**
 *  保存当前连接客户的数量
 */
public class ChatMsgInboundPool {

    private static List<MessageInbound> msgList = new ArrayList<MessageInbound>();

    static List<MessageInbound> getMessageInbounds(){
        return msgList;
    }
}
