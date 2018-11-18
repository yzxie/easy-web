package com.yzxie.easy.log.web.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.WebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xieyizun
 * @date 11/11/2018 21:43
 * @description:
 */
@Slf4j
public class LogMessageHandler extends SimpleChannelInboundHandler<String> {
    private WebSocketService webSocketService;

    public LogMessageHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * netty5.0 -> messageReceived，收到客户端的请求
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        StringBuilder response = null;
        ResData res = new ResData();
        if ("pong".equals(msg)) {
            log.info("received client pong.");
            return;
        }

        try {
            // 报文解析
            JSONObject data = JSON.parseObject(msg);
            // 将消息通过websocket传递给浏览器
            webSocketService.broadcastLogMessageToClients(data);
            log.info("LogMessageHandler received: {}", data);
            // 响应log-engine客户端
            response = new StringBuilder();
            response.append(res);
            response.append("\n");
            channelHandlerContext.writeAndFlush(response);
        } catch (Exception e) {
            res.setRet(1);
            res.setData("server error");
            log.error("log message handle error, {}, {}", e, e.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        // 当出现异常时关闭连接
        channelHandlerContext.close();
        log.error("exception catch, close channel {}", cause.getStackTrace());
    }
}
