package com.example.kjh.nettyclientdemo;

import android.app.Application;

public class App extends Application {

    private static App application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static App getInstance() {
        return application;
    }
}
