package com.example.matchgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        
        Thread timerThread = new Thread() 
        {
            @Override
            public void run() 
            {
                try 
                {
					sleep(1000);
				} 
                catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
                runOnUiThread(new Runnable() 
                {
                    public void run() 
                    {
                    	startNewIntent();
                    }
                });
            }
        };
        timerThread.start();
    }
	
	private void startNewIntent()
	{
		Intent login = new Intent(SplashScreen.this, Login.class);
    	SplashScreen.this.startActivity(login);
    	overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    	finish();
	}
}
