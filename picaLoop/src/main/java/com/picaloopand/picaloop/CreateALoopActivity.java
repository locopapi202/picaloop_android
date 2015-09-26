package com.picaloopand.picaloop;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;

public class CreateALoopActivity extends ActionBarActivity {
	
	SharedPreferences userProfile;
		
	SharedPreferences selectedSpots;
	SharedPreferences spotRanks;
	SharedPreferences displayOptions;
    public static Editor editSelectedSpots;
    public static Editor editSpotRanks;
    public static Editor editDisplayOptions;
    public static final String TRUE = "TRUE";
    public static final String FALSE = "FALSE";
    
    //Ordering constants
    int counter = 1;
    int drinkStatus = 0;
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
    

	//Spot Icons
	public ImageButton drinksButton ;
	public ImageButton foodButton ;
	public ImageButton movieButton ;
	public ImageButton bikeButton ;
	public ImageButton groceryButton ;
	public ImageButton waterButton ;
	public ImageButton hotelButton ;
	public ImageButton airportButton ;
	public ImageButton photoButton ;
	public ImageButton libraryButton ;
	public ImageButton natureButton ;
	public ImageButton paintingButton ;
	public ImageButton spotSubmit ;

	
	//Display when selected
	public ImageView drinksSelect ;
	public ImageView foodSelect ;
	public ImageView movieSelect ;
	public ImageView bikeSelect ;
	public ImageView grocerySelect ;
	public ImageView waterSelect ;
	public ImageView hotelSelect ;
	public ImageView airportSelect ;
	public ImageView photoSelect ;
	public ImageView librarySelect ;
	public ImageView natureSelect ;
	public ImageView paintingSelect ;
	protected MyApplication app;
	
	// Google client to interact with Google API
	public static GoogleApiClient mGoogleApiClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
       // Get the application instance
        userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);

        
        selectedSpots = getSharedPreferences("selectedSpots", MODE_PRIVATE);
        editSelectedSpots = selectedSpots.edit();
        
        spotRanks = getSharedPreferences("spotRanks", MODE_PRIVATE);
        editSpotRanks = spotRanks.edit();
		
        displayOptions = getSharedPreferences("displayOptions", MODE_PRIVATE);
        editDisplayOptions = displayOptions.edit();
        editDisplayOptions.putInt("display", 1);
        editDisplayOptions.commit();
        
        editSelectedSpots.clear();
        editSpotRanks.clear();
        editSelectedSpots.putString("drinks", FALSE);
        editSelectedSpots.putString("food", FALSE);
        editSelectedSpots.putString("movie", FALSE);
        editSelectedSpots.putString("bike", FALSE);
        editSelectedSpots.putString("grocery", FALSE);
        editSelectedSpots.putString("water", FALSE);
        editSelectedSpots.putString("hotel", FALSE);
        editSelectedSpots.putString("airport", FALSE);
        editSelectedSpots.putString("photo", FALSE);
        editSelectedSpots.putString("library", FALSE);
        editSelectedSpots.putString("nature", FALSE);
        editSelectedSpots.putString("painting", FALSE);
        editSelectedSpots.commit();
        editSpotRanks.putInt("drinks", 0);
        editSpotRanks.putInt("food", 0);
        editSpotRanks.putInt("movie", 0);
        editSpotRanks.putInt("bike", 0);
        editSpotRanks.putInt("grocery", 0);
        editSpotRanks.putInt("water", 0);
        editSpotRanks.putInt("hotel", 0);
        editSpotRanks.putInt("airport", 0);
        editSpotRanks.putInt("photo", 0);
        editSpotRanks.putInt("library", 0);
        editSpotRanks.putInt("nature", 0);
        editSpotRanks.putInt("painting", 0);
        editSpotRanks.putInt("populated", 1);
        editSpotRanks.commit();
        
        
        // Get the application instance
        app = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_create_aloop);
	       // enabling action bar app icon and behaving it as toggle button
	       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	       getSupportActionBar().setHomeButtonEnabled(true);
		
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
	
        drinksButton = (ImageButton) findViewById(R.id.drinksButton);
        foodButton = (ImageButton) findViewById(R.id.foodButton);
        movieButton = (ImageButton) findViewById(R.id.movieButton);
        bikeButton = (ImageButton) findViewById(R.id.bikeButton);
        groceryButton = (ImageButton) findViewById(R.id.groceryButton);
        waterButton = (ImageButton) findViewById(R.id.waterButton);
        hotelButton = (ImageButton) findViewById(R.id.hotelButton);
        airportButton = (ImageButton) findViewById(R.id.airportButton);
        photoButton = (ImageButton) findViewById(R.id.photoButton);
        libraryButton = (ImageButton) findViewById(R.id.libraryButton);
        natureButton = (ImageButton) findViewById(R.id.natureButton);
        paintingButton = (ImageButton) findViewById(R.id.paintingButton);
        spotSubmit = (ImageButton) findViewById(R.id.spotSubmit);
        
        drinksSelect = (ImageView) findViewById(R.id.drinksSelect);
        foodSelect = (ImageView) findViewById(R.id.foodSelect);
        movieSelect = (ImageView) findViewById(R.id.movieSelect);
        bikeSelect = (ImageView) findViewById(R.id.bikeSelect);
        grocerySelect = (ImageView) findViewById(R.id.grocerySelect);
        waterSelect = (ImageView) findViewById(R.id.waterSelect);
        hotelSelect = (ImageView) findViewById(R.id.hotelSelect);
        airportSelect = (ImageView) findViewById(R.id.airportSelect);
        photoSelect = (ImageView) findViewById(R.id.photoSelect);
        librarySelect = (ImageView) findViewById(R.id.librarySelect);
        natureSelect = (ImageView) findViewById(R.id.natureSelect);
        paintingSelect = (ImageView) findViewById(R.id.paintingSelect);
         
        
        drinksButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_bar_black_48dp;
            	  int grey = R.drawable.ic_local_bar_white_48dp;
      		      pickCounter("drinks",drinksSelect,drinksButton,black,grey);
               }
          });
        
        foodButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_restaurant_black_48dp;
            	  int grey = R.drawable.ic_local_restaurant_white_48dp;
      		      pickCounter("food",foodSelect,foodButton,black,grey);
               }
          });
       
        movieButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_movies_black_48dp;
            	  int grey = R.drawable.ic_local_movies_white_48dp;
      		      pickCounter("movie",movieSelect,movieButton,black,grey);
               }
          });
        
        bikeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_directions_bike_black_48dp;
            	  int grey = R.drawable.ic_directions_bike_white_48dp;
      		      pickCounter("bike",bikeSelect,bikeButton,black,grey);
               }
          });
       
        groceryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_grocery_store_black_48dp;
            	  int grey = R.drawable.ic_local_grocery_store_white_48dp;
      		      pickCounter("grocery",grocerySelect,groceryButton,black,grey);
               }
          });
        
        waterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_directions_ferry_black_48dp;
            	  int grey = R.drawable.ic_directions_ferry_white_48dp;
      		      pickCounter("water",waterSelect,waterButton,black,grey);
               }
          });

        hotelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_hotel_black_48dp;
            	  int grey = R.drawable.ic_hotel_white_48dp;
      		      pickCounter("hotel",hotelSelect,hotelButton,black,grey);
               }
          });
        
        airportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_flight_black_48dp;
            	  int grey = R.drawable.ic_flight_white_48dp;
      		      pickCounter("airport",airportSelect,airportButton,black,grey);
               }
          });
        
        photoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_see_black_48dp;
            	  int grey = R.drawable.ic_local_see_white_48dp;
      		      pickCounter("photo",photoSelect,photoButton,black,grey);
               }
          });
        
        libraryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_local_library_black_48dp;
            	  int grey = R.drawable.ic_local_library_white_48dp;
      		      pickCounter("library",librarySelect,libraryButton,black,grey);
               }
          });
        
        natureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_landscape_black_48dp;
            	  int grey = R.drawable.ic_landscape_white_48dp;
      		      pickCounter("nature",natureSelect,natureButton,black,grey);
               }
          });
        
        paintingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
            	  int black = R.drawable.ic_palette_black_48dp;
            	  int grey = R.drawable.ic_palette_white_48dp;
      		      pickCounter("painting",paintingSelect,paintingButton,black,grey);
            	  
               }
          });

      spotSubmit.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
        	  submitSpots();
          }
          });
	}
	
	private void submitSpots() {
	 Toast.makeText(getApplicationContext(),String.valueOf(counter), Toast.LENGTH_LONG).show();
        editSpotRanks.putInt("counter", counter);
        editSpotRanks.commit();
		Intent intent = new Intent(this, PopulateDetailsActivity.class);
		startActivity(intent);
	}

	
    private void pickCounter(String item,View view1,ImageButton view2,int black,int grey){

        if (spotRanks.getInt(item, 0) == 0){
     		editSpotRanks.putInt(item, counter);
           	editSpotRanks.commit();
           	view2.setImageResource(grey);
            view1.setVisibility(View.VISIBLE);
            view1.setBackgroundResource(orderSpot(counter));
           	counter++;
        }else {

       	  view1.setVisibility(View.GONE);
       	  view2.setImageResource(black);
          setRanks(item);
          refreshUI();
   		  editSpotRanks.putInt(item, 0);
       	  editSpotRanks.commit();
          counter--;
          
        }
        	
    }
    
    private void setRanks(String item){
    	for (int i=0;i<spots.length;i++){
    		if (spotRanks.getInt(spots[i],0)>spotRanks.getInt(item,0)){
    			editSpotRanks.putInt(spots[i],spotRanks.getInt(spots[i],0)-1 );
    	       	  editSpotRanks.commit();
    		}
    	}
    }
    
    private void refreshUI(){
		drinksSelect.setBackgroundResource(orderSpot(spotRanks.getInt("drinks", 0)));
		foodSelect.setBackgroundResource(orderSpot(spotRanks.getInt("food", 0))) ;
		movieSelect.setBackgroundResource(orderSpot(spotRanks.getInt("movie", 0))) ;
		bikeSelect.setBackgroundResource(orderSpot(spotRanks.getInt("bike", 0))) ;
		grocerySelect.setBackgroundResource(orderSpot(spotRanks.getInt("grocery", 0))) ;
		waterSelect.setBackgroundResource(orderSpot(spotRanks.getInt("water", 0))) ;
		hotelSelect.setBackgroundResource(orderSpot(spotRanks.getInt("hotel", 0))) ;
		airportSelect.setBackgroundResource(orderSpot(spotRanks.getInt("airport", 0))) ;
		photoSelect.setBackgroundResource(orderSpot(spotRanks.getInt("photo", 0)));
		librarySelect.setBackgroundResource(orderSpot(spotRanks.getInt("library", 0))) ;
		natureSelect.setBackgroundResource(orderSpot(spotRanks.getInt("nature", 0))) ;
		paintingSelect.setBackgroundResource(orderSpot(spotRanks.getInt("painting", 0))) ;
    }
    
    private int orderSpot(int counter){
    	switch (counter){
    	case 0: return R.drawable.ic_launcher_1_red;
    	case 1: return R.drawable.ic_launcher_1_red;
    	case 2: return R.drawable.ic_launcher_2_red;
      	case 3: return R.drawable.ic_launcher_3_red;
      	case 4: return R.drawable.ic_launcher_4_red;
      	case 5: return R.drawable.ic_launcher_5_red;
      	case 6: return R.drawable.ic_launcher_6_red;
      	case 7: return R.drawable.ic_launcher_7_red;
      	case 8: return R.drawable.ic_launcher_8_red;
      	case 9: return R.drawable.ic_launcher_9_red;
      	case 10: return R.drawable.ic_launcher_10_red;
      	case 11: return R.drawable.ic_launcher_11_red;
      	case 12: return R.drawable.ic_launcher_12_red;
      	default: return R.drawable.ic_launcher_1_red;
    	}
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	}


}
