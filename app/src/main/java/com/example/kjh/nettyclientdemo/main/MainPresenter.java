package com.example.kjh.nettyclientdemo.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mainView;

    private MainModel mainModel;

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        mainModel = new MainModel();
    }
}
