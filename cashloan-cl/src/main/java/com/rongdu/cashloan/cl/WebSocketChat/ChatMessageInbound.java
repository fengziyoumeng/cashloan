package com.rongdu.cashloan.cl.WebSocketChat;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class ChatMessageInbound extends MessageInbound{

    /**
     *  建立连接的触发事件
     * @param wsOutbound
     */
    @Override
    protected void onOpen(WsOutbound wsOutbound){
        ChatMsgInboundPool.getMessageInbounds().add(this); //向连接池添加当前的连接对象
        super.onOpen(wsOutbound);
    }

    /**
     *  关闭连接的触发事件
     * @param status
     */
    @Override
    protected void onClose(int status){
        ChatMsgInboundPool.getMessageInbounds().remove(this); //向连接池删除当前的连接对象
        super.onClose(status);
    }

    /**
     *  二进制消息
     * @param byteBuffer
     * @throws IOException
     */
    @Override
    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {

    }

    /**
     *  文本消息
     * @param charBuffer
     * @throws IOException
     */
    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        for(MessageInbound messageInbound : ChatMsgInboundPool.getMessageInbounds()){
            CharBuffer cb = CharBuffer.wrap(charBuffer); //消息放到缓冲区
            WsOutbound wsOutbound = messageInbound.getWsOutbound();
            wsOutbound.writeTextMessage(cb);
            wsOutbound.flush();
        }
    }
}
