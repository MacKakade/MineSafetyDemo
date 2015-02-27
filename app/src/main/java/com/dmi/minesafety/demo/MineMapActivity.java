package com.dmi.minesafety.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.ListView;
import android.widget.Spinner;


public class MineMapActivity extends Activity {

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private Spinner mDateSpinner;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private CharSequence mTitle;

    private String[] mDotsTitles;

    private String[] mDatesTitles;

    TouchImageView img;

    private int[] mDrawables = new int[]{R.drawable.purple, R.drawable.lightred,
            R.drawable.violet, R.drawable.red, R.drawable.magenta,
            R.drawable.lightviolet, R.drawable.lightblue, R.drawable.lighgreen,
            R.drawable.orange, R.drawable.green, R.drawable.brown,
            R.drawable.blue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_map);

        mTitle = mDrawerTitle = getTitle();
        mDotsTitles = getResources().getStringArray(R.array.dots_array);
        mDatesTitles = getResources().getStringArray(R.array.spinner_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        getActionBar().setDisplayHomeAsUpEnabled(true);

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
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        img = (TouchImageView) findViewById(R.id.imageViewMine);
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
                                MineMapActivity.this);
                        alert.setView(getLayoutInflater().inflate(R.layout.popup, null, false));
                        alert.show();
                    }

                    if (event.getX() > 1500 && event.getX() < 2000
                            ) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                MineMapActivity.this);
                        alert.setView(getLayoutInflater().inflate(R.layout.popup, null, false));
                        alert.show();
                    }

                    if (event.getX() > 150 && event.getX() < 200
                            ) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(
                                MineMapActivity.this);
                        alert.setView(getLayoutInflater().inflate(R.layout.popup, null, false));
                        alert.show();
                    }
//                    Toast.makeText(MineMapActivity.this, text,
//                            Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });

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
