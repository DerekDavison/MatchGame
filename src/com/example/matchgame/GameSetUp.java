package com.example.matchgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GameSetUp extends Activity implements OnClickListener
{
	private Button btnPlayAlone, btnSignInSecondPlayer, btnSignUpSecondPlayer;
	
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
	    		// TODO: some logic for setting up single player
	    		
	    	break; 
	    	
	    	case R.id.btnSignInSecondPlayer: 
	    		
	    		// broken code
	    		// TODO: Fix below

//	    		final Dialog signInDialogSecondPlayer = new Dialog(GameSetUp.this);
//	    		signInDialogSecondPlayer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	            
//	    		signInDialogSecondPlayer.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
//	    		signInDialogSecondPlayer.setContentView(R.layout.sign_up_dialog);
//	    		signInDialogSecondPlayer.setCancelable(true);
//
//	            final EditText edtLoginEmailSecondPlayer = (EditText) signInDialogSecondPlayer.findViewById(R.id.edtLoginEmailSecondPlayer); 
//	            final EditText edtLoginPasswordSecondPlayer = (EditText) signInDialogSecondPlayer.findViewById(R.id.edtLoginPasswordSecondPlayer);   
//
//	            Button btnEnterLoginSecondPlayer = (Button) signInDialogSecondPlayer.findViewById(R.id.btnEnterLoginSecondPlayer);
//	            Button btnCancelLoginSecondPlayer = (Button) signInDialogSecondPlayer.findViewById(R.id.btnCancelLoginSecondPlayer);
//	            
//	            btnEnterLoginSecondPlayer.setOnClickListener(new OnClickListener() 
//	            {
//		            public void onClick(View v) 
//		            {
//		            	// insertUser(edtNameSignUp, edtEmailSignUp, edtPasswordFirstSignUp, edtPasswordSecondSignUp);
//		            }
//	            });
//	            
//	            btnCancelLoginSecondPlayer.setOnClickListener(new OnClickListener() 
//	            {
//		            public void onClick(View v) 
//		            {
//		            	signInDialogSecondPlayer.dismiss();
//		            }
//	            });
//	              
//	            signInDialogSecondPlayer.show();

	    	break;
	    	
	    	case R.id.btnSignUpSecondPlayer: 
	    		
	    		// broken code
	    		// TODO: Fix below
	    		
//	    		final Dialog signUpDialogSecondPlayer = new Dialog(GameSetUp.this);
//	    		signUpDialogSecondPlayer.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	            
//	    		signUpDialogSecondPlayer.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
//	    		signUpDialogSecondPlayer.setContentView(R.layout.sign_up_dialog);
//	    		signUpDialogSecondPlayer.setCancelable(true);
//
//	            final EditText edtNameSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtNameSecondPlayer); 
//	            final EditText edtEmailSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtEmailSecondPlayer);  
//	            final EditText edtPasswordFirstSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtPasswordFirstSecondPlayer);  
//	            final EditText edtPasswordSecondSecondPlayer = (EditText) signUpDialogSecondPlayer.findViewById(R.id.edtPasswordSecondSecondPlayer);  
//
//	            Button btnEnterSecondPlayer = (Button) signUpDialogSecondPlayer.findViewById(R.id.btnEnterSecondPlayer);
//	            Button btnCancelSecondPlayer = (Button) signUpDialogSecondPlayer.findViewById(R.id.btnCancelSecondPlayer);
//	            
//	            btnEnterSecondPlayer.setOnClickListener(new OnClickListener() 
//	            {
//		            public void onClick(View v) 
//		            {
//		            	// insertUser(edtNameSignUp, edtEmailSignUp, edtPasswordFirstSignUp, edtPasswordSecondSignUp);
//		            }
//	            });
//	            
//	            btnCancelSecondPlayer.setOnClickListener(new OnClickListener() 
//	            {
//		            public void onClick(View v) 
//		            {
//		            	signUpDialogSecondPlayer.dismiss();
//		            }
//	            });
//	              
//	            signUpDialogSecondPlayer.show();
	    		
		    	break;
 
	    	default:
	    	throw new RuntimeException("Unknow button ID"); 
    	}
	}
	
	private void startNewIntent()
    {
    	Intent gamePlayIntent = new Intent(GameSetUp.this, GamePlay.class); 
		startActivity(gamePlayIntent);
		finish();
    }
}
