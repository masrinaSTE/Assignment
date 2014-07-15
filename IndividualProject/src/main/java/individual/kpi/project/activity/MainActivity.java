package individual.kpi.project.activity;


import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import individual.kpi.project.R;
import individual.kpi.project.adapter.NavigationDrawerAdapter;
import individual.kpi.project.adapter.TitleNavigationAdapter;
import individual.kpi.project.model.NavigationDrawerItem;
import individual.kpi.project.fragment.HomeFragment;
import individual.kpi.project.model.SpinnerNavItem;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    //navigation drawer title
    private CharSequence mDrawerTitle;
    // to store app title
    private CharSequence mTitle;
    // Slider menu items
    private String[] mOptions;
    private TypedArray navMenuIcons;
    private ArrayList<NavigationDrawerItem> navigationDrawerItems;
    private NavigationDrawerAdapter adapter;
    ListView postList;
    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navItems;
    private TitleNavigationAdapter navigationAdapter;
    private boolean synthetic = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        //---- For Spinner in Action Bar ----//
        actionBar = getActionBar();
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
        // Enable spinner dropdown navigation
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        // Spinner title data
        navItems = new ArrayList<SpinnerNavItem>();
        navItems.add(new SpinnerNavItem("Latest Post"));
        navItems.add(new SpinnerNavItem("Oldest Post"));

        // title dropdown adapter
        navigationAdapter = new TitleNavigationAdapter(getApplicationContext(), navItems);
        // assign the spinner navigation
        actionBar.setListNavigationCallbacks(navigationAdapter, this);
        //---- End of For Spinner in Action Bar ----//

        // to load slide menu item
        mOptions = getResources().getStringArray(R.array.options);
        // to load icons
        navMenuIcons = getResources().obtainTypedArray(R.array.options_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        postList = (ListView) findViewById(R.id.list);

        navigationDrawerItems = new ArrayList<NavigationDrawerItem>();
        // add navigation drawer items to array
        navigationDrawerItems.add(new NavigationDrawerItem(mOptions[0], navMenuIcons.getResourceId(0, -1)));
        navigationDrawerItems.add(new NavigationDrawerItem(mOptions[1], navMenuIcons.getResourceId(1, -1)));

        // Recycle the typed array
        navMenuIcons.recycle();

        // Setting the navigation drawer list adapter
        adapter = new NavigationDrawerAdapter(getApplicationContext(),
                navigationDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enable action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, // navigation menu toggle icon
                R.string.app_name, // navigation drawer open
                R.string.app_name // navigation drawer close
                ){
            public void onDrawerClosed(View view){
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);

        }

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        String[] order = getResources().getStringArray(R.array.sort_by);
        HomeFragment obj = new HomeFragment();
        Bundle bundle=new Bundle();
        if(synthetic){
            synthetic = false;
            return true;
        }
        switch (i){
            case 0:
                bundle.putString("url", HomeFragment.url_latest);
                break;
            case 1:
                bundle.putString("url", HomeFragment.url_oldest);
                break;
        }
        obj.setArguments(bundle);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, obj, order[i]).commit();
        Log.i("BundleValue: ", String.valueOf(obj));
        return true;
    }

    /**
     * Slide Menu item click listener
     */
    private class SlideMenuClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            displayView(position);
        }
    }

    private void displayView(int position){
        Fragment newFragment = null;
        switch(position){
            case 0:
                newFragment = new HomeFragment();
                break;
            case 1:
                newFragment = null;
                break;
        }
        if(newFragment != null){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, newFragment).commit();
            // update selected item and title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mOptions[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        // if navigation drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title){
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, need to call it during
     * onPostCreate() and onConfigurationChanged()
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occured.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}

