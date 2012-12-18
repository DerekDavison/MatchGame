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

	private String hostAnswer, userAnswer, playerName, question;
	private int total, qId, random;
	private EditText ans;
	private TextView txtHost, txtQuestion;
	private Button btnSubmit;
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> questionByIdAndRound, answerByIdRoundAndQuestionId;
	private CountDownTimer roundThreeAnnouncementTimer, delayToShowRoundThreeAnnouncementTimer;
	private Boolean firstTimeForRoundThreeAnnouncementTimer = true, dialogTimer = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_three);
        
        SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
        playerName = winnerName.getString(StaticData.WINNING_PLAYER_SHARED_PREF, "");
        
        SharedPreferences winnerScore = getSharedPreferences(StaticData.SCORE, 0); 
        total = winnerScore.getInt(StaticData.SCORE, 0);
        
        txtQuestion = (TextView)findViewById(R.id.txtRoundThreeDialog);
        ans = (EditText)findViewById(R.id.answer);
        
        btnSubmit = (Button)findViewById(R.id.btnFinalSubmit);
        
        btnSubmit.setOnClickListener(new View.OnClickListener() {
			
        public void onClick(View v) {  
        	
        		userAnswer = ans.getText().toString();
        		
    			if(userAnswer.equals(hostAnswer))
    			{
    				//winning dialog
    				//add to total
    			}
    			else
    			{
    				//good try dialog
    				//add total to db
    				//remove player active from game
    			}
    			}
    			
        });
    
   

       
		
    }


    
    private void loadRoundThreeIntroDialog()
    {
    	final Dialog roundThreeAnnouncementDialog = new Dialog(RoundThree.this);
		roundThreeAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		roundThreeAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		roundThreeAnnouncementDialog.setContentView(R.layout.round_three_dialog);
		roundThreeAnnouncementDialog.setCancelable(true); 
		
		txtHost = (TextView)roundThreeAnnouncementDialog.findViewById(R.id.txtIntro);
		txtHost.setText("Here we are in the Final Round!\n" + playerName + " is set to go up against...ME! Your lovely host.\n" + "Playing for " + (total * 10) + " points!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(5000, 1000) 
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
    	
    	txtHost = (TextView)roundThreeDialog.findViewById(R.id.txtIntro);
		txtHost.setText("In this round, You will go one on one with me to match my answer. It will be one question and one chance to match my answer. " +
				"If you succeed, you will increase your money tenfold! Otherwise, you will have to leave with your current winnings.");
        
        roundThreeAnnouncementTimer = new CountDownTimer(8000, 1000) 
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
    	
    	txtHost = (TextView)roundThreeStartDialog.findViewById(R.id.txtIntro);
		txtHost.setText("Are you ready? \nHere we go!");
        
        roundThreeAnnouncementTimer = new CountDownTimer(2000, 1000) 
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
	
	   			hostAnswer = getRandomAnswer(192, 183, qId).toString();
	
	   			roundThreeAnnouncementTimer.cancel();
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
    
    
    // need to get working
    
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
