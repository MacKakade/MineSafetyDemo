package com.dmi.minesafety.demo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.dmi.minesafety.demo.dummy.DummyContent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements GoogleMap.OnMapLoadedCallback, MineListFragment.Callbacks,
        OnMapReadyCallback {

    private GoogleMap googleMap;

    private ViewGroup infoWindow;

    private TextView infoTitle;

    private OnInfoWindowElemTouchListener infoButtonListener;

    private ArrayList<MarkerOptions> markerOptions
            = new ArrayList<MarkerOptions>();

    private MapWrapperLayout mapWrapperLayout;

    private Marker currentMarker;

    private final int RQS_GooglePlayServices = 1;

    private ImageView mMapView, mListView;

    private int mCurrentSelection = 0;

    SupportMapFragment mSupportMapFragment;

    // Create a LatLngBounds that includes USA.
    final LatLngBounds USA = new LatLngBounds(
            new LatLng(29, -126), new LatLng(47, -68.67));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = (ImageView) findViewById(
                R.id.map_view);

        mListView = (ImageView) findViewById(
                R.id.list_view);

        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentSelection == 1) {
                    mCurrentSelection = 0;
                    mMapView.setImageResource(R.drawable.map_selected);
                    mListView.setImageResource(R.drawable.list_unselected);
                    if (mSupportMapFragment != null) {
                        mSupportMapFragment = SupportMapFragment.newInstance();
                    }
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_container, mSupportMapFragment)
                            .commit();
                    mSupportMapFragment.getMapAsync(MainActivity.this);
                }
            }
        });

        mListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentSelection == 0) {
                    mCurrentSelection = 1;
                    mMapView.setImageResource(R.drawable.map_unselected);
                    mListView.setImageResource(R.drawable.list_selected);
                    Fragment fragment = new MineListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_container, fragment).commit();
                }
            }
        });
        mapWrapperLayout = (MapWrapperLayout) findViewById(
                R.id.map_relative_layout);
        mSupportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, mSupportMapFragment).commit();
        mSupportMapFragment.getMapAsync(MainActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS) {
//            Toast.makeText(getApplicationContext(),
//                    "isGooglePlayServicesAvailable SUCCESS",
//                    Toast.LENGTH_LONG).show();
        } else {
            GooglePlayServicesUtil
                    .getErrorDialog(resultCode, this, RQS_GooglePlayServices)
                    .show();
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

        for (int i = 0; i < DummyContent.MINES.size(); i++) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(DummyContent.MINES.get(i).lat,
                    DummyContent.MINES.get(i).longg));
            markerOption.icon(markerRed);
            markerOptions.add(markerOption);
        }
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
//                    Toast.makeText(MainActivity.this, "item 1 clicked",
//                            Toast.LENGTH_SHORT).show();
                } else if (tag.equals("item2")) {
                    startActivity(new Intent(MainActivity.this,
                            MineMapActivity.class));
//                    Toast.makeText(MainActivity.this, "item 2 clicked",
//                            Toast.LENGTH_SHORT).show();
                }

            }
        };
        infoWindow.setOnTouchListener(infoButtonListener);
    }

    @Override
    public void onMineSelected(String id) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (currentMarker != null) {
                            currentMarker.setIcon(BitmapDescriptorFactory
                                    .fromResource(R.drawable.marker_red));
                        }
                        marker.setIcon(BitmapDescriptorFactory
                                .fromResource(R.drawable.marker_green));
                        currentMarker = marker;
                        return false;
                    }
                });

        googleMap.setOnMapLoadedCallback(MainActivity.this);
        setMarkers();
    }
}
