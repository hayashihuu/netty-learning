package com.syun.nettydemo06.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/*
 * @description:模拟粘包,拆包
 * @program: netty-demo01
 * @author: syun
 * @create: 2018-09-12 13:40
 */
@Slf4j
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private int counter;

    private byte[] req;

    public TimeClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message;
        for (int i = 0; i < 3; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message)
                    .addListener((ChannelFutureListener) future -> {
                        if (future.isSuccess()) {
                            log.info("写入成功");
                        } else {
                            log.info("写入失败");
                            future.cause().printStackTrace();
                        }
                    });
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is:" + body + "; the counter is:" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Unexcepted exception from downstream:" + cause.getMessage());
        ctx.close();
    }
}
