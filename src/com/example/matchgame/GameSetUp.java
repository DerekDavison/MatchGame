package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GameSetUp extends Activity implements OnClickListener
{
	private Button btnPlayAlone, btnSignInSecondPlayer, btnSignUpSecondPlayer;
	private ArrayList<NameValuePair> newUserNameValuePairs, userByEmailNameValuePairs, updateUserStateByEmailNameValuePairs,
		updateUserSignInStateByEmail;
	private DBHelper dbHelper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_set_up);
        
        btnPlayAlone = (Button)findViewById(R.id.btnPlayAlone);
        btnSignInSecondPlayer = (Button)findViewById(R.id.btnSignInSecondPlayer);
        btnSignUpSecondPlayer = (Button)findViewById(R.id.btnSignUpSecondPlayer);
        
        btnPlayAlone.setOnClickListener(this);
        btnSignInSecondPlayer.setOnClickListener(this);
        btnSignUpSecondPlayer.setOnClickListener(this);
    }
	
	public void onClick(View v)
	{
		switch(v.getId()) 
    	{   
	    	case R.id.btnPlayAlone:   
    			
	    		startNewIntent();
	    		
	    		// TODO: issue with SharedPreferences giving null pointer exception
	    		// fix later and update player state (by email from SharedPreferences) to "single" for this event
	    		
//	    		SharedPreferences prefs = getPreferences(MODE_PRIVATE); 
//	    		String sharedPreferencesEmail = prefs.getString("user_email", null);
//	    		
//	    		updateUserStateByEmailNameValuePairs.add(new BasicNameValuePair("email", edtLoginEmail.getText().toString()));
//    			updateUserStateByEmailNameValuePairs.add(new BasicNameValuePair("player_state", StaticData.PLAYER_TWO_STATE));
	    		
	    		
	    	break; 
	    	
	    	case R.id.btnSignInSecondPlayer: 

    			final Dialog signInDialogSecondPlayer = new Dialog(GameSetUp.this);
	    		signInDialogSecondPlayer.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            
	    		signInDialogSecondPlayer.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	    		signInDialogSecondPlayer.setContentView(R.layout.log_in_dialog_second_player);
	    		signInDialogSecondPlayer.setCancelable(true);

	            final EditText edtLoginEmailSecondPlayer = (EditText) signInDialogSecondPlayer.findViewById(R.id.edtLoginEmailSecondPlayer); 
	            final EditText edtLoginPasswordSecondPlayer = (EditText) signInDialogSecondPlayer.findViewById(R.id.edtLoginPasswordSecondPlayer);   

	            Button btnEnterLoginSecondPlayer = (Button) signInDialogSecondPlayer.findViewById(R.id.btnEnterLoginSecondPlayer);
	            Button btnCancelLoginSecondPlayer = (Button) signInDialogSecondPlayer.findViewById(R.id.btnCancelLoginSecondPlayer);
	            
	            btnEnterLoginSecondPlayer.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	authenticateUser(edtLoginEmailSecondPlayer, edtLoginPasswordSecondPlayer);
		            }
	            });
	            
	            btnCancelLoginSecondPlayer.setOnClickListener(new OnClickListener() 
	            { 
		            public void onClick(View v) 
		            {
		            	signInDialogSecondPlayer.dismiss();
		            }
	            });
	              
	            signInDialogSecondPlayer.show();
	    		
	    	break;
	    	
	    	case R.id.btnSignUpSecondPlayer: 
	    		
	    		final Dialog signUpDialogSecondPlayer = new Dialog(GameSetUp.this);
	    		signUpDialogSecondPlayer.requestWindowFeature(Window.FEATURE_NO_TITLE);
	            
	    		signUpDialogSecondPlayer.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	    		signUpDialogSecondPlayer.setContentView(R.layout.sign_up_dialog_second_player);
	    		signUpDialogSecondPlayer.setCancelable(true);  

	            final EditText edtNameSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtNameSecondPlayer); 
	            final EditText edtEmailSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtEmailSecondPlayer);  
	            final EditText edtPasswordFirstSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtPasswordFirstSecondPlayer);  
	            final EditText edtPasswordSecondSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtPasswordSecondSecondPlayer);  

	            Button btnEnterSecondPlayer = (Button) signUpDialogSecondPlayer.findViewById(R.id.btnEnterSecondPlayer);
	            Button btnCancelSecondPlayer = (Button) signUpDialogSecondPlayer.findViewById(R.id.btnCancelSecondPlayer);
	            
	            btnEnterSecondPlayer.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	insertUser(edtNameSecondPlayer, edtEmailSecondPlayer, edtPasswordFirstSecondPlayer, edtPasswordSecondSecondPlayer);
		            }
	            });
	            
	            btnCancelSecondPlayer.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	signUpDialogSecondPlayer.dismiss();
		            }
	            });
	              
	            signUpDialogSecondPlayer.show();
	    		
		    	break;
 
	    	default:
	    	throw new RuntimeException("Unknow button ID"); 
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
    			updateUserStateByEmailNameValuePairs.add(new BasicNameValuePair("player_state", StaticData.PLAYER_TWO_STATE));
    			
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
			// default second player to sign in as player two
			newUserNameValuePairs.add(new BasicNameValuePair("player_state", "second"));
			// defualt new player to signed in
			newUserNameValuePairs.add(new BasicNameValuePair("signed_in", "1"));
			
			dbHelper.modifyData(newUserNameValuePairs, StaticData.INSERT_NEW_USER_PHP_FILE);
			
			edtName.setText(""); 
			edtEmail.setText("");
			edtPasswordFirst.setText("");
			edtPasswordSecond.setText(""); 
			
			startNewIntent();
		}
    }
	
	private void startNewIntent()
    {
    	Intent nextIntent = new Intent(GameSetUp.this, GamePlay.class); 
		startActivity(nextIntent);
		finish();
    }
}
