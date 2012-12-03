package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RoundTwo extends Activity implements OnClickListener
{
	
	ImageView imgContinue;
	int count = 0;
	TextView hostText;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_two);
        
        imgContinue = (ImageView)findViewById(R.id.imgContinue);
        hostText = (TextView)findViewById(R.id.txtQuestion);
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
    	    				hostText.setText("Now let's get this round started!");
    	    				break;
    	    			case 5:
    	    				//load question
    	    				imgContinue.setVisibility(0);
    	    				break;
    	    		}
    	    		
    	    		break;
    	    	default:
    	    		break;
        	}
    	}
    	    			
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round2, menu);
        return true;
    }
}
