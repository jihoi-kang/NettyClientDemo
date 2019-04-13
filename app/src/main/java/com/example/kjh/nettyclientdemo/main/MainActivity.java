package com.example.kjh.nettyclientdemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kjh.nettyclientdemo.App;
import com.example.kjh.nettyclientdemo.R;
import com.example.kjh.nettyclientdemo.netty.NettyService;

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

    public static Handler mainActHandler;

    /**------------------------------------------------------------------
     생명주기 ==> onCreate
     ------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        unbinder = ButterKnife.bind(this);

        mainPresenter = new MainPresenter(this);

        App.getInstance().startService(new Intent(App.getInstance(), NettyService.class));

        mainActHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                setResult(msg.getData().getString("result"));
            }
        };
    }

    /**------------------------------------------------------------------
     생명주기 ==> onDestroy
     ------------------------------------------------------------------*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.onDestroy();
        unbinder.unbind();
    }

    /**------------------------------------------------------------------
     클릭 이벤트 ==> 전송 버튼 이벤트
     ------------------------------------------------------------------*/
    @OnClick(R.id.send_btn)
    void onClick() {
        mainPresenter.onClickedSendBtn();
    }

    /**------------------------------------------------------------------
     메서드 ==> Type 문자 추출
     ------------------------------------------------------------------*/
    @Override
    public String getType() {
        return edit_type.getText().toString();
    }

    /**------------------------------------------------------------------
     메서드 ==> Message 문자 추출
     ------------------------------------------------------------------*/
    @Override
    public String getBody() {
        return edit_body.getText().toString();
    }

    /**------------------------------------------------------------------
     메서드 ==> Socket에서 받은 메시지 결과
     ------------------------------------------------------------------*/
    @Override
    public void setResult(String str) {
        result.setText(str);
    }

    /**------------------------------------------------------------------
     메서드 ==> 토스트 발생
     ------------------------------------------------------------------*/
    @Override
    public void showToast(String message) {
        Toast.makeText(MainActivity.this,"" + message, Toast.LENGTH_SHORT).show();
    }

}
