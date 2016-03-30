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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Examlogin extends Fragment
{
    TextView login;
   FragmentManager fm;
    EditText user,pass;
    String euser,epass,url="";
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;
    SharedPreferences pref_id;
    SharedPreferences.Editor editor_id ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.examinlogin,container,false);
        fm=getActivity().getSupportFragmentManager();

        pref_id=getActivity().getSharedPreferences("pref_id", 1);
        pref=getActivity().getSharedPreferences("pref",1);

        boolean b=pref.getBoolean("boolean",false);
        if(b)
        {
            profilefrg();
        }
        user=(EditText)v.findViewById(R.id.editText);
        pass=(EditText)v.findViewById(R.id.editText2);
        login=(TextView)v.findViewById(R.id.textView);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                euser = user.getText().toString();
                epass = pass.getText().toString();
                if (euser.length() < 1 & epass.length() < 1) {
                    Toast.makeText(getActivity(), "Please enter correct Username and Password", Toast.LENGTH_LONG).show();
                } else {

                    volley();
                }

            }
        });
        return v;
    }
    public void volley()
    {


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(true);
        pd.setMessage("Loading Please Wait");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        pd.show();


        RequestQueue queue= Volley.newRequestQueue(getActivity());

        url= Urlconstant.Url+"i_login.php?"+"username="+euser+"&password="+epass;
        url=url.replace(" ","%20");
        Log.e("name", url);
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.POST, url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                // TODO Auto-generated method stub

                Log.e("res", arg0.toString());
                String int_id="";

                try{
                    JSONArray jobarray=arg0.getJSONArray("name");

                    Log.e("response=", arg0.getJSONArray("name").toString());
                    prefedit=pref.edit();
                    if(jobarray.length()>0) {
                        for (int i = 0; i < jobarray.length(); i++) {
                            JSONObject obj = jobarray.getJSONObject(i);
                            int_id = obj.getString("id");


                        }

                        pd.dismiss();
                        Toast.makeText(getActivity(), "Successfully login", Toast.LENGTH_LONG).show();
                        prefedit.putBoolean("boolean", true);
                        prefedit.putString("role","examiner");
                        editor_id = pref_id.edit();
                        editor_id.putString("eid", int_id);
                        editor_id.commit();
                        profilefrg();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Please Enter Correct Username And Password", Toast.LENGTH_LONG).show();
                    }

                    prefedit.commit();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    pd.dismiss();
                    String er="No value for name";
                    if(e.getMessage().equalsIgnoreCase(er))
                        Toast.makeText(getActivity(), "Please Enter Correct Username And Password", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getActivity(), "Server error", Toast.LENGTH_LONG).show();
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
    public  void profilefrg()
    {
        Fragment search = new Searchfragment();
        fm.beginTransaction().replace(R.id.frame_layout, search).commit();
    }
}
