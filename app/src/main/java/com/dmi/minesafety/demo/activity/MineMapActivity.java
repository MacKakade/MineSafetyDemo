package com.dmi.minesafety.demo.activity;

import com.dmi.minesafety.demo.R;
import com.dmi.minesafety.demo.adaptor.SearchAdapter;
import com.dmi.minesafety.demo.dummy.DummyContent;
import com.dmi.minesafety.demo.fragment.DocumentDetailFragment;
import com.dmi.minesafety.demo.fragment.DocumentListFragment;
import com.dmi.minesafety.demo.fragment.DocumentListParentFragment;
import com.dmi.minesafety.demo.fragment.MineMapFragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MineMapActivity extends ActionBarActivity
        implements DocumentListFragment.Callbacks {

    private DrawerLayout mDrawerLayout;

    private ListView mDrawerList;

    private MenuItem switchMenu, inspectionMenu;

    private Spinner mDateSpinner;

    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private CharSequence mTitle;

    private String[] mDotsTitles;

    private String[] mDatesTitles;

    private Button mSelectAll;

    private ArrayList<Integer> mSelectedPositions = new ArrayList<Integer>();

//    private ImageView mMapView, mListView;

//    private SwitchCompat mSwitchView;

    private int mCurrentSelection = 0;

    private Fragment mCurrentFragment;

    private MineAdapter mineAdapter;

    private Menu menu;

    private TextView mMineTitle;

    private TextView mOrgTitle;

    private int index = 38;

    private SearchView search;

    private String queryString;

    Fragment fragment;

    private int[] mDrawables = new int[]{R.drawable.red, R.drawable.lighgreen,
            R.drawable.purple, R.drawable.blue, R.drawable.orange,
            R.drawable.lightviolet, R.drawable.brown, R.drawable.lighgreen,
            R.drawable.brown, R.drawable.green, R.drawable.brown,
            R.drawable.blue};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_map);

        index = getIntent().getIntExtra("mine_info", -1);

        mMineTitle = (TextView) findViewById(
                R.id.title_mine);

        mOrgTitle = (TextView) findViewById(
                R.id.title_org);

        DummyContent.Mine mine = DummyContent.MINES.get(index);
        mMineTitle.setText(mine.name);
        mOrgTitle.setText(
                mine.operatorName + ", " + mine.city + ", " + mine.state
                        + ", ID:"
                        + mine.id);

        mSelectAll = (Button) findViewById(
                R.id.button_select_all);

//        mSwitchView = (SwitchCompat) findViewById(
//                R.id.switch_view);

//        mSwitchView.setOnCheckedChangeListener(
//                new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView,
//                            boolean isChecked) {
//                        if (!isChecked) {
//                            Fragment fragment = new MineMapFragment();
//                            mCurrentFragment = fragment;
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.imageFrame, fragment)
//                                    .commit();
//                        } else {
//                            Fragment fragment
//                                    = new DocumentListParentFragment();
//                            mCurrentFragment = fragment;
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.imageFrame, fragment)
//                                    .commit();
//                        }
//                    }
//                });

        mSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSelectAll.getText().toString()
                        .equalsIgnoreCase("DESELECT ALL")) {
                    for (int i = 0; i < mDotsTitles.length; i++) {
                        mDrawerList.setItemChecked(i, true);
                        mSelectedPositions.add(i);
                    }
                    if (mCurrentFragment instanceof MineMapFragment) {
                        ((MineMapFragment) mCurrentFragment)
                                .reInitializeImage(
                                        R.drawable.both_map);

                    }
                    mSelectAll.setText(getString(R.string.deselect_all));
                } else {
                    for (int i = 0; i < mDotsTitles.length; i++) {
                        mDrawerList.setItemChecked(i, false);
                    }
                    mSelectedPositions.clear();
                    if (mCurrentFragment instanceof MineMapFragment) {
                        ((MineMapFragment) mCurrentFragment)
                                .reInitializeImage(
                                        R.drawable.blank_map);
                    }
                    mSelectAll.setText(getString(R.string.select_all));
                }

            }
        });

//        mMapView = (ImageView) findViewById(
//                R.id.map_view);
//
//        mListView = (ImageView) findViewById(
//                R.id.list_view);
//
//        mMapView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCurrentSelection == 1) {
//                    mCurrentSelection = 0;
//                    mMapView.setImageResource(R.drawable.map_selected);
//                    mListView.setImageResource(R.drawable.list_unselected);
//                    Fragment fragment = new MineMapFragment();
//                    mCurrentFragment = fragment;
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.imageFrame, fragment).commit();
//                }
//            }
//        });
//
//        mListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mCurrentSelection == 0) {
//                    mCurrentSelection = 1;
//                    mMapView.setImageResource(R.drawable.map_unselected);
//                    mListView.setImageResource(R.drawable.list_selected);
//                    Fragment fragment = new DocumentListParentFragment();
//                    mCurrentFragment = fragment;
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.imageFrame, fragment).commit();
//                }
//            }
//        });

        mTitle = mDrawerTitle = getTitle();
        mDotsTitles = getResources().getStringArray(R.array.dots_array);
        mDatesTitles = getResources().getStringArray(R.array.spinner_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDateSpinner = (Spinner) findViewById(R.id.spinner_date);

        mDateSpinner.setAdapter(new ArrayAdapter<>(this,
                R.layout.layout_spinner_item_drawer, mDatesTitles));

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                Gravity.START);
        // set up the drawer's list view with items and click listener

        mSelectedPositions.add(0);
        mSelectedPositions.add(1);

        mineAdapter = new MineAdapter(this, R.layout.layout_list_item_drawer,
                mDotsTitles);

        mDrawerList.setAdapter(mineAdapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (fragment == null) {
            fragment = new MineMapFragment();
        }
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.imageFrame, fragment).commit();

        for (int i = 0; i < mSelectedPositions.size(); i++) {
            mDrawerList.setItemChecked(i, true);
        }

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
        getMenuInflater().inflate(R.menu.menu_mine_map, menu);

        this.menu = menu;

        switchMenu = menu.findItem(R.id.myswitch);
        switchMenu.setActionView(R.layout.switch_layout);
        inspectionMenu = menu.findItem(R.id.menut_start_inspection);

        ((SwitchCompat) switchMenu.getActionView()
                .findViewById(R.id.switch_view)).setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                            boolean isChecked) {
                        if (!isChecked) {
                            Fragment fragment = new MineMapFragment();
                            mCurrentFragment = fragment;
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.imageFrame, fragment)
                                    .commit();
                        } else {
                            Fragment fragment
                                    = new DocumentListParentFragment();
                            mCurrentFragment = fragment;
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.imageFrame, fragment)
                                    .commit();
                        }

                    }

                });

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search),
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        switchMenu.setVisible(true);
                        inspectionMenu.setVisible(true);
                        getSupportActionBar().setBackgroundDrawable(
                                new ColorDrawable(getResources()
                                        .getColor(R.color.action_bar_bg)));
                        return true;  // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        switchMenu.setVisible(false);
                        inspectionMenu.setVisible(false);
                        getSupportActionBar().setBackgroundDrawable(
                                new ColorDrawable(getResources().getColor(
                                        R.color.artifacts_button_black)));
                        return true;  // Return true to expand action view
                    }
                });

        SearchManager manager = (SearchManager) getSystemService(
                Context.SEARCH_SERVICE);

        search = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setQueryHint("Mine name, Mine id");

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                queryString = query;
                loadMinesList(query);
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
                Intent intent = new Intent(MineMapActivity.this,
                        MainActivity.class);
                intent.putExtra(MainActivity.INDEX_SUGGESTION_CLICK, i);
                intent.putExtra(MainActivity.QUERY_STRING, queryString);
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.menut_start_inspection) {
            Intent intent = new Intent(MineMapActivity.this,
                    InspectionOptionsActivity.class);

            intent.putExtra("mine_info", index);

            startActivity(intent);
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
        public View getView(final int position, View convertView,
                ViewGroup parent) {

            View v = getLayoutInflater()
                    .inflate(R.layout.layout_list_item_drawer, null, false);

            CheckedTextView textView;
            textView = (CheckedTextView) v
                    .findViewById(R.id.text_item_drawer);
            textView.setText(mDotsTitles[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(mDrawables[position]),
                    null, null, null);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (((CheckedTextView) v).isChecked()) {
                        mDrawerList.setItemChecked(position, false);
                        ((CheckedTextView) v).setChecked(false);
                    } else {
                        mDrawerList.setItemChecked(position, true);
                        ((CheckedTextView) v).setChecked(true);
                    }

                    if (((CheckedTextView) v).isChecked()) {
                        mSelectedPositions.add(position);
                    } else {
                        mSelectedPositions.remove((Integer) position);
                    }

                    if (mCurrentFragment instanceof MineMapFragment) {
                        if (mSelectedPositions.contains(0)
                                && mSelectedPositions.contains(1)) {
                            ((MineMapFragment) mCurrentFragment)
                                    .reInitializeImage(
                                            R.drawable.both_map);
                        } else if (mSelectedPositions.contains(0)) {
                            ((MineMapFragment) mCurrentFragment)
                                    .reInitializeImage(
                                            R.drawable.citations_map);
                        } else if (mSelectedPositions.contains(1)) {
                            ((MineMapFragment) mCurrentFragment)
                                    .reInitializeImage(
                                            R.drawable.events_map);
                        } else {
                            ((MineMapFragment) mCurrentFragment)
                                    .reInitializeImage(
                                            R.drawable.blank_map);
                        }
                    }
                }
            });

            return v;
        }
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

    private void loadMinesList(String query) {
        final Cursor cursor = getCursor();
        int textLength = query.length();
        List<DummyContent.Mine> tempArrayList
                = new ArrayList<DummyContent.Mine>();
        if (textLength > 0) {
            for (DummyContent.Mine mine : DummyContent.MINES) {
                if (textLength <= mine.id.length()) {
                    if ((mine.id.contains(query) || mine.name.toLowerCase()
                            .contains(query.toLowerCase())) && !tempArrayList
                            .contains(mine)) {
                        tempArrayList.add(mine);
                    }
                }
            }
        }

        search.setSuggestionsAdapter(
                new SearchAdapter(this, cursor, tempArrayList));

    }
}
