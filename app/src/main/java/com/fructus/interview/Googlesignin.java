package com.fructus.interview;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fructus.interview.Fragment.Homefragment;
import com.fructus.interview.Fragment.Signupfragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;



public class Googlesignin extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    SharedPreferences pref_username;
    SharedPreferences.Editor prefedit;

    FragmentManager fm;
    private static final int RC_SIGN_IN = 0;
    public static GoogleApiClient mGoogleApiClient;
    public static String personName,personPhotoUrl,st_mobile,st_password;
    public static boolean mSignInClicked,mIntentInProgress;
    private static final int PROFILE_PIC_SIZE = 400;
    private ConnectionResult mConnectionResult;
    TextView btnSignIn;
    SharedPreferences signpref;
    SharedPreferences.Editor signeditor;
    public static String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlesignin);

        fm=getSupportFragmentManager();
        pref_username=getSharedPreferences("pref_username", 1);
        btnSignIn=(TextView)findViewById(R.id.textView23);


        signpref=getSharedPreferences("signpref", 1);

        boolean b=signpref.getBoolean("boolean", false);
        if(b)
        {
            Intent in=new Intent(getApplicationContext(),Candidate_Activity.class);
            startActivity(in);
            finish();
        }
        else
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(Googlesignin.this)
                    .addOnConnectionFailedListener(Googlesignin.this).addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        btnSignIn.setOnClickListener(Googlesignin.this);

    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        getProfileInformation();

        updateUI(true);

    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
        updateUI(false);


    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {


        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {

                resolveSignInError();
            }
        }

    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {

            btnSignIn.setVisibility(View.VISIBLE);
        }
    }
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                String address=currentPerson.getCurrentLocation();
                personPhotoUrl = currentPerson.getImage().getUrl();

                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                Log.e("google plus", "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl + ", address= " + address);


                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                prefedit=pref_username.edit();
                prefedit.putString("name",personName);
                prefedit.putString("email", email);
                prefedit.putString("pic", personPhotoUrl);
                prefedit.commit();


                Intent in=new Intent(getApplicationContext(),Candidate_Activity.class);
                startActivity(in);
                finish();


            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        // TODO Auto-generated method stub
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.textView23)
        {
            signInWithGplus();

        }

    }

}
