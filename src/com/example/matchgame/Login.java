package com.example.matchgame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Login extends Activity 
{

    @Override
    public void onCreate(Bundle savedInstanceState) 
    { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }
}
