package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class RoundOne extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_one);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round1, menu);
        return true;
    }
}
