package com.picaloopand.picaloop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoopMapViewActivity extends ActionBarActivity implements OnMapReadyCallback{
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loop_map_view);
		
		
			MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        		mapFragment.getMapAsync(this);
	}
	
    @Override
    public void onMapReady(GoogleMap map) {
        Double centerLat;
        Double centerLong;
    	
       
    	LatLng movie = new LatLng(40.087815,-75.393722);
        LatLng restuarant = new LatLng(40.146433,-75.31915);
        LatLng drinks = new LatLng(40.074188,-75.305604);
        
        map.setMyLocationEnabled(true);
        //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        
        centerLat = ( movie.latitude + restuarant.latitude + drinks.latitude)/3;
        centerLong = ( movie.longitude + restuarant.longitude + drinks.longitude)/3;
        
        LatLng center = new LatLng (centerLat,centerLong);
        
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(center,11));
         

       // Setting a custom info window adapter for the google map
        map.setInfoWindowAdapter(new InfoWindowAdapter(){

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            
         // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getLayoutInflater().inflate(R.layout.loop_grid_text_small, null);
                
                String info = arg0.getTitle();
               
                
                
                ImageView loopImage = (ImageView) v.findViewById(R.id.loop_image);
                loopImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
   	         	loopImage.setPadding(1, 1, 1, 1);
   	         	
                if (arg0.getSnippet() == "movie"){
                	loopImage.setImageResource(R.drawable.movie);
                }else if (arg0.getSnippet() == "drinks"){
                	loopImage.setImageResource(R.drawable.bar_wine);
                }else if (arg0.getSnippet() == "restuarant"){
                	loopImage.setImageResource(R.drawable.restuarant);
                };
   	         	
                
                TextView loopInfo = (TextView) v.findViewById(R.id.loop_info);
                loopInfo.setText(info);
                
                return v;

            }
            
           
        });
        
        Marker movieMarker = map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        .title("UA King of Prussia")
                .snippet("movie")
                .position(movie));

       movieMarker.showInfoWindow();
        
        Marker restMarker =  map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        .title("Minado")
        .snippet("restuarant")
        .position(restuarant));


        
        Marker drinksMarker = map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
        .title("Boathouse in Conshohocken")
        .snippet("drinks")
        .position(drinks));
        
        drinksMarker.showInfoWindow();
        
        PolylineOptions rectOptions = new PolylineOptions()
        .add(movie)
        .add(restuarant)
        .add(drinks)
        ;
        
        
        rectOptions.color(Color.RED).width(15).geodesic(true);
        
        Polyline polyline = map.addPolyline(rectOptions);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loop_map_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
