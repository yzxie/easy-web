package com.yzxie.easy.log.web.netty.handler;

import com.yzxie.easy.log.web.netty.NettyConstant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author xieyizun
 * @date 16/11/2018 11:35
 * @description: 连接管理，后期接入白名单、认证等
 */
@Slf4j
public class ConnectHandler extends ChannelInboundHandlerAdapter {
    /**
     * 客户端连接建立
     * @param channelHandlerContext
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress)channelHandlerContext.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();
        // 记录当前连接的远程客户端IP
        channelHandlerContext.channel().attr(NettyConstant.CLIENT_IP).set(clientIp);

        log.info("receive client connection: {}", clientIp);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        String clientIp = channelHandlerContext.channel().attr(NettyConstant.CLIENT_IP).get();
        log.info("client connection inactive: {}", clientIp);
    }
}
