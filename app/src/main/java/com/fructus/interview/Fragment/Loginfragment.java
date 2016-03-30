package com.fructus.interview.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Loginfragment extends Fragment
{
    SharedPreferences pref_roll;
    SharedPreferences.Editor editor_roll;
    EditText logtext,passtxt;
    TextView login,signup;
    FragmentManager fm;
    String clogin,cpass,url="";
    SharedPreferences pref;
    SharedPreferences.Editor prefedit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.login, null);
        fm=getActivity().getSupportFragmentManager();
        pref_roll=getActivity().getSharedPreferences("pref_roll", 1);
        pref=getActivity().getSharedPreferences("pref",1);




        boolean b=pref.getBoolean("boolean",false);
        if(b)
        {
                profilefrg();
        }
        logtext=(EditText)v.findViewById(R.id.editText);
        passtxt=(EditText)v.findViewById(R.id.editText2);
        login=(TextView)v.findViewById(R.id.textView);
        signup=(TextView)v.findViewById(R.id.textView2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signfrg();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clogin=logtext.getText().toString();
                cpass=passtxt.getText().toString();
                if(clogin.length()<1&cpass.length()<1)
                {
                    Toast.makeText(getActivity(), "Please Enter Correct Username And Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    volley();
                }
    }
});
        return v;
    }

    public  void profilefrg()
    {
        Fragment home = new Candidatehomefragment();
        fm.beginTransaction().replace(R.id.frame_layout, home).commit();
    }
    public  void signfrg()
    {
        Fragment signup = new Signupfragment();
        fm.beginTransaction().replace(R.id.frame_layout, signup).commit();
    }
    public void volley()
    {


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(true);
        pd.setMessage("Loading Please Wait");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        pd.show();


        RequestQueue queue= Volley.newRequestQueue(getActivity());
        url= Urlconstant.Url+"c_login.php?"+"username="+clogin+"&password="+cpass;
        url=url.replace(" ","%20");
        Log.e("name", url);
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.POST, url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                // TODO Auto-generated method stub

                Log.e("res", arg0.toString());
                String roll="";

                try{
                    JSONArray jobarray=arg0.getJSONArray("name");

                    Log.e("response=", arg0.getJSONArray("name").toString());
                    prefedit=pref.edit();
                    if(jobarray.length()>0) {
                        for (int i = 0; i < jobarray.length(); i++) {
                            JSONObject obj = jobarray.getJSONObject(i);
                            roll = obj.getString("roll_no");


                        }
                        pd.dismiss();
                       Toast.makeText(getActivity(), "Successfully login", Toast.LENGTH_LONG).show();
                        prefedit.putBoolean("boolean", true);
                        prefedit.putString("role", "candidate");
                        editor_roll = pref_roll.edit();
                        editor_roll.putString("croll", roll);
                        editor_roll.commit();
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.result_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment home = new Homefragment();
                fm.beginTransaction().replace(R.id.frame_layout,home).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
