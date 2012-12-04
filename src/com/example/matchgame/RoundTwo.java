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
	
	ImageView imgContinue;
	int count = 0;
	TextView hostText;
	Button submit;
	EditText ownText;
	RadioButton select;
	int random;
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
    	    	case R.id.rdbOther:
    	    		ownText.setVisibility(View.VISIBLE);
    	    		break;
    	    	case R.id.btnSubmit:
    	    		//reveal answers 1 by 1
    	    		//compare selected option to correct answers
    	    		//add winnings to running total
    	    		//move player to final round
    	    	
        	}
    	}
    	    			
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round2, menu);
        return true;
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
}
