package com.fructus.interview.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fructus.interview.R;

public class Candidatehomefragment extends Fragment
{
    TextView profile,result;
    FragmentManager fm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.candhome,container,false);
        fm=getActivity().getSupportFragmentManager();

        profile=(TextView)v.findViewById(R.id.textView7);
        result=(TextView)v.findViewById(R.id.textView8);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profile = new Profilefragment();
                fm.beginTransaction().replace(R.id.frame_layout, profile).commit();
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment result = new Resultfragment();
                fm.beginTransaction().replace(R.id.frame_layout, result).commit();
            }
        });
        return v;
    }

}
