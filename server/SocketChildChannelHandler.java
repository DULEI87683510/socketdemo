package com.dl.socketdemo.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SocketChildChannelHandler extends ChannelInitializer<SocketChannel> {
    
   @Autowired
   private SocketServerHandler socketServerHandler;
    


    

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // TODO Auto-generated method stub
    /*    //分割接收到的Bytebu，根据指定的分割符
     // ch.pipeline().addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));

        //心跳，10秒没有读或者写的操作，触发执行方法
         ch.pipeline().addLast(new IdleStateHandler(0, 0,15,TimeUnit.SECONDS));
         ch.pipeline().addLast("http-codec",new HttpServerCodec());
      // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消息
      ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
      // ChunkedWriteHandler：向客户端发送HTML5文件
      ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());*/
      ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
      ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
       ch.pipeline().addLast("http-codec", new HttpServerCodec());
        ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
    // ch.pipeline().addLast("w",new  WebSocketServerProtocolHandler("/ws",null,true,65535));  
     
        ch.pipeline().addLast("handler", socketServerHandler);
    }


}
