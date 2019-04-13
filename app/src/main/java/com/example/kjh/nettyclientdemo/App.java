package com.example.kjh.nettyclientdemo;

import android.app.Activity;
import android.app.Application;

public class App extends Application {

    private static App application;

    private Activity activity;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static App getInstance() {
        return application;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getMain() {
        return activity;
    }
}
