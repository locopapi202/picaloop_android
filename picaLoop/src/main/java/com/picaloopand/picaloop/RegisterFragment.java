package com.picaloopand.picaloop;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;


public class RegisterFragment extends FragmentActivity {
	/**
     * Defining layout items.
     **/
    EditText inputFirstName;
    EditText inputLastName;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    
    String email;
	String password;
	String lastname;
	String firstname;
	String username;
	
    ImageView inputUserPicture;
    Button btnRegister;
    byte[] image;
    TextView registerErrorMsg;
    ParseFile file = null;
    
    private View mProgressView;
	private View mLoginFormView;
    
    SharedPreferences userProfile;
	public static Editor editProfile;
	String userSignIn;
	
	public static final String SIGN_IN_METHOD = "pcl";
 
    private static int RESULT_LOAD_IMAGE = 1;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.fragment_register);
    /**
     * Defining all layout items
     **/
      //  getWindow().setBackgroundDrawableResource(R.drawable.loginsplash);
       
       
/**
 * Button which Switches back to the login screen on clicked
 **/
        Button login = (Button) findViewById(R.id.bktologin_button);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), EmailLoginActivity.class);
                startActivityForResult(myIntent, 0);
                finish();
            }
        });
        
        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptRegisteration();
			}
		});
    
        Button buttonLoadImage = (Button) findViewById(R.id.load_picture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View view) {
				LoadPicture();
        	}
        });
    }
       
    /**
     * Function to load the profile picture
     **/	
		private void LoadPicture() {
			
			Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		
		@Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);

	        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	        	 Uri selectedImage = data.getData();
	            Bitmap bitmap;
				try {
					     bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
		                 // Convert it to byte
		                 ByteArrayOutputStream stream = new ByteArrayOutputStream();
		                 // Compress image to lower quality scale 1 - 100
		                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		                 image = stream.toByteArray();
		                 decodeUri(data.getData());
		                 
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	        }
	             
		}

        
  
	private void decodeUri(Uri data) {
    	 ParcelFileDescriptor parcelFD = null;
    	    try {
    	        parcelFD = getContentResolver().openFileDescriptor(data, "r");
    	        FileDescriptor imageSource = parcelFD.getFileDescriptor();

    	        //decode image
    	        BitmapFactory.Options o = new BitmapFactory.Options();
    	        o.inJustDecodeBounds = true;
    	        BitmapFactory.decodeFileDescriptor(imageSource, null, o);

    	        // the new size we want to scale to
    	        final int REQUIRED_SIZE = 1024;

    	        // Find the correct scale value. It should be the power of 2.
    	        int width_tmp = o.outWidth, height_tmp = o.outHeight;
    	        int scale = 1;
    	        while (true) {
    	            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
    	                break;
    	            }
    	            width_tmp /= 2;
    	            height_tmp /= 2;
    	            scale *= 2;
    	        }

    	        // decode with inSampleSize
    	        BitmapFactory.Options o2 = new BitmapFactory.Options();
    	        o2.inSampleSize = scale;
    	        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
    	        ImageView imageView = (ImageView) findViewById(R.id.imgView);
    	        imageView.setImageBitmap(bitmap);

    	    } catch (FileNotFoundException e) {
    	        // handle errors
    	    	e.printStackTrace();
    	    } catch (IOException e) {
    	        // handle errors
    	    	e.printStackTrace();
    	    } finally {
    	        if (parcelFD != null)
    	            try {
    	                parcelFD.close();
    	            } catch (IOException e) {
    	                // ignored
    	            }
    	    }
    	}


	/**
	 * Function to register the user to parse
	 */
	public void attemptRegisteration() {
			
		    userProfile = this.getSharedPreferences("userProfile", MODE_PRIVATE);
		    userSignIn = userProfile.getString("userSignIn", null);
			editProfile = userProfile.edit();
		    
			mLoginFormView = findViewById(R.id.login_form);
			mProgressView = findViewById(R.id.login_progress);
			
		    inputFirstName = (EditText) findViewById(R.id.firstname);
	        inputLastName = (EditText) findViewById(R.id.lastname);
	        inputUsername = (EditText) findViewById(R.id.username);
	        inputEmail = (EditText) findViewById(R.id.email);
	        inputPassword = (EditText) findViewById(R.id.password);
	        inputUserPicture = (ImageView) findViewById(R.id.imgView); 
			// Reset errors.
    	    inputEmail.setError(null);
    	    inputPassword.setError(null);
    	    inputFirstName.setError(null);
    	    inputLastName.setError(null);
    	    inputUsername.setError(null);
    	     
    	    // Store values at the time of the login attempt.
 			email = inputEmail.getText().toString();
 			password = inputPassword.getText().toString();
 			lastname = inputLastName.getText().toString();
 			firstname = inputFirstName.getText().toString();
 			username = inputUsername.getText().toString();
    	   
	
			boolean cancel = false;
			View focusView = null;
			
			// Check if the password field is empty and validate the password
			if (TextUtils.isEmpty(password)) {
				inputPassword.setError(getString(R.string.error_field_required));
				focusView = inputPassword;
				cancel = true;
			
	         } else if (!isPasswordValid(password)) {
					inputPassword.setError(getString(R.string.error_invalid_password));
					focusView = inputPassword;
					cancel = true;
			 }
			
			// Check if the email field is empty and validate the email
			if (TextUtils.isEmpty(email)) {
				inputEmail.setError(getString(R.string.error_field_required));
				focusView = inputEmail;
				cancel = true;
			} else if (!isEmailValid(email)) {
				inputEmail.setError(getString(R.string.error_invalid_email));
				focusView = inputEmail;
				cancel = true;
			}
			
			//Check for a valid firstname, lastname and username
			if (TextUtils.isEmpty(firstname)) {
				inputFirstName.setError(getString(R.string.error_field_required));
				focusView = inputFirstName;
				cancel = true;
			}
			if (TextUtils.isEmpty(lastname)) {
				inputLastName.setError(getString(R.string.error_field_required));
				focusView = inputLastName;
				cancel = true;
			}
			if (TextUtils.isEmpty(username)) {
				inputUsername.setError(getString(R.string.error_field_required));
				focusView = inputUsername;
				cancel = true;
			}
	
			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			} 
			else {
				
				showProgress(true);
				if(image!=null)
				{
				 // Save the profile picture as ParseFile
				 file = new ParseFile("profile_pic.jpg", image);
				 
				 file.saveInBackground(new SaveCallback() {
				    public void done(ParseException e) {
				       SaveUser();
				    }
				  });
				}
				else
				{
					SaveUser();
				}
				
			}
	}
			
				 private void SaveUser() {
					 
					 // If successful add file to user and signUpInBackground
			    	// Save new user data into Parse.com Data Storage
					
					ParseUser user = new ParseUser();
					user.setUsername(username);
					user.setPassword(password);
					user.setEmail(email);
					user.put("userFirstName", firstname);
					user.put("userLastName",lastname);
					if(file!=null)
					{
						user.put("userPicture", file);
					}
					
			        user.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								// Show a simple Toast message upon successful registration
								Toast.makeText(getApplicationContext(),
										"Successfully Signed up!",
										Toast.LENGTH_LONG).show();
								//add the SIGN_IN_METHOD to the userProfile
								//should we move this on Parse?
								//not sure if we'll gonna need it for log off function
								if(userSignIn == null){
									editProfile.putString("userSignIn", SIGN_IN_METHOD);
									editProfile.commit();
								}
								//sent the user to the LoopActivity.class
								Intent intent = new Intent(RegisterFragment.this, LoopFeedActivity.class);
								startActivity(intent);
								finish();
								
							} else {
								//Toast.makeText(getApplicationContext(),
								//		"Sign up Error", Toast.LENGTH_LONG)
								//		.show();
								boolean cancel = false;
								View focusView = null;
								showProgress(false);
								Toast.makeText(getApplicationContext(),
										e.getMessage(),
										Toast.LENGTH_LONG).show();
								String error = e.getMessage();
								if(error.contains("email"))
								{
									inputEmail.setError(getString(R.string.error_invalid_email));
									focusView = inputEmail;
									cancel = true;
								}
								if(error.contains("username"))
								{
									inputUsername.setError(getString(R.string.error_invalid_username));
									focusView = inputUsername;
									cancel = true;
								}
								
								if (cancel) {
									// There was an error; don't attempt login and focus the first
									// form field with an error.
									
									focusView.requestFocus();
								} 
							}
						}
					});  
	       
			}
			
			
		

	private boolean isEmailValid(String email) {
		// TODO: Replace this with email logic
		return email.contains("@");
	}
	
	private boolean isPasswordValid(String password) {
		// TODO: Replace this with password logic
		return password.length() > 4;
	}
	
	
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
