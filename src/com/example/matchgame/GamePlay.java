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
			playerOneTurn = true, playerTwoTurn = false, changeTurnAnouncment = false, 
			firstTimeForloadingCheckingFinalResultsDialogTimer = true, firstTimeFortieAnnouncementDialogTimer = true,
			tieBreakerAnswerWasSubmitted = false;
	private Button btnGoToRoundTwo;
	private CountDownTimer roundOneAnnouncementTimer, delayToShowRoundOneAnnouncementTimer, loadingQuestionDialogTimer, 
		loadingCelebrityAnswersDialogTimer, loadingQuestionAnswerDialogTimer, loadingCheckingAnswerDialogTimer, 
		genericDelayForNSecondsTimer, mainCountDownTimer, loadingCheckingFinalResultsDialogTimer, 
		tieAnnouncementDialogTimer;
	private ProgressDialog loadingQuestionDialog, loadingCelebrityAnswersDialog, loadingCheckingAnswerDialog, 
		loadingCheckingFinalResultsDialog;
	private int questionIdCouter = 0, playerOneScore = 0, playerTwoScore = 0, numberOfCorrectMatches = 0, countTurns = 0, 
			seedForAnswers = 0, winner = 0;
	private ArrayList<NameValuePair> questionByIdAndRoundNameValuePairs, playerOneNameByEmailNameValuePairs, 
		playerTwoNameByEmailNameValuePairs, updatePlayerScoreByEmailNameValuePairs;
	private TextView txtRoundOneQuestion, txtGamePlayPlayerOne, txtGamePlayPlayerTwo, 
		txtGuestOneAnswer, txtGuestTwoAnswer, txtGuestThreeAnswer, txtGuestFourAnswer, txtGuestFiveAnswer, txtGuestSixAnswer, 
		txtPlayerOneAnswer, txtPlayerTwoAnswer, txtPlayerOneScore, txtPlayerTwoScore;
	private Timer checkIfPlayerSubmittedAnswerTimer, checkIfPlayerSubmittedTieBreakerAnswerTimer;
	private String currentQuestion = "", playerOneAnswer = "", guestAnswerOne = "", guestAnswerTwo = "", guestAnswerThree = "", 
			guestAnswerFour = "", guestAnswerFive = "", guestAnswerSix = "", playerOneName, playerTwoName, playerTwoAnswer = "", 
			playerOneEmail = "", playerTwoEmail = "", tieBreakerAnswer = "";
	
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

        // 900000000 second is 15000000 minutes
        mainCountDownTimer = new CountDownTimer(900000000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{
	   			if(playerOneTurn)
	   			{
	   				playerOneTurn = false;
	   				countTurns++;

	   				if (countTurns <= 4)
	   				{
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
	   				else
	   				{
	   					final Dialog tieAnnouncementDialog = new Dialog(GamePlay.this);
	   					tieAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	   		        
	   					tieAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	   					tieAnnouncementDialog.setContentView(R.layout.tie_anouncement_dialog);
	   					tieAnnouncementDialog.setCancelable(true);
	   					
	   					genericDelayForNSecondsTimer = new CountDownTimer(2000, 1000) 
	   	 			   	{
	   	 			   		public void onTick(long millisUntilFinished) { }

	   	 			   		public void onFinish() 
	   	 			   		{
		   	 			   		loadingCheckingFinalResultsDialogTimer = new CountDownTimer(3000, 1000) 
	    	    			   	{
	    	    			   		public void onTick(long millisUntilFinished) 
	    	    			   		{ 
	    	    			   			if (firstTimeForloadingCheckingFinalResultsDialogTimer)
	    	    			   			{
	    			   	 			   		loadingCheckingFinalResultsDialog = ProgressDialog.show(GamePlay.this, "","Checking the final results ...", true);
	    			   	 			   		firstTimeForloadingCheckingFinalResultsDialogTimer = false;
	    	    			   			}
	    	    			   		}
	
	    	    			   		// after checking answer progress dialog
	    	    			   		public void onFinish() 
	    	    			   		{
	    	    			   			firstTimeForloadingCheckingFinalResultsDialogTimer = true;
	    		   	 			   		loadingCheckingFinalResultsDialog.dismiss(); 
	    	    			   			loadingCheckingFinalResultsDialogTimer.cancel();
	    	    			   			
	    	    			   			// if there is a tie
	    	    			   			if (Integer.parseInt(txtPlayerOneScore.getText().toString()) == Integer.parseInt(txtPlayerTwoScore.getText().toString()))
	    	    			   			{
	    	    			   				genericDelayForNSecondsTimer = new CountDownTimer(1000, 1000) 
	    	    		   	 			   	{
	    	    		   	 			   		public void onTick(long millisUntilFinished) { }
	    	    	
	    	    		   	 			   		public void onFinish() 
	    	    		   	 			   		{
	    	    		   	 			   			tieAnnouncementDialogTimer = new CountDownTimer(15000, 1000) 
		    	    	    	    			   	{
		    	    	    	    			   		public void onTick(long millisUntilFinished) 
		    	    	    	    			   		{ 
		    	    	    	    			   			if (firstTimeFortieAnnouncementDialogTimer)
		    	    	    	    			   			{
		    	    	    	    			   				// show dialog
		    	    	    	    			   				tieAnnouncementDialog.show();
		    	    	    	    			   				firstTimeFortieAnnouncementDialogTimer = false;
		    	    	    	    			   			}
		    	    	    	    			   		}
	
		    	    	    	    			   		// after checking answer progress dialog
		    	    	    	    			   		public void onFinish() 
		    	    	    	    			   		{
		    	    	    	    			   			firstTimeFortieAnnouncementDialogTimer = true;
		    	    	    	    			   			// dismiss dialog
		    	    	    	    			   			tieAnnouncementDialog.dismiss();
		    	    	    	    			   			tieAnnouncementDialogTimer.cancel();
		    	    	    	    			   			
		    	    	    	    			   			runTieBreakerRound();
		    	    	    	    			   		}
		    	    	    	    			   	};
		    	    	    	    			   	tieAnnouncementDialogTimer.start();
	    	    		   	 			   			
	    	    		   	 		   				genericDelayForNSecondsTimer.cancel();
	    	    		   	 			   		}
	    	    		   	 			   	};
	    	    		   	 			   	genericDelayForNSecondsTimer.start();
	    	    			   			}
	    	    			   			else
	    	    			   			{
	    	    			   				final Dialog playerWinnerDialog = new Dialog(GamePlay.this);
	    	    			   				playerWinnerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	    				            
	    	    			   				playerWinnerDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	    	    			   				playerWinnerDialog.setContentView(R.layout.round_one_final_result_dialog);
	    	    			   				playerWinnerDialog.setCancelable(true);

	    	    				            final TextView txtRoundOneFinalResultMessage = (TextView) playerWinnerDialog.findViewById(R.id.txtRoundOneFinalResultMessage); 
	    	    				            final TextView txtThanksPlayerWhoLostMessage = (TextView) playerWinnerDialog.findViewById(R.id.txtThanksPlayerWhoLostMessage);  

	    	    				            // if player 1 wins
		    	    			   			if (Integer.parseInt(txtPlayerOneScore.getText().toString()) > Integer.parseInt(txtPlayerTwoScore.getText().toString()))
		    	    			   			{
		    	    			   				txtRoundOneFinalResultMessage.setText("Congratulations " + playerOneName + ". You are the Winner!");
		    	    				            txtThanksPlayerWhoLostMessage.setText("We thank " + playerTwoName + " for playing");
		    	    				            
		    	    				            SharedPreferences winnerEmail = getSharedPreferences(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, MODE_PRIVATE); 
		    	    				            SharedPreferences.Editor editor = winnerEmail.edit();
		    	    				            editor.putString(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, playerOneEmail);
		    	    				            editor.commit();
		    	    				            
		    	    				            SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
		    	    				            SharedPreferences.Editor e = winnerName.edit();
		    	    				            e.putString(StaticData.WINNING_PLAYER_SHARED_PREF, playerOneName);
		    	    				            e.commit();
		    	    				            
		    	    				            winner = 1;
		    	    			   			}
		    	    			   			else // if player 2 wins
		    	    			   				if (Integer.parseInt(txtPlayerOneScore.getText().toString()) < Integer.parseInt(txtPlayerTwoScore.getText().toString()))
		    	    			   			{
		    	    			   					txtRoundOneFinalResultMessage.setText("Congratulations " + playerTwoName + ". You are the Winner!");
			    	    				            txtThanksPlayerWhoLostMessage.setText("We thank " + playerOneName + " for playing");
			    	    				            
			    	    				            SharedPreferences winnerEmail = getSharedPreferences(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, MODE_PRIVATE); 
			    	    				            SharedPreferences.Editor editor = winnerEmail.edit();
			    	    				            editor.putString(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, playerTwoEmail);
			    	    				            editor.commit();
			    	    				            
			    	    				            SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
			    	    				            SharedPreferences.Editor e = winnerName.edit();
			    	    				            e.putString(StaticData.WINNING_PLAYER_SHARED_PREF, playerTwoName);
			    	    				            e.commit();
			    	    				            
			    	    				            winner = 2;
		    	    			   			}

	    	    				            final Button btnContinueToRoundTwo = (Button) playerWinnerDialog.findViewById(R.id.btnContinueToRoundTwo);

	    	    				            btnContinueToRoundTwo.setOnClickListener(new OnClickListener() 
	    	    				            {	
	    	    					            public void onClick(final View v) 
	    	    					            {
	    	    					            	playerWinnerDialog.dismiss();
	    	    					            	mainCountDownTimer.cancel();
	    	    					            	checkIfPlayerSubmittedAnswerTimer.cancel();
	    	    					            	checkIfPlayerSubmittedTieBreakerAnswerTimer.cancel();
	    	    					            	
	    	    					            	// update database  
	    	    					            	if (winner == 1)
	    	    					            	{
	    	    					            		updatePlayerScoreByEmail(playerOneEmail, playerOneScore + "");
	    	    					            	}
	    	    					            	
	    	    					            	if (winner == 2)
	    	    					            	{
	    	    					            		updatePlayerScoreByEmail(playerTwoEmail, playerTwoScore + "");
	    	    					            	}
	    	    					            	
	    	    					            	//start round 2 intent
	    	    					            	startRoundTwoIntent();
	    	    					            } 
	    	    				            });
	    	    				              
	    	    				            playerWinnerDialog.show();
	    	    			   			}
	    	    			   		}
	    	    			   	};
	    	    			   	loadingCheckingFinalResultsDialogTimer.start();
	   	 		   				genericDelayForNSecondsTimer.cancel();
	   	 			   		}
	   	 			   	};
	   	 			   	genericDelayForNSecondsTimer.start();
	   					mainCountDownTimer.cancel(); 
	   				}
	   			}
	   			else if (playerTwoTurn)
	   			{
	   				playerTwoTurn = false;
	   				countTurns++;
	   				
	   				if (countTurns <= 4)
	   				{
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
	   				else
	   				{
	   					Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
	   					mainCountDownTimer.cancel();
	   				}
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			mainCountDownTimer.cancel();
	   		}
	   	};
	   	mainCountDownTimer.start();
	   	
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
	   	
	   	checkIfPlayerSubmittedTieBreakerAnswerTimer = new Timer(); 
	   	checkIfPlayerSubmittedTieBreakerAnswerTimer.schedule(new TimerTask() 
        { 
            @Override 
            public void run() 
            { 
                timerForTiebreakerMethod(); 
            } 
     
        }, 0, 1000);  
    }
    
    public void onClick(View v)
	{
		switch(v.getId()) 
    	{  
	    	case R.id.btnGoToRoundTwo:
	    		startRoundTwoIntent();
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
		playerOneName = getPlayerOneNameByEmail();
		txtGamePlayPlayerOne.setText(playerOneName);

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
		playerTwoName = getPlayerTwoNameByEmail();
		txtGamePlayPlayerTwo.setText(playerTwoName);
		
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
        playerOneEmail = shared.getString(StaticData.PLAYER_ONE_EMAIL_SHARED_PREF_KEY, "");
        playerOneNameByEmailNameValuePairs.add(new BasicNameValuePair("email", playerOneEmail));
        return dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, playerOneNameByEmailNameValuePairs, "name").get(0).toString();
	}
	
	private String getPlayerTwoNameByEmail()
	{
		dbHelper = new DBHelper();
		playerTwoNameByEmailNameValuePairs = new ArrayList<NameValuePair>();
        SharedPreferences shared = getSharedPreferences(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF, MODE_PRIVATE);
        playerTwoEmail = shared.getString(StaticData.PLAYER_TWO_EMAIL_SHARED_PREF_KEY, "");
        playerTwoNameByEmailNameValuePairs.add(new BasicNameValuePair("email", playerTwoEmail));
        return dbHelper.readDBData(StaticData.SELECT_USER_BY_EMAIL_ADDRESS_PHP_FILE, playerTwoNameByEmailNameValuePairs, "name").get(0).toString();
	}
	
	private void updatePlayerScoreByEmail(String playerEmail, String playerScore)
	{
		dbHelper = new DBHelper();
		
		updatePlayerScoreByEmailNameValuePairs = new ArrayList<NameValuePair>();
		updatePlayerScoreByEmailNameValuePairs.add(new BasicNameValuePair("email", playerEmail));
		updatePlayerScoreByEmailNameValuePairs.add(new BasicNameValuePair("score", playerScore));
		
		dbHelper.modifyData(updatePlayerScoreByEmailNameValuePairs, StaticData.UPDATE_PLAYER_SCORE_BY_EMAIL);
	}

    private void startRoundTwoIntent()
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
		
		if (changeTurnAnouncment)
		{
			if(countTurns == 1 || countTurns == 3) 
	    	{
				TextView txtTimeForRoundOneMessage = (TextView) roundOneAnnouncementDialog.findViewById(R.id.txtTimeForRoundOneMessage);
				txtTimeForRoundOneMessage.setText("And now it's time for our other player, " + playerOneName + ".");
	    	}

	    	if(countTurns == 2 || countTurns == 4) 
	    	{
	    		TextView txtTimeForRoundOneMessage = (TextView) roundOneAnnouncementDialog.findViewById(R.id.txtTimeForRoundOneMessage);
				txtTimeForRoundOneMessage.setText("And now it's time for our other player, " + playerTwoName + ".");
	    	}
		}
        
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
	   			
	   			genericDelayForNSecondsTimer = new CountDownTimer(1000, 1000) 
			   	{
			   		public void onTick(long millisUntilFinished) { }

			   		public void onFinish() 
			   		{
			   			loadPlayerTurnDialog();
		   				genericDelayForNSecondsTimer.cancel();
			   		}
			   	};
			   	genericDelayForNSecondsTimer.start();
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

    	TextView txtPlayerTurnMessage = (TextView) playerTurnDialog.findViewById(R.id.txtPlayerTurnMessage); 
    	
    	if(countTurns == 1 || countTurns == 3) 
    	{
    		txtPlayerTurnMessage.setText("Player 1, it's your turn!");
    	}

    	if(countTurns == 2 || countTurns == 4) 
    	{
    		txtPlayerTurnMessage.setText("Player 2, it's your turn!");
    	}
    	
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
	   				loadingQuestionDialog = ProgressDialog.show(GamePlay.this, "","Loading your question ...", true);
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
    			   				genericDelayForNSecondsTimer = new CountDownTimer(2000, 1000) 
        	    			   	{
        	    			   		public void onTick(long millisUntilFinished) { }

        	    			   		public void onFinish() 
        	    			   		{
        	    			   			loadingCelebrityAnswersDialog = ProgressDialog.show(GamePlay.this, "","Loading celebrity answers ...", true);
            			   				firstTimeForLoadingCelebrityAnswersDialogTimer = false;
            			   				genericDelayForNSecondsTimer.cancel();
        	    			   		}
        	    			   	};
        	    			   	genericDelayForNSecondsTimer.start();
    			   			}
    			   		}

    			   		public void onFinish() 
    			   		{ 
    			   			seedForAnswers++;
    			   			firstTimeForLoadingCelebrityAnswersDialogTimer = true;
    			   			
    			   			loadingCelebrityAnswersDialog.dismiss();
    			   			loadingCelebrityAnswersDialogTimer.cancel();
    			   			
    			   			if (seedForAnswers == 1)
    			   			{
    			   				guestAnswerOne = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerTwo = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerThree = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerFour = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerFive = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerSix = getRandomAnswer(10, 1, seedForAnswers);
    			   			}
    			   			else if (seedForAnswers == 2)
    			   			{
    			   				guestAnswerOne = getRandomAnswer(62, 53, seedForAnswers);
        			   			guestAnswerTwo = getRandomAnswer(62, 53, seedForAnswers);
        			   			guestAnswerThree = getRandomAnswer(62, 53, seedForAnswers);
        			   			guestAnswerFour = getRandomAnswer(62, 53, seedForAnswers);
        			   			guestAnswerFive = getRandomAnswer(62, 53, seedForAnswers);
        			   			guestAnswerSix = getRandomAnswer(62, 53, seedForAnswers);
    			   			}
    			   			else if (seedForAnswers == 3)
    			   			{
    			   				guestAnswerOne = getRandomAnswer(72, 63, seedForAnswers);
        			   			guestAnswerTwo = getRandomAnswer(72, 63, seedForAnswers);
        			   			guestAnswerThree = getRandomAnswer(72, 63, seedForAnswers);
        			   			guestAnswerFour = getRandomAnswer(72, 63, seedForAnswers);
        			   			guestAnswerFive = getRandomAnswer(72, 63, seedForAnswers);
        			   			guestAnswerSix = getRandomAnswer(72, 63, seedForAnswers);
    			   			}
    			   			else if (seedForAnswers == 4)
    			   			{
    			   				guestAnswerOne = getRandomAnswer(82, 73, seedForAnswers);
        			   			guestAnswerTwo = getRandomAnswer(82, 73, seedForAnswers);
        			   			guestAnswerThree = getRandomAnswer(82, 73, seedForAnswers);
        			   			guestAnswerFour = getRandomAnswer(82, 73, seedForAnswers);
        			   			guestAnswerFive = getRandomAnswer(82, 73, seedForAnswers);
        			   			guestAnswerSix = getRandomAnswer(82, 73, seedForAnswers);
    			   			}
    			   			
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
    	    	    			   				loadingCheckingAnswerDialog = ProgressDialog.show(GamePlay.this, "","Checking your answers ...", true);
    	    	    			   				firstTimeForloadingCheckingAnswerDialogTimer = false;
    	    	    			   			}
    	    	    			   		}

    	    	    			   		// after checking answer progress dialog
    	    	    			   		public void onFinish() 
    	    	    			   		{
    	    	    			   			firstTimeForloadingCheckingAnswerDialogTimer = true;
    	    	    			   			
    	    	    			   			loadingCheckingAnswerDialog.dismiss(); 
    	    	    			   			
    	    	    			   			// compare results
    	    	    			   			calculatePlayerScore();
    	    	    			   			 
    	    	    			   			// display results 
    	    	    			   			if(countTurns == 1 || countTurns == 3)
	    	    				            {
    	    	    			   				txtPlayerOneScore.setText(playerOneScore + ""); 
	    	    				            }
	    	    				            
	    	    				            if(countTurns == 2 || countTurns == 4)
	    	    				            {
	    	    				            	txtPlayerTwoScore.setText(playerTwoScore + ""); 
	    	    				            }
    	    	    			   			
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

    	    	    				            if (numberOfCorrectMatches == 1)
    	    	    				            {
    	    	    				            	txtRoundOneNumberOfMatches.setText("You got " + numberOfCorrectMatches  + " match.");
    	    	    				            }
    	    	    				            else if (numberOfCorrectMatches >= 2)
    	    	    				            {
    	    	    				            	txtRoundOneNumberOfMatches.setText("You got " + numberOfCorrectMatches  + " matches.");
    	    	    				            }

    	    	    				            Button btnNextPlayerTurn = (Button) congratulationsDialog.findViewById(R.id.btnNextPlayerTurn);

    	    	    				            if(countTurns == 1 || countTurns == 3)
    	    	    				            {
    	    	    				            	btnNextPlayerTurn.setText("Start player two turn");
    	    	    				            	txtRoundOnePlayerTurnScore.setText("Your score is " + playerOneScore);
    	    	    				            }
    	    	    				            
    	    	    				            if(countTurns == 2)
    	    	    				            {
    	    	    				            	btnNextPlayerTurn.setText("Start player one turn");
    	    	    				            	txtRoundOnePlayerTurnScore.setText("Your score is " + playerTwoScore);
    	    	    				            }
    	    	    				            
    	    	    				            if(countTurns == 4)
    	    	    				            {
    	    	    				            	btnNextPlayerTurn.setText("Continue");
    	    	    				            	txtRoundOnePlayerTurnScore.setText("Your score is " + playerTwoScore);
    	    	    				            }

    	    	    				            btnNextPlayerTurn.setOnClickListener(new OnClickListener() 
    	    	    				            {	
    	    	    					            public void onClick(final View v) 
    	    	    					            {
    	    	    					            	congratulationsDialog.dismiss();

    	    	    					            	if (countTurns == 1 || countTurns == 3)
    	    	    					            	{
    	    	    					            		playerOneTurn = false;
    	    	    					            		playerTwoTurn = true;
    	    	    					            	}

    	    	    					            	if (countTurns == 2 || countTurns == 4)
    	    	    					            	{
    	    	    					            		playerTwoTurn = false;
    	    	    					            		playerOneTurn = true;
    	    	    					            	}
    	    	    					            } 
    	    	    				            });
    	    	    				              
    	    	    				            congratulationsDialog.show();
    	    	    			   				
    	    	    			   				// clear answers and question
    	    	    				            txtPlayerOneAnswer.setText("");
    	    	    				            txtPlayerTwoAnswer.setText("");
    	    	    				            txtGuestOneAnswer.setText("");
    	    	    				            txtGuestTwoAnswer.setText("");
    	    	    				            txtGuestThreeAnswer.setText("");
    	    	    				            txtGuestFourAnswer.setText("");
    	    	    				            txtGuestFiveAnswer.setText("");
    	    	    				            txtGuestSixAnswer.setText("");
    	    	    				            txtRoundOneQuestion.setText("");
    	    	    				            
    	    	    				            changeTurnAnouncment = true;
    	    	    			   			}
    	    	    			   			else
    	    	    			   			{
    	    	    			   				// display sorry message 
    	    	    			   				final Dialog sorryDialog = new Dialog(GamePlay.this);
    	    	    			   				sorryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	    	    				            
    	    	    			   				sorryDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	    	    			   				sorryDialog.setContentView(R.layout.sorry_on_turn_round_one_dialog);
    	    	    			   				sorryDialog.setCancelable(true);
    	    	    				            
    	    	    				            Button btnNextPlayerTurnSorryDialog = (Button) sorryDialog.findViewById(R.id.btnNextPlayerTurnSorryDialog);

    	    	    				            if(countTurns == 1 || countTurns == 3)
    	    	    				            {
    	    	    				            	btnNextPlayerTurnSorryDialog.setText("Start player two turn");
    	    	    				            }
    	    	    				            
    	    	    				            if(countTurns == 2)
    	    	    				            {
    	    	    				            	btnNextPlayerTurnSorryDialog.setText("Start player one turn");
    	    	    				            }
    	    	    				            
    	    	    				            if(countTurns == 4)
    	    	    				            {
    	    	    				            	btnNextPlayerTurnSorryDialog.setText("Continue");
    	    	    				            }
    	    	    				            
    	    	    				            btnNextPlayerTurnSorryDialog.setOnClickListener(new OnClickListener() 
    	    	    				            {	
    	    	    					            public void onClick(final View v) 
    	    	    					            {
    	    	    					            	sorryDialog.dismiss();
    	    	    					            	
    	    	    					            	if (countTurns == 1 || countTurns == 3)
    	    	    					            	{
    	    	    					            		playerOneTurn = false;
    	    	    					            		playerTwoTurn = true;
    	    	    					            	}

    	    	    					            	if (countTurns == 2 || countTurns == 4)
    	    	    					            	{
    	    	    					            		playerTwoTurn = false;
    	    	    					            		playerOneTurn = true;
    	    	    					            	}
    	    	    					            } 
    	    	    				            });
    	    	    				            
    	    	    				            sorryDialog.show();
    	    	    			   				
	    	    	    			   			// clear answers
    	    	    				            txtPlayerOneAnswer.setText("");
    	    	    				            txtPlayerTwoAnswer.setText("");
    	    	    				            txtGuestOneAnswer.setText(""); 
    	    	    				            txtGuestTwoAnswer.setText("");
    	    	    				            txtGuestThreeAnswer.setText("");
    	    	    				            txtGuestFourAnswer.setText("");
    	    	    				            txtGuestFiveAnswer.setText("");
    	    	    				            txtGuestSixAnswer.setText("");
    	    	    				            txtRoundOneQuestion.setText("");
    	    	    				            
    	    	    				            changeTurnAnouncment = true;
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
	   	        			if(countTurns == 1 || countTurns == 3)
				            {
	   	        				answerQuestionDialog.dismiss();
		   	        			userHasSubmittedAnswer = true;
		   	        			playerOneAnswer = edtRoundOneAnswer.getText().toString();
		   	        			txtPlayerOneAnswer.setText(playerOneAnswer);
		   	        			txtPlayerTwoAnswer.setText("");
				            }
				            
				            if(countTurns == 2 || countTurns == 4)
				            {
				            	answerQuestionDialog.dismiss();
		   	        			userHasSubmittedAnswer = true;
		   	        			playerTwoAnswer = edtRoundOneAnswer.getText().toString();
		   	        			txtPlayerTwoAnswer.setText(playerTwoAnswer);
		   	        			txtPlayerOneAnswer.setText("");
				            }
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
	
	private void calculatePlayerScore()
	{
		if(countTurns == 1 || countTurns == 3)
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
        
        if(countTurns == 2 || countTurns == 4)
        {
        	if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerOne.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
    		
    		if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerTwo.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
    		
    		if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerThree.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
    		
    		if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerFour.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
    		
    		if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerFive.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
    		
    		if (playerTwoAnswer.toUpperCase().trim().equals(guestAnswerSix.toUpperCase().trim()))
    		{
    			playerTwoScore += 50;
    			numberOfCorrectMatches++;
    		}
        }
	}
	
	private void runTieBreakerRound()
	{
		loadingQuestionDialogTimer = new CountDownTimer(3000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForloadingQuestionDialogTimer)
	   			{
	   				loadingQuestionDialog = ProgressDialog.show(GamePlay.this, "","Loading your tiebreaker question ...", true);
	   				firstTimeForloadingQuestionDialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			firstTimeForloadingQuestionDialogTimer = true;
	   			loadingQuestionDialog.dismiss();
	   			setQuestionForRound();
	   			
	   			runTiebreakerAnswerQuestionDialog();	   			
	   			
	   			loadingQuestionDialogTimer.cancel();
	   		}
	   	};
	   	loadingQuestionDialogTimer.start();
	}
	
	private void runTiebreakerAnswerQuestionDialog()
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
            	/////////////////////////////////////////////////////////
            	//
            	// THE PROGRAM CRASHES AT THIS POINT
            	//
            	///////////////////////////////////////////////////////// 
            	
            	if(!edtRoundOneAnswer.toString().matches(""))
            	{
            		tieBreakerAnswerWasSubmitted = true;
            		tieBreakerAnswer = edtRoundOneAnswer.getText().toString();
            	}
            	else
            	{
            		Toast.makeText(getApplicationContext(), "Enter an answer", Toast.LENGTH_LONG).show();
            	}
            }
        });
    	answerQuestionDialog.show();
	}
	
	private void timerForTiebreakerMethod() 
    { 
        this.runOnUiThread(tiebreakerTimerTick); 
    } 
     
    private Runnable tiebreakerTimerTick = new Runnable() 
    { 
        public void run() 
        {
    		try
    		{
    			if (tieBreakerAnswerWasSubmitted)
    			{
    				tieBreakerAnswerWasSubmitted = false;

    				loadingCelebrityAnswersDialogTimer = new CountDownTimer(2000, 1000) 
    			   	{
    			   		public void onTick(long millisUntilFinished) 
    			   		{ 
    			   			if (firstTimeForLoadingCelebrityAnswersDialogTimer)
    			   			{
    			   				genericDelayForNSecondsTimer = new CountDownTimer(2000, 1000) 
        	    			   	{
        	    			   		public void onTick(long millisUntilFinished) { }

        	    			   		public void onFinish() 
        	    			   		{
        	    			   			loadingCelebrityAnswersDialog = ProgressDialog.show(GamePlay.this, "","Loading celebrity answers ...", true);
            			   				firstTimeForLoadingCelebrityAnswersDialogTimer = false;
            			   				genericDelayForNSecondsTimer.cancel();
        	    			   		}
        	    			   	};
        	    			   	genericDelayForNSecondsTimer.start();
    			   			}
    			   		}

    			   		public void onFinish() 
    			   		{ 
    			   			seedForAnswers++;
    			   			firstTimeForLoadingCelebrityAnswersDialogTimer = true;
    			   			
    			   			loadingCelebrityAnswersDialog.dismiss();
    			   			loadingCelebrityAnswersDialogTimer.cancel();
    			   			
    			   			if (seedForAnswers == 5)
    			   			{
    			   				guestAnswerOne = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerTwo = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerThree = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerFour = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerFive = getRandomAnswer(10, 1, seedForAnswers);
        			   			guestAnswerSix = getRandomAnswer(10, 1, seedForAnswers);
    			   			}
    			   			
    			   			txtGuestOneAnswer.setText(guestAnswerOne); 
    			   			txtGuestTwoAnswer.setText(guestAnswerTwo);
    			   			txtGuestThreeAnswer.setText(guestAnswerThree);
    			   			txtGuestFourAnswer.setText(guestAnswerFour);
    			   			txtGuestFiveAnswer.setText(guestAnswerFive);
    			   			txtGuestSixAnswer.setText(guestAnswerSix);

    			   			genericDelayForNSecondsTimer = new CountDownTimer(2000, 1000) 
    	    			   	{
    	    			   		public void onTick(long millisUntilFinished) { }

    	    			   		public void onFinish() 
    	    			   		{
    	    			   			// load checking answer progress dialog
    	    	    			   	loadingCheckingAnswerDialogTimer = new CountDownTimer(2000, 1000) 
    	    	    			   	{
    	    	    			   		public void onTick(long millisUntilFinished) 
    	    	    			   		{ 
    	    	    			   			if (firstTimeForloadingCheckingAnswerDialogTimer)
    	    	    			   			{
    	    	    			   				loadingCheckingAnswerDialog = ProgressDialog.show(GamePlay.this, "","Checking your answers ...", true);
    	    	    			   				firstTimeForloadingCheckingAnswerDialogTimer = false;
    	    	    			   			}
    	    	    			   		}

    	    	    			   		// after checking answer progress dialog
    	    	    			   		public void onFinish() 
    	    	    			   		{
    	    	    			   			firstTimeForloadingCheckingAnswerDialogTimer = true;
    	    	    			   			
    	    	    			   			loadingCheckingAnswerDialog.dismiss();
    	    	    			   			numberOfCorrectMatches = 0;
    	    	    			   			
    	    	    			   			// check number of matches
	    	    			   	        	if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerOne.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
	    	    			   	    		
	    	    			   	    		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerTwo.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
	    	    			   	    		
	    	    			   	    		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerThree.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
	    	    			   	    		
	    	    			   	    		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerFour.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
	    	    			   	    		
	    	    			   	    		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerFive.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
	    	    			   	    		
	    	    			   	    		if (playerOneAnswer.toUpperCase().trim().equals(guestAnswerSix.toUpperCase().trim()))
	    	    			   	    		{
	    	    			   	    			numberOfCorrectMatches++;
	    	    			   	    		}
    	    	    			   			
	    	    			   	    		final Dialog tieBreakerWinnerDialog = new Dialog(GamePlay.this);
	    	    			   	    		tieBreakerWinnerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	    				            
	    	    			   	    		tieBreakerWinnerDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
	    	    			   	    		tieBreakerWinnerDialog.setContentView(R.layout.generic_tiebreaker_dialog);
	    	    			   	    		tieBreakerWinnerDialog.setCancelable(true);

	    	    				            final TextView txtTiebreakerCongratsMessage = (TextView) tieBreakerWinnerDialog.findViewById(R.id.txtTiebreakerCongratsMessage); 
	    	    				            final TextView txtTieBreakerNumberOfMatches = (TextView) tieBreakerWinnerDialog.findViewById(R.id.txtTieBreakerNumberOfMatches);  

	    	    				            if (numberOfCorrectMatches >= 3)
    	    	    			   			{
	    	    				            	txtTiebreakerCongratsMessage.setText("Congratulations " + playerOneName + ". You are the Winner!");
    	    	    			   			}
	    	    				            else
	    	    				            {
	    	    				            	txtTiebreakerCongratsMessage.setText("Congratulations " + playerTwoName + ". You are the Winner!");
	    	    				            }

	    	    				            txtTieBreakerNumberOfMatches.setText("You got " + numberOfCorrectMatches + " matches.");
	    	    				            
	    	    				            final Button btnTieBreakerStartNextRound = (Button) tieBreakerWinnerDialog.findViewById(R.id.btnTieBreakerStartNextRound);

	    	    				            btnTieBreakerStartNextRound.setOnClickListener(new OnClickListener() 
	    	    				            {	
	    	    					            public void onClick(final View v) 
	    	    					            {
	    	    					            	// update db and start round 2 intent
	    	    					            	checkIfPlayerSubmittedAnswerTimer.cancel();
	    	    					            	checkIfPlayerSubmittedTieBreakerAnswerTimer.cancel();
	    	    					            	
	    	    					            	// player 1 wins
	        	    	    			   			if (numberOfCorrectMatches >= 3)
	        	    	    			   			{
	        	    	    			   				updatePlayerScoreByEmail(playerOneEmail, playerOneScore + "");
	        	    	    			   			}
	        	    	    			   			else // player 2 wins
	        	    	    			   			{
	        	    	    			   				updatePlayerScoreByEmail(playerTwoEmail, playerTwoScore + "");
	        	    	    			   			}
	    	    					            	
	    	    					            	tieBreakerWinnerDialog.dismiss();
	    	    					            	mainCountDownTimer.cancel();
	    	    					            } 
	    	    				            });
	    	    				              
	    	    				            tieBreakerWinnerDialog.show();
    	    	    			   			
    	    	    			   			// clear answers and question
	    	    				            txtPlayerOneAnswer.setText("");
	    	    				            txtPlayerTwoAnswer.setText("");
	    	    				            txtGuestOneAnswer.setText("");
	    	    				            txtGuestTwoAnswer.setText("");
	    	    				            txtGuestThreeAnswer.setText("");
	    	    				            txtGuestFourAnswer.setText("");
	    	    				            txtGuestFiveAnswer.setText("");
	    	    				            txtGuestSixAnswer.setText("");
	    	    				            txtRoundOneQuestion.setText("");
	    	    				            
	    	    				            changeTurnAnouncment = true;
    	    	    			   			
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
}
