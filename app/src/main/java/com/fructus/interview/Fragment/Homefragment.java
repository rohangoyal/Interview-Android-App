package com.fructus.interview.Fragment;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fructus.interview.Googlesignin;
import com.fructus.interview.R;


public class Homefragment extends Fragment
{
    SharedPreferences pref_choose;
    SharedPreferences.Editor editor_choose;
    TextView candidate,examiner;
    FragmentManager fm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, null);
        fm=getActivity().getSupportFragmentManager();

        candidate=(TextView)v.findViewById(R.id.textView7);
        examiner=(TextView)v.findViewById(R.id.textView8);

        pref_choose=getActivity().getSharedPreferences("pref_choose",1);

        candidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor_choose=pref_choose.edit();
                editor_choose.putString("role", "candidate");
                editor_choose.putBoolean("boolean",true);
                editor_choose.commit();
                Intent in=new Intent(getActivity(), Googlesignin.class);
                startActivity(in);
                getActivity().finish();
            }
        });
        examiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor_choose = pref_choose.edit();
                editor_choose.putString("role", "interviewer");
                editor_choose.putBoolean("boolean", true);
                editor_choose.commit();
                examloginfrg();
            }
        });
        return v;
    }

    public  void examloginfrg()
    {
        Fragment login = new Examlogin();
        fm.beginTransaction().replace(R.id.frame_layout, login).commit();
    }

}
