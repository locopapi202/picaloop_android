package com.picaloopand.picaloop;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class FacebookLoginActivity extends FragmentActivity {

	private LoginButton loginButton;
	public UiLifecycleHelper uilifecyclehelper ;
	public Session session;
	private GraphUser user;
	private ProfilePictureView profilePictureView;
	private TextView greeting;
    private UiLifecycleHelper uiHelper;
    private static final String TAG = "FacebookLoginActivity";
     
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	 setContentView(R.layout.activity_facebook_login);
    	
    	uiHelper = new UiLifecycleHelper(this, callback);
    	uiHelper.onCreate(savedInstanceState);
    	
    	loginButton = (LoginButton) findViewById(R.id.authButton);
    	profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
    	greeting = (TextView) findViewById(R.id.greeting);
    	
        
    	
    	loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
               FacebookLoginActivity.this.user = user;
               updateUI();
            }
        });
        
        
       
    }
    
    @Override
    public void onResume() {
        super.onResume();
     // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
               (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }
    
    private void updateUI() {
       // Session session = Session.getActiveSession();
       

        if (user != null) {
            profilePictureView.setProfileId(user.getId());
            greeting.setText(getString(R.string.hello_user, user.getFirstName(), user.getLastName()));
            Toast.makeText(getApplicationContext(), "Session is yu to the hu !!", Toast.LENGTH_LONG).show();
            
        } else {
            profilePictureView.setProfileId(null);
        	greeting.setText(null);
            Toast.makeText(getApplicationContext(), "Session is inactive", Toast.LENGTH_LONG).show();
        }
    }    

}   
  