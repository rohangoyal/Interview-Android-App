package com.fructus.interview;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.fructus.interview.Fragment.Candidatehomefragment;
import com.fructus.interview.Fragment.Homefragment;
import com.fructus.interview.Fragment.Searchfragment;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref_choose;
    FragmentManager fm;
    String option;
    boolean b;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Interview");
        toolbar.setVisibility(View.GONE);

       fm=getSupportFragmentManager();
        pref_choose=getSharedPreferences("pref_choose", 1);
        b=pref_choose.getBoolean("boolean",false);
        option=pref_choose.getString("role","");

        if(option.equals("interviewer")&b)
        {
            Fragment search = new Searchfragment();
            fm.beginTransaction().replace(R.id.frame_layout, search).commit();
        }
        else if(option.equals("candidate")&b){
            Fragment home = new Candidatehomefragment();
            fm.beginTransaction().replace(R.id.frame_layout, home).commit();
        }
        else
        {
            Fragment home = new Homefragment();
            fm.beginTransaction().replace(R.id.frame_layout, home).commit();
        }
    }
}
