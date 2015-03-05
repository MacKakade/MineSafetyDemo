package com.dmi.minesafety.demo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmi.minesafety.demo.dummy.DummyContent;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements GoogleMap.OnMapLoadedCallback, MineListFragment.Callbacks,
        OnMapReadyCallback {

    private Menu menu;

    MineListAdapter mineListAdapter;

    private GoogleMap googleMap;

    private ViewGroup infoWindow;

    private Fragment mCurrentFragment;

    private OnInfoWindowElemTouchListener infoButtonListener;

    private ArrayList<MarkerOptions> markerOptions
            = new ArrayList<MarkerOptions>();

    private MapWrapperLayout mapWrapperLayout;

    private Marker currentMarker;

    private final int RQS_GooglePlayServices = 1;

    private ImageView mMapView, mListView;

    private int mCurrentSelection = 0;

    private ArrayList<DummyContent.Mine> tempArrayList;

    SupportMapFragment mSupportMapFragment;

    MineListFragment mineListFragment;

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
                    mCurrentFragment = mSupportMapFragment;
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
                    mineListFragment = new MineListFragment();

                    mCurrentFragment = mineListFragment;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.layout_container, mineListFragment).commit();
                }
            }
        });
        mapWrapperLayout = (MapWrapperLayout) findViewById(
                R.id.map_relative_layout);
        mSupportMapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.layout_container, mSupportMapFragment).commit();
        mCurrentFragment = mSupportMapFragment;
        mSupportMapFragment.getMapAsync(MainActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;
        SearchManager manager = (SearchManager) getSystemService(
                Context.SEARCH_SERVICE);

        final SearchView search = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setInputType(InputType.TYPE_CLASS_NUMBER);
        search.setQueryHint("Mine ID");

        final Cursor cursor = getCursor();

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                loadMinesList(query, cursor);
                return true;
            }

        });

        search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int i) {
                if (i <= tempArrayList.size() && mCurrentFragment instanceof MineListFragment) {
                    List<DummyContent.Mine> tempList = new ArrayList<DummyContent.Mine>();
                    tempList.add(tempArrayList.get(i));
                    mineListAdapter = new MineListAdapter(MainActivity.this,
                            R.layout.layout_spinner_item_mines, tempList);
                    mineListFragment.getListView().setAdapter(
                            mineListAdapter);
                    search.setQuery(tempArrayList.get(i).id, false);
                    hideKeyboard();
                }
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private Cursor getCursor() {

        // Cursor
        String[] columns = new String[]{"_id", "text"};
        Object[] temp = new Object[]{0, "default"};

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < DummyContent.MINES.size(); i++) {

            temp[0] = i;
            temp[1] = DummyContent.MINES.get(i);
            cursor.addRow(temp);
        }
        return cursor;
    }

    private void loadMinesList(String query, Cursor cursor) {

        int textLength = query.length();
        tempArrayList = new ArrayList<DummyContent.Mine>();
        if (textLength > 0) {
            for (DummyContent.Mine mine : DummyContent.MINES) {
                if (textLength <= mine.id.length()) {
                    if (mine.id.contains(query) && !tempArrayList.contains(mine)) {
                        tempArrayList.add(mine);
                    }
                }
            }
        }

        final SearchView search = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        search.setSuggestionsAdapter(
                new SearchAdapter(this, cursor, tempArrayList));

        mineListAdapter = new MineListAdapter(MainActivity.this,
                R.layout.layout_spinner_item_mines, tempArrayList);

        if (mCurrentFragment instanceof MineListFragment) {
            mineListFragment.getListView().setAdapter(
                    mineListAdapter);
        } else {

        }
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
                    DummyContent.MINES.get(i).lng));
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
                new ColorDrawable(Color.TRANSPARENT),
                new ColorDrawable(Color.LTGRAY)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                String tag = (String) v.getTag();
                if (tag.equalsIgnoreCase("1")) {
//                    Toast.makeText(MainActivity.this, "item 1 clicked",
//                            Toast.LENGTH_SHORT).show();
                } else if (tag.equalsIgnoreCase("2")) {
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

        startActivity(new Intent(MainActivity.this,
                MineMapActivity.class));
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

                        TextView txvMineName = (TextView) infoWindow.findViewById(R.id.mine_name);
                        for (DummyContent.Mine mine : DummyContent.MINES) {
                            if(marker.getPosition().equals(new LatLng(mine.lat, mine.lng))) {
                                txvMineName.setText(mine.name);
                            }
                        }

                        return false;
                    }
                });

        googleMap.setOnMapLoadedCallback(MainActivity.this);
        setMarkers();
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
