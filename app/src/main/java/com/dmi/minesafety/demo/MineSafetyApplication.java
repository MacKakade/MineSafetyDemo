package com.dmi.minesafety.demo;

import com.splunk.mint.Mint;

import android.app.Application;

public class MineSafetyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, "90254e95");

    }


}
