package com.example.dell.navigate;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

public class MyAccount extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

private static final String TAG = MainActivity.class.getSimpleName();
private static final int RC_SIGN_IN = 007;

private GoogleApiClient mGoogleApiClient;

 String s_number,s_address,pref_name,pref_email,pref_number,pref_address;
private SignInButton btnSignIn;
private Button btnSignOut;
        Button b_edit_no,b_edit_address,b_save_no,b_save_address;

        LinearLayout ll_save_number,ll_save_address;
       RelativeLayout ll_number,ll_address;
private TextView txtName, txtEmail;
        TextView txt_no,txt_address;
        EditText et_number,et_address;
        SessionManager session;
        HashMap<String, String> user;
        Boolean check;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
    //noinspection ConstantConditions
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    assert upArrow != null;
    upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
    getSupportActionBar().setHomeAsUpIndicator(upArrow);
    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_account) + "</font>"));

    session= new SessionManager(MyAccount.this);


ids();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(MyAccount.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Toast.makeText(this,"User Login Status:"+session.isLoggedIn(),Toast.LENGTH_LONG).show();


       }

        private void ids() {
                btnSignIn = (SignInButton) findViewById(R.id.sign_in);
                btnSignOut = (Button) findViewById(R.id.SignOut);
                b_save_no= (Button) findViewById(R.id.b_save_number);
                b_save_address= (Button) findViewById(R.id.b_save_address);
                b_edit_no= (Button) findViewById(R.id.b_edit_number);
                b_edit_address= (Button) findViewById(R.id.b_edit_address);
                      //llProfileLayout = (LinearLayout) findViewById(R.id.linear)
                txtName = (TextView) findViewById(R.id.profile_name);
                txtEmail = (TextView) findViewById(R.id.profile_email);
                txt_no= (TextView) findViewById(R.id.tv_number);
                txt_address= (TextView) findViewById(R.id.tv_view_address);

                ll_address=(RelativeLayout) findViewById(R.id.ll_for_address);
                ll_number=(RelativeLayout) findViewById(R.id.ll_for_number);
                ll_save_number=(LinearLayout) findViewById(R.id.ll_save_numner);
                ll_save_address=(LinearLayout) findViewById(R.id.ll_save_address);

                et_number=(EditText) findViewById(R.id.et_number);
                et_address=(EditText) findViewById(R.id.et_address);

                 btnSignIn.setOnClickListener(this);
                btnSignOut.setOnClickListener(this);
                b_save_no.setOnClickListener(this);
                b_save_address.setOnClickListener(this);
                b_edit_no.setOnClickListener(this);
                b_edit_address.setOnClickListener(this);
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nothing, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
             if(session.isLoggedIn())
              finish();
                else
             startActivity(new Intent(MyAccount.this,MainActivity.class));
                break;
        }
        return true;
    }


        private void signIn() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        }


private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
        new ResultCallback<Status>() {

                @Override
public void onResult(@Nullable Status status) {
        updateUI(false);
        }
        });
        }



private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
                // Signed in successfully, show authenticated UI.
                GoogleSignInAccount acct = result.getSignInAccount();


                if (acct!=null) {
                        Log.v(TAG, "display name: " + acct.getDisplayName());

                        String personName = acct.getDisplayName();


                    String email = acct.getEmail();
                        session.createLoginSession(personName,email);


                        Log.v(TAG, "Name: " + personName + ", email: " + email
                                + ", Image: ");


                    txtName.setText(personName);
                    txtEmail.setText(email);
                    ll_save_number.setVisibility(View.VISIBLE);
                    ll_save_address.setVisibility(View.VISIBLE);
                    new UserPHP(MyAccount.this).execute(email,
                            new SharedToken(MyAccount.this).getUserDetails().get(SharedToken.KEY_TOKEN));
                    updateUI(true);
                }
        }
        else
        {

            Toast.makeText(MyAccount.this,"Check Internet Connection",Toast.LENGTH_LONG).show();
        updateUI(false);
        }

        }

@Override
public void onClick(View v) {
        int id = v.getId();

        switch (id) {
        case R.id.sign_in:
                signIn();
        break;

        case R.id.SignOut:
                signOut();
                txtName.setText(R.string.profile_name);
                txtEmail.setText(R.string.email);
                session.logoutUser();
                ll_number.setVisibility(View.GONE);
                ll_address.setVisibility(View.GONE);
                ll_save_number.setVisibility(View.GONE);
                ll_save_address.setVisibility(View.GONE);
                break;

                case R.id.b_save_number:
              s_number=et_number.getText().toString();
                        if(s_number.trim().length()>0)
                        {
                                session.createPhoneSession(s_number);
                                ll_number.setVisibility(View.VISIBLE);
                                ll_save_number.setVisibility(View.GONE);
                                txt_no.setText(s_number);
                        }
                        else
                        {
                                Toast.makeText(this,"First Enter Your Number",Toast.LENGTH_LONG).show();
                        }
                break;
                case R.id.b_save_address:

                        s_address=et_address.getText().toString();
                        if(s_address.trim().length()>0)
                        {
                                session.createAddressSession(s_address);
                                ll_address.setVisibility(View.VISIBLE);
                                ll_save_address.setVisibility(View.GONE);
                                txt_address.setText(s_address);
                        }
                        else
                        {
                                Toast.makeText(this,"Enter Your Address",Toast.LENGTH_LONG).show();
                        }

                        break;
                case R.id.b_edit_number:
                        ll_number.setVisibility(View.GONE);
                        ll_save_number.setVisibility(View.VISIBLE);

                        break;
                case R.id.b_edit_address:
                        ll_address.setVisibility(View.GONE);
                        ll_save_address.setVisibility(View.VISIBLE);
                        break;
        }
        }

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                Log.v(TAG,"Result:"+result.toString());
        handleSignInResult(result);
        }
        }


@Override
public void onStart() {

        super.onStart();

    final CheckConnection checkConnection=new CheckConnection(MyAccount.this);
    if(!checkConnection.checkInternetConenction())
    {
    Intent intent=new Intent(MyAccount.this,Internet.class);
        startActivity(intent);
    }

    if(session.isLoggedIn()) {
        user = session.getUserDetails();
        pref_name = user.get(SessionManager.KEY_NAME);
        pref_email = user.get(SessionManager.KEY_EMAIL);
        pref_number = user.get(SessionManager.KEY_NUMBER);
        pref_address = user.get(SessionManager.KEY_ADDRESS);
        ll_number.setVisibility(View.VISIBLE);
        ll_address.setVisibility(View.VISIBLE);
        txt_address.setText(pref_address);
        txtName.setText(pref_name);
        txt_no.setText(pref_number);
        txtEmail.setText(pref_email);
        updateUI(true);
    }
}


@Override
public void onConnectionFailed(@Nullable ConnectionResult connectionResult) {

        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        }


private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
        btnSignIn.setVisibility(View.GONE);
        btnSignOut.setVisibility(View.VISIBLE);
       } else {

        btnSignIn.setVisibility(View.VISIBLE);
        btnSignOut.setVisibility(View.GONE);

        }
        }


        }



