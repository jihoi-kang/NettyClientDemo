package com.example.kjh.nettyclientdemo.netty;

import com.example.kjh.nettyclientdemo.netty.handler.NettyHandler;
import com.example.kjh.nettyclientdemo.netty.protocol.ProtocolDecoder;
import com.example.kjh.nettyclientdemo.netty.protocol.ProtocolEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private NettyListener listener;

    public NettyClientInitializer(NettyListener listener) {
        this.listener = listener;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

//        IN / OUT bound
//        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("IdleStateHandler", new IdleStateHandler(6, 0, 0));
//        Inbound
        pipeline.addLast("ProtocolDecoder", new ProtocolDecoder());
//        Outbound
        pipeline.addLast("ProtocolEncoder", new ProtocolEncoder());
//        Inbound
        pipeline.addLast("NettyHandler", new NettyHandler(listener));

    }
}
