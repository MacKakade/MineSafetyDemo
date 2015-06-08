package com.dmi.minesafety.demo.widget;

/**
 * Created by Digvijay on 2/24/2015.
 */

import com.google.android.gms.maps.model.Marker;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class OnInfoWindowElemTouchListener implements OnTouchListener {

    private final View view;

    private final Drawable bgDrawableNormal;

    private final Drawable bgDrawablePressed;

    private final Handler handler = new Handler();

    private Marker marker;

    private boolean pressed = false;

    //Marker window item co-ordinates

    private final float correctionFactor = -120;  //for phone

//    private final float correctionFactor = -47; //for tablet

    private final float markerItemHeight = 47;

    private boolean itemClicked;

    private String mClickedItemPos = "0";

    public OnInfoWindowElemTouchListener(View view, Drawable bgDrawableNormal,
            Drawable bgDrawablePressed) {
        this.view = view;
        this.bgDrawableNormal = bgDrawableNormal;
        this.bgDrawablePressed = bgDrawablePressed;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean onTouch(View vv, MotionEvent event) {

        if (event.getX() >= 0 && event.getX() <= view.getWidth() &&
                event.getY() >= view.getHeight() + correctionFactor - (
                        markerItemHeight * 2) && event.getY()
                <= view.getHeight() + correctionFactor - markerItemHeight) {
            mClickedItemPos = "1";
            itemClicked = true;
        } else if (event.getX() >= 0 && event.getX() <= view.getWidth() &&
                event.getY() >= view.getHeight() + correctionFactor
                        - markerItemHeight
                && event.getY() <= view.getHeight() + correctionFactor) {
            mClickedItemPos = "2";
            itemClicked = true;
        }

        if (itemClicked) {
            itemClicked = false;
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPress();
                    break;

                // We need to delay releasing of the view a little so it shows the pressed state on the screen
                case MotionEvent.ACTION_UP:
                    handler.postDelayed(confirmClickRunnable, 150);
                    break;

                case MotionEvent.ACTION_CANCEL:
                    endPress();
                    break;
                default:
                    break;
            }
        } else {
            // If the touch goes outside of the view's area
            // (like when moving finger out of the pressed button)
            // just release the press
            endPress();
        }
        return false;
    }

    private void startPress() {
        if (!pressed) {
            pressed = true;
            handler.removeCallbacks(confirmClickRunnable);
            view.findViewWithTag(mClickedItemPos)
                    .setBackground(bgDrawablePressed);
            if (marker != null) {
                marker.showInfoWindow();
            }
        }
    }

    private boolean endPress() {
        if (pressed) {
            this.pressed = false;
            handler.removeCallbacks(confirmClickRunnable);
            view.findViewWithTag(mClickedItemPos).setBackground(bgDrawableNormal);
            if (marker != null) {
                marker.showInfoWindow();
            }
            return true;
        } else {
            return false;
        }
    }

    private final Runnable confirmClickRunnable = new Runnable() {
        public void run() {
            if (endPress()) {
                onClickConfirmed(view.findViewWithTag(mClickedItemPos), marker);
            }
        }
    };

    /**
     * This is called after a successful click
     */
    protected abstract void onClickConfirmed(View v, Marker marker);
}