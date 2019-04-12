package com.example.kjh.nettyclientdemo.netty.protocol;

import com.example.kjh.nettyclientdemo.data.MessageHolder;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 프로토콜을 인코딩하는 클래스
 * @author 강지회
 * @version 1.0.0
 * @since 2019. 4. 12. AM 11:52
 **/
public class ProtocolEncoder extends MessageToByteEncoder<MessageHolder> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageHolder msg, ByteBuf out) throws UnsupportedEncodingException {
        String body = msg.getBody();

        if(body == null)
            throw new NullPointerException("MessageHolder's body is null");

        byte[] bytes = body.getBytes("utf-8");

//        프로토콜 헤더 만들기
        out.writeShort(ProtocolHeader.START)
                .writeByte(msg.getSign())
                .writeByte(msg.getType())
                .writeInt(bytes.length)
                .writeBytes(bytes);
    }
}
