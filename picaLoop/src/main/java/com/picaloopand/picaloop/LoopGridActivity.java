package com.picaloopand.picaloop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

public class LoopGridActivity extends BaseAdapter {

	SharedPreferences spotRanks;
	SharedPreferences userProfile;
	private final LayoutInflater mInflater;
	public static Editor editSpotRanks;
	
	
	private Context mContext;
	
	// Keep all Images in array
    public Uri[] mThumbUris = {
        };
    public Integer[] mIcons = {
    };

    public String[] mSpotNames = {
    };
    
    public String[] mSpotMentions = {
    };
    
    public Integer[] mSpotRating = {
    };
    
    public String[] mSpotNamesBlanks = {"","","",""
    };
    

    public Integer option = 1;
	// Constructor
	public LoopGridActivity(Context c) {
	      mContext = c;
	      mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	   }
	
	
	public LoopGridActivity(Context c, Integer display, Uri[] spots, String[] spotnames, String[] spotmentions, Integer[] icons, Integer[] rating) {
		      mContext = c;
		      option = display;
		      mThumbUris = spots;
		      mSpotNames = spotnames;
		      mSpotMentions = spotmentions;
		      mIcons = icons;
		      mSpotRating = rating;
		      
		      mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   }
		 
   
	   public int getCount() {
	      return mThumbUris.length;
	   }

	   public Object getItem(int position) {
	      return null;
	   }

	   public long getItemId(int position) {
	      return 0;
	   }
	   
	   // create a new ImageView for each item referenced by the Adapter
	   public View getView(int position, View convertView, ViewGroup parent) {
	        // RelativeLayout as used in the xml view
	        RelativeLayout customView = null;
	        if (convertView == null) {
	        	convertView = (RelativeLayout) mInflater.inflate(R.layout.loop_grid_text, null);
	        					
	        } 

	         ImageView loop_image = (ImageView) convertView.findViewById(R.id.loop_image);
	         loop_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_image.setPadding(1, 1, 1, 1);
	         
	         //Scale down image to avoid running out of memory
	            BitmapFactory.Options options = new BitmapFactory.Options();
	            options.inDither = false;
	            options.inJustDecodeBounds = false;
	            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	            options.inPurgeable = true;
	            if(position == 0){
	                options.inSampleSize = 1;
	            }
	            else{
		            options.inSampleSize = 4;
	            }
	            InputStream is = null;
				try {
					is = mContext.getContentResolver().openInputStream(mThumbUris[position]);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
	            try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            loop_image.setImageBitmap(bitmap);
	         
	         //loop_image.setImageResource(mThumbIds[position]);
	         

	         TextView loop_info = (TextView) convertView.findViewById(R.id.loop_info);
	         
	         if (option == 1){
	        	 loop_info.setText(mSpotNames[position]);
	         }
	         
	         if (option == 3){
	        	 loop_info.setText(mSpotMentions[position]);
	         }
              
	         
	         ImageView loop_icon = (ImageView) convertView.findViewById(R.id.loop_icon);
	         loop_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_icon.setPadding(1, 1, 1, 1);
	         loop_icon.setImageResource(mIcons[position]);
	         
	         ImageView loop_rating1 = (ImageView) convertView.findViewById(R.id.loop_rating1);
	         loop_rating1.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_rating1.setPadding(1, 1, 1, 1);
	         
	         ImageView loop_rating2 = (ImageView) convertView.findViewById(R.id.loop_rating2);
	         loop_rating2.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_rating2.setPadding(1, 1, 1, 1);
	         
	         ImageView loop_rating3 = (ImageView) convertView.findViewById(R.id.loop_rating3);
	         loop_rating3.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_rating3.setPadding(1, 1, 1, 1);
	         
	         ImageView loop_rating4 = (ImageView) convertView.findViewById(R.id.loop_rating4);
	         loop_rating4.setScaleType(ImageView.ScaleType.CENTER_CROP);
	         loop_rating4.setPadding(1, 1, 1, 1);
	         if (option == 2){

	        	switch (mSpotRating[position]){
	        	case 1:loop_rating1.setImageResource(R.drawable.ic_stat_rating);
	        	       break;
	        	case 2:loop_rating1.setImageResource(R.drawable.ic_stat_rating);
	        		   loop_rating2.setImageResource(R.drawable.ic_stat_rating);
	        		   break;
	        	case 3:loop_rating1.setImageResource(R.drawable.ic_stat_rating);
	        	       loop_rating2.setImageResource(R.drawable.ic_stat_rating);
	        	       loop_rating3.setImageResource(R.drawable.ic_stat_rating);
	        	       break;
	        	case 4:loop_rating1.setImageResource(R.drawable.ic_stat_rating);
	        	       loop_rating2.setImageResource(R.drawable.ic_stat_rating);
	        	       loop_rating3.setImageResource(R.drawable.ic_stat_rating);
	        	       loop_rating4.setImageResource(R.drawable.ic_stat_rating);
	        	       break;
	        	default:loop_rating1.setImageResource(R.drawable.ic_stat_rating);
	        	}
	  
	        	
	         

	         }
			 return convertView;
			 

	    }
       

	}