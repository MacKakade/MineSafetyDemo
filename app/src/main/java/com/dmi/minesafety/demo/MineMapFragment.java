package com.dmi.minesafety.demo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * An activity representing a list of Documents. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a {@link
 * DocumentDetailActivity} representing item details. On
 * tablets, the activity presents the list of items and item details
 * side-by-side using two vertical panes. <p> The activity makes heavy use of
 * fragments. The list of items is a {@link DocumentListFragment}
 * and the item details (if present) is a {@link DocumentDetailFragment}.
 * <p> This activity also implements the required {@link
 * com.dmi.minesafety.demo.DocumentListFragment.Callbacks} interface to listen
 * for item selections.
 */
public class MineMapFragment extends Fragment{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    TouchImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: If exposing deep links into your app, handle intents here.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View v = inflater
                .inflate(R.layout.fragment_mine_map, container, false);
        img = (TouchImageView) v.findViewById(R.id.imageViewMine);
        //       img.setZoom(2);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String text = "You click at x = " + event.getX()
                            + " and y = " + event.getY();

                    if (event.getX() > 900 && event.getX() < 1000
                            ) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                getActivity());
                        alert.setTitle("Location: SCBA Storage");
                        alert.setView(inflater
                                .inflate(R.layout.popup, null, false));
                        alert.show();
                    }

                    if (event.getX() > 1500 && event.getX() < 2000
                            ) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                getActivity());
                        alert.setTitle("Location: SCBA Storage");
                        alert.setView(inflater
                                .inflate(R.layout.popup, null, false));
                        alert.show();
                    }

                    if (event.getX() > 150 && event.getX() < 200
                            ) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                getActivity());
                        alert.setTitle("Location: SCBA Storage");
                        alert.setView(inflater
                                .inflate(R.layout.popup, null, false));
                        alert.show();
                    }
//                    Toast.makeText(MineMapActivity.this, text,
//                            Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });

        return v;
    }


}
