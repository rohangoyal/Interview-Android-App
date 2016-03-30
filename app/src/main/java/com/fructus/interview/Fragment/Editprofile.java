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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Editprofile extends Fragment
{
    ArrayList<get_sign> candidatedata;
    SharedPreferences pref_roll;
    FragmentManager fm;
    EditText name,username,mobile,college;
    TextView save,rollno,logout;
    String croll_no,url="";
    String cname,cusername,cmobile,ccollege,croll;
    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v=inflater.inflate(R.layout.editprofile,container,false);
        fm=getActivity().getSupportFragmentManager();
        
        pref_roll=getActivity().getSharedPreferences("pref_roll", 1);
        name=(EditText)v.findViewById(R.id.editText3);
        username=(EditText)v.findViewById(R.id.editText4);
        mobile=(EditText)v.findViewById(R.id.editText6);
        college=(EditText)v.findViewById(R.id.editText7);
        save = (TextView)v.findViewById(R.id.textView5);
        rollno=(TextView)v.findViewById(R.id.editText5);

        back=(ImageView)v.findViewById(R.id.back);

        croll_no=pref_roll.getString("croll","");
        volley();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cname = name.getText().toString();
                cusername = username.getText().toString();
                cmobile = mobile.getText().toString();
                ccollege = college.getText().toString();
                croll = rollno.getText().toString();

                editvolley();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profile = new Profilefragment();
                fm.beginTransaction().replace(R.id.frame_layout, profile).commit();
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

    public void editvolley()
    {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setCancelable(true);
        pd.setMessage("Loading Please Wait");
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.show();


        RequestQueue queue= Volley.newRequestQueue(getActivity());
        cname=cname.replace(" ","%20");
        cusername=cusername.replace(" ","%20");
        ccollege=ccollege.replace(" ","%20");
        Date d = new Date();
        String currentdate= DateFormat.getDateTimeInstance().format(d);
        url= Urlconstant.Url+"update_profile.php?"+"c_name="+cname+"&roll_no="+croll+"&college="+ccollege+"&mobile="+cmobile+"&username="+cusername+"&password="+candidatedata.get(0).getPass()+"&reg_date="+currentdate;
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
                        Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_LONG).show();
                        Fragment home = new Candidatehomefragment();
                        fm.beginTransaction().replace(R.id.frame_layout, home).commit();

                    }
                    else
                    {

                        Toast.makeText(getActivity(),"Not Updated",Toast.LENGTH_LONG).show();

                    }
                    pd.dismiss();


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
