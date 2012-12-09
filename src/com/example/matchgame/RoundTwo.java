package com.example.matchgame;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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
	private DBHelper dbHelper;
	private ArrayList<NameValuePair> questionByIdAndRound;

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
	    			ownText.setVisibility(View.VISIBLE);
	    			choice = ownText.getText().toString();
	    			
	    			determineScore(total);
	    			//compare choice to $100 $200 and $500 answers grabbed from database
	    		}
	    		else if(guest1.isChecked())
	    		{
	    			ownText.setVisibility(View.INVISIBLE);
	    			choice = guest1.getText().toString();
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guest2.isChecked())
	    		{
	    			ownText.setVisibility(View.INVISIBLE);
	    			choice = guest2.getText().toString();
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		else if(guest3.isChecked())
	    		{
	    			ownText.setVisibility(View.INVISIBLE);
	    			choice = guest3.getText().toString();
	    			
	    			determineScore(total);//compare choice to $100 $200 and $500 answers grabbed from database
	    			
	    		}
	    		
	    		//reveal answers 1 by 1
	    		//add winnings to running total
	    		//move player to final round
    	}
	}
        
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
