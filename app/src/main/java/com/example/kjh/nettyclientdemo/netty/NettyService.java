package com.example.kjh.nettyclientdemo.netty;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.kjh.nettyclientdemo.data.MessageHolder;
import com.example.kjh.nettyclientdemo.otto.BusProvider;
import com.example.kjh.nettyclientdemo.otto.Events;

/**
 * Netty 서비스 클래스
 * @author 강지회
 * @version 1.0.0
 * @since 2019. 4. 11. PM 11:59
 **/
public class NettyService extends Service implements NettyListener {

    private NetworkReceiver receiver;

    /**------------------------------------------------------------------
     생명주기 ==> onCreate() -- Receiver 등록
     ------------------------------------------------------------------*/
    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NetworkReceiver();

//        로컬 브로드캐스트에 대한 수신 등록
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    /**------------------------------------------------------------------
     생명주기 ==> onStartCommand() -- 서버와 연결
     ------------------------------------------------------------------*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NettyClient.getInstance().setListener(this);
        connect();
        return START_NOT_STICKY;
    }

    /**------------------------------------------------------------------
     생명주기 ==> onBind()
     ------------------------------------------------------------------*/
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**------------------------------------------------------------------
     생명주기 ==> onDestroy()
     ------------------------------------------------------------------*/
    @Override
    public void onDestroy() {
        super.onDestroy();

//        브로드 캐스트 수신 해제
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        shutdown();
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버와 연결
     ------------------------------------------------------------------*/
    private void connect() {
        if(!NettyClient.getInstance().isConnect()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NettyClient.getInstance().connect();
                }
            }).start();
        }
    }

    /**------------------------------------------------------------------
     메서드 ==> 서버와 연결 끊음
     ------------------------------------------------------------------*/
    private void shutdown() {
        NettyClient.getInstance().setReconnectNum(0);
        NettyClient.getInstance().disconnect();
    }

    /**------------------------------------------------------------------
     메서드 ==> 연결 상태 모니터링
     ------------------------------------------------------------------*/
    @Override
    public void connectStatusChanged(int statusCode) {
        if (statusCode == NettyListener.STATUS_CONNECT_SUCCESS) {
            /** 연결 성공시 */
            // TODO: 인증 데이터 요청
        } else {
            /** 연결 실패시 */
        }
    }

    /**------------------------------------------------------------------
     메서드 ==> 메시지 처리
     ------------------------------------------------------------------*/
    @Override
    public void onMessageResponse(MessageHolder messageHolder) {
        // TODO: 메시지 처리
        Events.Event1 event1 = new Events.Event1(messageHolder.getChatLogs());
        BusProvider.getBus().post(event1);
    }

    /**------------------------------------------------------------------
     클래스 ==> 네트워크 상태에 따라 서버와 연결
     ------------------------------------------------------------------*/
    public class NetworkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI
                        || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connect();
                }
            }
        }
    }
}
