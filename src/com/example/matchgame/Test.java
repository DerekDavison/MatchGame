package com.example.matchgame;

import java.util.ArrayList;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;

public class Test extends Activity
{
//	private CountDownTimer delayToShowRoundOneAnnouncementTimer, roundOneAnnouncementTimer;
//	private Boolean firstTimeForRoundOneAnnouncementTimer = true;
	
	private ArrayList<NameValuePair> answerByIdRoundAndQuestionIdNameValuePair;
	private DBHelper dbHelper;

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {

    }
	
	private String getRandomAnswer()
	{
		dbHelper = new DBHelper();
        answerByIdRoundAndQuestionIdNameValuePair = new ArrayList<NameValuePair>(); 

        Random rand = new Random();
    	int randomNum = rand.nextInt(5 - 3 + 1) + 3;
		
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("id", randomNum + ""));
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("question_id", "1"));
        answerByIdRoundAndQuestionIdNameValuePair.add(new BasicNameValuePair("round", StaticData.ROUND_ONE));
        
        return dbHelper.readDBData(StaticData.SELECT_ANSWER_BY_ID_QUESTION_ID_AND_ROUND, answerByIdRoundAndQuestionIdNameValuePair, "answer").get(0).toString();
	}
	
//	private void loadTimeForRoundOneDialog()
//    {
//		System.out.println("");
//		
//    	final Dialog roundOneAnnouncementDialog = new Dialog(Test.this);
//		roundOneAnnouncementDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        
//		System.out.println("");
//		
//		roundOneAnnouncementDialog.requestWindowFeature(WindowManager.LayoutParams.WRAP_CONTENT);
//		roundOneAnnouncementDialog.setContentView(R.layout.time_for_round_one_dialog);
//		roundOneAnnouncementDialog.setCancelable(true); 
//        
//		System.out.println("");
//        roundOneAnnouncementTimer = new CountDownTimer(4000, 1000) 
//	   	{
//	   		public void onTick(long millisUntilFinished) 
//	   		{ 
//	   			if (firstTimeForRoundOneAnnouncementTimer)
//	   			{
//	   				System.out.println("");
//	   				roundOneAnnouncementDialog.show();
//		   			firstTimeForRoundOneAnnouncementTimer = false;
//	   			}
//	   		}
//
//	   		public void onFinish() 
//	   		{
//	   			roundOneAnnouncementDialog.dismiss();
//	   			roundOneAnnouncementTimer.cancel();
//	   		}
//	   	};
//	   	roundOneAnnouncementTimer.start();
//    }
}
