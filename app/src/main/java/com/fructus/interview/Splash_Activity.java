package com.fructus.interview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by rohan on 3/26/2016.
 */
public class Splash_Activity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Intent in=new Intent(Splash_Activity.this,MainActivity.class);
                startActivity(in);
                finish();
            }

        }, 2000);
    }
}
