package com.example.matchgame;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

public class AvatarSelection extends Activity implements OnClickListener
{
	private Button btnPlayerOne, btnPlayerTwo, btnPlayerThree, btnPlayerFour, btnPlayerFive, btnPlayerSix, btnPlayerSeven, btnPlayerEight, 
		btnPlayerNine, btnPlayerTen, btnCheckOne, btnCheckTwo, btnCheckThree, btnCheckFour, btnCheckFive, btnCheckSix, btnCheckSeven, 
		btnCheckEight, btnCheckNine, btnCheckTen;
	private TextView txtAvatarSelection;
	private TranslateAnimation playerOneTextAnimation, playerTwoTextAnimation;
	private CountDownTimer playerOneAnimationTimer, playerTwoAnimationTimer, delayNextActivityTimer;
	private Boolean playerOneTurn = true;
	private ArrayList<NameValuePair> updatePlayerOneAvitarPictureIdByEmailNameValuePairs, updatePlayerTwoAvitarPictureIdByEmailNameValuePairs;  
	private DBHelper dbHelper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_selection);
        
        btnPlayerOne = (Button)findViewById(R.id.btnPlayerOne);
        btnPlayerTwo = (Button)findViewById(R.id.btnPlayerTwo);
        btnPlayerThree = (Button)findViewById(R.id.btnPlayerThree);
        btnPlayerFour = (Button)findViewById(R.id.btnPlayerFour);
        btnPlayerFive = (Button)findViewById(R.id.btnPlayerFive);
        btnPlayerSix = (Button)findViewById(R.id.btnPlayerSix);
        btnPlayerSeven = (Button)findViewById(R.id.btnPlayerSeven);
        btnPlayerEight = (Button)findViewById(R.id.btnPlayerEight);
        btnPlayerNine = (Button)findViewById(R.id.btnPlayerNine);
        btnPlayerTen = (Button)findViewById(R.id.btnPlayerTen); 
        
        btnCheckOne = (Button)findViewById(R.id.btnCheckOne);
        btnCheckTwo = (Button)findViewById(R.id.btnCheckTwo);
        btnCheckThree = (Button)findViewById(R.id.btnCheckThree);
        btnCheckFour = (Button)findViewById(R.id.btnCheckFour);
        btnCheckFive = (Button)findViewById(R.id.btnCheckFive);
        btnCheckSix = (Button)findViewById(R.id.btnCheckSix);
        btnCheckSeven = (Button)findViewById(R.id.btnCheckSeven);
        btnCheckEight = (Button)findViewById(R.id.btnCheckEight);
        btnCheckNine = (Button)findViewById(R.id.btnCheckNine);
        btnCheckTen = (Button)findViewById(R.id.btnCheckTen); 
        
        txtAvatarSelection = (TextView)findViewById(R.id.txtAvatarSelection); 
        
        btnPlayerOne.setOnClickListener(this);
        btnPlayerTwo.setOnClickListener(this);
        btnPlayerThree.setOnClickListener(this);
        btnPlayerFour.setOnClickListener(this);
        btnPlayerFive.setOnClickListener(this);
        btnPlayerSix.setOnClickListener(this);
        btnPlayerSeven.setOnClickListener(this);
        btnPlayerEight.setOnClickListener(this);
        btnPlayerNine.setOnClickListener(this);
        btnPlayerTen.setOnClickListener(this);
        
     // make the check buttons invisible
        btnCheckOne.setVisibility(btnCheckOne.GONE);
        btnCheckTwo.setVisibility(btnCheckTwo.GONE);
        btnCheckThree.setVisibility(btnCheckThree.GONE);
        btnCheckFour.setVisibility(btnCheckFour.GONE);
        btnCheckFive.setVisibility(btnCheckFive.GONE);
        btnCheckSix.setVisibility(btnCheckSix.GONE);
        btnCheckSeven.setVisibility(btnCheckSeven.GONE);
        btnCheckEight.setVisibility(btnCheckEight.GONE);
        btnCheckNine.setVisibility(btnCheckNine.GONE);
        btnCheckTen.setVisibility(btnCheckTen.GONE);
        
        playerOneTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
        playerOneTextAnimation.setDuration(1000);
        playerOneTextAnimation.setFillAfter(true);
        playerOneTextAnimation.setRepeatCount(-1);
        playerOneTextAnimation.setRepeatMode(Animation.REVERSE);
	   	txtAvatarSelection.setAnimation(playerOneTextAnimation);

	   	playerOneAnimationTimer = new CountDownTimer(3000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) {}

	   		public void onFinish() 
	   		{
	   			playerOneTextAnimation = new TranslateAnimation(0, 0, 0, 0);
	   			txtAvatarSelection.setAnimation(playerOneTextAnimation);
	   			playerOneAnimationTimer.cancel();
	   		}
	   	};
	   	playerOneAnimationTimer.start();
    }
	
	public void onClick(View v)
	{ 
		switch(v.getId()) 
    	{   
	    	case R.id.btnPlayerOne: 
	    		
	    		try
	    		{
	    			// if player one making selection
		    		if (playerOneTurn)
		    		{
		    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
			    		
			    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
			    		playerTwoTextAnimation.setDuration(1000);
			    		playerTwoTextAnimation.setFillAfter(true);
			    		playerTwoTextAnimation.setRepeatCount(-1);
			    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
			  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

			  	   	    // for how long the animation will run
			  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
			  	   	    {
			  	   	    	public void onTick(long millisUntilFinished) {}

			  	   	    	public void onFinish() 
			  	   	    	{
			  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
			  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
			  	   	    		playerTwoAnimationTimer.cancel();
			  	   	    	}
			  	   	    };
			  	   	    playerTwoAnimationTimer.start();
			  	   	    playerOneTurn = false;
			  	   	    
			  	   	    btnPlayerOne.setEnabled(false);
		    		
			    		btnCheckOne.setVisibility(btnCheckOne.VISIBLE);
			            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
			            btnCheckThree.setVisibility(btnCheckThree.GONE);
			            btnCheckFour.setVisibility(btnCheckFour.GONE);
			            btnCheckFive.setVisibility(btnCheckFive.GONE);
			            btnCheckSix.setVisibility(btnCheckSix.GONE);
			            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
			            btnCheckEight.setVisibility(btnCheckEight.GONE);
			            btnCheckNine.setVisibility(btnCheckNine.GONE);
			            btnCheckTen.setVisibility(btnCheckTen.GONE);
			            
			            setPlayerOneAvatarSelection("1");
		    		}
		    		else // player two choice
		    		{  
		    			setPlayerTwoAvatarSelection("1");
		    			
		    			btnCheckOne.setVisibility(btnCheckOne.VISIBLE);
		    			
			            delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
			            {
			            	Boolean firstTickNotDone = true;
			            	
			                public void onTick(long millisUntilFinished) 
			                { 
			                	if (firstTickNotDone)
			                	{
			                		showLoadingGameDialog();
			                		firstTickNotDone = false;
			                	}
			                }

			                public void onFinish() 
			                {
			                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
				    			startActivity(nextIntent);
				    			delayNextActivityTimer.cancel();
				    			finish();
			                }
			            };
			            delayNextActivityTimer.start();
		    		}
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println(e.toString());
	    		}
	    		
	    		break; 
	    	
	    	case R.id.btnPlayerTwo:
	    		
	    		// if player one making selection
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerTwo.setEnabled(false);
		    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.VISIBLE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("2");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("2");
	    			
	    			btnCheckTwo.setVisibility(btnCheckTwo.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}

		    	break;
		    	
	    	case R.id.btnPlayerThree:
	    		
	    		// if player one making selection
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerThree.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.VISIBLE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("3");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("3");
	    			
	    			btnCheckThree.setVisibility(btnCheckThree.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerFour:   
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerFour.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.VISIBLE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("4");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("4");
	    			
	    			btnCheckFour.setVisibility(btnCheckFour.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerFive:
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerFive.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.VISIBLE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("5");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("5");
	    			
	    			btnCheckFive.setVisibility(btnCheckFive.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                } 

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerSix:  
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerSix.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.VISIBLE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("6");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("6");
	    			
	    			btnCheckSix.setVisibility(btnCheckSix.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true; 
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerSeven: 
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerSeven.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.VISIBLE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("7");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("7");
	    			
	    			btnCheckSeven.setVisibility(btnCheckSeven.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerEight:  
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerEight.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.VISIBLE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("8");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("8");
	    			
	    			btnCheckEight.setVisibility(btnCheckEight.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerNine: 
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerNine.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.VISIBLE);
		            btnCheckTen.setVisibility(btnCheckTen.GONE);
		            
		            setPlayerOneAvatarSelection("9");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("9");
	    			
	    			btnCheckNine.setVisibility(btnCheckNine.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}
	    		
		    	break;
		    	
	    	case R.id.btnPlayerTen:  
	    		
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(60.0f, -40.0f, 0.0f, 0.0f);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3500, 1000) 
		  	   	    {
		  	   	    	public void onTick(long millisUntilFinished) {}

		  	   	    	public void onFinish() 
		  	   	    	{
		  	   	    		playerTwoTextAnimation = new TranslateAnimation(0, 0, 0, 0);
		  	   	    		txtAvatarSelection.setAnimation(playerTwoTextAnimation);
		  	   	    	playerTwoAnimationTimer.cancel();
		  	   	    	}
		  	   	    };
		  	   	    playerTwoAnimationTimer.start();
		  	   	    playerOneTurn = false;
		  	   	    
		  	   	    btnPlayerTen.setEnabled(false);
	    		
		    		btnCheckOne.setVisibility(btnCheckOne.GONE);
		            btnCheckTwo.setVisibility(btnCheckTwo.GONE);
		            btnCheckThree.setVisibility(btnCheckThree.GONE);
		            btnCheckFour.setVisibility(btnCheckFour.GONE);
		            btnCheckFive.setVisibility(btnCheckFive.GONE);
		            btnCheckSix.setVisibility(btnCheckSix.GONE);
		            btnCheckSeven.setVisibility(btnCheckSeven.GONE);
		            btnCheckEight.setVisibility(btnCheckEight.GONE);
		            btnCheckNine.setVisibility(btnCheckNine.GONE);
		            btnCheckTen.setVisibility(btnCheckTen.VISIBLE);
		            
		            setPlayerOneAvatarSelection("10");
	    		}
	    		else // player two choice
	    		{
	    			setPlayerTwoAvatarSelection("10");
	    			
	    			btnCheckTen.setVisibility(btnCheckTen.VISIBLE);
	    			
	    			delayNextActivityTimer = new CountDownTimer(5000 , 1000) 
		            {
		            	Boolean firstTickNotDone = true;
		            	
		                public void onTick(long millisUntilFinished) 
		                { 
		                	if (firstTickNotDone)
		                	{
		                		showLoadingGameDialog();
		                		firstTickNotDone = false;
		                	}
		                }

		                public void onFinish() 
		                {
		                	Intent nextIntent = new Intent(AvatarSelection.this, GamePlay.class); 
			    			startActivity(nextIntent);
			    			delayNextActivityTimer.cancel();
			    			finish();
		                }
		            };
		            delayNextActivityTimer.start();
	    		}

		    	break;
	    	
	    	default:
		    	throw new RuntimeException("Unknow button ID"); 
	    }
	}
	
	private void showLoadingGameDialog()
	{
		ProgressDialog dialog = ProgressDialog.show(AvatarSelection.this, "","Please wait, loading game ...", true);
	}
	
	private void setPlayerOneAvatarSelection(String playerChosen)
	{
		dbHelper = new DBHelper();
		SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF, MODE_PRIVATE);
		updatePlayerOneAvitarPictureIdByEmailNameValuePairs = new ArrayList<NameValuePair>();
		
		updatePlayerOneAvitarPictureIdByEmailNameValuePairs.add(new BasicNameValuePair("email", shared.getString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, "")));
		updatePlayerOneAvitarPictureIdByEmailNameValuePairs.add(new BasicNameValuePair("avitar_picture_id", playerChosen));
		dbHelper.modifyData(updatePlayerOneAvitarPictureIdByEmailNameValuePairs, StaticData.UPDATE_PLAYER_AVATAR_ID_BY_EMAIL);
	}
	
	private void setPlayerTwoAvatarSelection(String playerChosen)
	{
		dbHelper = new DBHelper();
		SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF, MODE_PRIVATE);
		updatePlayerTwoAvitarPictureIdByEmailNameValuePairs = new ArrayList<NameValuePair>();
		
		updatePlayerTwoAvitarPictureIdByEmailNameValuePairs.add(new BasicNameValuePair("email", shared.getString(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF_KEY, "")));
		updatePlayerTwoAvitarPictureIdByEmailNameValuePairs.add(new BasicNameValuePair("avitar_picture_id", playerChosen));
		dbHelper.modifyData(updatePlayerTwoAvitarPictureIdByEmailNameValuePairs, StaticData.UPDATE_PLAYER_AVATAR_ID_BY_EMAIL);
	}
}
