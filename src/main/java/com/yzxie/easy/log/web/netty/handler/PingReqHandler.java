package com.yzxie.easy.log.web.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xieyizun
 * @date 15/11/2018 09:59
 * @description:
 */
public class PingReqHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(PingReqHandler.class);

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
                    StringBuilder ping = new StringBuilder("ping").append("\n");
                    channelHandlerContext.writeAndFlush(ping);
                    LOG.info("client READER_IDLE, ping.");
                    break;
                case WRITER_IDLE:
                    LOG.info("client WRITER_IDLE, ping.");
                    break;
                case ALL_IDLE:
                    LOG.info("client ALL_IDLE, ping.");
                    break;
            }
        } else {
            // 其他则传给channelpipeline中的下一个handler处理
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
