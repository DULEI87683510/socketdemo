package com.dl.socketdemo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *@ClassName NettySocketServer
 *@Description TODO
 *@Author DL
 *@Date 2019/10/18 16:49    
 *@Version 1.0
 */
@Component
public class NettySocketServer {
    //用于客户端连接请求

    private EventLoopGroup bossGroup = new NioEventLoopGroup();

    //用于处理客户端I/O操作

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    //服务器的辅助启动类

    private ServerBootstrap serverBootstrap =new ServerBootstrap();


    private ChannelFuture channelFuture;
    @Autowired
    private SocketChildChannelHandler socketChildChannelHandler;
    /**
     * 服务端口
     */
    @Value("${socket-port:9999}")
    private int port;

    public NettySocketServer(){

        System.out.println("初始化netty");
    }
    @PostConstruct
    public void run() {

        // TODO Auto-generated method stub
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bulid(port);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();





    }

    public void bulid(int port) throws Exception{

        try {

            //（1）boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
            //（2）配置客户端的channel类型
            //(3)配置TCP参数，握手字符串长度设置
            //(4)TCP_NODELAY是一种算法，为了充分利用带宽，尽可能发送大块数据，减少充斥的小块数据，true是关闭，可以保持高实时性,若开启，减少交互次数，但是时效性相对无法保证
            //(5)开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
            //(6)netty提供了2种接受缓存区分配器，FixedRecvByteBufAllocator是固定长度，但是拓展，AdaptiveRecvByteBufAllocator动态长度
            //(7)绑定I/O事件的处理类,WebSocketChildChannelHandler中定义
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))
                    .childHandler(socketChildChannelHandler);


            channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("netty服务端启动成功");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            // TODO: handle exception
            //优雅关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }finally {
            close();
        }

    }

    //执行之后关闭

    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();


    }

}
