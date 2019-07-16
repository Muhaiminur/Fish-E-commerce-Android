package com.gtechnologies.fishbangla.APPLICATION;

import android.app.Application;

import com.fxn.stash.Stash;

public class FISHBANGLA extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
    }
}