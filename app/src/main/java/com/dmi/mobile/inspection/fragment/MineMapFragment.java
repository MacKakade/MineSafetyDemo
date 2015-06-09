package com.dmi.mobile.inspection.fragment;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.dmi.mobile.inspection.R;
import com.dmi.mobile.inspection.utils.ImageOperations;
import com.dmi.mobile.inspection.widget.TouchImageView;

public class MineMapFragment extends Fragment {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public TouchImageView img;

    Bitmap bitmap;

    public LayoutInflater mLayoutInflater;

    AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        View v = inflater
                .inflate(R.layout.fragment_mine_map, container, false);
        img = (TouchImageView) v.findViewById(R.id.imageViewMine);
        reInitializeImage(R.drawable.both_map);

        return v;
    }

    public void reInitializeImage(int imageResource) {

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            bitmap = BitmapFactory.decodeResource(getActivity().getResources(),imageResource);
            img.setImageResource(imageResource);
            bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        } else {
            setResizedBitmap(imageResource);
        }

        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String text = "You click at x = " + event.getX()
                            + " and y = " + event.getY();

                    Matrix inverse = new Matrix();
                    img.getImageMatrix().invert(inverse);
                    float[] touchPoint = new float[]{event.getX(),
                            event.getY()};
                    inverse.mapPoints(touchPoint);
                    int xCoord = Integer.valueOf((int) touchPoint[0]);
                    int yCoord = Integer.valueOf((int) touchPoint[1]);

                    int pixel = bitmap.getPixel(xCoord, yCoord);

                    int redValue = Color.red(pixel);
                    int blueValue = Color.blue(pixel);
                    int greenValue = Color.green(pixel);

//                    Toast.makeText(getActivity(),
//                            redValue + " " + blueValue + " " + greenValue,
//                            Toast.LENGTH_LONG).show();

                    if ((redValue >= 250 && redValue <= 255) && (greenValue >= 0
                            && greenValue <= 30) && (blueValue >= 0
                            && blueValue <= 20)) {
                        final AlertDialog.Builder alert
                                = new AlertDialog.Builder(
                                getActivity());
                        alertDialog = alert.create();

                        View view = mLayoutInflater
                                .inflate(R.layout.popup, null, false);
                        view.findViewById(R.id.close)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                        alertDialog.setView(view);
                        alertDialog.show();
                    }

                    if ((redValue >= 40 && redValue <= 50) && (greenValue >= 145
                            && greenValue <= 160)
                            && (blueValue >= 60 && blueValue <= 70)) {
                        final AlertDialog.Builder alert
                                = new AlertDialog.Builder(
                                getActivity());
                        alertDialog = alert.create();

                        View view = mLayoutInflater
                                .inflate(R.layout.popup, null, false);
                        view.findViewById(R.id.close)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        alertDialog.dismiss();
                                    }
                                });
                        ((TextView) view.findViewById(R.id.title_popup)).setText("Event#: 878765");
                        alertDialog.setView(view);
                        alertDialog.show();
                    }

//                    if (event.getX() > 900 && event.getX() < 1000) {
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(
//                                getActivity());
//                        alert.setTitle("Citation #878942");
//                        alert.setView(inflater
//                                .inflate(R.layout.popup, null, false));
//                        alert.show();
//                    }
//
//                    if (event.getX() > 1500 && event.getX() < 2000
//                            ) {
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(
//                                getActivity());
//                        alert.setTitle("Citation #877156");
//                        alert.setView(inflater
//                                .inflate(R.layout.popup, null, false));
//                        alert.show();
//                    }
//
//                    if (event.getX() > 150 && event.getX() < 200
//                            ) {
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(
//                                getActivity());
//                        alert.setTitle("Citation #877144");
//                        alert.setView(inflater
//                                .inflate(R.layout.popup, null, false));
//                        alert.show();
//                    }
//                    Toast.makeText(MineMapActivity.this, text,
//                            Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });

    }

    private void setResizedBitmap(final int resId) {
        ViewTreeObserver vto = img.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                img.getViewTreeObserver().removeOnPreDrawListener(this);
                double finalHeight = img.getMeasuredHeight() * 0.4;
                double finalWidth = img.getMeasuredWidth() * 0.4;
                bitmap = ImageOperations.decodeSampledBitmapFromResource(getResources(), resId, finalWidth, finalHeight);
                img.setImageBitmap(bitmap);
                return true;
            }
        });
    }
}
