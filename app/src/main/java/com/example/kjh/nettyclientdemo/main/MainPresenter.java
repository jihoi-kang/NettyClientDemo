package com.example.kjh.nettyclientdemo.main;

import com.example.kjh.nettyclientdemo.data.ChatLogs;
import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.netty.FutureListener;
import com.example.kjh.nettyclientdemo.netty.protocol.ProtocolHeader;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    private MainModel mainModel;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        mainModel = new MainModel();
    }

    @Override
    public void onClickedSendBtn() {
        ChatLogs chatLogs = new ChatLogs();
        chatLogs.setBody(mainView.getBody());

        MessageHolder holder = new MessageHolder();
        holder.setSign(ProtocolHeader.REQUEST);
        holder.setChatLogs(chatLogs);
        switch (mainView.getType()) {
            case "conn":
                holder.setType(ProtocolHeader.CONN);
                break;
        }

        mainModel.sendMessage(holder, new FutureListener() {
            @Override
            public void success() {

            }

            @Override
            public void error() {

            }
        });


    }

}
