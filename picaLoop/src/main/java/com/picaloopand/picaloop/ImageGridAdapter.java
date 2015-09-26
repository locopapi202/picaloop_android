package com.picaloopand.picaloop;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    protected MyApplication app;
    private static final String TAG = "imagegridadapter";
    
    // reference to camera button
    
    public Uri[] mThumbUris = {
    			
    };

    public ImageGridAdapter(Context c) {
        mContext = c;

    }

    public ImageGridAdapter(Context c, Uri uri) {
        mContext = c;
        this.addUri(uri);
    }
    
    public ImageGridAdapter(Context c, Uri[] uri) {
        mContext = c;
        Log.e(TAG, "existing thumarray in adapter intantiate " + mThumbUris.length);
        Log.e(TAG, "comming in array in adapter intantiate " + uri.length);
        this.mThumbUris = uri.clone();
        Log.e(TAG, "after clone in adapter intantiate " + mThumbUris.length);
    }

    public void addUri(Uri newUri) {
    	
     mThumbUris = addElement(mThumbUris, newUri);
    }
    
    private Uri[] addElement(Uri[] mThumbUris2, Uri newUri) {
    	mThumbUris2 = Arrays.copyOf(mThumbUris2, mThumbUris2.length + 1);
    	mThumbUris2[mThumbUris2.length - 1] = newUri;
		return mThumbUris2;
	}

	public int getCount() {
        return mThumbUris.length;
    }

    public Object getItem(int position) {
        return null;
    }
    
    public Uri[] getUris() {
        return mThumbUris;
    }
    
    public Uri getUri(int position) {
        return mThumbUris[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(325, 325));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);

            
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
            
            imageView.setImageBitmap(bitmap);
                        
        }   
        else {
            imageView = (ImageView) convertView;
        }

        return imageView;
    }
    
   


public String getRealPathFromURI(Context context, Uri contentUri) {
  Cursor cursor = null;
  try { 
    String[] proj = { MediaStore.Images.Media.DATA };
    cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
    return cursor.getString(column_index);
  } finally {
    if (cursor != null) {
      cursor.close();
    }
  }
}



   

}