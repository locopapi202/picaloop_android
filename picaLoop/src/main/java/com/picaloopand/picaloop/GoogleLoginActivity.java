package com.picaloopand.picaloop;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.FunctionCallback;
import com.parse.LogInCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;


public class GoogleLoginActivity extends Activity implements ConnectionCallbacks,
OnConnectionFailedListener {
	
    ImageView imgProfilePic;
    TextView googleName, googleEmail;
    //private LinearLayout googlellProfileLayout;
	//boolean googleLoginSignInClicked = true;
	boolean googleLoginIntentInProgress;
	// Google client to interact with Google API
	public static GoogleApiClient mGoogleApiClient;
	public static final int RC_SIGN_IN = 0;
	private static final String TAG = "WelcomeActivity";
	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean googleLoginButtonClicked = true;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult googleLoginConnectionResult;
	
    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
	
    SharedPreferences userProfile;
    public static Editor editProfile;
	
    protected MyApplication app;
    
    public static final String SIGN_IN_METHOD = "google";
    

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_google_login);
        
       // imgProfilePic = (ImageView) findViewById(R.id.googleProfilePic);
       // googleName = (TextView) findViewById(R.id.googleName);
      //  googleEmail = (TextView) findViewById(R.id.googleEmail);
      //  googlellProfileLayout = (LinearLayout) findViewById(R.id.googlellProfile);
        userProfile = getSharedPreferences("userProfile", MODE_PRIVATE);
        editProfile = userProfile.edit();
        // Get the application instance
       app = (MyApplication)getApplication();
        
        // Initializing google plus api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
  
    }
	
    public void onStart() {
        super.onStart();
       mGoogleApiClient.connect();
    }
 
    
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }




	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
	if (googleLoginConnectionResult == null){
			Toast.makeText(this, "googleLoginConnectionResult null!", Toast.LENGTH_LONG).show();
		}
	 if (googleLoginConnectionResult.hasResolution()) {
	    try {
	      googleLoginIntentInProgress = true;
	      googleLoginConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
	    } catch (SendIntentException e) {
	      // The intent was canceled before it was sent.  Return to the default
	      // state and attempt to connect to get an updated ConnectionResult.
	     googleLoginIntentInProgress = false;
	      mGoogleApiClient.connect();
	    }
	  }
	}

	public void onActivityResult(int requestCode, int responseCode, Intent intent) {
		  if (requestCode == RC_SIGN_IN) {
		    if (responseCode != Activity.RESULT_OK) {
		    	googleLoginButtonClicked = false;
		    }

		    googleLoginIntentInProgress = false;

		    if (!mGoogleApiClient.isConnecting()) {
		      mGoogleApiClient.connect();
		    }
		  }
		}
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		   if (!result.hasResolution()) {
		        GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
		                0).show();
				editProfile.putString("userSignIn", null);
				editProfile.commit();
			   //Intent intent = new Intent(this, WelcomeActivity.class);
				//startActivity(intent);
				
		        return;
		    }
		  if (!googleLoginIntentInProgress) {
		    // Store the ConnectionResult so that we can use it later when the user clicks
		    // 'sign-in'.
		    googleLoginConnectionResult = result;

		    if (googleLoginButtonClicked) {
		      // The user has already clicked 'sign-in' so we attempt to resolve all
		      // errors until the user is signed in, or they cancel.
		      resolveSignInError();
		    }
		  }
			/*editProfile.putString("userSignIn", null);
			editProfile.commit();
		    Intent intent = new Intent(this, WelcomeActivity.class);
			startActivity(intent);*/
		}
		
	@Override
	public void onConnected(Bundle connectionHint) {
		googleLoginButtonClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	    // Get user's information
	    getProfileInformation();
	    

	    // Update the UI after signin
	    //updateUI(true);
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
	    mGoogleApiClient.connect();
	   // updateUI(false);
	}
	


	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
	    try {
	        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	            Person currentPerson = Plus.PeopleApi
	                    .getCurrentPerson(mGoogleApiClient);
	            String personName = currentPerson.getDisplayName();
	            String personPhotoUrl = currentPerson.getImage().getUrl();
	            String personGooglePlusProfile = currentPerson.getUrl();
	           final String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
	 
	            Log.e(TAG, "Name: " + personName + ", plusProfile: "
	                    + personGooglePlusProfile + ", email: " + email
	                    + ", Image: " + personPhotoUrl);
	 
	            // by default the profile url gives 50x50 px image only
	            // we can replace the value with whatever dimension we want by
	            // replacing sz=X
	            personPhotoUrl = personPhotoUrl.substring(0,
	                    personPhotoUrl.length() - 2)
	                    + PROFILE_PIC_SIZE;
	            
	            
	            editProfile.clear();
	            editProfile.putString("userSignIn", SIGN_IN_METHOD);
	            editProfile.putString("userName", personName);
	            editProfile.putString("userEmail", email);
	            editProfile.putString("userProfilePic", personPhotoUrl);
	            editProfile.putString("firstTime", "no");
	            editProfile.commit();  
	            
	          //  UsersDBTable user = new UsersDBTable(personName, null, null, null, email, personPhotoUrl, SIGN_IN_METHOD);
	          //  user.save();
	            
	            //googleName.setText(personName);
	            //googleEmail.setText(email);
	 
            
	          //  new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						googleAuthWithParse(email);
					}
				}).start();
	 
	        } else {
	            Toast.makeText(this,
	                    "Person information is null", Toast.LENGTH_LONG).show();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
   
	    
	}

	public void showToast(String text)
	{
		Toast.makeText(this,
				text, Toast.LENGTH_LONG).show();
	}
	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	 
	    public LoadProfileImage(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }
	 
	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }
	 
	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}



	protected void googleAuthWithParse(String email) {
		// TODO Auto-generated method stub
		String scopes = "oauth2:" + Scopes.PLUS_LOGIN + " ";
		String googleAuthCode = null;
		try {
			googleAuthCode = GoogleAuthUtil.getToken(
					this,                                           // Context context
					email,                                             // String email
					scopes,                                            // String scope
					null                                      // Bundle bundle
			);
		} catch (UserRecoverableAuthException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (GoogleAuthException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Log.e(TAG, "Authentication Code: " + googleAuthCode);
		Log.e(TAG, "Email: " + email);

		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("code", googleAuthCode);
		params.put("email", email);
		//loads the Cloud function to create a Google user
		ParseCloud.callFunctionInBackground("accessGoogleUser", params, new FunctionCallback<Object>() {
			@Override
			public void done(Object returnObj, ParseException e) {
				if (e == null) {
					Log.e("AccessToken", returnObj.toString());
					ParseUser.becomeInBackground(returnObj.toString(), new LogInCallback() {
						public void done(final ParseUser user, ParseException e) {
							if (user != null && e == null) {
								showToast("The Google user validated");

								if (user.isNew()) {
									//isNew means firsttime
								} else {

									loginSuccess();

								}
							} else if (e != null) {
								showToast("There was a problem creating your account.");
								e.printStackTrace();
								mGoogleApiClient.disconnect();
							} else
								showToast("The Google token could not be validated");
						}
					});
				} else {
					if (e != null) {

						try {
							JSONObject jsonObject = new JSONObject(e.getMessage());
							showToast(jsonObject.getString("message"));
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
						mGoogleApiClient.disconnect();
					}
				}
			}
		});
	}

private void loginSuccess()
{
	Intent intent = new Intent(this, LoopFeedActivity.class);
	startActivity(intent);
	finish();
}
	/**
	 * Sign-out from google
	 * */
	/*public static void signOutFromGplus(MyApplication app, GoogleApiClient googleApi) {
	    if (googleApi.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(googleApi);
	        googleApi.disconnect();
	        googleApi.connect();
	      //  updateUI(false);
	        editProfile.putString("userSignIn", null);
			app.getApplicationContext().startActivity(new Intent(app.getApplicationContext(), GoogleLoginActivity.class));
			
	    }
	} */
	
	
}
