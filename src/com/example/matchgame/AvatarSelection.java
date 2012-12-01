package com.example.matchgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AvatarSelection extends Activity implements OnClickListener
{
	private Button btnPlayerOne, btnPlayerTwo, btnPlayerThree, btnPlayerFour, btnPlayerFive, btnPlayerSix, btnPlayerSeven, btnPlayerEight, 
		btnPlayerNine, btnPlayerTen;
	private TextView txtAvatarSelection;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_selection);
        
        btnPlayerOne = (Button)findViewById(R.id.btnPlayerOne);
        btnPlayerTwo = (Button)findViewById(R.id.btnPlayerTwo);
        btnPlayerThree = (Button)findViewById(R.id.btnPlayerThree);
        btnPlayerFour = (Button)findViewById(R.id.btnPlayerFour);
        btnPlayerFive = (Button)findViewById(R.id.btnPlayerFive);
        btnPlayerSix = (Button)findViewById(R.id.btnPlayerSix);
        btnPlayerSeven = (Button)findViewById(R.id.btnPlayerSeven);
        btnPlayerEight = (Button)findViewById(R.id.btnPlayerEight);
        btnPlayerNine = (Button)findViewById(R.id.btnPlayerNine);
        btnPlayerTen = (Button)findViewById(R.id.btnPlayerTen); 
        
        txtAvatarSelection = (TextView)findViewById(R.id.txtAvatarSelection); 
        
        btnPlayerOne.setOnClickListener(this);
        btnPlayerTwo.setOnClickListener(this);
        btnPlayerThree.setOnClickListener(this);
        btnPlayerFour.setOnClickListener(this);
        btnPlayerFive.setOnClickListener(this);
        btnPlayerSix.setOnClickListener(this);
        btnPlayerSeven.setOnClickListener(this);
        btnPlayerEight.setOnClickListener(this);
        btnPlayerNine.setOnClickListener(this);
        btnPlayerTen.setOnClickListener(this);
    }
	
	public void onClick(View v)
	{ 
		switch(v.getId()) 
    	{   
	    	case R.id.btnPlayerOne: 
	    		
	    		btnPlayerOne.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
	    		break; 
	    	
	    	case R.id.btnPlayerTwo:
	    		
	    		btnPlayerTwo.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerThree:
	    		
	    		btnPlayerThree.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerFour:   
	    		
	    		btnPlayerFour.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerFive:
	    		
	    		btnPlayerFive.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerSix:  
	    		
	    		btnPlayerSix.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerSeven: 
	    		
	    		btnPlayerSeven.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerEight:  
	    		
	    		btnPlayerEight.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerNine: 
	    		
	    		btnPlayerNine.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
		    	
	    	case R.id.btnPlayerTen:  
	    		
	    		btnPlayerTen.setEnabled(false);
	    		txtAvatarSelection.setText("Player 2 - Choose an Avatar");
	    		
		    	break;
	    	
	    	default:
		    	throw new RuntimeException("Unknow button ID"); 
	    }
	}
}
