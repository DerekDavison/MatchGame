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
	private ImageView imgContinue;
	private TextView hostText;
	private Button submit;
	private EditText ownText;
	private RadioButton select, guest1, guest2, guest3;
	private String choice, ans1, ans2, ans3;
	private int random, prize, roundPrize, total, count = 0;
	private RadioGroup selection;
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> questionByIdAndRound;
	private CountDownTimer roundOneAnnouncementTimer, loadingQuestionDialogTimer;
	private Boolean firstTimeForRoundOneAnnouncementTimer = true, firstTimeForPlayerTurnDialogTimer = true, 
			firstTimeForloadingQuestionDialogTimer = true;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.round_two);
        
        imgContinue = (ImageView)findViewById(R.id.imgContinue);
        hostText = (TextView)findViewById(R.id.txtQuestion);
        submit = (Button)findViewById(R.id.btnSubmit);
        ownText = (EditText)findViewById(R.id.response);
        select = (RadioButton)findViewById(R.id.rdbOther);
        guest1 = (RadioButton)findViewById(R.id.rdbGuest1);
        guest2 = (RadioButton)findViewById(R.id.rdbGuest2);
        guest3 = (RadioButton)findViewById(R.id.rdbGuest3);
        selection = (RadioGroup)findViewById(R.id.radioGroup1);
        
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
	    	case R.id.imgContinue:
	    		
	    		count++;
	    		switch(count)
	    		{
	    			case 1:
	    				//Replace "Winner of round 1" once player class created.
	    				hostText.setText("Moving on today we have the lovely" + " " + "Winner of round 1" + ".");
	    				break;
	    			case 2:
	    				hostText.setText("In this round, our player will get responses from three of our guests.");
	    				break;
	    			case 3:
	    				hostText.setText("Our player may choose to take one of their answers or select their own.");
	    				break;
	    			case 4:
	    				hostText.setText("If the selected answer matches one of the corresponding prizes, our champion will have that prize added to their total.");
	    				break;
	    			case 5:
	    				hostText.setText("Now let's get this round started!");
	    				break;
	    			case 6:
	    				generateRandomQuestion();
	    				imgContinue.setVisibility(View.INVISIBLE);
	    				break;	
	    			default:
	    				break;
	    		}
	    		
	    		break;
	    	
	    	case R.id.btnSubmit:
	    	
				if(select.isChecked())
	    		{
	    			
	    			choice = ownText.getText().toString();
	    			ownText.setText(choice);
	    			System.out.println(choice);
	    			
	    			determineScore(total);
	    			//compare choice to $100 $200 and $500 answers grabbed from database
	    		}
	    		else if(guest1.isChecked())
	    		{
	    			
	    			choice = guest1.getText().toString();
	    			ownText.setText(choice);
	    			System.out.println(choice);
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guest2.isChecked())
	    		{
	    			
	    			choice = guest2.getText().toString();
	    			ownText.setText(choice);
	    			System.out.println(choice);
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guest3.isChecked())
	    		{
	    			
	    			choice = guest3.getText().toString();
	    			ownText.setText(choice);
	    			System.out.println(choice);
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		
	    		//reveal answers 1 by 1
	    		//add winnings to running total
	    		//move player to final round
    	}
	}
    
    private void loadTimeForRoundOneDialog()
    {
    	final Dialog roundOneAnnouncementDialog = new Dialog(RoundTwo.this);
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
    	final Dialog playerTurnDialog = new Dialog(RoundTwo.this);
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
	   			//runLoadingQuestionDialog();
	   			roundOneAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundOneAnnouncementTimer.start();
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
		
		questionByIdAndRound = new ArrayList<NameValuePair>();
		
		
		Random r = new Random();
		random = (r.nextInt(6 - 1 + 1) + 1);
		
		questionByIdAndRound.add(new BasicNameValuePair("id", random + ""));

		for (String s : dbHelper.readDBData("SelectQuestionByIdAndRound.php", questionByIdAndRound, "question"))
		{
			hostText.setText(s);
		}
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
