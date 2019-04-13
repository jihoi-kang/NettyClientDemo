package com.example.kjh.nettyclientdemo.main;

import android.os.Bundle;
import android.os.Message;

import com.example.kjh.nettyclientdemo.data.ChatLogs;
import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.netty.FutureListener;
import com.example.kjh.nettyclientdemo.netty.NettyClient;
import com.example.kjh.nettyclientdemo.netty.protocol.ProtocolHeader;
import com.example.kjh.nettyclientdemo.netty.serializer.Serializer;
import com.example.kjh.nettyclientdemo.otto.BusProvider;
import com.example.kjh.nettyclientdemo.otto.Events;
import com.squareup.otto.Subscribe;

import static com.example.kjh.nettyclientdemo.main.MainActivity.mainActHandler;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    private MainModel mainModel;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        mainModel = new MainModel();

        BusProvider.getInstance().register(this);
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

        NettyClient.getInstance().sendMsgToServer(holder, new FutureListener() {
            @Override
            public void success() {
                mainView.showToast("전송 완료");
            }

            @Override
            public void error() {
                mainView.showToast("전송 실패");
            }
        });
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void socketCallBackEvent (Events.SocketCallBack event) {
        /** CallBack 받은 데이터 문자 추출해서 View에 보내기 */
        ChatLogs chatLogs = event.getChatLogs();

        Message msg = mainActHandler.obtainMessage();

        Bundle bundle = new Bundle();
        bundle.putString("result", Serializer.serialize(chatLogs));

        msg.setData(bundle);

        mainActHandler.sendMessage(msg);
    }

}
