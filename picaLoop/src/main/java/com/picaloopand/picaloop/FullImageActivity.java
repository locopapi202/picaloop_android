package com.picaloopand.picaloop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

 
public class FullImageActivity extends Activity {
	private static final String TAG = "FullImageActivity";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        // get intent data
        Intent i = getIntent();
 
        // Selected image
        Uri selectImage = i.getParcelableExtra("imageUri");

 
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageURI(selectImage);
    }
 
}