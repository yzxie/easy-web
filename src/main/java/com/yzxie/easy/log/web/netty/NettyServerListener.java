package com.yzxie.easy.log.web.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author xieyizun
 * @date 11/11/2018 22:03
 * @description: netty服务启动监听器
 */
@WebListener
public class NettyServerListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(NettyServerListener.class);

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("NettyServerListener: spring context inited.");
        Thread nettyServerThread = new Thread(new NettyServerThread());
        nettyServerThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOG.info("NettyServerListener: spring context closed.");
    }

    /**
     * netty服务启动线程
     */
    private class NettyServerThread implements Runnable {

        @Override
        public void run() {
            nettyServer.start();
        }
    }
}
