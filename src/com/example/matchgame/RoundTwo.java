package com.example.matchgame;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RoundTwo extends Activity implements OnClickListener
{

	private TextView qText, txtIntro;
	private Button submit;
	private EditText ownText;
	private RadioButton select, guestOne, guestTwo, guestThree;
	private String choice, ans1, ans2, ans3, playerName, question;
	private int random, prize, roundPrize, total, count = 0, qId;
	private RadioGroup selection;
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> questionByIdAndRound, answerByIdRoundAndQuestionId;
	private CountDownTimer roundTwoAnnouncementTimer, loadingQuestionDialogTimer, delayToShowRoundTwoAnnouncementTimer;
	private Boolean firstTimeForRoundTwoAnnouncementTimer = true, dialogTimer = true, 
			firstTimeForloadingQuestionDialogTimer = true;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.round_two);
        
       
        qText = (TextView)findViewById(R.id.txtQuestion);
        submit = (Button)findViewById(R.id.btnSubmit);
        ownText = (EditText)findViewById(R.id.response);
        select = (RadioButton)findViewById(R.id.rdbOther);
        guestOne = (RadioButton)findViewById(R.id.rdbGuest1);
        guestTwo = (RadioButton)findViewById(R.id.rdbGuest2);
        guestThree = (RadioButton)findViewById(R.id.rdbGuest3);
        selection = (RadioGroup)findViewById(R.id.radioGroup1);
        
        
        SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
        playerName = winnerName.getString(StaticData.WINNING_PLAYER_SHARED_PREF, "");
       
        
        delayToShowRoundTwoAnnouncementTimer = new CountDownTimer(2000, 1000) 
		   	{
		   		public void onTick(long millisUntilFinished) { }

		   		public void onFinish() 
		   		{
		   			loadRoundTwoIntroDialog();
		   			delayToShowRoundTwoAnnouncementTimer.cancel();
		   		}
		   	};
		   	delayToShowRoundTwoAnnouncementTimer.start();
        
         selection.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener() {

            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.rdbOther:
                        ownText.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rdbGuest1:
                        ownText.setVisibility(View.GONE);                    
                        break;
                    case R.id.rdbGuest2:
                        ownText.setVisibility(View.GONE);                    
                        break;
                    case R.id.rdbGuest3:
                        ownText.setVisibility(View.GONE);                    
                        break;
                }
            }
            });
        
    }
    
   
        
    public void onClick(View v)
	{
    	
    	
		switch(v.getId()) 
    	{  
	    	
	    	
	    	case R.id.btnSubmit:
	    	
				if(select.isChecked())
	    		{
	    			
	    			choice = ownText.getText().toString();
	    			ownText.setText(choice);
	    			
	    			
	    			determineScore(total);
	    			//compare choice to $100 $200 and $500 answers grabbed from database
	    		}
	    		else if(guestOne.isChecked())
	    		{
	    			
	    			choice = guestOne.getText().toString();
	    			ownText.setText(choice);
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guestTwo.isChecked())
	    		{
	    			
	    			choice = guestTwo.getText().toString();
	    			ownText.setText(choice);
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guestThree.isChecked())
	    		{
	    			
	    			choice = guestThree.getText().toString();
	    			ownText.setText(choice);
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		
	    		//reveal answers 1 by 1
	    		//add winnings to running total
	    		//move player to final round
    	}
	}
    
    private void loadRoundTwoIntroDialog()
    {
    	final Dialog roundTwoAnnouncementDialog = new Dialog(RoundTwo.this);
		roundTwoAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
        
		
		roundTwoAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		roundTwoAnnouncementDialog.setContentView(R.layout.round_two_intro);
		roundTwoAnnouncementDialog.setCancelable(true); 
		
		txtIntro = (TextView)roundTwoAnnouncementDialog.findViewById(R.id.txtIntro);
		txtIntro.setText("Hello and Welcome Back! \nMoving on today we have the lovely \n" + playerName + ".");
        
        roundTwoAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForRoundTwoAnnouncementTimer)
	   			{
	   				roundTwoAnnouncementDialog.show();
		   			firstTimeForRoundTwoAnnouncementTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			firstTimeForRoundTwoAnnouncementTimer = true;
	   		
	   			roundTwoAnnouncementDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			
	   			loadRulesDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadRulesDialog()
    {
    	final Dialog roundTwoDialog = new Dialog(RoundTwo.this);
    	roundTwoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoDialog.setContentView(R.layout.round_two_rules);
    	roundTwoDialog.setCancelable(true); 
        
        roundTwoAnnouncementTimer = new CountDownTimer(10000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadStartDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadStartDialog()
    {
    	final Dialog roundTwoStartDialog = new Dialog(RoundTwo.this);
    	roundTwoStartDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoStartDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoStartDialog.setContentView(R.layout.round_two_start);
    	roundTwoStartDialog.setCancelable(true); 
        
        roundTwoAnnouncementTimer = new CountDownTimer(3000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoStartDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoStartDialog.dismiss();
	   			generateRandomQuestion();
	   			
	   			switch(qId)
	   			{
	   			case 1:
	   				guestOne.setText(getRandomAnswer(152, 143, qId));
	   				guestTwo.setText(getRandomAnswer(152, 143, qId));
	   				guestThree.setText(getRandomAnswer(152, 143, qId));
	   				break;
	   			case 2:
	   				guestOne.setText(getRandomAnswer(162, 153, qId));
	   				guestTwo.setText(getRandomAnswer(162, 153, qId));
	   				guestThree.setText(getRandomAnswer(162, 153, qId));
	   				break;
	   			case 3:
	   				guestOne.setText(getRandomAnswer(172, 163, qId));
	   				guestTwo.setText(getRandomAnswer(172, 163, qId));
	   				guestThree.setText(getRandomAnswer(172, 163, qId));
	   				break;
	   			case 4:
	   				guestOne.setText(getRandomAnswer(182, 173, qId));
	   				guestTwo.setText(getRandomAnswer(182, 173, qId));
	   				guestThree.setText(getRandomAnswer(182, 173, qId));
	   				break;
	   			case 5:
	   				guestOne.setText(getRandomAnswer(192, 183, qId));
	   				guestTwo.setText(getRandomAnswer(192, 183, qId));
	   				guestThree.setText(getRandomAnswer(192, 183, qId));
	   				break;
	   			}
	   			
	   			
	   			
	   			
	   			
	   			roundTwoAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
   /* private void runLoadingQuestionDialog()
    {
    	loadingQuestionDialogTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForloadingQuestionDialogTimer)
	   			{
	   				loadingQuestionDialog = ProgressDialog.show(RoundTwo.this, "","Loading your question...", true);
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
    }*/
    
        
    private void generateRandomQuestion()
    {
    	dbHelper = new DBHelper();
    	
		Random r = new Random();
		random = r.nextInt(5 - 1 + 1) + 1;
		
		questionByIdAndRound = new ArrayList<NameValuePair>();
		questionByIdAndRound.add(new BasicNameValuePair("id", random + ""));
    	questionByIdAndRound.add(new BasicNameValuePair("round", StaticData.ROUND_TWO));
    	
    	question = dbHelper.readDBData(StaticData.SELECT_QUESTION_BY_ID_AND_ROUND, questionByIdAndRound, "question").get(0).toString();
    	qId = random;
   
    	qText.setText(question);
    	
		
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
        answerByIdRoundAndQuestionId.add(new BasicNameValuePair("round", StaticData.ROUND_TWO));
        

        return dbHelper.readDBData(StaticData.SELECT_ANSWER_BY_ID_QUESTION_ID_AND_ROUND, answerByIdRoundAndQuestionId, "answer").get(0).toString();
	}
	    
    public void determineScore(int total)
    {
	    if(choice == ans1)
		{
			roundPrize = 100;
			total = total + roundPrize;
			//fade away answer 1 to reveal prize
			//host announces that is your winnings but lets see other answers.
			//display others one by one.
			
			
		}
		if(choice == ans2)
		{
			roundPrize = 250;
			total = total + roundPrize;
			//fade away answer 1 that is not your answer
			//reveal answer 2 that is your prize
			//display final answer with disappointment
			
		}
		if(choice == ans3)
		{
			roundPrize = 500;
			total = total + roundPrize;
			//reveal first 2 answers with excitement
			//reveal final answer with anticipation
			//big celebration
		}
		else
		{
			roundPrize = 10;
			total = total + roundPrize;
			//all answers revealed and no winner
			//host feels sad and gives the player 10$ and moves him/her to final round
    	}
	}
}
