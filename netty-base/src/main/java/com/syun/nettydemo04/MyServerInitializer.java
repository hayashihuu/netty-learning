package com.syun.nettydemo04;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @description: 读写空闲的测试
 * @program: netty-base
 * @author: syun
 * @create: 2019-01-08 15:16
 */

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new IdleStateHandler(5, 7, 10, TimeUnit.SECONDS))
                .addLast(new MyServerHandler());

    }
}
