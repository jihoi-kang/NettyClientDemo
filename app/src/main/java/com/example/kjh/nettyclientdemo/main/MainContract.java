package com.example.kjh.nettyclientdemo.main;

public interface MainContract {

    interface View {

        String getType();
        String getBody();
        void setResult(String str);
        void showToast(String message);

    }

    interface Presenter {

        void onClickedSendBtn();
        void onDestroy();
    }

    interface Model {

    }

}
