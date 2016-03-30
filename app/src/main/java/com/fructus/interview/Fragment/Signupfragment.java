package com.fructus.interview.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fructus.interview.R;
import com.fructus.interview.constant.Urlconstant;
import com.fructus.interview.getter_setter.get_sign;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Signupfragment extends Fragment
{
    SharedPreferences pref_roll;
    SharedPreferences.Editor editor_roll;
    SharedPreferences pref,pref_username;
    SharedPreferences.Editor prefedit;
    EditText mobile,collage,roll;
    TextView account,name,username;
    FragmentManager fm;
    String cname,cusername,cmobile,ccollage,croll,url="";
    ArrayList<get_sign> signup;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.signup,container,false);
        fm=getActivity().getSupportFragmentManager();
        pref_roll=getActivity().getSharedPreferences("pref_roll", 1);
        pref=getActivity().getSharedPreferences("pref", 1);
        pref_username=getActivity().getSharedPreferences("pref_username", 1);

        boolean b=pref.getBoolean("boolean",false);
        if(b)
        {
           homefrg();
        }

        roll=(EditText)v.findViewById(R.id.editText2);
        account=(TextView)v.findViewById(R.id.textView5);
        name=(TextView)v.findViewById(R.id.editText3);
        username=(TextView)v.findViewById(R.id.editText4);
        mobile=(EditText)v.findViewById(R.id.editText6);
        collage=(EditText)v.findViewById(R.id.editText7);

        cname=pref_username.getString("name","");
        cusername=pref_username.getString("email","");
        name.setText(cname);
        username.setText(cusername);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmobile=mobile.getText().toString();
                ccollage=collage.getText().toString();
                croll=roll.getText().toString();

                if(cmobile.length()<1&ccollage.length()<1&croll.length()<1)
                {
                    Toast.makeText(getActivity(),"Please fill all fields",Toast.LENGTH_LONG).show();
                }
                    else
                    {
                        editor_roll = pref_roll.edit();
                        editor_roll.putString("croll", croll);
                        editor_roll.commit();
                        volley();
                    }
                }
        });
        return v;
    }
    public  void homefrg()
    {
        Fragment home = new Candidatehomefragment();
        fm.beginTransaction().replace(R.id.frame_layout, home).commit();
    }
    public void volley()
    {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(true);
        pd.setMessage("Loading Please Wait");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        pd.show();


        RequestQueue queue= Volley.newRequestQueue(getActivity());

        cname=cname.replace(" ","%20");
        cusername=cusername.replace(" ","%20");
        ccollage=ccollage.replace(" ","%20");
        Date d = new Date();
        String currentdate= DateFormat.getDateTimeInstance().format(d);
        url= Urlconstant.Url+"signup.php?"+"c_name="+cname+"&roll_no="+croll+"&college="+ccollage+"&mobile="+cmobile+"&username="+cusername+"&reg_date="+currentdate;
        url=url.replace(" ","%20");
        Log.e("name", url);
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.POST, url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                // TODO Auto-generated method stub

                Log.e("res", arg0.toString());


                try{
                    prefedit=pref.edit();
                    if(arg0.getString("scalar").equals("inserted")==true)
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Successfully Signup", Toast.LENGTH_LONG).show();
                        prefedit.putBoolean("boolean",true);
                        homefrg();
                    }
                   else
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"Not done",Toast.LENGTH_LONG).show();

                    }
                    prefedit.commit();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    pd.dismiss();
                    Log.e("hi", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                // TODO Auto-generated method stub

                pd.dismiss();
                Log.e("error", e.toString());
            }
        });
        queue.add(jsonreq);
    }
}
