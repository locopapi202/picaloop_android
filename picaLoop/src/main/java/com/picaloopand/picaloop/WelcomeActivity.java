package com.picaloopand.picaloop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;


public class WelcomeActivity extends Activity implements OnClickListener {
	
	SignInButton googleLoginButton;
	Button fbLoginButton;
	Button pclLoginButton;
	String googleLoginButtonText = "Sign in with Google";
	private static final String GOOGLESIGNIN = "google";
	private static final String FACEBOOKSIGNIN = "facebook";
	private static final String PCLSIGNIN = "pcl";
	
	SharedPreferences userProfile;
	public static Editor editProfile;		
	public static final String EXTRA_MESSAGE = "com.picaloopand.picaloop.MESSAGE";
		
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        userProfile = this.getSharedPreferences("userProfile", MODE_PRIVATE);
        String userSignIn = userProfile.getString("userSignIn", null);
        editProfile = userProfile.edit();
        
        if(userSignIn != null){
        	if(userSignIn.contains(GOOGLESIGNIN)){
        		Intent intent = new Intent(this, GoogleLoginActivity.class);
        		startActivity(intent);
        		finish();
        	}
        	if(userSignIn.contains(FACEBOOKSIGNIN)){
        		Intent intent = new Intent(this, FacebookLoginActivity.class);
        		startActivity(intent);
        		finish();
        	}
        	if(userSignIn.contains(PCLSIGNIN)){
        		Intent intent = new Intent(this, EmailLoginActivity.class);
        		startActivity(intent);
        		finish();
        	}
        	
        }
        else{
        setContentView(R.layout.activity_welcome);
        
        googleLoginButton = (SignInButton) findViewById(R.id.googleLoginButton);
        googleLoginButton.setOnClickListener(this);
        fbLoginButton = (Button) findViewById(R.id.fbLoginButton);
        fbLoginButton.setOnClickListener(this);
        pclLoginButton = (Button) findViewById(R.id.pclLoginbutton);
        pclLoginButton.setOnClickListener(this);
        
        setGooglePlusButtonText(googleLoginButton, googleLoginButtonText);
        
      //Temp place holder for button colors
        fbLoginButton.setBackgroundColor(Color.rgb(59, 89, 182));
        fbLoginButton.setTextColor(Color.WHITE);
        pclLoginButton.setBackgroundColor(Color.rgb(64, 0, 72));
        pclLoginButton.setTextColor(Color.WHITE);
        }        
    }
    
	   public void onStart() {
	        super.onStart();
	        SharedPreferences editProfile = this.getSharedPreferences("userProfile", MODE_PRIVATE);
	        String userSignIn = editProfile.getString("userSignIn", null);
	        
	        if(userSignIn != null){
	        	if(userSignIn.contains(GOOGLESIGNIN)){
	        		Intent intent = new Intent(this, GoogleLoginActivity.class);
	        		startActivity(intent);
	        	}
	        	if(userSignIn.contains(FACEBOOKSIGNIN)){
	        		Intent intent = new Intent(this, FacebookLoginActivity.class);
	        		startActivity(intent);
	        	}
	        	if(userSignIn.contains(PCLSIGNIN)){
	        		Intent intent = new Intent(this, EmailLoginActivity.class);
	        		//String message =  FIRSTSIGNIN;
	        		//intent.putExtra(EXTRA_MESSAGE, message);
	        		startActivity(intent);
	        	}
	        	
	        }
	    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
 
    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item); */
    	return true;
    }


	@Override
	public void onClick(View view) {
		  switch (view.getId()) {
	        case R.id.googleLoginButton:
	            // gmail Signin button clicked
	            signInWithGoogle();
	            break;
	        case R.id.fbLoginButton:
	            // fb Signin button clicked
	        	signInWithFb();
	            break;
	        case R.id.pclLoginbutton:
	            // pcl Signin button clicked
	        	signInWithPcl();
	            break;
	        }
	}
	

	/**
	 * Sign-in using gmail
	 * */
	private void signInWithGoogle() {
		//Toast.makeText(getApplicationContext(), "Google Sign in Clicked!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, GoogleLoginActivity.class);
		startActivity(intent);
		finish();
	}
	/**
	 * Sign-in using fb
	 * */
	private void signInWithFb() {
		//Toast.makeText(getApplicationContext(), "FB Sign in Clicked!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, FacebookLoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * Sign-in using pcl
	 * */
	private void signInWithPcl() {
		//Toast.makeText(getApplicationContext(), "PCL Sign in Clicked!", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this, EmailLoginActivity.class);
		startActivity(intent);	
		finish();
	}
	
	 public void onBackPressed() {
		   Intent intent = new Intent(Intent.ACTION_MAIN);
		   intent.addCategory(Intent.CATEGORY_HOME);
		   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   startActivity(intent);
		 }
	
}
