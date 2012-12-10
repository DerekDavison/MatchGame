package com.example.matchgame;

import java.util.ArrayList;
import java.util.Random;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class GamePlay extends Activity implements OnClickListener
{
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> playerOneAvatarIdByEmail, playerTwoAvatarIdByEmail, singlePlayerNameValuePairs, 
		answerByIdRoundAndQuestionIdNameValuePair;
	private ImageView imgPlayerOne, imgPlayerTwo;
	private Boolean singlePlayerMode = false, firstTimeForRoundOneAnnouncementTimer = true, firstTimeForPlayerTurnDialogTimer = true, 
			firstTimeForloadingQuestionDialogTimer = true, userHasSubmittedAnswer = false, 
			firstTimeForLoadingCelebrityAnswersDialogTimer = true, firstTimeForloadingCheckingAnswerDialogTimer = true, 
			playerOneTurn = true, playerTwoTurn = false;
	private Button btnGoToRoundTwo;
	private CountDownTimer roundOneAnnouncementTimer, delayToShowRoundOneAnnouncementTimer, loadingQuestionDialogTimer, 
		loadingCelebrityAnswersDialogTimer, loadingQuestionAnswerDialogTimer, loadingCheckingAnswerDialogTimer, 
		genericDelayForNSecondsTimer, test;
	private ProgressDialog loadingQuestionDialog, loadingCelebrityAnswersDialog, loadingCheckingAnswerDialog;
	private int questionIdCouter = 0, playerOneScore = 0, playerTwoScore = 0, numberOfCorrectMatches = 0;
	private ArrayList<NameValuePair> questionByIdAndRoundNameValuePairs, playerOneNameByEmailNameValuePairs, 
		playerTwoNameByEmailNameValuePairs;
	private TextView txtRoundOneQuestion, txtGamePlayPlayerOne, txtGamePlayPlayerTwo, 
		txtGuestOneAnswer, txtGuestTwoAnswer, txtGuestThreeAnswer, txtGuestFourAnswer, txtGuestFiveAnswer, txtGuestSixAnswer, 
		txtPlayerOneAnswer, txtPlayerTwoAnswer, txtPlayerOneScore, txtPlayerTwoScore;
	private String playeOneName, playeTwoName;
	private Timer checkIfPlayerSubmittedAnswerTimer;
	private String currentQuestion = "", playerOneAnswer = "", guestAnswerOne = "", guestAnswerTwo = "", guestAnswerThree = "", 
			guestAnswerFour = "", guestAnswerFive = "", guestAnswerSix = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        btnGoToRoundTwo = (Button)findViewById(R.id.btnGoToRoundTwo);  
        btnGoToRoundTwo.setOnClickListener(this);
        
        txtGuestOneAnswer = (TextView)findViewById(R.id.txtGuestOneAnswer); 
        txtGuestTwoAnswer = (TextView)findViewById(R.id.txtGuestTwoAnswer); 
        txtGuestThreeAnswer = (TextView)findViewById(R.id.txtGuestThreeAnswer); 
        txtGuestFourAnswer = (TextView)findViewById(R.id.txtGuestFourAnswer); 
        txtGuestFiveAnswer = (TextView)findViewById(R.id.txtGuestFiveAnswer); 
        txtGuestSixAnswer = (TextView)findViewById(R.id.txtGuestSixAnswer); 
        
        txtPlayerOneAnswer = (TextView)findViewById(R.id.txtPlayerOneAnswer); 
        txtPlayerTwoAnswer = (TextView)findViewById(R.id.txtPlayerTwoAnswer);  
        
        txtPlayerOneScore = (TextView)findViewById(R.id.txtPlayerOneScore);
        txtPlayerTwoScore = (TextView)findViewById(R.id.txtPlayerTwoScore);  
        
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
        
        // have schedule that checks if a flag is set to true
        // have it true at first
        // then switch it to false right away
        // then when player two done, switch it to true, and then back to false right away
        
        // 900000000 second is 15000000 minutes
        test = new CountDownTimer(900000000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{
	   			if(playerOneTurn)
	   			{
	   				playerOneTurn = false;
	   				
	   				// wait four seconds and then call loadTimeForRoundOneDialog()
	   		        delayToShowRoundOneAnnouncementTimer = new CountDownTimer(3000, 1000) 
	   			   	{
	   			   		public void onTick(long millisUntilFinished) { }

	   			   		public void onFinish() 
	   			   		{
	   			   			loadTimeForRoundOneDialog();
	   			   			delayToShowRoundOneAnnouncementTimer.cancel();
	   			   		}
	   			   	};
	   			   	delayToShowRoundOneAnnouncementTimer.start();
	   			}
	   			else if (playerTwoTurn)
	   			{
	   				playerTwoTurn = false;
	   				
	   		        delayToShowRoundOneAnnouncementTimer = new CountDownTimer(3000, 1000) 
	   			   	{
	   			   		public void onTick(long millisUntilFinished) { }

	   			   		public void onFinish() 
	   			   		{
	   			   			loadTimeForRoundOneDialog();
	   			   			delayToShowRoundOneAnnouncementTimer.cancel();
	   			   		}
	   			   	};
	   			   	delayToShowRoundOneAnnouncementTimer.start();
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			test.cancel();
	   		}
	   	};
	   	test.start();
	   	
	   	// this runs on a schedule and checks every second if the user has submitted an answer
	   	checkIfPlayerSubmittedAnswerTimer = new Timer(); 
	   	checkIfPlayerSubmittedAnswerTimer.schedule(new TimerTask() 
        { 
            @Override 
            public void run() 
            { 
                timerMethod(); 
            } 
     
        }, 0, 1000); 
		
    }
    
    public void onClick(View v)
	{
		switch(v.getId()) 
    	{  
	    	case R.id.btnGoToRoundTwo:
	    		startNewIntent();
	    		break;
	    	default:
		    	throw new RuntimeException("Unknow button ID"); 
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
	   			firstTimeForRoundOneAnnouncementTimer = true;
	   		
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
	   			firstTimeForPlayerTurnDialogTimer = true;
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
	   			firstTimeForloadingQuestionDialogTimer = true;
	   			loadingQuestionDialog.dismiss();
	   			setQuestionForRound();
	   			
	   			loadAnswerQuestionDialog();	   			
	   			
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
    	
    	currentQuestion = dbHelper.readDBData(StaticData.SELECT_QUESTION_BY_ID_AND_ROUND, questionByIdAndRoundNameValuePairs, "question").get(0).toString();
    	
    	txtRoundOneQuestion = (TextView)findViewById(R.id.txtRoundOneQuestion);
    	txtRoundOneQuestion.setText(currentQuestion);
    }
    
    private void timerMethod() 
    { 
        this.runOnUiThread(timerTick); 
    } 
     
    private Runnable timerTick = new Runnable() 
    { 
        public void run() 
        {
    		try
    		{
    			// if at any point a user submits an answer
    			// display progress dialog for loading celebrity answers
    			if (userHasSubmittedAnswer)
    			{
    				loadingCelebrityAnswersDialogTimer = new CountDownTimer(3000, 1000) 
    			   	{
    			   		public void onTick(long millisUntilFinished) 
    			   		{ 
    			   			if (firstTimeForLoadingCelebrityAnswersDialogTimer)
    			   			{
    			   				loadingCelebrityAnswersDialog = ProgressDialog.show(GamePlay.this, "","Loading celebrity answers...", true);
    			   				firstTimeForLoadingCelebrityAnswersDialogTimer = false;
    			   			}
    			   		}

    			   		public void onFinish() 
    			   		{ 
    			   			firstTimeForLoadingCelebrityAnswersDialogTimer = true;
    			   			
    			   			loadingCelebrityAnswersDialog.dismiss();
    			   			loadingCelebrityAnswersDialogTimer.cancel();
    			   			
    			   			guestAnswerOne = getRandomAnswer(5, 3, 1);
    			   			guestAnswerTwo = getRandomAnswer(5, 3, 1);
    			   			guestAnswerThree = getRandomAnswer(5, 3, 1);
    			   			guestAnswerFour = getRandomAnswer(5, 3, 1);
    			   			guestAnswerFive = getRandomAnswer(5, 3, 1);
    			   			guestAnswerSix = getRandomAnswer(5, 3, 1);
    			   			
    			   			txtGuestOneAnswer.setText(guestAnswerOne); 
    			   			txtGuestTwoAnswer.setText(guestAnswerTwo);
    			   			txtGuestThreeAnswer.setText(guestAnswerThree);
    			   			txtGuestFourAnswer.setText(guestAnswerFour);
    			   			txtGuestFiveAnswer.setText(guestAnswerFive);
    			   			txtGuestSixAnswer.setText(guestAnswerSix);

    			   			genericDelayForNSecondsTimer = new CountDownTimer(3000, 1000) 
    	    			   	{
    	    			   		public void onTick(long millisUntilFinished) { }

    	    			   		public void onFinish() 
    	    			   		{
    	    			   			// load checking answer progress dialog
    	    	    			   	loadingCheckingAnswerDialogTimer = new CountDownTimer(3000, 1000) 
    	    	    			   	{
    	    	    			   		public void onTick(long millisUntilFinished) 
    	    	    			   		{ 
    	    	    			   			if (firstTimeForloadingCheckingAnswerDialogTimer)
    	    	    			   			{
    	    	    			   				loadingCheckingAnswerDialog = ProgressDialog.show(GamePlay.this, "","Checking your answers...", true);
    	    	    			   				firstTimeForloadingCheckingAnswerDialogTimer = false;
    	    	    			   			}
    	    	    			   		}

    	    	    			   		// after checking answer progress dialog
    	    	    			   		public void onFinish() 
    	    	    			   		{
    	    	    			   			firstTimeForloadingCheckingAnswerDialogTimer = true;
    	    	    			   			
    	    	    			   			loadingCheckingAnswerDialog.dismiss(); 
    	    	    			   			
    	    	    			   			// compare results
    	    	    			   			calculatePlayerOneScore();
    	    	    			   			 
    	    	    			   			// display results 
    	    	    			   			txtPlayerOneScore.setText(playerOneScore + "");
    	    	    			   			
    	    	    			   			if (numberOfCorrectMatches > 0)
    	    	    			   			{
    	    	    			   				// display congratulations message 
    	    	    			   				final Dialog congratulationsDialog = new Dialog(GamePlay.this);
    	    	    			   				congratulationsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	    	    				            
    	    	    			   				congratulationsDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	    	    			   				congratulationsDialog.setContentView(R.layout.congratulations_on_turn_round_one_dialog);
    	    	    			   				congratulationsDialog.setCancelable(true);

    	    	    				            final TextView txtRoundOneNumberOfMatches = (TextView) congratulationsDialog.findViewById(R.id.txtRoundOneNumberOfMatches); 
    	    	    				            final TextView txtRoundOnePlayerTurnScore = (TextView) congratulationsDialog.findViewById(R.id.txtRoundOnePlayerTurnScore);  

    	    	    				            txtRoundOneNumberOfMatches.setText("You got " + numberOfCorrectMatches  + " matches.");
    	    	    				            txtRoundOnePlayerTurnScore.setText("Your score is " + playerOneScore);
    	    	    				            
    	    	    				            Button btnPlayerTwoTurn = (Button) congratulationsDialog.findViewById(R.id.btnPlayerTwoTurn);

    	    	    				            btnPlayerTwoTurn.setOnClickListener(new OnClickListener() 
    	    	    				            {	
    	    	    					            public void onClick(final View v) 
    	    	    					            {
    	    	    					            	congratulationsDialog.dismiss();
    	    	    					            	// set flag to trigger player 2 turn
    	    	    					            	playerTwoTurn = true;
    	    	    					            } 
    	    	    				            });
    	    	    				              
    	    	    				            congratulationsDialog.show();
    	    	    			   				
    	    	    			   				// clear answers and question
    	    	    				            txtPlayerOneAnswer.setText("");
    	    	    				            txtGuestOneAnswer.setText("");
    	    	    				            txtGuestTwoAnswer.setText("");
    	    	    				            txtGuestThreeAnswer.setText("");
    	    	    				            txtGuestFourAnswer.setText("");
    	    	    				            txtGuestFiveAnswer.setText("");
    	    	    				            txtGuestSixAnswer.setText("");
    	    	    				            txtRoundOneQuestion.setText("");
    	    	    				            
    	    	    			   				// move to player two
    	    	    			   			}
    	    	    			   			else
    	    	    			   			{
    	    	    			   				// display sorry message 
    	    	    			   				final Dialog sorryDialog = new Dialog(GamePlay.this);
    	    	    			   				sorryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	    	    				            
    	    	    			   				sorryDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	    	    			   				sorryDialog.setContentView(R.layout.sorry_on_turn_round_one_dialog);
    	    	    			   				sorryDialog.setCancelable(true);
    	    	    				            
    	    	    				            Button btnPlayerTwoTurnSorryDialog = (Button) sorryDialog.findViewById(R.id.btnPlayerTwoTurnSorryDialog);

    	    	    				            btnPlayerTwoTurnSorryDialog.setOnClickListener(new OnClickListener() 
    	    	    				            {	
    	    	    					            public void onClick(final View v) 
    	    	    					            {
    	    	    					            	sorryDialog.dismiss();
    	    	    					            	// set flag to trigger player 2 turn
    	    	    					            	playerTwoTurn = true;
    	    	    					            } 
    	    	    				            });
    	    	    				            
    	    	    				            sorryDialog.show();
    	    	    			   				
	    	    	    			   			// clear answers
    	    	    				            txtPlayerOneAnswer.setText("");
    	    	    				            txtGuestOneAnswer.setText("");
    	    	    				            txtGuestTwoAnswer.setText("");
    	    	    				            txtGuestThreeAnswer.setText("");
    	    	    				            txtGuestFourAnswer.setText("");
    	    	    				            txtGuestFiveAnswer.setText("");
    	    	    				            txtGuestSixAnswer.setText("");
    	    	    				            txtRoundOneQuestion.setText("");
    	    	    				            
    	    	    			   				// move to player two
    	    	    			   			}
    	    	    			   			
    	    	    			   			numberOfCorrectMatches = 0;
    	    	    			   			loadingCheckingAnswerDialogTimer.cancel();
    	    	    			   		}
    	    	    			   	};
    	    	    			   	loadingCheckingAnswerDialogTimer.start();
    	    			   		}
    	    			   	};
    	    			   	genericDelayForNSecondsTimer.start();
    			   		}
    			   	};
    			   	loadingCelebrityAnswersDialogTimer.start();	
    			   	userHasSubmittedAnswer = false;
    			}
    		}
    		catch(Exception e) { }
        } 
    };
    
    private void loadAnswerQuestionDialog() 
    { 
    	loadingQuestionAnswerDialogTimer = new CountDownTimer(2000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) { }

	   		public void onFinish() 
	   		{
	   			final Dialog answerQuestionDialog = new Dialog(GamePlay.this);
	   	    	answerQuestionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	   	    	
	   	    	answerQuestionDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	   	    	answerQuestionDialog.setContentView(R.layout.submit_round_one_player_answer_dialog);
	   	    	answerQuestionDialog.setCancelable(true); 
	   	    	
	   	    	final EditText edtRoundOneAnswer = (EditText) answerQuestionDialog.findViewById(R.id.edtRoundOneAnswer); 
	   	    	TextView txtRoundOneQuestion = (TextView) answerQuestionDialog.findViewById(R.id.txtRoundOneQuestion); 
	   	    	
	   	    	txtRoundOneQuestion.setText(currentQuestion);
	   	    	
	   	    	Button btnSubmitAnswerRoundOne = (Button) answerQuestionDialog.findViewById(R.id.btnSubmitAnswerRoundOne); 
	   	    	btnSubmitAnswerRoundOne.setOnClickListener(new OnClickListener() 
	   	        {
	   	            public void onClick(final View v) 
	   	            {
	   	            	if (edtRoundOneAnswer.getText().toString().matches(""))
	   	        		{
	   	        			Toast.makeText(getApplicationContext(), "Enter answer.", Toast.LENGTH_LONG).show();
	   	        		}
	   	        		else
	   	        		{
	   	        			answerQuestionDialog.dismiss();
	   	        			userHasSubmittedAnswer = true;
	   	        			playerOneAnswer = edtRoundOneAnswer.getText().toString();
	   	        			txtPlayerOneAnswer.setText(playerOneAnswer);
	   	        		}
	   	            }
	   	        });
	   	    	answerQuestionDialog.show();
	   			
	   			loadingQuestionAnswerDialogTimer.cancel();
	   		}
	   	};
	   	loadingQuestionAnswerDialogTimer.start();
    }
    
	private String getRandomAnswer(int max, int min, int questionId)
	{
		dbHelper = new DBHelper();
        answerByIdRoundAndQuestionIdNameValuePair = new ArrayList<NameValuePair>(); 

        Random rand = new Random();
    	int randomNum = rand.nextInt(max - min + 1) + min;
		
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("id", randomNum + ""));
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("question_id", questionId + ""));
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("round", StaticData.ROUND_ONE));
        
        return dbHelper.readDBData(StaticData.SELECT_ANSWER_BY_ID_QUESTION_ID_AND_ROUND, answerByIdRoundAndQuestionIdNameValuePair, "answer").get(0).toString();
	}
	
	private void calculatePlayerOneScore()
	{
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerOne.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
		
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerTwo.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
		
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerThree.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
		
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerFour.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
		
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerFive.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
		
		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerSix.toUpperCase().trim()))
		{
			playerOneScore += 50;
			numberOfCorrectMatches++;
		}
	}
}
