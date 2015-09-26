package com.picaloopand.picaloop;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class LoopCollectionFragmentGrid extends Fragment implements OnItemClickListener {
	
private static final String TAG = "LoopCollectionFragmentGrid";

ImageView image;
TextView addImages;
View view; 
GridView gridview;
Uri newPic;
ImageGridAdapter imgAdpt;
Uri addLoop = Uri.parse("android.resource://com.picaloopand.picaloop/drawable/ic_my_library_add_grey600_48dp");
protected MyApplication app;
private static final String ARG_SECTION_NUMBER = "section_number";


public static LoopCollectionFragmentGrid newInstance(int sectionNumber) {
	LoopCollectionFragmentGrid fragment = new LoopCollectionFragmentGrid();
	Bundle args = new Bundle();
	args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	fragment.setArguments(args);
	return fragment;
}

public LoopCollectionFragmentGrid() {
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	view = inflater.inflate(R.layout.fragment_loop_collection_grid,
			container, false);
	gridview = (GridView) view.findViewById(R.id.loopcollectiongridview);
	
	imgAdpt = new ImageGridAdapter(getActivity(), addLoop);
	gridview.setAdapter(imgAdpt);
	
	app = (MyApplication)getActivity().getApplicationContext();
	
    gridview.setOnItemClickListener(new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
           // Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
            if(position == 0){
            	MyApplication.createALoop(app);
                
            };
        }
    });

	return view;

}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    // TODO Auto-generated method stub
	    super.onSaveInstanceState(outState);
	}



}

