package com.dmi.minesafety.demo;

import com.dmi.minesafety.demo.dummy.DummyContent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class SplashScreenActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        String[] mineID = getResources().getStringArray(R.array.mine_id);
        String[] mineName = getResources().getStringArray(R.array.mine_name);
        String[] mineOperator = getResources()
                .getStringArray(R.array.mine_operator);
        String[] mineCity = getResources().getStringArray(R.array.mine_city);
        String[] mineState = getResources().getStringArray(R.array.mine_state);

        for (int i = 0; i < mineID.length; i++) {
            DummyContent.addMine(new DummyContent.Mine(mineID[i], mineName[i],
                    mineOperator[i], mineCity[i], mineState[i]));
        }

        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    startActivity(new Intent(SplashScreenActivity.this,
                            MainActivity.class));
                }
            }
        };
        splashThread.start();
    }
}
