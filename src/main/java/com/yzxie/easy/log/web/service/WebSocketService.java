package com.yzxie.easy.log.web.service;

import com.alibaba.fastjson.JSONObject;
import com.yzxie.easy.log.web.constant.WebSocketConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xieyizun
 * @date 4/11/2018 15:09
 * @description:
 */
@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;


    public void broadcastMessageToClients(JSONObject content) {
        simpMessagingTemplate.convertAndSend(WebSocketConstant.BROADCAST_CHANNEL, content);
    }

    public void sendChatMessage(String toUserName, JSONObject content) {
        simpMessagingTemplate.convertAndSendToUser(toUserName, WebSocketConstant.P2P_CHANNEL, content);
    }

    public void broadcastLogMessageToClients(JSONObject logContent) {
        simpMessagingTemplate.convertAndSend(WebSocketConstant.LOG_CHANNEL, logContent);
    }
}
