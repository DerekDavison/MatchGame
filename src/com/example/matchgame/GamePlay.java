package com.example.matchgame;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class GamePlay extends Activity implements OnClickListener
{
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> playerOneAvatarIdByEmail, playerTwoAvatarIdByEmail, singlePlayerNameValuePairs;
	private ImageView imgPlayerOne, imgPlayerTwo;
	private Boolean singlePlayerMode = false, firstTimeForRoundOneAnnouncementTimer = true, firstTimeForPlayerTurnDialogTimer = true, 
			firstTimeForloadingQuestionDialogTimer = true, userHasSubmittedAnswer = false;
	private Button btnRoundTwo;
	private CountDownTimer roundOneAnnouncementTimer, delayToShowRoundOneAnnouncementTimer, loadingQuestionDialogTimer;
	private ProgressDialog loadingQuestionDialog;
	private int questionIdCouter = 0;
	private ArrayList<NameValuePair> questionByIdAndRoundNameValuePairs, playerOneNameByEmailNameValuePairs, 
		playerTwoNameByEmailNameValuePairs;
	private TextView txtRoundOneQuestion, txtGamePlayPlayerOne, txtGamePlayPlayerTwo;
	private String playeOneName, playeTwoName;
	private Timer checkIfPlayerSubmittedAnswerTimer; 

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        btnRoundTwo = (Button)findViewById(R.id.btnRoundtwo);  
        btnRoundTwo.setOnClickListener(this);
        
        determineGameMode();
        if(singlePlayerMode)
        {
        	setPlayerOneAvatar();
        	
        	// need to find a better icon
//        	Drawable avatarOne = getResources().getDrawable(R.drawable.computer_icon);
//        	imgPlayerTwo = (ImageView)findViewById(R.id.imgPlayerTwo);
//        	imgPlayerTwo.setImageDrawable(avatarOne);
        }
        else
        {
        	setPlayerOneAvatar();
            setPlayerTwoAvatar();
        }
        
        // wait four seconds and then call loadTimeForRoundOneDialog()
        delayToShowRoundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) { }

	   		public void onFinish() 
	   		{
	   			loadTimeForRoundOneDialog();
	   			delayToShowRoundOneAnnouncementTimer.cancel();
	   		}
	   	};
	   	delayToShowRoundOneAnnouncementTimer.start();
	   	
	   	// this runs on a schedule and check every second if the user has submitted an answer
	   	checkIfPlayerSubmittedAnswerTimer = new Timer(); 
	   	checkIfPlayerSubmittedAnswerTimer.schedule(new TimerTask() 
        { 
            @Override 
            public void run() 
            { 
                TimerMethod(); 
            } 
     
        }, 0, 1000); 
    }
    
    public void onClick(View v)
	{
		switch(v.getId()) 
    	{  
	    	case R.id.btnRoundtwo:
	    		startNewIntent();
	    		break;
	    	default:
	    		break;
    	}
	}
    
	private String getPlayerOneEmail()
	{
		SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF, MODE_PRIVATE);
		return shared.getString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, "");
	}
	
	private String getPlayerTwoEmail()
	{
		SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF, MODE_PRIVATE);
		return shared.getString(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF_KEY, "");
	}
	
	private void setPlayerOneAvatar()
	{
		txtGamePlayPlayerOne = (TextView)findViewById(R.id.txtGamePlayPlayerOne);
		playeOneName = getPlayerOneNameByEmail();
		txtGamePlayPlayerOne.setText(playeOneName);

		imgPlayerOne = (ImageView)findViewById(R.id.imgPlayerOne);
		dbHelper = new DBHelper();
        playerOneAvatarIdByEmail = new ArrayList<NameValuePair>(); 
        playerOneAvatarIdByEmail.add(new BasicNameValuePair("email", getPlayerOneEmail()));
		
		AbsoluteLayout.LayoutParams playerOneParams = new AbsoluteLayout.LayoutParams(80, 80, 30, 100);
        imgPlayerOne.setLayoutParams(playerOneParams);
		
		switch(Integer.parseInt(dbHelper.readDBData(StaticData.SELECT_PLAYER_AVITAR_ID_BY_EMAIL, playerOneAvatarIdByEmail, "avitar_picture_id").get(0))) 
    	{  
	    	case 1: 
	    		Drawable avatarOne = getResources().getDrawable(R.drawable.player_one);
	    		imgPlayerOne.setImageDrawable(avatarOne);
	    		break; 
	    	
	    	case 2: 
	    		Drawable avatarTwo = getResources().getDrawable(R.drawable.player_two);
	    		imgPlayerOne.setImageDrawable(avatarTwo);
	    		break;
	    	
	    	case 3:  
	    		Drawable avatarThree = getResources().getDrawable(R.drawable.player_three);
	    		imgPlayerOne.setImageDrawable(avatarThree);
		    	break;
		    	
	    	case 4: 
	    		Drawable avatarFour = getResources().getDrawable(R.drawable.player_four);
	    		imgPlayerOne.setImageDrawable(avatarFour);
		    	break;
		    	
	    	case 5: 
	    		Drawable avatarFive = getResources().getDrawable(R.drawable.player_five);
	    		imgPlayerOne.setImageDrawable(avatarFive);
		    	break;
		    	
	    	case 6: 
	    		Drawable avatarSix = getResources().getDrawable(R.drawable.player_six);
	    		imgPlayerOne.setImageDrawable(avatarSix);
		    	break;
		    	
	    	case 7: 
	    		Drawable avatarSeven = getResources().getDrawable(R.drawable.player_seven);
	    		imgPlayerOne.setImageDrawable(avatarSeven);
		    	break;
		    	
	    	case 8: 
	    		Drawable avatarEight = getResources().getDrawable(R.drawable.player_eight);
	    		imgPlayerOne.setImageDrawable(avatarEight);
		    	break;
		    	
	    	case 9: 
	    		Drawable avatarNine = getResources().getDrawable(R.drawable.player_nine);
	    		imgPlayerOne.setImageDrawable(avatarNine);
		    	break;
		    	
	    	case 10:
	    		Drawable avatarTen = getResources().getDrawable(R.drawable.player_ten);
	    		imgPlayerOne.setImageDrawable(avatarTen);
		    	break;

	    	default:
	    	throw new RuntimeException("Unknow button ID"); 
    	}
	}
	
	private void setPlayerTwoAvatar()
	{
		txtGamePlayPlayerTwo = (TextView)findViewById(R.id.txtGamePlayPlayerTwo);
		playeTwoName = getPlayerTwoNameByEmail();
		txtGamePlayPlayerTwo.setText(playeTwoName);
		
		imgPlayerTwo = (ImageView)findViewById(R.id.imgPlayerTwo);
        dbHelper = new DBHelper();
        playerTwoAvatarIdByEmail = new ArrayList<NameValuePair>(); 
        playerTwoAvatarIdByEmail.add(new BasicNameValuePair("email", getPlayerTwoEmail()));
       
        AbsoluteLayout.LayoutParams playerTwoParams = new AbsoluteLayout.LayoutParams(80, 80, 135, 100);
        imgPlayerTwo.setLayoutParams(playerTwoParams);

        switch(Integer.parseInt(dbHelper.readDBData(StaticData.SELECT_PLAYER_AVITAR_ID_BY_EMAIL, playerTwoAvatarIdByEmail, "avitar_picture_id").get(0))) 
    	{  
	    	case 1: 
	    		Drawable avatarOne = getResources().getDrawable(R.drawable.player_one);
	    		imgPlayerTwo.setImageDrawable(avatarOne);
	    		break; 
	    	
	    	case 2: 
	    		Drawable avatarTwo = getResources().getDrawable(R.drawable.player_two);
	    		imgPlayerTwo.setImageDrawable(avatarTwo);
	    		break;
	    	
	    	case 3: 
	    		Drawable avatarThree = getResources().getDrawable(R.drawable.player_three);
	    		imgPlayerTwo.setImageDrawable(avatarThree);
		    	break;
		    	
	    	case 4: 
	    		Drawable avatarFour = getResources().getDrawable(R.drawable.player_four);
	    		imgPlayerTwo.setImageDrawable(avatarFour);
		    	break;
		    	
	    	case 5: 
	    		Drawable avatarFive = getResources().getDrawable(R.drawable.player_five);
	    		imgPlayerTwo.setImageDrawable(avatarFive);
		    	break;
		    	
	    	case 6: 
	    		Drawable avatarSix = getResources().getDrawable(R.drawable.player_six);
	    		imgPlayerTwo.setImageDrawable(avatarSix);
		    	break;
		    	
	    	case 7: 
	    		Drawable avatarSeven = getResources().getDrawable(R.drawable.player_seven);
	    		imgPlayerTwo.setImageDrawable(avatarSeven);
		    	break;
		    	
	    	case 8: 
	    		Drawable avatarEight = getResources().getDrawable(R.drawable.player_eight);
	    		imgPlayerTwo.setImageDrawable(avatarEight);
		    	break;
		    	
	    	case 9: 
	    		Drawable avatarNine = getResources().getDrawable(R.drawable.player_nine);
	    		imgPlayerTwo.setImageDrawable(avatarNine);
		    	break;
		    	
	    	case 10:
	    		Drawable avatarTen = getResources().getDrawable(R.drawable.player_ten);
	    		imgPlayerTwo.setImageDrawable(avatarTen);
		    	break;

	    	default:
	    	throw new RuntimeException("Unknow button ID"); 
    	}
	}
	
	private void determineGameMode()
	{
		dbHelper = new DBHelper();
        singlePlayerNameValuePairs = new ArrayList<NameValuePair>();
        SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF, MODE_PRIVATE);
		singlePlayerNameValuePairs.add(new BasicNameValuePair("email", shared.getString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, "")));

		if (dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, singlePlayerNameValuePairs, "player_state").get(0).toString().equals("single"))
		{
			singlePlayerMode = true;
		} 
		else
		{
			singlePlayerMode = false;
		}
	}
	
	private String getPlayerOneNameByEmail()
	{
		dbHelper = new DBHelper();
		playerOneNameByEmailNameValuePairs = new ArrayList<NameValuePair>();
        SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF, MODE_PRIVATE);
        playerOneNameByEmailNameValuePairs.add(new BasicNameValuePair("email", shared.getString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, "")));
        return dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, playerOneNameByEmailNameValuePairs, "name").get(0).toString();
	}
	
	private String getPlayerTwoNameByEmail()
	{
		dbHelper = new DBHelper();
		playerTwoNameByEmailNameValuePairs = new ArrayList<NameValuePair>();
        SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF, MODE_PRIVATE);
        playerTwoNameByEmailNameValuePairs.add(new BasicNameValuePair("email", shared.getString(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF_KEY, "")));
        return dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, playerTwoNameByEmailNameValuePairs, "name").get(0).toString();
	}

    private void startNewIntent()
    {
    	Intent continueIntent = new Intent(GamePlay.this, RoundTwo.class); 
		startActivity(continueIntent);
		finish();
    }
    
    private void loadTimeForRoundOneDialog()
    {
    	final Dialog roundOneAnnouncementDialog = new Dialog(GamePlay.this);
		roundOneAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		roundOneAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		roundOneAnnouncementDialog.setContentView(R.layout.time_for_round_one_dialog);
		roundOneAnnouncementDialog.setCancelable(true); 
        
        roundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForRoundOneAnnouncementTimer)
	   			{
	   				roundOneAnnouncementDialog.show();
		   			firstTimeForRoundOneAnnouncementTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			roundOneAnnouncementDialog.dismiss();
	   			roundOneAnnouncementTimer.cancel();
	   			
	   			try 
	   			{
					Thread.sleep(2000);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
	   			
	   			loadPlayerTurnDialog();
	   		}
	   	};
	   	roundOneAnnouncementTimer.start();
    }
    
    private void loadPlayerTurnDialog()
    {
    	final Dialog playerTurnDialog = new Dialog(GamePlay.this);
    	playerTurnDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	playerTurnDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	playerTurnDialog.setContentView(R.layout.player_one_its_your_turn);
    	playerTurnDialog.setCancelable(true); 
        
        roundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForPlayerTurnDialogTimer)
	   			{
	   				playerTurnDialog.show();
	   				firstTimeForPlayerTurnDialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			playerTurnDialog.dismiss();
	   			runLoadingQuestionDialog();
	   			roundOneAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundOneAnnouncementTimer.start();
    }
    
    private void runLoadingQuestionDialog()
    {
    	loadingQuestionDialogTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForloadingQuestionDialogTimer)
	   			{
	   				loadingQuestionDialog = ProgressDialog.show(GamePlay.this, "","Loading your question...", true);
	   				firstTimeForloadingQuestionDialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			loadingQuestionDialog.dismiss();
	   			setQuestionForRound();
	   			loadingQuestionDialogTimer.cancel();
	   		}
	   	};
	   	loadingQuestionDialogTimer.start();
    }
    
    private void setQuestionForRound()
    {
    	questionIdCouter++;
    	
    	dbHelper = new DBHelper();
    	questionByIdAndRoundNameValuePairs = new ArrayList<NameValuePair>(); 
    	questionByIdAndRoundNameValuePairs.add(new BasicNameValuePair("id", questionIdCouter + ""));
    	questionByIdAndRoundNameValuePairs.add(new BasicNameValuePair("round", StaticData.ROUND_ONE));
    	
    	txtRoundOneQuestion = (TextView)findViewById(R.id.txtRoundOneQuestion);
    	txtRoundOneQuestion.setText(dbHelper.readDBData(StaticData.SELECT_QUESTION_BY_ID_AND_ROUND, questionByIdAndRoundNameValuePairs, "question").get(0).toString());
    }
    
    private void TimerMethod() 
    { 
        this.runOnUiThread(Timer_Tick); 
    } 
     
    private Runnable Timer_Tick = new Runnable() 
    { 
        public void run() 
        {
    		try
    		{
    			if (userHasSubmittedAnswer)
    			{
    				// add logic for dealing with if user has submitted an answer
    				
    				// also, need to give user dialog to enter answer
    			}
    		}
    		catch(Exception e) { }
        } 
    };
}
