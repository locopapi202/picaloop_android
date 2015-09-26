package com.picaloopand.picaloop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.androidquery.AQuery;

public class UserProfileActivity extends ActionBarActivity {
	
	SharedPreferences userProfile;
	protected MyApplication app;
	String picture;
	String user;
	String firstName;
	String lastName;
	String email;
	String signInMethod;
	AQuery aq;
	
	TextView userName, userEmail, SignIn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
	       // Get the application instance
		app = (MyApplication)getApplication();
		
		aq = new AQuery(this);
		setContentView(R.layout.activity_user_profile);
	       // enabling action bar app icon and behaving it as toggle button
	       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	       getSupportActionBar().setHomeButtonEnabled(true);
		
		userName = (TextView) findViewById(R.id.userName);
		userEmail = (TextView) findViewById(R.id.userEmail);
		SignIn = (TextView) findViewById(R.id.SignIn);

		picture = getUserPic();
		user = getUser();
		email = getEmail();
		signInMethod = getSignIn();
		
		displayProfile(picture, user, email, signInMethod);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
		return true;
	}

	
	public void displayProfile(String picUrl, String user, String email, String signIn){
		
		aq.id(R.id.ProfilePic).progress(R.id.progress).image(picUrl, true, true, 0, R.drawable.image_missing);
		 
		userName.setText(user);
		userEmail.setText(email);
		SignIn.setText(signIn);
			
	}
	
	public String getUserPic(){
		String pic = userProfile.getString("userProfilePic", null);
		return pic;
	}
	
	public String getUser(){
		String userName = userProfile.getString("userName", null);
		return userName;
	}
	
	public String getEmail(){
		String email = userProfile.getString("userEmail", null);
		return email;
	}
	
	public String getSignIn(){
		String signIn = userProfile.getString("userSignIn", null);
		return signIn;
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
