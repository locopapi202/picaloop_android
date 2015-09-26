package com.picaloopand.picaloop;

import com.picaloopand.picaloop.SpotNameFragment.SpotNameInterface;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class CreateASpotActivity extends ActionBarActivity implements  SpotNameInterface{
	
	protected MyApplication app;
	SharedPreferences userProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	       // Get the application instance
		app = (MyApplication)getApplication();
	    userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
	    
		setContentView(R.layout.activity_create_aspot);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new SpotNameFragment()).commit();
		}
	}
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar_menu, menu);
		return true;
	}

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
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


	@Override
	public void sendSpotName(String s) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Spot Name=" + s, Toast.LENGTH_SHORT).show();
		//getFragmentManager().beginTransaction()
		//.remove(SpotNameFragment).commit();
	}

}
