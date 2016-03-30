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
import com.fructus.interview.getter_setter.get_sign;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profilefragment extends Fragment
{
    ArrayList<get_sign> candidatedata;
    SharedPreferences pref_roll;
    FragmentManager fm;
    TextView save,name,username,mobile,college,rollno,edit;
    String croll_no;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.profile,container,false);
        fm=getActivity().getSupportFragmentManager();

        pref_roll=getActivity().getSharedPreferences("pref_roll", 1);
        name=(TextView)v.findViewById(R.id.editText3);
        username=(TextView)v.findViewById(R.id.editText4);
        rollno=(TextView)v.findViewById(R.id.editText5);
        mobile=(TextView)v.findViewById(R.id.editText6);
        college=(TextView)v.findViewById(R.id.editText7);
        save = (TextView)v.findViewById(R.id.textView5);
        edit=(TextView)v.findViewById(R.id.textView17);

        back=(ImageView)v.findViewById(R.id.back);

        croll_no=pref_roll.getString("croll","");
        volley();
        save.setOnClickListener(new View.OnClickListener() {
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment edit = new Editprofile();
                fm.beginTransaction().replace(R.id.frame_layout, edit).commit();
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
        String url = Urlconstant.Url+"profile.php?roll_no="+croll_no;
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
                    name.setText(candidatedata.get(0).getName().toString());
                    username.setText(candidatedata.get(0).getUsername());
                    mobile.setText(candidatedata.get(0).getMobile());
                    college.setText(candidatedata.get(0).getCollage());
                    rollno.setText(candidatedata.get(0).getRoll());
                    dialog.dismiss();
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
