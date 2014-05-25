package individual.kpi.project;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import individual.kpi.project.drawer.adapter.NavigationDrawerAdapter;
import individual.kpi.project.drawer.model.NavigationDrawerItem;
import individual.kpi.project.drawer.model.PostItems;
import individual.kpi.project.fragment.HomeFragment;
import individual.kpi.project.fragment.MyFavouriteFragment;

//import android.support.v4.app.Fragment;

public class MainActivity extends ActionBarActivity{

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
    LoginActivity2 loginActivity2;
    SessionManagement sessionManagement;
    List<PostItems> postItemses = new ArrayList<PostItems>();
    ListView postList;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManagement = new SessionManagement(getApplicationContext());
        if(!sessionManagement.isLoggedIn()){
            Log.e("Have to Login", "Loggiiiiinnn");
            Intent i = new Intent(MainActivity.this, LoginActivity2.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Close all activities
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start new activity
            startActivity(i);
            finish();
        }
        Log.e("Success login", "Successssss");
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();

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
        navigationDrawerItems.add(new NavigationDrawerItem(mOptions[2], navMenuIcons.getResourceId(2, -1)));

//        Toast.makeText(getApplicationContext(), "User Login Status: " + sessionManagement.isLoggedIn(), Toast.LENGTH_LONG).show();

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
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = new HomeFragment();
//                populateList();
                break;
            case 1:
                fragment = new MyFavouriteFragment();
                break;
            case 2:
                fragment = null;//new LoginFragment();

                break;
            default:
                break;
        }
        if(fragment != null){
            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            // update selected item and title, and close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mOptions[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
//            sessionManagement.logoutUser();
            Intent myIntent = new Intent(this, LoginActivity2.class);
//            myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
            startActivity(myIntent);
            finish();
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


    /**
     * For the list of blog posts
     */
    private void populateList(){
        ArrayAdapter<PostItems> postItemsArrayAdapter = new PostListAdapter();
        postList.setAdapter(postItemsArrayAdapter);
    }

    private class PostListAdapter extends ArrayAdapter<PostItems> {
        public PostListAdapter(){
            super(MainActivity.this, R.layout.post_item_list, postItemses);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.post_item_list, parent, false);
            }
            PostItems currPost = postItemses.get(position);
            TextView title = (TextView) view.findViewById(R.id.blog_post_title);
            title.setText(currPost.getTitle());
//            TextView btnLike = (TextView) view.findViewById(R.id.like_post);
            return view;
        }
    }
}

