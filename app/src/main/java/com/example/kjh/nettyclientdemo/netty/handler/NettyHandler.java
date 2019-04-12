package com.example.kjh.nettyclientdemo.netty.handler;

import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.netty.NettyClient;
import com.example.kjh.nettyclientdemo.netty.NettyListener;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyHandler extends ChannelInboundHandlerAdapter {

    private NettyListener listener;

    public NettyHandler(NettyListener listener) {
        this.listener = listener;
    }

    /**------------------------------------------------------------------
     메서드 ==> channelActive() -- 채널 활성화시
     ------------------------------------------------------------------*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        NettyClient.getInstance().setConnect(true);
        listener.connectStatusChanged(NettyListener.STATUS_CONNECT_SUCCESS);
    }

    /**------------------------------------------------------------------
     메서드 ==> channelRead() -- 서버로 부터 메시지 수신
     ------------------------------------------------------------------*/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof MessageHolder) {
            if(listener != null) {
                MessageHolder messageHolder = (MessageHolder) msg;
                listener.onMessageResponse(messageHolder);
            }
        }
    }

    /**------------------------------------------------------------------
     메서드 ==> channelReadComplete()
     ------------------------------------------------------------------*/
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**------------------------------------------------------------------
     메서드 ==> channelInactive() -- 채널 비활성시
     ------------------------------------------------------------------*/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        NettyClient.getInstance().setConnect(false);
        listener.connectStatusChanged(NettyListener.STATUS_CONNECT_CLOSED);
        NettyClient.getInstance().reconnect();
    }

    /**------------------------------------------------------------------
     메서드 ==> exceptionCaught() -- Exception 발생시
     ------------------------------------------------------------------*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
