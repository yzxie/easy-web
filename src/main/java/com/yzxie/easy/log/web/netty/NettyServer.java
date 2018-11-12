package com.yzxie.easy.log.web.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xieyizun
 * @date 11/11/2018 21:06
 * @description:
 */
@Component
public class NettyServer {
    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

    private static final int WORKER_NUM = Runtime.getRuntime().availableProcessors()/2;

    @Autowired
    private NettyServerInitializer nettyServerInitializer;

    /**
     * 监听端口号
     */
    @Value("${netty.port}")
    private int port;

    /**
     * 启动netty服务器
     */
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(WORKER_NUM);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 服务端自身配置
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            // 处理客户端请求的配置
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(nettyServerInitializer);
            // 绑定端口，开始接收进来的连接
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            LOG.info("netty listen on port: {}", port);
            // 等待服务器socket关闭
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            LOG.error("netty server start failed {}", e, e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
