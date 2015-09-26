package com.picaloopand.picaloop;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.picaloopand.picaloop.LoopLibraryActivity.PlaceholderFragment;
import com.picaloopand.slidingmenu.adapter.NavDrawerListAdapter;
import com.picaloopand.slidingmenu.model.NavDrawerItem;
//import android.support.v4.app.ActionBarDrawerToggle;

public class LoopFeedActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
 
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    
    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
	
	protected MyApplication app;
	SharedPreferences userProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop_feed);
		
	       // Get the application instance
       app = (MyApplication)getApplication();
       userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
       
       mTitle = getTitle();
       
       mDrawerTitle = getString(R.string.app_name);

       // load slide menu items
       navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

       // nav drawer icons from resources
       navMenuIcons = getResources()
               .obtainTypedArray(R.array.nav_drawer_icons);

       mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
       mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

       navDrawerItems = new ArrayList<NavDrawerItem>();

       // adding nav drawer items to array
       // Loop Feed
       navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
       // Loop Library
       navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
       // Explore
       navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
              

       // Recycle the typed array
       navMenuIcons.recycle();

       // setting the nav drawer list adapter
       adapter = new NavDrawerListAdapter(getApplicationContext(),
               navDrawerItems);
       mDrawerList.setAdapter(adapter);
       mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

       // enabling action bar app icon and behaving it as toggle button
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setHomeButtonEnabled(true);

       mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               R.drawable.ic_drawer, //nav menu toggle icon
               R.string.app_name // nav drawer open - description for accessibility
               //R.string.app_name // nav drawer close - description for accessibility
       ){
           public void onDrawerClosed(View view) {
        	   getSupportActionBar().setTitle(mTitle);
               // calling onPrepareOptionsMenu() to show action bar icons
               invalidateOptionsMenu();
           }

           public void onDrawerOpened(View drawerView) {
        	   getSupportActionBar().setTitle(mDrawerTitle);
               // calling onPrepareOptionsMenu() to hide action bar icons
               invalidateOptionsMenu();
           }
       };
       mDrawerLayout.setDrawerListener(mDrawerToggle);

       if (savedInstanceState == null) {
           // on first time display view for first nav item
           displayView(0);
       }
   }

 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.actionbar_menu, menu);
		return true;
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		
		// Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_signoff:
	        	MyApplication.signOut(app, userProfile);
	        	//finish();
	        	return true;
	        case R.id.action_profile:
	        	MyApplication.openProfile(app);
	            return true;
	        case R.id.action_settings:
	        	MyApplication.openSettings(app);
	            return true;
	        case R.id.action_search:
	        	MyApplication.search(app);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}
    
    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    
    
     /**
    * Diplaying fragment view for selected nav drawer list item
    * */
   private void displayView(int position) {
		// update the main content by replacing fragments
		if(position == 1){
   		Intent intent = new Intent(this, LoopLibraryActivity.class);
   		startActivity(intent);
   		
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        setTitle(navMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
		}
		else{
		//Toast.makeText(this, " section" + position, Toast.LENGTH_SHORT).show();
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.frame_container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		
		
           mDrawerList.setItemChecked(position, true);
           mDrawerList.setSelection(position);
           setTitle(navMenuTitles[position]);
           mDrawerLayout.closeDrawer(mDrawerList);
		}
       }
   
       
/**
 * Slide menu item click listener
 * */
	private class SlideMenuClickListener implements
	        ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {
	        // display view for selected nav drawer item
	        displayView(position);
	    }
	}

}
