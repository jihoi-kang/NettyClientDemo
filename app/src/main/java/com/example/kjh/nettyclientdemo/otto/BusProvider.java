package com.example.kjh.nettyclientdemo.otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider {

    private static Bus instance;

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus(ThreadEnforcer.ANY);
        }
        return instance;
    }

}
