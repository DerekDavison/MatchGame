package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener
{
	private Button btnSignUp, btnLogIn;
	private ArrayList<NameValuePair> newUserNameValuePairs, userByEmailNameValuePairs;
	private DBHelper dbHelper;
	private ArrayList<String> usersByEmail;

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
	            
	            btnEnterLogin.setOnClickListener(new OnClickListener() 
	            {
		            public void onClick(View v) 
		            {
		            	// TODO:
		            	//
		            	// try and get user by email
		            	//		if not possible, login failed
		            	// if email exists, check if password retrieved by that email matches the password given in dialog
		            	
//		            	dbHelper = new DBHelper();
//			    		
//			    		userByEmailNameValuePairs = new ArrayList<NameValuePair>(); 
//			    		userByEmailNameValuePairs.add(new BasicNameValuePair("email", "bob"));
//			    		
//			    		if (dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, userByEmailNameValuePairs, "email").size() > 0)
//			    		{
//			    			
//			    		}
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
    	if (edtName.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter name.", Toast.LENGTH_SHORT).show();
		}
		else if (edtEmail.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_SHORT).show();
		}
		else if (edtPasswordFirst.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
		}
		else if (edtPasswordSecond.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Re-enter password.", Toast.LENGTH_SHORT).show();
		}
		else if (!edtPasswordFirst.getText().toString().equals(edtPasswordSecond.getText().toString()))
		{
			Toast.makeText(getApplicationContext(), "Passwords don't match. Try again.", Toast.LENGTH_SHORT).show();
			edtPasswordFirst.setText("");
			edtPasswordSecond.setText("");
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
			
			dbHelper = new DBHelper();
			dbHelper.insertUser(newUserNameValuePairs, StaticData.INSERT_NEW_USER_PHP_FILE);
			
			edtName.setText(""); 
			edtEmail.setText("");
			edtPasswordFirst.setText("");
			edtPasswordSecond.setText("");
			
			finish();
		}
    }
    
    private void startNewIntent()
    {
    	Intent gamePlayIntent = new Intent(Login.this, GamePlay.class); 
		startActivity(gamePlayIntent);
    }
}
