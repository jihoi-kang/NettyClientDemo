package com.example.kjh.nettyclientdemo.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kjh.nettyclientdemo.App;
import com.example.kjh.nettyclientdemo.R;
import com.example.kjh.nettyclientdemo.netty.NettyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    Unbinder unbinder;
    @BindView(R.id.edit)    EditText edit;
    @BindView(R.id.result)  TextView result;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this);

        App.getInstance().startService(new Intent(App.getInstance(), NettyService.class));
    }

    @OnClick(R.id.send_btn)
    void onClick() {

    }
}
