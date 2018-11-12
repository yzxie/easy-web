package com.yzxie.easy.log.web.netty.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzxie.easy.log.web.data.ResData;
import com.yzxie.easy.log.web.service.WebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author xieyizun
 * @date 11/11/2018 21:43
 * @description:
 */
@Component
public class LogMessageHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger LOG = LoggerFactory.getLogger(LogMessageHandler.class);

    @Autowired
    private WebSocketService webSocketService;

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
            LOG.info("received client pong.");
            return;
        }

        try {
            // 报文解析
            JSONObject data = JSON.parseObject(msg);
            // 将消息通过websocket传递给浏览器
            webSocketService.broadcastLogMessageToClients(data);
            LOG.info("LogMessageHandler received: {}", data);
            // 响应log-engine客户端
            response = new StringBuilder();
            response.append(res);
            response.append("\n");
            channelHandlerContext.writeAndFlush(response);
        } catch (Exception e) {
            res.setRet(1);
            res.setData("server error");
            LOG.error("log message handle error, {}, {}", e, e.getMessage());
        }
    }

    /**
     * 客户端连接建立
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();
        LOG.info("receive client connection: {}", clientIp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        // 当出现异常时关闭连接
        channelHandlerContext.close();
    }

    /**
     * 长连接心跳检测
     * @param channelHandlerContext
     * @param evt
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent)evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    LOG.info("client READER_IDLE, ping.");
                    StringBuilder ping = new StringBuilder("ping").append("\n");
                    channelHandlerContext.writeAndFlush(ping);
                    break;
                case WRITER_IDLE:
                    LOG.info("client WRITER_IDLE, ping.");
                    break;
                case ALL_IDLE:
                    LOG.info("client ALL_IDLE, ping.");
                    break;
            }
        } else {
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
