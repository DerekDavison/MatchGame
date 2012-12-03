package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener
{
	private Button btnSignUp, btnLogIn;
	private ArrayList<NameValuePair> newUserNameValuePairs, userByEmailNameValuePairs, updateUserStateByEmailNameValuePairs, 
		updateUserSignInStateByEmail;
	private DBHelper dbHelper;
	private Thread progresBartimerThread, authenticationThread;
	private Boolean progressBarIsComplete = false, addSignUpButtonsBack = false;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        
        btnSignUp.setOnClickListener(this); 
        btnLogIn.setOnClickListener(this);
    }
     
    public void onClick(View v)
	{
		switch(v.getId()) 
    	{  
	    	case R.id.btnSignUp:   
	    		
	    		//set up dialog 
	            final Dialog signUpDialog = new Dialog(Login.this);
	            signUpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            
	            signUpDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	            signUpDialog.setContentView(R.layout.sign_up_dialog);
	            signUpDialog.setCancelable(true); 

	            final EditText edtNameSignUp = (EditText) signUpDialog.findViewById(R.id.edtName); 
	            final EditText edtEmailSignUp = (EditText) signUpDialog.findViewById(R.id.edtEmail);  
	            final EditText edtPasswordFirstSignUp = (EditText) signUpDialog.findViewById(R.id.edtPasswordFirst);  
	            final EditText edtPasswordSecondSignUp = (EditText) signUpDialog.findViewById(R.id.edtPasswordSecond);  
	            
            	final ProgressBar signUpProgressBar = (ProgressBar) signUpDialog.findViewById(R.id.pgbSignUpStatus);
            	if (signUpProgressBar != null)
            	{
            		signUpProgressBar.setProgress(0);
		            signUpProgressBar.setMax(100);
		            signUpProgressBar.setVisibility(v.GONE);
            	}

	            final Button btnEnterSignUp = (Button) signUpDialog.findViewById(R.id.btnEnter);
	            final Button btnCancelSignUp = (Button) signUpDialog.findViewById(R.id.btnCancel);
	            
	            btnEnterSignUp.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(final View v) 
		            {
		            		if (edtNameSignUp.getText().toString().matches(""))
			        		{
			        			Toast.makeText(getApplicationContext(), "Enter name.", Toast.LENGTH_LONG).show();
			        		}
			        		else if (edtEmailSignUp.getText().toString().matches(""))
			        		{
			        			Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_LONG).show(); 
			        		}
			        		else if (edtPasswordFirstSignUp.getText().toString().matches(""))
			        		{
			        			Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_LONG).show();
			        		}
			        		else if (edtPasswordSecondSignUp.getText().toString().matches(""))
			        		{ 
			        			Toast.makeText(getApplicationContext(), "Re-enter password.", Toast.LENGTH_LONG).show();
			        		}
			        		else
			        		{ 
			        			progresBartimerThread = new Thread()
				            	{
			        				int progressBarStatus = 0;
			        				Boolean breakLoop = false;
				            	    public void run()
				            	    { 
				            	    	if (signUpProgressBar == null)
				        				{
				        					System.out.println("signUpProgressBar is null");
				        				}
				            	    	
			            	            while(progressBarStatus < 100)
			            	            {
			            	                Login.this.runOnUiThread(new Runnable()
			            	                {
			            	                    public void run()
			            	                    {
		            	                    		progressBarStatus += 15; 
		            	                    		btnEnterSignUp.setVisibility(v.GONE);
		            	                    		btnCancelSignUp.setVisibility(v.GONE);
		            	                    		signUpProgressBar.setVisibility(1);
		            	                    		signUpProgressBar.setProgress(progressBarStatus);

			            	                    	if (progressBarIsComplete)
			            	                    	{ 
			            	                    		signUpProgressBar.setVisibility(v.GONE);
		            			            			btnEnterSignUp.setVisibility(v.VISIBLE);
		            	            	    			btnCancelSignUp.setVisibility(v.VISIBLE);

			            	                    		progressBarIsComplete = false;
			            	                    		breakLoop = true;
			            	                    	}
			            	                    }
			            	                });
			            	                 
			            	                try 
			            	                {
												Thread.sleep(900);
											} 
			            	                catch (InterruptedException e) 
			            	                {
												e.printStackTrace();
											}
			            	                
			            	                if (breakLoop)
			            	                {
			            	                	break;
			            	                }
			            	            }
				            	    }
				            	};
				            	progresBartimerThread.start();
				            	progresBartimerThread.stop(); 
				            	
				            	authenticationThread = new Thread()
				            	{
				            	    public void run() 
				            	    {
				            	    	if (!insertUser(edtNameSignUp, edtEmailSignUp, edtPasswordFirstSignUp, 
				            	    			edtPasswordSecondSignUp, signUpProgressBar))
				            	    	{
				            	    		addSignUpButtonsBack = true;
				            	    	}
				            	    }
				            	};
				            	authenticationThread.start();
				            	authenticationThread.stop();
			        		}
		            }
	            });
	            
	            btnCancelSignUp.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	signUpDialog.dismiss();
		            }
	            });
	              
	            signUpDialog.show();
	    		
	    	break; 
	    	
	    	case R.id.btnLogIn: 
	    		
	    		final Dialog loginDialog = new Dialog(Login.this);
	    		loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            
	    		loginDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	    		loginDialog.setContentView(R.layout.log_in_dialog);
	    		loginDialog.setCancelable(true);

	            final EditText edtLoginEmail = (EditText) loginDialog.findViewById(R.id.edtLoginEmail); 
	            final EditText edtLoginPassword = (EditText) loginDialog.findViewById(R.id.edtLoginPassword);  

	            Button btnEnterLogin = (Button) loginDialog.findViewById(R.id.btnEnterLogin);
	            Button btnCancelLogin = (Button) loginDialog.findViewById(R.id.btnCancelLogin);
	            
	            final ProgressBar loginProgressBar = (ProgressBar) loginDialog.findViewById(R.id.pgbLoginStatus); 
	            if (loginProgressBar != null)
	            {
	            	loginProgressBar.setProgress(0);
		            loginProgressBar.setMax(100);
		            loginProgressBar.setVisibility(v.GONE);
	            }

	            btnEnterLogin.setOnClickListener(new OnClickListener() 
	            {	
		            public void onClick(final View v) 
		            {	
		            	// check if edit text boxes are empty
	            		if (edtLoginEmail.getText().toString().matches(""))
		        		{
		        			Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_LONG).show();
		        		}
		        		else if (edtLoginPassword.getText().toString().matches(""))
		        		{
		        			Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_LONG).show();
		        		}
		        		else
		        		{
		        			// a separate thread for the progress bar
		        			progresBartimerThread = new Thread()
			            	{
		        				int progressBarStatus = 0;
		        				Boolean breakLoop = false;
			            	    public void run()
			            	    {
			            	    	// while progress bar is not complete, run progress bar thread on UI thread 
		            	            while(progressBarStatus < 100)
		            	            {
		            	                Login.this.runOnUiThread(new Runnable()
		            	                {
		            	                    public void run()
		            	                    {
	            	                    		progressBarStatus += 15; 
		            	                        loginProgressBar.setVisibility(1);
		            	                        loginProgressBar.setProgress(progressBarStatus);

		            	                    	// to make the progress bar disappear after error
		            	                    	if (progressBarIsComplete)
		            	                    	{
		            	                    		loginProgressBar.setVisibility(v.GONE);
		            	                    		progressBarIsComplete = false;
		            	                    		breakLoop = true;
		            	                    	}
		            	                    }
		            	                });
		            	                 
		            	                // to control the progress bar timing by pausing the progresBartimerThread thread
		            	                try 
		            	                {
											Thread.sleep(900);
										} 
		            	                catch (InterruptedException e) 
		            	                {
											e.printStackTrace();
										}
		            	                
		            	                // to stop looping and progress bar if authentication happens quicker than full progess is set
		            	                if (breakLoop)
		            	                {
		            	                	break;
		            	                }
		            	            }
			            	    }
			            	};
			            	progresBartimerThread.start();
			            	progresBartimerThread.stop(); 
			            	
			            	// must authenticate user on different thread so the progress bar and 
			            	// authentication thread can  run at the same time, in parallel 
			            	authenticationThread = new Thread()
			            	{
			            	    public void run() 
			            	    {
			            	    	authenticateUser(edtLoginEmail, edtLoginPassword, loginProgressBar);
			            	    }
			            	};
			            	authenticationThread.start();
			            	// important to stop thread before moving on
			            	authenticationThread.stop();
		        		}
		            } 
	            });
	            
	            btnCancelLogin.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	loginDialog.dismiss();
		            }
	            });
	              
	            loginDialog.show();

	    	break;
 
	    	default:
	    	throw new RuntimeException("Unknow button ID"); 
    	}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
    
    private Boolean insertUser(final EditText edtName, final EditText edtEmail, final EditText edtPasswordFirst, 
    		final EditText edtPasswordSecond, final ProgressBar signUpProgressBar)
    {
    	Boolean isSuccess = false;
    	
    	dbHelper = new DBHelper();
    	
		userByEmailNameValuePairs = new ArrayList<NameValuePair>(); 
		userByEmailNameValuePairs.add(new BasicNameValuePair("email", edtEmail.getText().toString()));
    	
		if (!edtPasswordFirst.getText().toString().equals(edtPasswordSecond.getText().toString()))
		{
			runOnUiThread(new Runnable() 
			{
			    public void run() 
			    {
			    	signUpProgressBar.setProgress(100);
			    	progressBarIsComplete = true;
			    	Toast.makeText(getApplicationContext(), "Passwords don't match. Try again.", Toast.LENGTH_LONG).show();
					edtPasswordFirst.setText("");
					edtPasswordSecond.setText("");
			    }
			});
		}
		else // if the email is already in the database
			if (dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "email").size() > 0)
		{ 
				runOnUiThread(new Runnable() 
				{
				    public void run() 
				    {
				    	signUpProgressBar.setProgress(100);
				    	progressBarIsComplete = true;
				    	Toast.makeText(getApplicationContext(), "Email already used. Try again.", Toast.LENGTH_LONG).show();
						edtEmail.setText("");
				    }
				});
		}
		else
		{
			isSuccess = true;
			newUserNameValuePairs = new ArrayList<NameValuePair>(); 
			
			newUserNameValuePairs.add(new BasicNameValuePair("id", "0"));
			newUserNameValuePairs.add(new BasicNameValuePair("name", edtName.getText().toString()));
			newUserNameValuePairs.add(new BasicNameValuePair("email", edtEmail.getText().toString()));
			newUserNameValuePairs.add(new BasicNameValuePair("password", edtPasswordFirst.getText().toString()));
			// new user, default score to 0
			newUserNameValuePairs.add(new BasicNameValuePair("score", "0"));
			// new user, default avatar ID to 1 until user changes from default 
			newUserNameValuePairs.add(new BasicNameValuePair("avitar_picture_id", "1"));
			// default first player to sign in as player one
			newUserNameValuePairs.add(new BasicNameValuePair("player_state", "first"));
			// defualt new player to signed in
			newUserNameValuePairs.add(new BasicNameValuePair("signed_in", "1"));
			
			dbHelper.modifyData(newUserNameValuePairs, StaticData.INSERT_NEW_USER_PHP_FILE);
			
			savePlayerOneEmail(edtEmail);
			
			runOnUiThread(new Runnable() 
			{
			    public void run() 
			    {
			    	signUpProgressBar.setProgress(100);
			    	progressBarIsComplete = true;
			    	edtName.setText(""); 
					edtEmail.setText("");
					edtPasswordFirst.setText("");
					edtPasswordSecond.setText("");
			    }
			});

			startNewIntent();
		}
		
		return isSuccess;
    }
    
    private void authenticateUser(final EditText edtLoginEmail, final EditText edtLoginPassword, final ProgressBar loginProgressBar)
    {
    	dbHelper = new DBHelper();
		
		userByEmailNameValuePairs = new ArrayList<NameValuePair>(); 
		updateUserStateByEmailNameValuePairs = new ArrayList<NameValuePair>();
		updateUserSignInStateByEmail = new ArrayList<NameValuePair>();
		userByEmailNameValuePairs.add(new BasicNameValuePair("email", edtLoginEmail.getText().toString()));
		
		// if user exists in database via email
		if (dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "email").size() > 0)
		{
			// set up to get email by password
			userByEmailNameValuePairs.add(new BasicNameValuePair("password", edtLoginEmail.getText().toString()));
			
			// set the value we're going to send the database for user status
			updateUserStateByEmailNameValuePairs.add(new BasicNameValuePair("email", edtLoginEmail.getText().toString()));
			updateUserStateByEmailNameValuePairs.add(new BasicNameValuePair("player_state", StaticData.PLAYER_ONE_STATE));
			
			updateUserSignInStateByEmail.add(new BasicNameValuePair("email", edtLoginEmail.getText().toString()));
			updateUserSignInStateByEmail.add(new BasicNameValuePair("signed_in", "1"));
			
			// get password of user of record where email = email, and 
			for (String password : dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "password"))
			{
				// go through array list and test password
				if (edtLoginPassword.getText().toString().equals(password))
				{
					// if the password passes, update player_state in db (set as player 1 or 2) and start next intent
					dbHelper.modifyData(updateUserStateByEmailNameValuePairs, StaticData.UPDATE_PLAYER_STATE_BY_EMAIL);
					
					// check if the user is already signed in, and if so, don't allow again
					for(String signedInStated : dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "signed_in"))
					{
						if (Integer.parseInt(signedInStated) == 1)
						{
							// need to run toast and other functions on same thread as UI or you get a thread looper exception
							runOnUiThread(new Runnable() 
							{
							    public void run() 
							    {
							    	loginProgressBar.setProgress(100);
							    	progressBarIsComplete = true;
							    	Toast.makeText(getApplicationContext(), "Already logged in. Try again.", Toast.LENGTH_LONG).show();
									edtLoginEmail.setText("");
									edtLoginPassword.setText("");
							    }
							});
							
							break;
						}
						else if (Integer.parseInt(signedInStated) == 0)
						{
							// update sign in status
							dbHelper.modifyData(updateUserSignInStateByEmail, StaticData.UPDATE_PLAYER_SIGN_IN_STATUS_BY_EMAIL);
							
							savePlayerOneEmail(edtLoginEmail);
							startNewIntent();
							break;
						}
					}
				}
				else 
				{
					runOnUiThread(new Runnable() 
					{
					    public void run() 
					    {
					    	loginProgressBar.setProgress(100);
					    	progressBarIsComplete = true;
					    	Toast.makeText(getApplicationContext(), "Password incorrect. Try again.", Toast.LENGTH_LONG).show();
							edtLoginPassword.setText("");
					    }
					});
					
					break;
				}
			}
		}
		else // cannot get user by email (the email is not in the database)
		{
			runOnUiThread(new Runnable() 
			{
			    public void run() 
			    {
			    	loginProgressBar.setProgress(100);
			    	progressBarIsComplete = true;
			    	Toast.makeText(getApplicationContext(), "Login failed. We don't have record of this email.", Toast.LENGTH_LONG).show();
					edtLoginEmail.setText("");
					edtLoginPassword.setText("");
			    }
			});
		}
    }
    
    
    private void startNewIntent()
    {
    	Intent continueIntent = new Intent(Login.this, GameSetUp.class); 
		startActivity(continueIntent);
		finish();
    }
    
    private void savePlayerOneEmail(EditText edtEmail)
    {
    	SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF, MODE_PRIVATE);
    	SharedPreferences.Editor editor = shared.edit();
    	editor.putString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, edtEmail.getText().toString());
    	editor.commit();
    }
}
