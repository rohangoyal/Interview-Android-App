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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fructus.interview.Adapter.Examineradapter;
import com.fructus.interview.R;
import com.fructus.interview.constant.Urlconstant;
import com.fructus.interview.getter_setter.get_sign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Searchfragment extends Fragment
{
    EditText search;
    TextView title,logout;
    ImageView searchicon;
    Examineradapter adapter;
    ListView list;
    boolean b=false;
    String searchtxt,eid;
   FragmentManager fm;
    SharedPreferences pref_detail,pref;
    SharedPreferences.Editor editor_detail;
    SharedPreferences pref_id;
    ArrayList<get_sign> candidatedata;
    SharedPreferences.Editor editer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.examinersearch,container,false);
        list=(ListView)v.findViewById(R.id.listView);
        search=(EditText)v.findViewById(R.id.editText10);
        fm=getActivity().getSupportFragmentManager();
        title=(TextView)v.findViewById(R.id.headertext);
        searchicon=(ImageView)v.findViewById(R.id.imageView5);
        logout=(TextView)v.findViewById(R.id.logout);
        pref_detail=getActivity().getSharedPreferences("pref_detail", 1);
        pref=getActivity().getSharedPreferences("pref", 1);
        pref_id=getActivity().getSharedPreferences("pref_id", 1);
        volley();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editor_detail = pref_detail.edit();
                editor_detail.putString("name", candidatedata.get(position).getName());
                editor_detail.putString("username", candidatedata.get(position).getUsername());
                editor_detail.putString("mobile", candidatedata.get(position).getMobile());
                editor_detail.putString("college", candidatedata.get(position).getCollage());
                editor_detail.putString("roll", candidatedata.get(position).getRoll());
                editor_detail.commit();
                ratingfrg();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editer=pref.edit();
                editer.clear();
                editer.commit();
                Fragment home = new Homefragment();
                fm.beginTransaction().replace(R.id.frame_layout, home).commit();
            }
        });
        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b==false) {
                    title.setVisibility(View.GONE);
                    search.setVisibility(View.VISIBLE);
                    search.setFocusableInTouchMode(true);
                    search.setFocusable(true);
                    b = true;
                } else {
                    eid=pref_id.getString("eid","");
                    searchtxt = search.getText().toString();
                    searchvolley();
                }
            }
        });
        return v;
    }
    public void volley()
    {

        candidatedata=new ArrayList<get_sign>();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..Please wait.");
	     dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.e("url= ", "jjava.lang.String[]iii");
        String url = Urlconstant.Url+"candidate.php?";
        Log.e("url= ", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject res) {

                try {
                    JSONArray jr = res.getJSONArray("name");
                    int len = jr.length();

                    for (int i = 0; i < jr.length(); i++) {
                        JSONObject jsonobj = jr.getJSONObject(i);
                        candidatedata.add(new get_sign(jsonobj.getInt("id"), jsonobj.getString("name"), jsonobj.getString("email"), jsonobj.getString("password"), jsonobj.getString("mobile"), jsonobj.getString("college"), jsonobj.getString("reg_date"), jsonobj.getString("roll_no")));

                    }
                    dialog.dismiss();
                    adapter=new Examineradapter(candidatedata);
                    list.setAdapter(adapter);

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

    public void searchvolley()
    {

        candidatedata=new ArrayList<get_sign>();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..Please wait.");
	     dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        Log.e("url= ", "jjava.lang.String[]iii");
        String url = Urlconstant.Url+"search.php?c_name="+searchtxt+"&int_uname="+eid;
        Log.e("url= ", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject res) {

                try {
                    JSONArray jr = res.getJSONArray("name");
                    int len = jr.length();

                    for (int i = 0; i < jr.length(); i++) {
                        JSONObject jsonobj = jr.getJSONObject(i);
                        candidatedata.add(new get_sign(jsonobj.getInt("id"), jsonobj.getString("name"), jsonobj.getString("email"), jsonobj.getString("password"), jsonobj.getString("mobile"), jsonobj.getString("college"), jsonobj.getString("reg_date"), jsonobj.getString("roll_no")));

                    }
                    dialog.dismiss();
                    adapter=new Examineradapter(candidatedata);
                    list.setAdapter(adapter);

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

    public  void ratingfrg()
    {
        Fragment rate = new CandidateRating();
        fm.beginTransaction().replace(R.id.frame_layout, rate).commit();
    }
}
