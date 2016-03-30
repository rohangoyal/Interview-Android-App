package com.fructus.interview.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fructus.interview.R;
import com.fructus.interview.constant.Urlconstant;
import com.fructus.interview.getter_setter.get_result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Resultfragment extends Fragment
{

    SharedPreferences pref_roll,pref;
   TextView ok,rollno,name,mobile,college,result;
   FragmentManager fm;
    String croll_no;
    ArrayList<get_result> candidateresult;
    ImageView back;
    int rank;
    SharedPreferences.Editor editer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.candresult,container,false);
        fm=getActivity().getSupportFragmentManager();

        pref_roll=getActivity().getSharedPreferences("pref_roll", 1);
        pref=getActivity().getSharedPreferences("pref", 1);

        ok=(TextView)v.findViewById(R.id.textView5);
        rollno=(TextView)v.findViewById(R.id.editText3);
        name=(TextView)v.findViewById(R.id.editText4);
        mobile=(TextView)v.findViewById(R.id.editText6);
        college=(TextView)v.findViewById(R.id.editText7);
        result=(TextView)v.findViewById(R.id.textView6);
        back=(ImageView)v.findViewById(R.id.back);
        croll_no=pref_roll.getString("croll","");
        volley();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment home = new Candidatehomefragment();
                fm.beginTransaction().replace(R.id.frame_layout, home).commit();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment home = new Candidatehomefragment();
                fm.beginTransaction().replace(R.id.frame_layout, home).commit();
            }
        });

        return v;
    }

    public void volley()
    {

        candidateresult=new ArrayList<get_result>();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..Please wait.");
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.e("url= ", "jjava.lang.String[]iii");
        String url = Urlconstant.Url+"result.php?roll_no="+croll_no;
        Log.e("url= ", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject res) {

                try {
                    JSONArray jr = res.getJSONArray("name");
                    int len = jr.length();

                    for (int i = 0; i < jr.length(); i++) {
                        JSONObject jsonobj = jr.getJSONObject(i);
                        candidateresult.add(new get_result(jsonobj.getInt("id"),jsonobj.getString("roll_no"),jsonobj.getString("name"), jsonobj.getString("email"), jsonobj.getString("password"), jsonobj.getString("mobile"), jsonobj.getString("college"), jsonobj.getString("rank")));

                    }
                    dialog.dismiss();
                    name.setText(candidateresult.get(0).getName().toString());
                    rollno.setText(candidateresult.get(0).getRoll());
                    mobile.setText(candidateresult.get(0).getMobile());
                    college.setText(candidateresult.get(0).getCollege());
                    rank=Integer.parseInt(candidateresult.get(0).getResult());
                    if(rank==0)
                    {
                        result.setText("Result Panding");
                        result.setTextColor(Color.parseColor("#ff0000"));
                    }
                    else if(rank>0)
                    {
                        switch (rank)
                        {
                            case 1:
                                result.setText("5th Rank");
                                result.setTextColor(Color.parseColor("#12C609"));
                                break;
                            case 2:
                                result.setText("4th Rank");
                                result.setTextColor(Color.parseColor("#12C609"));
                                break;
                            case 3:
                                result.setText("3rd Rank");
                                result.setTextColor(Color.parseColor("#12C609"));
                                break;
                            case 4:
                                result.setText("2nd Rank");
                                result.setTextColor(Color.parseColor("#12C609"));
                                break;
                            case 5:
                                result.setText("1st Rank");
                                result.setTextColor(Color.parseColor("#12C609"));
                                break;
                        }

                    }



                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Log.e("catch exp= ", e.toString());
                    e.printStackTrace();
                    dialog.dismiss();
                }

            }

        }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {

                Log.e("error", arg0.toString());
                dialog.dismiss();
            }
        });
        int socketTimeout = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }
}
