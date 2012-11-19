package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Round2 extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.round2, menu);
        return true;
    }
}
