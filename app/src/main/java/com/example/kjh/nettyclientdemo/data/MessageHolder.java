package com.example.kjh.nettyclientdemo.data;

import io.netty.channel.Channel;

public class MessageHolder {

    private byte sign;
    private byte type;
    private String body;
    private Channel channel;

    public byte getSign() {
        return sign;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "MessageHolder{" +
                "sign=" + sign +
                ", type=" + type +
                ", body='" + body + '\'' +
                ", channel=" + channel +
                '}';
    }
}
