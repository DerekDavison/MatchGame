package com.example.matchgame;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

public class Test extends Activity
{
	private CountDownTimer delayToShowRoundOneAnnouncementTimer, roundOneAnnouncementTimer;
	private Boolean firstTimeForRoundOneAnnouncementTimer = true;

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);
        
        delayToShowRoundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) { }

	   		public void onFinish() 
	   		{
	   			System.out.println("1");
	   			loadTimeForRoundOneDialog();
	   			System.out.println("2");
	   			delayToShowRoundOneAnnouncementTimer.cancel();
	   			System.out.println("3");
	   		}
	   	};
	   	delayToShowRoundOneAnnouncementTimer.start();
    }
	
	private void loadTimeForRoundOneDialog()
    {
		System.out.println("");
		
    	final Dialog roundOneAnnouncementDialog = new Dialog(Test.this);
		roundOneAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		System.out.println("");
		
		roundOneAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
		roundOneAnnouncementDialog.setContentView(R.layout.time_for_round_one_dialog);
		roundOneAnnouncementDialog.setCancelable(true); 
        
		System.out.println("");
        roundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
	   	{
	   		public void onTick(long millisUntilFinished) 
	   		{ 
	   			if (firstTimeForRoundOneAnnouncementTimer)
	   			{
	   				System.out.println("");
	   				roundOneAnnouncementDialog.show();
		   			firstTimeForRoundOneAnnouncementTimer = false;
	   			}
	   		}

	   		public void onFinish() 
	   		{
	   			roundOneAnnouncementDialog.dismiss();
	   			roundOneAnnouncementTimer.cancel();
	   		}
	   	};
	   	roundOneAnnouncementTimer.start();
    }
}
