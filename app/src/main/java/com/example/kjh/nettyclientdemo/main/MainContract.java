package com.example.kjh.nettyclientdemo.main;

import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.netty.FutureListener;

public interface MainContract {

    interface View {

        String getType();
        String getBody();

    }

    interface Presenter {

        void onClickedSendBtn();
    }

    interface Model {

        void sendMessage(MessageHolder holder, FutureListener listener);
    }

}
