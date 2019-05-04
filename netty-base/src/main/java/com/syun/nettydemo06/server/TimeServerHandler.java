package com.syun.nettydemo06.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
 * @description:
 * @program: netty-demo01
 * @author: syun
 * @create: 2018-09-12 13:38
 */
@Slf4j
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private int counter;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String body = (String) msg;
        System.out.println("The time server receive order:" + body + "; the counter is:" + ++counter);
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        Channel channel = ctx.channel();
        Runnable writer = () -> {
            log.info("写入。。。");
            channel.writeAndFlush(resp);
        };
//        使用不同的线程写入
//        ctx.writeAndFlush(resp);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(writer);
//        executor.execute(writer);

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("channel read complete ");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("exceptionCaught");
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel 注册");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("channel 取消注册");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel 处于活跃状态");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive 处于非活跃状态，没有连接到远程主机");
        super.channelInactive(ctx);
    }
}
