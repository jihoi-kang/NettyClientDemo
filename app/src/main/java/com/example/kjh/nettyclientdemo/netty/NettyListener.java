package com.example.kjh.nettyclientdemo.netty;

import com.example.kjh.nettyclientdemo.data.MessageHolder;

public interface NettyListener {

    int STATUS_CONNECT_ERROR = 0;
    int STATUS_CONNECT_SUCCESS = 1;
    int STATUS_CONNECT_CLOSED = 2;

    void connectStatusChanged(int statusCode);

    void onMessageResponse(MessageHolder messageHolder);
}
