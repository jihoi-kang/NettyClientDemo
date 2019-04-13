package com.example.kjh.nettyclientdemo.main;

import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.netty.FutureListener;
import com.example.kjh.nettyclientdemo.netty.NettyClient;

public class MainModel implements MainContract.Model {


    @Override
    public void sendMessage(MessageHolder holder, FutureListener listener) {
        NettyClient.getInstance().sendMsgToServer(holder, listener);
    }
}
