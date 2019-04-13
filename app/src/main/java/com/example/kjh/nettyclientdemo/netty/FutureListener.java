package com.example.kjh.nettyclientdemo.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/** Socket Write 성공 여부를 알리는 클래스 */
public abstract class FutureListener implements ChannelFutureListener {

    @Override
    public void operationComplete(ChannelFuture future) {
        if (future.isSuccess()) {
            success();
        } else {
            error();
        }
    }

    public abstract void success();

    public abstract void error();
}
