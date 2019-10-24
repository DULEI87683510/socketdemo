package com.dl.socketdemo.server;



import com.alibaba.fastjson.JSONObject;
import com.dl.socketdemo.user.entity.User;
import com.dl.socketdemo.user.service.impl.UserServiceImpl;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@Sharable
public class SocketServerHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger log = LoggerFactory
            .getLogger(SocketServerHandler.class);
    @Autowired
    private UserServiceImpl userService;
    /**
     * 推送单个
     */
    public static final void push(final ChannelHandlerContext ctx, final String message) {

        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        ctx.channel().writeAndFlush(tws);

    }

    /**
     * 群发
     */
    public static final void push(final ChannelGroup ctxGroup, final String message) {

        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        ctxGroup.writeAndFlush(tws);

    }

    /**
     * 当客户端连接成功，返回个成功信息
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
    log.info("客户端：{}已连接",ctx.channel().remoteAddress());
    }

    /**
     * 当客户端断开连接
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub

        log.info("客户端：{}已断开连接",ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // TODO Auto-generated method stub
        ctx.flush();
    }
    //收到客户端发来的消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // TODO Auto-generated method stub
        log.info("收到客户端-{}发来的消息：{}",ctx.channel().remoteAddress(),msg);
        User user= JSONObject.parseObject(msg,User.class);
        userService.saveUser(user);


    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


    }
}