package com.picaloopand.picaloop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class MyApplication extends android.app.Application {

    private static MyApplication instance;
	private static final String GOOGLESIGNIN = "google";
	private static final String FACEBOOKSIGNIN = "facebook";
	private static final String PCLSIGNIN = "pcl";
	
	public static final String YOUR_APPLICATION_ID = "X1st94Z1U9ftnyGsMln0cDxRvXzoJOJ7XFmxaUC7";
	public static final String YOUR_CLIENT_KEY = "Qy8MSDJhnJP7e27gpReol92HvkFqKMLxfX4mYzgG";

    public MyApplication() {
    	instance = this;
    }
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        //Notice this initialization code here
//        ActiveAndroid.initialize(this);
        
     // Add your initialization code here
      Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
      ParseUser.enableAutomaticUser();
      ParseACL defaultACL = new ParseACL();
       // If you would like all objects to be private by default, remove this line.
      defaultACL.setPublicReadAccess(true);
      ParseACL.setDefaultACL(defaultACL, true);
      
    }

    public static Context getContext() {
    	return instance;
    }
    
    public static void openProfile(MyApplication app){
    	
        Intent userProfileIntent = new Intent(app.getApplicationContext(), UserProfileActivity.class);
        userProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	app.getApplicationContext().startActivity(userProfileIntent);
    	//Toast.makeText(app.getApplicationContext(), "open profile Clicked!", Toast.LENGTH_LONG).show();
    }
    
    public static void search(MyApplication app){
        Toast.makeText(app.getApplicationContext(), "Search Coming Soon!!", Toast.LENGTH_LONG).show();
    }
    
    public static void openSettings(MyApplication app){
    	
        Intent settingsIntent = new Intent(app.getApplicationContext(), SettingsActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	app.getApplicationContext().startActivity(settingsIntent);
        Toast.makeText(app.getApplicationContext(), "open settings Clicked!", Toast.LENGTH_LONG).show();
    }
    
    public static void createALoop(MyApplication app){
    	
        Intent createALoopIntent = new Intent(app.getApplicationContext(), CreateALoopActivity.class);
        createALoopIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.getApplicationContext().startActivity(createALoopIntent);
    }
    
    public static void createASpot(MyApplication app){
    	
        Intent createASpotIntent = new Intent(app.getApplicationContext(), CreateASpotActivity.class);
        createASpotIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        app.getApplicationContext().startActivity(createASpotIntent);
    }
    
    
    public static void signOut(MyApplication app, SharedPreferences userProfile){
    	String userSignInMethod;
        Editor editProfile;
        Intent welcomeIntent = new Intent(app.getApplicationContext(), WelcomeActivity.class);
	    welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	
        userSignInMethod = userProfile.getString("userSignIn", null);
        //ParseUser.logOut();
        //Toast.makeText(app.getApplicationContext(), "signOut Clicked! pcl", Toast.LENGTH_LONG).show();
        //app.getApplicationContext().startActivity(welcomeIntent);
        if(userSignInMethod != null){
			editProfile = userProfile.edit();
	        editProfile.putString("userSignIn", null);
	        editProfile.commit();
			
			if(userSignInMethod.contains(GOOGLESIGNIN)){
				//MyApplication.signOutFromGplus(app, mGoogleApiClient);
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
                ParseUser.logOut();
				app.getApplicationContext().startActivity(welcomeIntent);
			}
			if(userSignInMethod.contains(FACEBOOKSIGNIN)){
				Toast.makeText(app.getApplicationContext(), "signOut Clicked! facebooklogin", Toast.LENGTH_LONG).show();
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
				app.getApplicationContext().startActivity(welcomeIntent);
			}
			if(userSignInMethod.contains(PCLSIGNIN)){
				ParseUser.logOut();
			 	Toast.makeText(app.getApplicationContext(), "signOut Clicked! pcl", Toast.LENGTH_LONG).show();
				//app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), WelcomeActivity.class));
				app.getApplicationContext().startActivity(welcomeIntent);
							
			}
         }
    	
    }
    
    
	public static void signOutFromGplus(MyApplication app, GoogleApiClient googleApi) {
	  //  if (googleApi.isConnected()) {
	    //    Plus.AccountApi.clearDefaultAccount(googleApi);
	      //  googleApi.disconnect();
	     //   googleApi.connect();
	      //  updateUI(false);
		    Intent welcomeIntent = new Intent(app.getApplicationContext(), WelcomeActivity.class);
		    welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			app.getApplicationContext().startActivity(welcomeIntent);
            			
	   // }
	} 

}