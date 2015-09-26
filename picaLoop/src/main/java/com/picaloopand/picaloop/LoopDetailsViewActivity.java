package com.picaloopand.picaloop;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoopDetailsViewActivity extends ActionBarActivity {
	SharedPreferences spotRanks;
	SharedPreferences displayOptions;
	
	Uri cameraButton = Uri.parse("android.resource://com.picaloopand.picaloop/drawable/bar_wine");


    public static Editor editSpotRanks;
    public static Editor editDisplayOptions;
    
    private String[] listOfSpots = {
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
    
    private List<String> selectionList;
    private String[] selectionArray;
    
	/*
	private Integer[] spots = {
			R.drawable.bar_wine,
			R.drawable.hiking,
			R.drawable.hotel,
			R.drawable.movie,
			R.drawable.photography,
			R.drawable.restuarant
			};
	*/
	private Integer counter=1;
	
    private Uri[] spots = {
    		Uri.parse("android.resource://com.picaloopand.picaloop/drawable/bar_wine"),
    		Uri.parse("android.resource://com.picaloopand.picaloop/drawable/restuarant"),
    		Uri.parse("android.resource://com.picaloopand.picaloop/drawable/movie"),
    		Uri.parse("android.resource://com.picaloopand.picaloop/drawable/hotel")
			};
	private String[] spotnames = {
    		"Bahama breeze","Minado","UA King of Prussia","Radisson"
    };
	private String[] spotmentions = {
    		"3 Friends","4 Friends","22 Friends","No mentions"
    };
	private Integer[] icons = {
			R.drawable.ic_launcher_1_red,
            R.drawable.ic_launcher_2_red,
            R.drawable.ic_launcher_3_red,
            R.drawable.ic_launcher_4_red
			};

	private Integer[] ratings = {
			2,
			2,
			4,
			3
			};
    

	public LoopGridActivity loopGrid;
	public GridView gridview;
	
	SharedPreferences userProfile;
	protected MyApplication app;
	public Button mapDisplay ;
	public Button rating ;
	public Button mentions;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
		setContentView(R.layout.activity_loop_details_view);
		
        displayOptions = getSharedPreferences("displayOptions", MODE_PRIVATE);
        editDisplayOptions = displayOptions.edit();
        
		spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);

		for (int z=1;z<spotRanks.getInt("counter", 1);z++){
			
			    for (int i=0;i<listOfSpots.length;i++){
						if (spotRanks.getInt(listOfSpots[i], 1) == z){
							Toast.makeText(getApplicationContext(),String.valueOf(z), Toast.LENGTH_LONG).show();
							//selectionList.add(listOfSpots[i]);
						}
				}
		}
		
		//spotnames = selectionList.toArray(spotnames);
		
		mapDisplay = (Button) findViewById(R.id.map);
		rating = (Button) findViewById(R.id.rating);
		mentions = (Button) findViewById(R.id.mentions);
		
		app = (MyApplication)getApplication();

        
		      GridView gridview = (GridView) findViewById(R.id.gridview2);
		      if ((spots.length % 2 ) == 0){
		    	  gridview.setNumColumns(2);
		      } else {
		    	  gridview.setNumColumns(1);
		      }
		    	  
			  loopGrid = new LoopGridActivity(this,displayOptions.getInt("display",0),spots,spotnames,spotmentions,icons,ratings);
              gridview.setAdapter(loopGrid);
          
        mapDisplay.setOnClickListener(new OnClickListener(){
	          @Override
	          public void onClick(View view) {
	        	  displayOption1();
        	 }
        	}); 
              

	     
		 rating.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View view) {
	        	  displayOption2();
	          }
	          });
		 
		 mentions.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View view) {
	        	  displayOption3();
	          }
	          });


	         
	       
	}

	private void displayOption1(){
        editDisplayOptions.putInt("display", 1);
        editDisplayOptions.commit();
		Intent intent = new Intent(this, LoopDetailsViewActivity.class);
		startActivity(intent);
	}
	private void displayOption2(){
        editDisplayOptions.putInt("display", 2);
        editDisplayOptions.commit();
		Intent intent = new Intent(this, LoopDetailsViewActivity.class);
		startActivity(intent);
	}
  
	private void displayOption3(){
        editDisplayOptions.putInt("display", 3);
        editDisplayOptions.commit();
		Intent intent = new Intent(this, LoopDetailsViewActivity.class);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loop_details_view, menu);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}
}
