package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameOver extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.game_over, menu);
        return true;
    }
}
