package com.yzxie.easy.log.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.data.websocket.NormalMessage;
import com.yzxie.easy.log.web.data.websocket.P2pMessage;
import com.yzxie.easy.log.web.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xieyizun
 * @date 4/11/2018 18:06
 * @description:
 */
@RestController
public class WebSocketController {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketController.class);

    private AtomicLong broadCastCount = new AtomicLong();

    @Autowired
    private WebSocketService webSocketService;


    /**
     * 接收客户端发送过来的消息
     * /acceptMessage对应客户端sockjs.send方法的topic参数
     * @param message
     */
    @MessageMapping("/groupMessage")
    public void groupMessage(NormalMessage message) {
        if (message != null) {
            long count = broadCastCount.incrementAndGet();
            JSONObject broadcastMessage = new JSONObject();
            broadcastMessage.put("content", message.getContent());
            webSocketService.broadcastMessageToClients(broadcastMessage);

            LOG.info("groupMessage: {}, No.{}", message.getContent(), count);
        }
    }

    /**
     * 点对点聊天
     */
    @MessageMapping("/chat")
    public void chat(P2pMessage message) {
        if (message != null) {
            JSONObject toMessage = new JSONObject();
            toMessage.put("content", message.getContent());
            webSocketService.sendChatMessage(message.getUserName(), toMessage);

            LOG.info("chat to {}", message.getUserName());
        }
    }
}
