package com.picaloopand.picaloop;

import java.util.Arrays;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoadMediaFragmentGrid extends Fragment implements OnItemClickListener {
	
private static final int SELECT_PICTURE = 1;
private static final String TAG = "LoadMediaFragmentGrid";

ImageView image;
TextView addImages;
View view; 
GridView gridview;
Uri newPic;
ImageGridAdapter imgAdpt;
Uri cameraButton = Uri.parse("android.resource://com.picaloopand.picaloop/drawable/ic_camera_alt_grey600_48dp");


@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	if (savedInstanceState == null) {
		imgAdpt = new ImageGridAdapter(getActivity(), cameraButton);
	}
	else{
		if (savedInstanceState != null) {
	        if (savedInstanceState.containsKey("img")) {
	        	
	        	Parcelable[] parcelableArray = savedInstanceState.getParcelableArray("img");
	        	Uri[] mThumbUri = null;
	        	if (parcelableArray != null) {
	        		mThumbUri = Arrays.copyOf(parcelableArray, parcelableArray.length, Uri[].class);
	        		imgAdpt = new ImageGridAdapter(getActivity());
	        		int numOfImages = mThumbUri.length;
	        		Log.e(TAG, "images in numOfImages " + numOfImages);
	        		for(int i=0; i < numOfImages; i++){
	        			imgAdpt.addUri(mThumbUri[i]);
	        			Log.e(TAG, "images in loop " + mThumbUri[i]);
	        		}
	        		//Toast.makeText(getActivity(), " images in oncreate" + imgAdpt.getCount(), Toast.LENGTH_SHORT).show();
	        		Log.e(TAG, "images in oncreate " + imgAdpt.getCount());
	        		
	        	}
	        	
	         }
	    }
	}
	
	
}


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_load_media_grid,
			container, false);
	gridview = (GridView) view.findViewById(R.id.gridview);
	
	gridview.setAdapter(imgAdpt);
	
	
    gridview.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
           // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            if(position == 0){
            	Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);  
              //  Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                
            }
            else{
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getActivity().getApplicationContext(), FullImageActivity.class);
    		
                Uri selectUri = imgAdpt.getUri(position);
                
                i.putExtra("imageUri", selectUri);
                startActivity(i);            	
            };
        }
    });

	return view;

}




@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	// TODO Auto-generated method stub
	
}


public void onActivityResult(int requestCode, int resultCode, Intent data) {
	Log.e(TAG, "in onactivity ");
    if (resultCode == Activity.RESULT_OK) {
        if (requestCode == SELECT_PICTURE) {
        	newPic = data.getData();
        	imgAdpt.addUri(newPic);
        	//Toast.makeText(getActivity(), "images in onactivityres " + imgAdpt.getCount(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "images in onactivityres " + imgAdpt.getCount());
        	gridview.setAdapter(imgAdpt);
        	
        /*	Uri[] bUri = imgAdpt.getUris();
    		int numOfImages = bUri.length;
    		Log.e(TAG, "images in numOfImages onact " + numOfImages);
    		for(int i=0; i < numOfImages; i++){
    			Log.e(TAG, "images in loop onact " + bUri[i]);
    		}*/
        	           
        }
    }
}

@Override
public void onStop(){
	super.onStop();
	Log.e(TAG, "images in onstop " + imgAdpt.getCount());
}

@Override
public void onDestroyView(){
	super.onDestroyView();
	Log.e(TAG, "images in ondestroyview " + imgAdpt.getCount());
}

@Override
public void onDestroy(){
	super.onDestroy();
	Log.e(TAG, "images in onDestroy " + imgAdpt.getCount());
}

@Override
public void onDetach(){
	super.onDetach();
	Log.e(TAG, "images in onDetach " + imgAdpt.getCount());
}




@Override
public void onSaveInstanceState(Bundle outState) {
    // TODO Auto-generated method stub

	Uri[] mThumbUris = imgAdpt.getUris();
	Toast.makeText(getActivity(), " images in onsaveinsta" + imgAdpt.getCount(), Toast.LENGTH_SHORT).show();
	Log.e(TAG, "images in onsaveinsta " + imgAdpt.getCount());
	outState.putParcelableArray("img", mThumbUris);
    super.onSaveInstanceState(outState);
}

/*
@Override
public void onViewStateRestored(Bundle savedInstanceState) {
    // TODO Auto-generated method stub
    if (savedInstanceState != null) {
        if (savedInstanceState.containsKey("img")) {
        	
        	Parcelable[] parcelableArray = savedInstanceState.getParcelableArray("img");
        	Uri[] mThumbUri = null;
        	if (parcelableArray != null) {
        		mThumbUri = Arrays.copyOf(parcelableArray, parcelableArray.length, Uri[].class);
        		if(imgAdpt == null)
        			Toast.makeText(getActivity(), "imgAdpt in restore is null", Toast.LENGTH_SHORT).show();
        		imgAdpt = new ImageGridAdapter(getActivity(), mThumbUri);
        		gridview.setAdapter(imgAdpt);
        		savedInstanceState = null;
        	}
        	
        }
    }

    super.onViewStateRestored(savedInstanceState);
} */

public String getPath(Uri uri) {
	String res = null;
    String[] projection = { MediaStore.Images.Media.DATA };
    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
    if(cursor.moveToFirst()){;
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    res = cursor.getString(column_index);
 }
 cursor.close();
 return res;
}


}

