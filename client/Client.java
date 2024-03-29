package com.dl.socketdemo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;

@Slf4j
public class Client {
    public static void main(String[] args) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new MyClientInitializer());


        ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("连接成功");
                } else {
                    log.info("连接失败");
                }
            }
        });

        Channel channel = channelFuture.channel();
      //  channel.closeFuture().sync();
        //标准输入
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        //利用死循环，不断读取客户端在控制台上的输入内容
        for (; ; ) {
            channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
        }


    }

}



