
package com.picaloopand.picaloop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 










import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;


public class EmailLoginActivity extends Activity {
 
	// Declare Variables
	Button mRegisterButton;
	Button mEmailSignInButton;
	String userNametxt;
	String userPasswordtxt;
	String userFirstNametxt;
	String userLastNametxt;
	String userEmailtxt;
	
	EditText userPassword;
	EditText userName;
	EditText userEmail;
	EditText userFirstName;
	EditText userLastName;
	EditText userPicture;
	EditText signInMethod;
	
	private View mProgressView;
	private View mLoginFormView;
	
	SharedPreferences userProfile;
	public static Editor editProfile;
	String userSignIn;
	
	public static final String SIGN_IN_METHOD = "pcl";

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = this.getSharedPreferences("userProfile", MODE_PRIVATE);
		editProfile = userProfile.edit();
		userSignIn = userProfile.getString("userSignIn", null);
		// Determine whether the current user is an anonymous user
		if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
			// If user is anonymous, send the user to LoginSignupActivity.class
			LoginSignup();
		
			//finish();
		} else {
			// If current user is NOT anonymous user
			// Get current user data from Parse.com
			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				editProfile.clear();
	            editProfile.putString("userSignIn", SIGN_IN_METHOD);
	            editProfile.commit();
				// Send logged in users to LoopFeedActivity.class
				Intent intent = new Intent(EmailLoginActivity.this, LoopFeedActivity.class);
				startActivity(intent);
				finish();
			} else {
				// Send user to login or sign up
				LoginSignup();
			}
		}
 
	}

	private void LoginSignup() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_email_login);
		//getWindow().setBackgroundDrawableResource(R.drawable.loginsplash);
		// Locate EditTexts in main.xml
		userName = (EditText) findViewById(R.id.username);
		userPassword = (EditText) findViewById(R.id.password);
		//userEmail = (EditText) findViewById(R.id.email);
		// Locate Buttons in main.xml
		mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mRegisterButton = (Button) findViewById(R.id.email_register_button);
 
		// Login Button Click Listener
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
 
			public void onClick(View arg0) {
				
				// Retrieve the text entered from the EditText
				userPasswordtxt = userPassword.getText().toString();
				userNametxt = userName.getText().toString();
				//userEmailtxt = userEmail.getText().toString();
				mLoginFormView = findViewById(R.id.login_form);
				mProgressView = findViewById(R.id.login_progress);
				
				// Reset errors.
				userName.setError(null);
				userPassword.setError(null);
				
				boolean cancel = false;
				View focusView = null;
				
				// Check if the password field is empty and validate the password
				if (TextUtils.isEmpty(userPasswordtxt)) {
					userPassword.setError(getString(R.string.error_field_required));
					focusView = userPassword;
					cancel = true;
				
		        } 
				if (TextUtils.isEmpty(userNametxt)) {
					userName.setError(getString(R.string.error_field_required));
					focusView = userName;
					cancel = true;
				}
				if (cancel) {
					// There was an error; don't attempt login and focus the first
					// form field with an error.
					focusView.requestFocus();
				} 
				else {
				showProgress(true);
				// Send data to Parse.com for verification
				ParseUser.logInInBackground(userNametxt, userPasswordtxt,
						new LogInCallback() {
							public void done(ParseUser user, ParseException e) {
								if (user != null) {
									// If user exist and authenticated, send user to LoopFeedActivity.class
									//add the SIGN_IN_METHOD to the userProfile
									//should we move this on Parse?
									//not sure if we'll gonna need it for log off function
									editProfile.clear();
						            editProfile.putString("userSignIn", SIGN_IN_METHOD);
						            editProfile.commit();
						            
									Intent intent = new Intent(
											EmailLoginActivity.this,
											LoopFeedActivity.class);
									startActivity(intent);
									Toast.makeText(getApplicationContext(),
											"Successfully Logged in!",
											Toast.LENGTH_LONG).show();
									finish();
							} else {
									Toast.makeText(
											getApplicationContext(),
										"The user or password is not correct!",
											Toast.LENGTH_LONG).show();
									showProgress(false);
								}
							}
						});
				}
			}
		});
		
		// Sign up Button Click Listener
		mRegisterButton.setOnClickListener(new OnClickListener() {
 
				public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), RegisterFragment.class);
					startActivity(intent);	
					finish();
				}
			});
 
	}
	
	/*
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}

/*package com.picaloopand.picaloop;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

*//**
 * A login screen that offers login via email/password.
 *//*
public class EmailLoginActivity extends Activity implements
		LoaderCallbacks<Cursor> {

	*//**

	 * Keep track of the login task to ensure we can cancel it if requested.
	 *//*
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	SharedPreferences userProfile;
	public static Editor editProfile;
	Boolean firstTime = true;
	String email;
	String password;
	String userSignIn;
	String userName;
	String userPassword;
	
	public static final String SIGN_IN_METHOD = "pcl";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		userProfile = this.getSharedPreferences("userProfile", MODE_PRIVATE);
		editProfile = userProfile.edit();
		userSignIn = userProfile.getString("userSignIn", null);
		userName = userProfile.getString("userName", null);
		userPassword = userProfile.getString("userPassword", null);
		
    	if(userSignIn != null){
    		setContentView(R.layout.activity_email_login);
    		getWindow().setBackgroundDrawableResource(R.drawable.loginsplash);
			// Set up the login form.
			mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
			populateAutoComplete();
	
			mPasswordView = (EditText) findViewById(R.id.password);
						
			mLoginFormView = findViewById(R.id.login_form);
			mProgressView = findViewById(R.id.login_progress);
			
			mProgressView.setVisibility(View.VISIBLE);
			mLoginFormView.setVisibility(View.GONE);
			
    		firstTime = false;
    		attemptLogin();
    	}
    	else{
			setContentView(R.layout.activity_email_login);
			getWindow().setBackgroundDrawableResource(R.drawable.loginsplash);
			// Set up the login form.
			mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
			populateAutoComplete();
	
			mPasswordView = (EditText) findViewById(R.id.password);
			mPasswordView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
	
			Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
			mEmailSignInButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					attemptLogin();
				}
			});
	
			mLoginFormView = findViewById(R.id.login_form);
			mProgressView = findViewById(R.id.login_progress);
    	}
	}

	
	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	*//**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 *//*
	public void attemptLogin() {
		if (mAuthTask != null) {
			return;
		}
		
		if (firstTime == false){
			mAuthTask = new UserLoginTask(userName, userPassword);
			mAuthTask.execute((Void) null);
		}
		
		else{
			// Reset errors.
			mEmailView.setError(null);
			mPasswordView.setError(null);
	
			// Store values at the time of the login attempt.
			String email = mEmailView.getText().toString();
			String password = mPasswordView.getText().toString();
	
			boolean cancel = false;
			View focusView = null;
	
			// Check for a valid password, if the user entered one.
			if (!isPasswordValid(password)) {
				mPasswordView.setError(getString(R.string.error_invalid_password));
				focusView = mPasswordView;
				cancel = true;
			}
			// Check for a valid password, if the user entered one.
			if (TextUtils.isEmpty(password)) {
				mPasswordView.setError(getString(R.string.error_field_required));
				focusView = mPasswordView;
				cancel = true;
			}
	
			// Check for a valid email address.
			if (TextUtils.isEmpty(email)) {
				mEmailView.setError(getString(R.string.error_field_required));
				focusView = mEmailView;
				cancel = true;
			} else if (!isEmailValid(email)) {
				mEmailView.setError(getString(R.string.error_invalid_email));
				focusView = mEmailView;
				cancel = true;
			}
	
			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			} 
			else {
				// Show a progress spinner, and kick off a background task to
				// perform the user login attempt.
				showProgress(true);
				mAuthTask = new UserLoginTask(email, password);
				mAuthTask.execute((Void) null);
			}
		}
		
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with email logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with password logic
		return password.length() > 4;
	}

	*//**
	 * Shows the progress UI and hides the login form.
	 *//*
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	*//**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 *//*
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				EmailLoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	*//**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 *//*
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			
	    	if(userSignIn != null){
	    		return true;
	    	}
						
			if(userPassword != null){
				if(userName.equals(mEmail) && userPassword.equals(mPassword)){
					return true;
				}
				else{
					
					return false;
				}
			}
			else{
				// TODO: register the new account here.
	            editProfile.clear();
	            editProfile.putString("userSignIn", SIGN_IN_METHOD);
	            editProfile.putString("userName", mEmail);
	            editProfile.putString("userPassword", mPassword);
	            editProfile.putString("userEmail", mEmail);
	            // editProfile.putString("userProfilePic", personPhotoUrl);
	            editProfile.commit();
	            
	            UsersDBTable user = new UsersDBTable(mEmail, null, null, mPassword, mEmail, null, SIGN_IN_METHOD);
	            user.save();

			}
			
			return true;
		}

		
		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			//showProgress(false);

			if (success) {
				if(userSignIn == null){
					editProfile.putString("userSignIn", SIGN_IN_METHOD);
					editProfile.commit();
				}
			    Toast.makeText(getApplicationContext(), "User is connected!", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getApplicationContext(), LoopFeedActivity.class);
				startActivity(intent);
				finish();
			} else {
				showProgress(false);
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
*/