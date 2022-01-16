package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread LogoThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    int waited = 0;
                    while (waited < 2800)
                    {
                        sleep(50);
                        waited += 100;
                    }
                } catch (InterruptedException e)
                {
                    // do nothing
                } finally
                {
                    finish();
                    Intent i = new Intent(Logo.this,Registration.class);
                    startActivity(i);
                }
            }
        };
        LogoThread.start();
    }
}