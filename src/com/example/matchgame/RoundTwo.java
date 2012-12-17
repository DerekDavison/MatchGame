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

public class RoundTwo extends Activity
{

	private TextView qText, txtIntro, txtFirst, txtSecond, txtThird;
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
        txtFirst = (TextView)findViewById(R.id.txt100);
        txtSecond = (TextView)findViewById(R.id.txt250);
        txtThird = (TextView)findViewById(R.id.txt500);
        
        
        SharedPreferences winnerName = getSharedPreferences(StaticData.WINNING_PLAYER_SHARED_PREF, MODE_PRIVATE); 
        playerName = winnerName.getString(StaticData.WINNING_PLAYER_SHARED_PREF, "");
        
        SharedPreferences winnerScore = getSharedPreferences(StaticData.SCORE, 0); 
        total = winnerScore.getInt(StaticData.SCORE, 0);
       
        submit.setOnClickListener(new View.OnClickListener() {
			
        public void onClick(View v) {    				
    			
    			switch(selection.getCheckedRadioButtonId())
    			{
    			case R.id.rdbOther:
    				choice = ownText.getText().toString();
    				System.out.print(choice);
	    			determineScore();
	    			loadRoundTwoEndDialog();
    				break;
    			case R.id.rdbGuest1:
    				choice = guestOne.getText().toString();
    				System.out.println(choice);
	    			determineScore();//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
	    			break;
    			case R.id.rdbGuest2:
    				choice = guestTwo.getText().toString();
	    			System.out.println(choice);
	    			determineScore();//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
    				break;
    			case R.id.rdbGuest3:
    				choice = guestThree.getText().toString();
	    			System.out.println();
	    			determineScore();//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
    				break;
    			}
        }
        });
    
    	
        
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
                        ownText.setText("");
                        choice = guestThree.getText().toString();
                        break;
                    case R.id.rdbGuest2:
                        ownText.setVisibility(View.GONE); 
                        ownText.setText("");
                        choice = guestThree.getText().toString();
                        break;
                    case R.id.rdbGuest3:
                        ownText.setVisibility(View.GONE);
                        ownText.setText("");
                        choice = guestThree.getText().toString();
                        break;
                }
            }
            });
        
    }
    
    
    			/*	
    		        if(select.isChecked())
    	    		{
    	    			
    	    			choice = ownText.getText().toString();
    	    			
    	    			
    	    			determineScore(total);
    	    			loadRoundTwoEndDialog();
    	    			//compare choice to $100 $200 and $500 answers grabbed from database
    	    		}
    	    		else if(guestOne.isChecked())
    	    		{
    	    			
    	    			choice = guestOne.getText().toString();
    	    			
    	    			
    	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
    	    			loadRoundTwoEndDialog();
    	    		}
    	    		else if(guestTwo.isChecked())
    	    		{
    	    			
    	    			choice = guestTwo.getText().toString();
    	    			
    	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
    	    			loadRoundTwoEndDialog();
    	    		}
    	    		else if(guestThree.isChecked())
    	    		{
    	    			
    	    			choice = guestThree.getText().toString();
    	    			
    	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
    	    			loadRoundTwoEndDialog();
    	    		}*/
    
    
        
    /*  public void onClick(View v)
	{
    	
    	
		switch(v.getId()) 
    	{  
	    	
	    	case R.id.btnSubmit:
	    	
				if(select.isChecked())
	    		{
	    			
	    			choice = ownText.getText().toString();
	    			
	    			
	    			determineScore(total);
	    			loadRoundTwoEndDialog();
	    			//compare choice to $100 $200 and $500 answers grabbed from database
	    		}
	    		else if(guestOne.isChecked())
	    		{
	    			
	    			choice = guestOne.getText().toString();
	    			
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
	    		}
	    		else if(guestTwo.isChecked())
	    		{
	    			
	    			choice = guestTwo.getText().toString();
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
	    		}
	    		else if(guestThree.isChecked())
	    		{
	    			
	    			choice = guestThree.getText().toString();
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			loadRoundTwoEndDialog();
	    		}
	    		break;
	    	default:
	    			break;
	    		//reveal answers 1 by 1
	    		//add winnings to running total
	    		//move player to final round
    	}
	
    };*/
    
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
        
        roundTwoAnnouncementTimer = new CountDownTimer(2000, 1000) 
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
	   				ans1 = getRandomAnswer(152, 143, qId).toString();
	   				ans2 = getRandomAnswer(152, 143, qId).toString();
	   				ans3 = getRandomAnswer(152, 143, qId).toString();
	   				break;
	   			case 2:
	   				guestOne.setText(getRandomAnswer(162, 153, qId));
	   				guestTwo.setText(getRandomAnswer(162, 153, qId));
	   				guestThree.setText(getRandomAnswer(162, 153, qId));
	   				ans1 = getRandomAnswer(162, 153, qId).toString();
	   				ans2 = getRandomAnswer(162, 153, qId).toString();
	   				ans3 = getRandomAnswer(162, 153, qId).toString();
	   				break;
	   			case 3:
	   				guestOne.setText(getRandomAnswer(172, 163, qId));
	   				guestTwo.setText(getRandomAnswer(172, 163, qId));
	   				guestThree.setText(getRandomAnswer(172, 163, qId));
	   				ans1 = getRandomAnswer(172, 163, qId).toString();
	   				ans2 = getRandomAnswer(172, 163, qId).toString();
	   				ans3 = getRandomAnswer(172, 163, qId).toString();
	   				break;
	   			case 4:
	   				guestOne.setText(getRandomAnswer(182, 173, qId));
	   				guestTwo.setText(getRandomAnswer(182, 173, qId));
	   				guestThree.setText(getRandomAnswer(182, 173, qId));
	   				ans1 = getRandomAnswer(182, 173, qId).toString();
	   				ans2 = getRandomAnswer(182, 173, qId).toString();
	   				ans3 = getRandomAnswer(182, 173, qId).toString();
	   				break;
	   			case 5:
	   				guestOne.setText(getRandomAnswer(192, 183, qId));
	   				guestTwo.setText(getRandomAnswer(192, 183, qId));
	   				guestThree.setText(getRandomAnswer(192, 183, qId));
	   				ans1 = getRandomAnswer(192, 183, qId).toString();
	   				ans2 = getRandomAnswer(192, 183, qId).toString();
	   				ans3 = getRandomAnswer(192, 183, qId).toString();
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
    	
    	question = dbHelper.readDBData(StaticData.SELECT_ROUND_TWO_QUESTION_BY_ID_AND_ROUND, questionByIdAndRound, "question").get(0).toString();
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
	    
    public void determineScore()
    {
    	System.out.println(choice);
    	System.out.println(ans1);
    	System.out.println(ans2);
    	System.out.println(ans3);
	    if(choice.equals(ans1))
		{
	    	System.out.println(choice);
			roundPrize = 100;
			//total = total + roundPrize;
			loadAnswersDialog();
			loadAnswersFDialog();
			//fade away answer 1 to reveal prize
			//host announces that is your winnings but lets see other answers.
			//display others one by one.
			
			
		}
	    else if(choice.equals(ans2))
		{
	    	System.out.println(choice);
			roundPrize = 250;
			//total = total + roundPrize;
			loadAnswersDialog();
			loadAnswersSDialog();
			//fade away answer 1 that is not your answer
			//reveal answer 2 that is your prize
			//display final answer with disappointment
			
		}
	    else if(choice.equals(ans3))
		{
	    	System.out.println(choice);
			roundPrize = 500;
			//total = total + roundPrize;
			loadAnswersDialog();
			loadAnswersTDialog();
			//reveal first 2 answers with excitement
			//reveal final answer with anticipation
			//big celebration
		}
		else
		{
			System.out.println(choice);
			roundPrize = 10;
			//total = total + roundPrize;
			loadAnswersDialog();
			loadAnswersLoseDialog();
			//all answers revealed and no winner
			//host feels sad and gives the player 10$ and moves him/her to final round
    	}
	}
    
    private void loadAnswersDialog()
    {
    	System.out.println(choice);
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
        
        roundTwoAnnouncementTimer = new CountDownTimer(3000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersFDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 100 point answer is...\n" + ans1);
    	txtFirst.setText(ans1);
        
        roundTwoAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersFTwoDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersFTwoDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("Not bad, not bad. At least we can move on to the final round!");
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersFThreeDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersFThreeDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("Just for the fun of it, lets look at those other answers.");
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			txtSecond.setText(ans2);
	   			txtThird.setText(ans3);
	   			
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersSDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 100 point answer is...\n" + ans1 + "\n Not quite... Let`s see if it`s the 250 point answer.");
    	txtFirst.setText(ans1);
        
        roundTwoAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersSTwoDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersSTwoDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 250 point answer is...\n" + ans2 +"\nNow we are making some money!");
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersSThreeDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersSThreeDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("Let`s look at what the 500 point answer was.");
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			txtThird.setText(ans3);
	   			
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersTDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 100 point answer is...\n" + ans1 + "\n Not quite... Let`s see if it`s the 250 point answer.");
    	txtFirst.setText(ans1);
        
        roundTwoAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersTTwoDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersTTwoDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 250 point answer is...\n" + ans2 +"\nOne last chance! All or Nothing!");
        txtSecond.setText(ans2);
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersTThreeDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersTThreeDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 500 point answer is...\n" + ans3 + "\n Wow! That`s what I am talking about! You truely are a pro!");
        txtThird.setText(ans3);
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			
	   			
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersLoseDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 100 point answer is...\n" + ans1 + "\n Not quite... Let`s see if it`s the 250 point answer.");
    	txtFirst.setText(ans1);
        
        roundTwoAnnouncementTimer = new CountDownTimer(5000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersLoseTwoDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersLoseTwoDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 250 point answer is...\n" + ans2 +"\nOne last chance! All or Nothing!");
        txtSecond.setText(ans2);
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			loadAnswersLoseThreeDialog();
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    
    private void loadAnswersLoseThreeDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("The 500 point answer is...\n" + ans3 + "\n Oh no! What a shame... \nFor being such a great sport, we will give you 10 points to move to the final round.");
        txtThird.setText(ans3);
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			
	   			
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }
    private void loadRoundTwoEndDialog()
    {
    	final Dialog roundTwoAnswersDialog = new Dialog(RoundTwo.this);
    	roundTwoAnswersDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
    	roundTwoAnswersDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
    	roundTwoAnswersDialog.setContentView(R.layout.round_two_answers);
    	roundTwoAnswersDialog.setCancelable(true); 
    	
    	TextView answers = (TextView)findViewById(R.id.txtAnswerDialog);
    	answers.setText("Time to move onto the final round where we will go on to see if we can multiply your winnings by 10.");
        
        roundTwoAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (dialogTimer)
	   			{
	   				roundTwoAnswersDialog.show();
	   				dialogTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			dialogTimer = true;
	   			roundTwoAnswersDialog.dismiss();
	   			roundTwoAnnouncementTimer.cancel();
	   			
	   			
	   		}
	   	};
	   	roundTwoAnnouncementTimer.start();
    }

}
