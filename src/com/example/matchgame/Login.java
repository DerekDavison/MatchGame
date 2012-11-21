package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener
{
	private Button btnSignUp, btnLogIn;
	private EditText edtLoginSignUpName, edtLoginSignUpEmail, edtLoginSignUpPasswordFirst, edtLoginSignUpPasswordSecond; 
	ArrayList<NameValuePair> newUserNameValuePairs;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnLogIn = (Button)findViewById(R.id.btnLogIn);
        edtLoginSignUpName = (EditText)findViewById(R.id.edtLoginSignUpName);
        edtLoginSignUpEmail = (EditText)findViewById(R.id.edtLoginSignUpEmail);
        edtLoginSignUpPasswordFirst = (EditText)findViewById(R.id.edtLoginSignUpPasswordFirst);
        edtLoginSignUpPasswordSecond = (EditText)findViewById(R.id.edtLoginSignUpPasswordSecond);
        
        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);
    }
    
    public void onClick(View v)
	{
		switch(v.getId()) 
    	{  
	    	case R.id.btnSignUp:   
	    		
	    		insertUser();
	    		
	    	break; 
	    	
	    	case R.id.btnLogIn: 
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
    
    private void insertUser()
    {
    	if (edtLoginSignUpName.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter name.", Toast.LENGTH_SHORT).show();
		}
		else if (edtLoginSignUpEmail.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter email.", Toast.LENGTH_SHORT).show();
		}
		else if (edtLoginSignUpPasswordFirst.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
		}
		else if (edtLoginSignUpPasswordSecond.getText().toString().matches(""))
		{
			Toast.makeText(getApplicationContext(), "Re-enter password.", Toast.LENGTH_SHORT).show();
		}
		else if (!edtLoginSignUpPasswordFirst.getText().toString().equals(edtLoginSignUpPasswordSecond.getText().toString()))
		{
			Toast.makeText(getApplicationContext(), "Passwords don't match. Try again.", Toast.LENGTH_SHORT).show();
			edtLoginSignUpPasswordFirst.setText("");
			edtLoginSignUpPasswordSecond.setText("");
		}
		else
		{
			newUserNameValuePairs = new ArrayList<NameValuePair>();
			
			newUserNameValuePairs.add(new BasicNameValuePair("userId", "0"));
			newUserNameValuePairs.add(new BasicNameValuePair("name", edtLoginSignUpName.getText().toString()));
			newUserNameValuePairs.add(new BasicNameValuePair("email", edtLoginSignUpEmail.getText().toString()));
			newUserNameValuePairs.add(new BasicNameValuePair("password", edtLoginSignUpPasswordFirst.getText().toString()));
			
			DBHelper dbHelper = new DBHelper();
			dbHelper.insertUser(newUserNameValuePairs, StaticData.TEST_INSERT_PHP_FILE);
			
			edtLoginSignUpName.setText("");
			edtLoginSignUpEmail.setText("");
			edtLoginSignUpPasswordFirst.setText("");
			edtLoginSignUpPasswordSecond.setText("");
		}
    }
    
    private void startNewIntent()
    {
    	Intent gamePlayIntent = new Intent(Login.this, GamePlay.class); 
		startActivity(gamePlayIntent);
    }
}
