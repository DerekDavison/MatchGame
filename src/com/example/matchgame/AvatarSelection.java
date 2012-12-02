package com.example.matchgame;

import android.app.Activity;
import android.content.Intent;
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
        
        playerOneTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
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
	    		
	    		// if player one making selection
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{   
	    			btnCheckOne.setVisibility(btnCheckOne.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
	    	
	    	case R.id.btnPlayerTwo:
	    		
	    		// if player one making selection
	    		if (playerOneTurn)
	    		{
	    			txtAvatarSelection.setText("Player 2 - Choose an Avatar");
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckTwo.setVisibility(btnCheckTwo.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckThree.setVisibility(btnCheckThree.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckFour.setVisibility(btnCheckFour.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckFive.setVisibility(btnCheckFive.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckSix.setVisibility(btnCheckSix.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckSeven.setVisibility(btnCheckSeven.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckEight.setVisibility(btnCheckEight.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckNine.setVisibility(btnCheckNine.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
		    		
		    		playerTwoTextAnimation = new TranslateAnimation(-10, 20, 0, 0);
		    		playerTwoTextAnimation.setDuration(1000);
		    		playerTwoTextAnimation.setFillAfter(true);
		    		playerTwoTextAnimation.setRepeatCount(-1);
		    		playerTwoTextAnimation.setRepeatMode(Animation.REVERSE);
		  	   	    txtAvatarSelection.setAnimation(playerTwoTextAnimation);

		  	   	    // for how long the animation will run
		  	   	    playerTwoAnimationTimer = new CountDownTimer(3000, 1000) 
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
	    		}
	    		else // player two choice
	    		{
	    			btnCheckTen.setVisibility(btnCheckTen.VISIBLE);
	    			
		            delayNextActivityTimer = new CountDownTimer(2000 , 1000) 
		            {
		                public void onTick(long millisUntilFinished) { }

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
}
