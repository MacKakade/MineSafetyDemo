package com.dmi.minesafety.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
        implements GoogleMap.OnMapLoadedCallback {

    private GoogleMap googleMap;

    private ViewGroup infoWindow;

    private TextView infoTitle;

    private OnInfoWindowElemTouchListener infoButtonListener;

    private MarkerOptions markerOptions[];

    private MapWrapperLayout mapWrapperLayout;
    private Marker currentMarker;
    private final int RQS_GooglePlayServices = 1;

    // Create a LatLngBounds that includes USA.
    final LatLngBounds USA = new LatLngBounds(
            new LatLng(29, -126), new LatLng(47, -68.67));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;
        setMarkers();

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapWrapperLayout = (MapWrapperLayout) findViewById(
                R.id.map_relative_layout);
        googleMap = mapFragment.getMap();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (currentMarker != null) {
                    currentMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));
                }
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green));
                currentMarker = marker;
                return false;
            }
        });

        googleMap.setOnMapLoadedCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS) {
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }
    }

    private void putMarkers() {
        googleMap.clear();
        for (MarkerOptions markerOption : markerOptions) {
            Marker marker = googleMap.addMarker(markerOption);
            marker.showInfoWindow();
        }
    }

    private void setMarkers() {
        BitmapDescriptor markerRed = BitmapDescriptorFactory
                .fromResource(R.drawable.marker_red);
        markerOptions = new MarkerOptions[]
                {new MarkerOptions().position(new LatLng(32.75, -113.98))
                        .icon(markerRed),
                        new MarkerOptions()
                                .position((new LatLng(32.13, -112.66)))
                                .icon(markerRed),
                        new MarkerOptions()
                                .position((new LatLng(34.88, -114.02)))
                                .icon(markerRed),
                        new MarkerOptions()
                                .position((new LatLng(34.82, -113.32)))
                                .icon(markerRed),
                        new MarkerOptions()
                                .position((new LatLng(35.46, -111.85)))
                                .icon(markerRed),
                        new MarkerOptions()
                                .position((new LatLng(36.42, -111.68)))
                                .icon(markerRed)};
    }

    @Override
    public void onMapLoaded() {
        putMarkers();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(USA, 0));
        googleMap.setInfoWindowAdapter(new InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker arg0) {
                infoButtonListener.setMarker(arg0);
                mapWrapperLayout.setMarkerWithInfoWindow(arg0, infoWindow);
                return infoWindow;
            }
        });
        mapWrapperLayout.init(googleMap, 0);
        infoWindow = (ViewGroup) getLayoutInflater()
                .inflate(R.layout.marker_window, null);

        infoButtonListener = new OnInfoWindowElemTouchListener(infoWindow,
                null,
                null) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                String tag = (String) v.getTag();
                if (tag.equals("item1")) {
                    Toast.makeText(MainActivity.this, "item 1 clicked",
                            Toast.LENGTH_SHORT).show();
                } else if (tag.equals("item2")) {
                    startActivity(new Intent(MainActivity.this,
                            MineMapActivity.class));
                    Toast.makeText(MainActivity.this, "item 2 clicked",
                            Toast.LENGTH_SHORT).show();
                }

            }
        };
        infoWindow.setOnTouchListener(infoButtonListener);
    }
}
