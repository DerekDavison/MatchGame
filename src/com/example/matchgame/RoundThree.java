package com.example.matchgame;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RoundThree extends Activity 
{

	private String hostAnswer, userAnswer, playerName, question, playerEmail;
	private int total, qId, random;
	private EditText ans;
	private TextView txtHost, txtQuestion, finalAnswer;
	private Button btnSubmit;
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> questionByIdAndRound, answerByIdRoundAndQuestionId, updatePlayerScoreByEmailNameValuePairs,
			updateUserStateByEmailNameValuePairs, updateUserSignInStateByEmail;
	private CountDownTimer roundThreeAnnouncementTimer, delayToShowRoundThreeAnnouncementTimer;
	private Boolean firstTimeForRoundThreeAnnouncementTimer = true, dialogTimer = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_three);
        
        SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
        playerName = winnerName.getString(StaticData.WINNING_PLAYER_SHARED_PREF, "");
        
        SharedPreferences winnerEmail = getSharedPreferences(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, MODE_PRIVATE); 
        playerEmail = winnerEmail.getString(StaticData.WINNING_PLAYER_EMAIL_SHARED_PREF, "");
        
        SharedPreferences winnerScore = getSharedPreferences(StaticData.SCORE, 0); 
        total = winnerScore.getInt(StaticData.SCORE, 0); 
        
        txtQuestion = (TextView)findViewById(R.id.txtPhrase);
        ans = (EditText)findViewById(R.id.answer);
        finalAnswer = (TextView)findViewById(R.id.txtAnswerFinal);
        
        loadRoundThreeIntroDialog();
        
        btnSubmit = (Button)findViewById(R.id.btnFinalSubmit);
        
        btnSubmit.setOnClickListener(new View.OnClickListener() 
        {
	        public void onClick(View v) 
	        {  
	    		userAnswer = ans.getText().toString();
	    		
				if(userAnswer.equals(hostAnswer))
				{
					loadWinningDialog();
					finalAnswer.setText(hostAnswer);
					total = total * 10;
					updatePlayerScoreByEmail(playerEmail, total + "");
					updateUserSignInStateByEmail = new ArrayList<NameValuePair>();
					
					updateUserSignInStateByEmail.add(new BasicNameValuePair("email", playerEmail));
					updateUserSignInStateByEmail.add(new BasicNameValuePair("signed_in", "0"));
					dbHelper.modifyData(updateUserSignInStateByEmail, StaticData.UPDATE_PLAYER_SIGN_IN_STATUS_BY_EMAIL);
					
					//winning dialog
					//add to total
				}
				else
				{
					loadLosingDialog();
					finalAnswer.setText(hostAnswer);
					updatePlayerScoreByEmail(playerEmail, total + "");
					updateUserSignInStateByEmail = new ArrayList<NameValuePair>();
					
					updateUserSignInStateByEmail.add(new BasicNameValuePair("email", playerEmail));
					updateUserSignInStateByEmail.add(new BasicNameValuePair("signed_in", "0"));
					dbHelper.modifyData(updateUserSignInStateByEmail, StaticData.UPDATE_PLAYER_SIGN_IN_STATUS_BY_EMAIL);
					
					//good try dialog
					//add total to db
					//remove player active from game
				}
	    	}	
        });
    }

	private void updatePlayerScoreByEmail(String playerEmail, String playerScore)
	{
		dbHelper = new DBHelper();
		
		updatePlayerScoreByEmailNameValuePairs = new ArrayList<NameValuePair>();
		updatePlayerScoreByEmailNameValuePairs.add(new BasicNameValuePair("email", playerEmail));
		updatePlayerScoreByEmailNameValuePairs.add(new BasicNameValuePair("score", playerScore));
		
		dbHelper.modifyData(updatePlayerScoreByEmailNameValuePairs, StaticData.UPDATE_PLAYER_SCORE_BY_EMAIL);
	}

    private void loadRoundThreeIntroDialog()
    {
    	final Dialog roundThreeAnnouncementDialog = new Dialog(RoundThree.this);
		roundThreeAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		roundThreeAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		roundThreeAnnouncementDialog.setContentView(R.layout.round_three_dialog);
		roundThreeAnnouncementDialog.setCancelable(true); 
		
		txtHost = (TextView)roundThreeAnnouncementDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("Here we are in the Final Round!\n" + playerName + " is set to go up against...ME! Your lovely host.\n" + "Playing for " + (total * 10) + " points!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(7000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForRoundThreeAnnouncementTimer)
	   			{
	   				roundThreeAnnouncementDialog.show();
		   			firstTimeForRoundThreeAnnouncementTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			firstTimeForRoundThreeAnnouncementTimer = true;
	   		 
	   			roundThreeAnnouncementDialog.dismiss();
	   			roundThreeAnnouncementTimer.cancel();
	   			
	   			loadRulesDialog();
	   		}
	   	};
	   	roundThreeAnnouncementTimer.start();
    }
     
    private void loadRulesDialog() 
    {
    	final Dialog roundThreeDialog = new Dialog(RoundThree.this);
    	roundThreeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundThreeDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT); 
    	roundThreeDialog.setContentView(R.layout.round_three_dialog);
    	roundThreeDialog.setCancelable(true); 
    	
    	txtHost = (TextView)roundThreeDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("In this round, You will go one on one with me to match my answer. It will be one question and one chance to match my answer. " +
				"If you succeed, you will increase your money tenfold! Otherwise, you will have to leave with your current winnings.");
        
        roundThreeAnnouncementTimer = new CountDownTimer(10000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundThreeDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundThreeDialog.dismiss();
	   			roundThreeAnnouncementTimer.cancel();
	   			loadStartDialog();
	   		}
	   	};
	   	roundThreeAnnouncementTimer.start();
    }
    
    private void loadStartDialog()
    {
    	final Dialog roundThreeStartDialog = new Dialog(RoundThree.this);
    	roundThreeStartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundThreeStartDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundThreeStartDialog.setContentView(R.layout.round_three_dialog);
    	roundThreeStartDialog.setCancelable(true); 
    	
    	txtHost = (TextView)roundThreeStartDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("Are you ready? \nHere we go!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundThreeStartDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundThreeStartDialog.dismiss();
	   			generateRandomQuestion();
	   			
	   			switch(qId)
	   			{
	   			case 1:
	   				hostAnswer = getRandomAnswer(197, 193, qId).toString();
	   				break;
	   			case 2:
	   				hostAnswer = getRandomAnswer(202, 198, qId).toString();
	   				break;
	   			case 3:
	   				hostAnswer = getRandomAnswer(207, 203, qId).toString();
	   				break;
	   			case 4:
	   				hostAnswer = getRandomAnswer(212, 208, qId).toString();
	   				break;
	   			case 5:
	   				hostAnswer = getRandomAnswer(217, 213, qId).toString();
	   				break;
	   			}
	
	   			roundThreeAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundThreeAnnouncementTimer.start();
    }
    
    private void loadWinningDialog()
    {
    	final Dialog winningDialog = new Dialog(RoundThree.this);
		winningDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		winningDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		winningDialog.setContentView(R.layout.round_three_dialog);
		winningDialog.setCancelable(true); 
		
		txtHost = (TextView)winningDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("WOW! You matched me exactly! And do you know what that means? You got it! Your winnings are increased by 10. Congratulations!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(7000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForRoundThreeAnnouncementTimer)
	   			{
	   				winningDialog.show();
		   			firstTimeForRoundThreeAnnouncementTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			firstTimeForRoundThreeAnnouncementTimer = true;
	   		
	   			winningDialog.dismiss();
	   			roundThreeAnnouncementTimer.cancel();
	   			loadEndingDialog();
	   		}
	   	}; 
	   	roundThreeAnnouncementTimer.start();
    }
    
    private void loadEndingDialog()
    {
    	final Dialog endingDialog = new Dialog(RoundThree.this);
    	endingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	endingDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	endingDialog.setContentView(R.layout.round_three_dialog);
    	endingDialog.setCancelable(true); 
    	
    	txtHost = (TextView)endingDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("Well that wraps up todays match, " + playerName + " has earned a whopping " + total + ".\nWe look forward to seeing you next time!\nGoodbye!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(10000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				endingDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			endingDialog.dismiss();
	   			roundThreeAnnouncementTimer.cancel();
	   			
	   			Intent continueIntent = new Intent(RoundThree.this, Login.class); 
	   			startActivity(continueIntent);
	   			finish();
	   		}
	   	};
	   	roundThreeAnnouncementTimer.start();   
    }
    
    private void loadLosingDialog()
    {
    	final Dialog losingDialog = new Dialog(RoundThree.this);
    	losingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	losingDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	losingDialog.setContentView(R.layout.round_three_dialog);
    	losingDialog.setCancelable(true); 
    	
    	txtHost = (TextView)losingDialog.findViewById(R.id.txtRoundThreeDialog);
		txtHost.setText("Oh no! Not quite this time. Hopefully next time you will have better luck.");
        
        roundThreeAnnouncementTimer = new CountDownTimer(7000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				losingDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			losingDialog.dismiss();
	   			
	   			roundThreeAnnouncementTimer.cancel();
	   			loadEndingDialog();
	   		}
	   	};
	   	roundThreeAnnouncementTimer.start();
    }
    
    private void generateRandomQuestion()
    {
    	dbHelper = new DBHelper();
    	
		Random r = new Random();
		random = r.nextInt(5 - 1 + 1) + 1;
		
		questionByIdAndRound = new ArrayList<NameValuePair>();
		questionByIdAndRound.add(new BasicNameValuePair("id", random + ""));
    	questionByIdAndRound.add(new BasicNameValuePair("round", StaticData.ROUND_THREE));
    	
    	question = dbHelper.readDBData(StaticData.SELECT_ROUND_THREE_QUESTION_BY_ID_AND_ROUND, questionByIdAndRound, "question").get(0).toString();
    	qId = random;
    	
    	txtQuestion.setText(question);
    }
 
    private String getRandomAnswer(int max, int min, int questionId)
	{
		dbHelper = new DBHelper();
        answerByIdRoundAndQuestionId = new ArrayList<NameValuePair>(); 

        Random rand = new Random();
    	int randomNum = rand.nextInt(max - min + 1) + min;
    	
        answerByIdRoundAndQuestionId.add(new BasicNameValuePair("id", randomNum + ""));
        answerByIdRoundAndQuestionId.add(new BasicNameValuePair("question_id", questionId + ""));
        answerByIdRoundAndQuestionId.add(new BasicNameValuePair("round", StaticData.ROUND_THREE));
        
        return dbHelper.readDBData(StaticData.SELECT_ANSWER_BY_ID_QUESTION_ID_AND_ROUND, answerByIdRoundAndQuestionId, "answer").get(0).toString();
	}    
}
