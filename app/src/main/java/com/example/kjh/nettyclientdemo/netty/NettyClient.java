package com.example.kjh.nettyclientdemo.netty;

import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.etc.Statics;
import com.example.kjh.nettyclientdemo.netty.protocol.ProtocolHeader;
import com.example.kjh.nettyclientdemo.netty.serializer.Serializer;

import java.io.UnsupportedEncodingException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    private static NettyClient instance = new NettyClient();
    private EventLoopGroup group;
    private NettyListener listener;
    private Channel channel;

    private boolean isConnect = false;
    private int reconnectNum = Integer.MAX_VALUE;
    private long reconnectIntervalTime = 5000;

    public static NettyClient getInstance() {
        return instance;
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버와 연결
     synchronized --> 한 시점에서 하나의 스레드만 접근이 가능
     ------------------------------------------------------------------*/
    public synchronized NettyClient connect() {
        if(!isConnect) {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap().group(group)
                                .option(ChannelOption.SO_KEEPALIVE, true)
//                                Non-blocking
                                .channel(NioSocketChannel.class)
                                .handler(new NettyClientInitializer(listener));

            try {
                ChannelFuture future = bootstrap.connect(Statics.SOCKET_HOST,Statics.SOCKET_PORT).sync();
                if(future != null && future.isSuccess()) {
                    channel = future.channel();
                    isConnect = true;
                } else {
                    isConnect = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.connectStatusChanged(NettyListener.STATUS_CONNECT_ERROR);
                reconnect();
            }

        }

        return this;
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버와 연결 끊음
     ------------------------------------------------------------------*/
    public void disconnect() {
        group.shutdownGracefully();
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버와 재연결
     ------------------------------------------------------------------*/
    public void reconnect() {
        if(reconnectNum > 0 && !isConnect) {
            reconnectNum--;
            try {
                Thread.sleep(reconnectIntervalTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            disconnect();
            connect();
        } else {
            disconnect();
        }
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버에 메시지를 보내는 기능
     ------------------------------------------------------------------*/
    public boolean sendMsgToServer(MessageHolder msg, FutureListener listener) {
        boolean flag = channel !=null && isConnect;
        if(flag) {
            String body = Serializer.serialize(msg.getChatLogs());
            if (body == null)
                throw new NullPointerException("Body is null");

            byte[] bytes = new byte[0];
            try {
                bytes = body.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ByteBuf buf = Unpooled.buffer();

            buf.writeShort(ProtocolHeader.START)
                    .writeByte(msg.getSign())
                    .writeByte(msg.getType())
                    .writeInt(bytes.length)
                    .writeBytes(bytes);

            if(listener != null){
                channel.writeAndFlush(buf).addListener(listener);
            } else {
                channel.writeAndFlush(buf).addListener(new FutureListener() {
                    @Override
                    public void success() {

                    }

                    @Override
                    public void error() {

                    }
                });
            }
        }

        return flag;
    }

    /** Setter & Getter */
    public void setListener(NettyListener listener) {
        this.listener = listener;
    }
    public boolean isConnect() {
        return isConnect;
    }
    public void setConnect(boolean isConnect) {
        this.isConnect = isConnect;
    }
    public int getReconnectNum() {
        return reconnectNum;
    }
    public void setReconnectNum(int reconnectNum) {
        this.reconnectNum = reconnectNum;
    }

}
