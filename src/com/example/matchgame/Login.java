package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

	            Button btnEnterSignUp = (Button) signUpDialog.findViewById(R.id.btnEnter);
	            Button btnCancelSignUp = (Button) signUpDialog.findViewById(R.id.btnCancel);
	            
	            btnEnterSignUp.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	insertUser(edtNameSignUp, edtEmailSignUp, edtPasswordFirstSignUp, edtPasswordSecondSignUp);
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
	            loginProgressBar.setProgress(0);
	            loginProgressBar.setMax(100);

	            btnEnterLogin.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {	
		            	Thread timer = new Thread()
		            	{
		            		int progressBarStatus = 0;
		            	    public void run()
		            	    {
	            	            while(progressBarStatus < 5000)
	            	            {
	            	                Login.this.runOnUiThread(new Runnable()
	            	                {
	            	                    public void run()
	            	                    {
	            	                        progressBarStatus += 20;
	            	                        loginProgressBar.setProgress(progressBarStatus);
	            	                    }
	            	                });
	            	            }
	            	            
	            	            try {
									Thread.sleep(500);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block   
									e.printStackTrace();
								} 
		            	    }
		            	};
		            	timer.start();
		            	
		            	authenticateUser(edtLoginEmail, edtLoginPassword);
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
    
    private void insertUser(EditText edtName, EditText edtEmail, EditText edtPasswordFirst, EditText edtPasswordSecond)
    {
    	dbHelper = new DBHelper();
    	
		userByEmailNameValuePairs = new ArrayList<NameValuePair>(); 
		userByEmailNameValuePairs.add(new BasicNameValuePair("email", edtEmail.getText().toString()));
    	
    	if (edtName.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter name.", Toast.LENGTH_LONG).show();
		}
		else if (edtEmail.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_LONG).show();
		}
		else if (edtPasswordFirst.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_LONG).show();
		}
		else if (edtPasswordSecond.getText().toString().matches(""))
		{ 
			Toast.makeText(getApplicationContext(), "Re-enter password.", Toast.LENGTH_LONG).show();
		}
		else // if the two passwords don't match
			if (!edtPasswordFirst.getText().toString().equals(edtPasswordSecond.getText().toString()))
		{
			Toast.makeText(getApplicationContext(), "Passwords don't match. Try again.", Toast.LENGTH_LONG).show();
			edtPasswordFirst.setText("");
			edtPasswordSecond.setText("");
		}
		else // if the email is already in the database
			if (dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "email").size() > 0)
		{ 
				Toast.makeText(getApplicationContext(), "Email already used. Try again.", Toast.LENGTH_LONG).show();
				edtEmail.setText("");
		}
		else
		{
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
			 
			edtName.setText(""); 
			edtEmail.setText("");
			edtPasswordFirst.setText("");
			edtPasswordSecond.setText(""); 
			
			saveEmailToNextActivity(edtEmail);
			startNewIntent();
		}
    }
    
    private void authenticateUser(EditText edtLoginEmail, EditText edtLoginPassword)
    {
    	dbHelper = new DBHelper();
		
		userByEmailNameValuePairs = new ArrayList<NameValuePair>(); 
		updateUserStateByEmailNameValuePairs = new ArrayList<NameValuePair>();
		updateUserSignInStateByEmail = new ArrayList<NameValuePair>();
		userByEmailNameValuePairs.add(new BasicNameValuePair("email", edtLoginEmail.getText().toString()));
		
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
    							Toast.makeText(getApplicationContext(), "Already logged in. Try again.", Toast.LENGTH_LONG).show();
    							edtLoginEmail.setText("");
    							edtLoginPassword.setText("");
    							break;
    						}
    						else if (Integer.parseInt(signedInStated) == 0)
    						{
    							// update sign in status
    							dbHelper.modifyData(updateUserSignInStateByEmail, StaticData.UPDATE_PLAYER_SIGN_IN_STATUS_BY_EMAIL);
    							
    							saveEmailToNextActivity(edtLoginEmail);
    							startNewIntent();
    							break;
    						}
    					}
    				}
    				else 
    				{
    					Toast.makeText(getApplicationContext(), "Password incorrect. Try again.", Toast.LENGTH_LONG).show();
    					edtLoginPassword.setText("");
    					break;
    				}
    			}
    		}
    		else // cannot get user by email (the email is not in the database)
    		{
    			Toast.makeText(getApplicationContext(), "Login failed. We don't have record of this email.", Toast.LENGTH_LONG).show();
    			edtLoginEmail.setText("");
				edtLoginPassword.setText("");
    		}
		}
    }
    
    
    private void startNewIntent()
    {
    	Intent continueIntent = new Intent(Login.this, GameSetUp.class); 
		startActivity(continueIntent);
		finish();
    }
    
    private void saveEmailToNextActivity(EditText edtEmail)
    {
    	SharedPreferences shared = getSharedPreferences(StaticData.USER_EMAIL_SHARED_PREF, MODE_PRIVATE);
    	SharedPreferences.Editor editor = shared.edit();
    	editor.putString(StaticData.USER_EMAIL_SHARED_PREF_KEY, edtEmail.getText().toString());
    	editor.commit();
    }
}
