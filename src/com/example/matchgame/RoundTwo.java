package com.example.matchgame;

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

public class RoundTwo extends Activity implements OnClickListener
{
	
	ImageView imgContinue;
	int count = 0;
	TextView hostText;
	Button submit;
	EditText ownText;
	RadioButton select;

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
    	    				//load question
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
    	    		
    	    	
        	}
    	}
    	    			
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round2, menu);
        return true;
    }
}
