package com.fructus.interview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.fructus.interview.Fragment.Signupfragment;

/**
 * Created by rohan on 3/25/2016.
 */
public class Candidate_Activity extends AppCompatActivity
{
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_activity);

        fm=getSupportFragmentManager();
        Fragment signup = new Signupfragment();
        fm.beginTransaction().replace(R.id.frame_layout,signup).commit();
    }
}
