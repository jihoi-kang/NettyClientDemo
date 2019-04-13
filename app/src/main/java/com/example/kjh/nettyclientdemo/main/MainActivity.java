package com.example.kjh.nettyclientdemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kjh.nettyclientdemo.App;
import com.example.kjh.nettyclientdemo.R;
import com.example.kjh.nettyclientdemo.data.ChatLogs;
import com.example.kjh.nettyclientdemo.netty.NettyService;
import com.example.kjh.nettyclientdemo.netty.serializer.Serializer;
import com.example.kjh.nettyclientdemo.otto.BusProvider;
import com.example.kjh.nettyclientdemo.otto.Events;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Unbinder unbinder;
    @BindView(R.id.edit_type)    EditText edit_type;
    @BindView(R.id.edit_body)    EditText edit_body;
    @BindView(R.id.result)  TextView result;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getInstance().setActivity(this);

        unbinder = ButterKnife.bind(this);

        BusProvider.getBus().register(this);

        mainPresenter = new MainPresenter(this);

        App.getInstance().startService(new Intent(App.getInstance(), NettyService.class));
    }

    @OnClick(R.id.send_btn)
    void onClick() {
        mainPresenter.onClickedSendBtn();
    }

    @Override
    public String getType() {
        return edit_type.getText().toString();
    }

    @Override
    public String getBody() {
        return edit_body.getText().toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getBus().unregister(this);
    }

    @Subscribe
    public void getChatLogs(Events.Event1 event) {
        ChatLogs chatLogs = event.getChatLogs();
        result.setText("" + Serializer.serialize(chatLogs));
    }

}
