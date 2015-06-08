package com.dmi.minesafety.demo.activity;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.dummy.DummyContent;
import com.dmi.minesafety.demo.utils.ImageOperations;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;


public class SplashScreenActivity extends ActionBarActivity {

    public final double[] mLatArray = new double[]{
            38.905378,
            39.493977,
            37.725954,
            38.137607,
            38.288119,
            37.949831,
            38.597626,
            37.785278,
            37.596503,
            38.597626,
            37.335949,
            38.136220,
            39.495086,
            38.179267,
            39.503695,
            37.887329,
            37.340668,
            39.272883,
            40.202293,
            37.774991,
            37.778170,
            38.597626,
            39.036586,
            38.232046,
            38.231217,
            40.018129,
            39.626990,
            37.268725,
            38.381492,
            37.922043,
            38.597626,
            39.718997,
            37.878438,
            38.321212,
            37.952690,
            38.468717,
            37.762335,
            38.150662,
            37.903440
    };

    public final double[] mLngArray = new double[]{
            -80.275638,
            -79.642830,
            -80.642024,
            -81.274276,
            -81.810532,
            -81.418720,
            -80.454903,
            -81.792778,
            -81.320935,
            -80.454903,
            -81.436493,
            -81.099548,
            -79.815061,
            -81.710955,
            -80.166745,
            -81.670114,
            -81.737610,
            -79.364490,
            -80.660355,
            -81.202528,
            -81.188156,
            -80.454903,
            -80.006819,
            -81.537618,
            -81.192053,
            -80.734249,
            -78.227196,
            -81.666775,
            -81.112053,
            -81.689407,
            -80.454903,
            -80.215350,
            -81.827897,
            -81.426507,
            -81.714874,
            -80.625092,
            -81.412327,
            -81.287332,
            -81.677337
    };

    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        img = (ImageView) findViewById(R.id.splash_image);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            // do something
        } else {
            ViewTreeObserver vto = img.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    img.getViewTreeObserver().removeOnPreDrawListener(this);
                    double finalHeight = img.getMeasuredHeight() * 0.4;
                    double finalWidth = img.getMeasuredWidth() * 0.4;
                    Log.i("size", "Height: " + finalHeight + " Width: " + finalWidth);
                    Bitmap bitmap = ImageOperations.decodeSampledBitmapFromResource(getResources(), R.drawable.splashscreen, finalWidth, finalHeight);
                    img.setImageBitmap(bitmap);
                    return true;
                }
            });
        }
        String[] mineID = getResources().getStringArray(R.array.mine_id);
        String[] mineName = getResources().getStringArray(R.array.mine_name);
        String[] mineOperator = getResources()
                .getStringArray(R.array.mine_operator);
        String[] mineCity = getResources().getStringArray(R.array.mine_city);
        String[] mineState = getResources().getStringArray(R.array.mine_state);

        for (int i = 0; i < mineID.length; i++) {
            DummyContent.addMine(new DummyContent.Mine(i,mineID[i], mineName[i],
                    mineOperator[i], mineCity[i], mineState[i], mLatArray[i],
                    mLngArray[i]));
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
