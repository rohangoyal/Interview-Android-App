package com.fructus.interview.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.fructus.interview.R;
import com.fructus.interview.getter_setter.get_sign;

import java.util.ArrayList;

public class Examineradapter extends BaseAdapter {
    ImageView image,rate;
    TextView name,rollno;
    ArrayList<get_sign> info;
    String secondLetter;
    int r;

    public Examineradapter(ArrayList<get_sign> info)
    {
        this.info=info;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        Context cc=parent.getContext();
        LayoutInflater inf=(LayoutInflater)cc.getSystemService(cc.LAYOUT_INFLATER_SERVICE);
        View v=inf.inflate(R.layout.examineradapter,parent,false);

        image=(ImageView)v.findViewById(R.id.imageView3);
        name=(TextView)v.findViewById(R.id.textView18);
        rollno=(TextView)v.findViewById(R.id.textView10);
        rate=(ImageView)v.findViewById(R.id.imageView2);

        name.setText(info.get(i).getName().toUpperCase());
        rollno.setText(info.get(i).getRoll());

        secondLetter = String.valueOf(info.get(i).getName().toUpperCase().charAt(0));
			ColorGenerator generator1 = ColorGenerator.MATERIAL;
			int color1 = generator1.getRandomColor();
			TextDrawable drawable1 = TextDrawable.builder()
					.buildRound(secondLetter, color1);
            image.setImageDrawable(drawable1);
        return v;
    }
}
