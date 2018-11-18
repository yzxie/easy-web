package com.yzxie.easy.log.web.netty;

import com.yzxie.easy.log.web.netty.handler.ConnectHandler;
import com.yzxie.easy.log.web.netty.handler.LogMessageHandler;
import com.yzxie.easy.log.web.netty.handler.PingReqHandler;
import com.yzxie.easy.log.web.service.WebSocketService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @author xieyizun
 * @date 11/11/2018 21:24
 * @description:
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private WebSocketService webSocketService;

    public NettyServerInitializer(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    /**
     * 初始化与客户端连接的channel
     * @param socketChannel
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 设置心跳检查，维持长连接
        // 客户端写日志数据过来，则服务端为读
        pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
        // 基于换行符分隔
        pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        // 注意：数据读写相关的handler需要放在encoder和decoder后面，否则无法读取或写数据
        pipeline.addLast(new ConnectHandler());
        pipeline.addLast(new PingReqHandler());
        // 真正的数据处理器
        pipeline.addLast(new LogMessageHandler(this.webSocketService));
    }
}
