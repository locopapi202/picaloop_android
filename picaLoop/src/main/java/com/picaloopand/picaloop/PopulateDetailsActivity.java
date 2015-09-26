package com.picaloopand.picaloop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PopulateDetailsActivity extends ActionBarActivity {
	
	FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    
    SharedPreferences userProfile;
    SharedPreferences spotRanks;
    public static Editor editSpotRanks;
    
    private String[] spots = {
            "drinks",
            "food",
            "movie",
            "bike",
            "grocery",
            "water",
            "hotel",
            "airport",
            "photo",
            "library",
            "nature",
            "painting"
        };
    
    public Button loopSubmit ;
    protected MyApplication app;
    
    String category;
   

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
     spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);
	 
   
      for (int i=0;i<spots.length;i++){
    	  if (spotRanks.getInt(spots[i],0) == spotRanks.getInt("populated",0) ){
          	      category = spots[i]; 
    	  	}
         }
    
	    setTitle(category.toUpperCase());
		//setTitle(R.string.title_library);
		
		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
	       // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_populate_details);
		 loopSubmit = (Button) findViewById(R.id.submitLoop);
		
	       // enabling action bar app icon and behaving it as toggle button
	       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	       getSupportActionBar().setHomeButtonEnabled(true);
		
//	displayDetailFragments();
		
	       loopSubmit.setOnClickListener(new OnClickListener() {
	           @Override
	           public void onClick(View view) {
	         	  submitLoop();
	           }
	           });
	   

	}
	
	private void submitLoop() {
		if (spotRanks.getInt("populated",0) == spotRanks.getInt("counter",0)-1){
			Intent intent = new Intent(this, LoopMapViewActivity.class);
			//Toast.makeText(getApplicationContext(),String.valueOf(spotRanks.getInt("populated",0)), Toast.LENGTH_LONG).show();
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, PopulateDetailsActivity.class);
			editSpotRanks = spotRanks.edit();
			editSpotRanks.putInt("populated", spotRanks.getInt("populated",0)+1);
	        editSpotRanks.commit();
	        //Toast.makeText(getApplicationContext(),String.valueOf(spotRanks.getInt("populated",0)), Toast.LENGTH_LONG).show();
	        startActivity(intent);
		}
		
		
	}

	public void displayDetailFragments(){
		// get fragment manager
				 fragmentManager = getFragmentManager();
				
				 // add
				 fragmentTransaction = fragmentManager.beginTransaction();
				 fragmentTransaction.add(R.id.bar_details, new LocationFragment());
				 fragmentTransaction.commit();
	}
	
    @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.create_loop_actionbar_menu, menu);
	    return super.onCreateOptionsMenu(menu);

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


	  
}
