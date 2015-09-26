package com.picaloopand.picaloop;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadMediaFragment extends Fragment implements OnItemClickListener {
	
private static final int SELECT_PICTURE = 1;
private String selectedImagePath;

ImageView image;
TextView addImages;
View view;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_load_media,
			container, false);
	image = (ImageView) view.findViewById(R.id.bar_image);
			
	addImages = (TextView) view.findViewById(R.id.barTextView);
	addImages.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view)
        {
           //Toast.makeText(getActivity().getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
        	Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);  
        }
    });
	
	return view;

}

public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK) {
        if (requestCode == SELECT_PICTURE) {
            Uri selectedImageUri = data.getData();
            selectedImagePath = getPath(selectedImageUri);
            System.out.println("Image Path : " + selectedImagePath);
            image.setImageURI(selectedImageUri);
        }
    }
}

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

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	// TODO Auto-generated method stub
	
}



}

