package com.dmi.minesafety.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;


public class MineMapActivity extends ActionBarActivity    implements DocumentListFragment.Callbacks {

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private Spinner mDateSpinner;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private CharSequence mTitle;

    private String[] mDotsTitles;

    private String[] mDatesTitles;



    private ImageView mMapView, mListView;

    private int mCurrentSelection = 0;

    private int[] mDrawables = new int[]{R.drawable.lighgreen, R.drawable.red,
            R.drawable.purple, R.drawable.red, R.drawable.orange,
            R.drawable.lightviolet, R.drawable.brown, R.drawable.lighgreen,
            R.drawable.brown, R.drawable.green, R.drawable.brown,
            R.drawable.blue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_map);

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
                    Fragment fragment = new MineMapFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.imageFrame,fragment).commit();
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
                    Fragment fragment = new DocumentListParentFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.imageFrame,fragment).commit();
                }
            }
        });

        mTitle = mDrawerTitle = getTitle();
        mDotsTitles = getResources().getStringArray(R.array.dots_array);
        mDatesTitles = getResources().getStringArray(R.array.spinner_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDateSpinner = (Spinner) findViewById(R.id.spinner_date);

        mDateSpinner.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_spinner_item_drawer, mDatesTitles));

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                Gravity.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(
                new MineAdapter(this, R.layout.layout_list_item_drawer,
                        mDotsTitles));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        Fragment fragment = new MineMapFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.imageFrame,fragment).commit();

    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            // selectItem(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mine_map, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.menut_start_inspection) {
            startActivity(new Intent(MineMapActivity.this,
                    InspectionOptionsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String id) {

            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(DocumentDetailFragment.ARG_ITEM_ID, id);
            DocumentDetailFragment fragment = new DocumentDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.document_detail_container, fragment)
                    .commit();


    }

    private class MineAdapter extends ArrayAdapter<String> {


        public MineAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = getLayoutInflater()
                    .inflate(R.layout.layout_list_item_drawer, null, false);
            ((CheckedTextView) v.findViewById(R.id.text1))
                    .setText(mDotsTitles[position]);
            ((CheckedTextView) v.findViewById(R.id.text1))
                    .setCompoundDrawablesWithIntrinsicBounds(
                            getResources().getDrawable(mDrawables[position]),
                            null, null, null);
            return v;
        }
    }
}
