package com.rongdu.cashloan.cl.WebSocketChat;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = "/chat")
public class WebSocketChat extends WebSocketServlet {
    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest httpServletRequest) {
        return new ChatMessageInbound();
    }
}
