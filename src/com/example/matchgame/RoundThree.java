package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RoundThree extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_three);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round3, menu);
        return true;
    }
}
