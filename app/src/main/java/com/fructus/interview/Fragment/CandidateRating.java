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
import android.widget.ImageView;
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

public class CandidateRating extends Fragment
{
    SharedPreferences pref_detail;
    SharedPreferences pref_id;
    TextView name,username,mobile,collage,save;
    EditText rating;
    FragmentManager fm;
    String rate,interviewname,url="",roll,croll,interviewerid;
    ImageView back;
    int rateing;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.rating,container,false);
        fm=getActivity().getSupportFragmentManager();

        pref_detail=getActivity().getSharedPreferences("pref_detail", 1);
        pref_id=getActivity().getSharedPreferences("pref_id", 1);
        name=(TextView)v.findViewById(R.id.editText3);
        username=(TextView)v.findViewById(R.id.editText4);
        mobile=(TextView)v.findViewById(R.id.editText6);
        collage=(TextView)v.findViewById(R.id.editText7);
        rating=(EditText)v.findViewById(R.id.editText8);
        save=(TextView)v.findViewById(R.id.textView5);
        back=(ImageView)v.findViewById(R.id.back);

        name.setText(pref_detail.getString("name",""));
        username.setText(pref_detail.getString("username",""));
        mobile.setText(pref_detail.getString("mobile",""));
        collage.setText(pref_detail.getString("college",""));
        croll=pref_detail.getString("roll","");
        interviewerid = pref_id.getString("eid", "");
        ratevolley();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = rating.getText().toString();
                roll = pref_detail.getString("roll", "");
                interviewname = pref_id.getString("eid", "");

                rateing=Integer.parseInt(rate);
                if(rateing<6) {
                    volley();
                }
                else
                {
                    Toast.makeText(getActivity(),"Please Enter Rating Between 1 to 5",Toast.LENGTH_LONG).show();
                }

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment main = new Searchfragment();
                fm.beginTransaction().replace(R.id.frame_layout,main).commit();
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
        url= Urlconstant.Url+"rating.php?"+"rating="+rate+"&roll_no="+roll+"&int_id="+interviewname;
        url=url.replace(" ","%20");
        Log.e("name", url);
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.POST, url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                // TODO Auto-generated method stub

                Log.e("res", arg0.toString());


                try{
                    if(arg0.getString("scalar").equals("updated")==true)
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Successfully Inserted", Toast.LENGTH_LONG).show();
                        homefrg();
                    }
                    else if(arg0.getString("scalar").equals("already exist")==true)
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Rating Already Exist", Toast.LENGTH_LONG).show();
                        homefrg();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(getActivity(),"Not Inserted",Toast.LENGTH_LONG).show();

                    }

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

    public  void homefrg()
    {
        Fragment main = new Searchfragment();
        fm.beginTransaction().replace(R.id.frame_layout,main).commit();
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
                homefrg();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void ratevolley()
    {


        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(true);
        pd.setMessage("Loading Please Wait");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        pd.show();


        RequestQueue queue= Volley.newRequestQueue(getActivity());

        url= Urlconstant.Url+"checkrating.php?"+"roll_no="+croll+"&int_id="+interviewerid;
        url=url.replace(" ","%20");
        Log.e("name", url);
        JsonObjectRequest jsonreq=new JsonObjectRequest(Request.Method.POST, url, null,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject arg0) {
                // TODO Auto-generated method stub

                Log.e("res", arg0.toString());
                String ratingtxt="";

                try{
                    JSONArray jobarray=arg0.getJSONArray("name");

                    Log.e("response=", arg0.getJSONArray("name").toString());
                    if(jobarray.length()>0) {
                        for (int i = 0; i < jobarray.length(); i++) {
                            JSONObject obj = jobarray.getJSONObject(i);
                            ratingtxt = obj.getString("int"+interviewerid);
                        }
                        int g=Integer.parseInt(ratingtxt);
                        if(g>0)
                        {
                            rating.setEnabled(false);
                            rating.setText(ratingtxt);
                        }
                        else
                        {
                            rating.setText(ratingtxt);
                        }


                        pd.dismiss();

                    }
                    else {
                        pd.dismiss();
                    }

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
