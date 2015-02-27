package com.dmi.minesafety.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class SplashScreenActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 1000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }
        };
        splashThread.start();
    }
}
