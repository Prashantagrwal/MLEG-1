package com.example.dell.navigate;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class UserPHP extends AsyncTask<String,Void,String>
{
    Context context;
    String JsonString;
    String log_tag=UserPHP.class.getSimpleName();
    JSONArray contact = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_ID = "id";
    private static final String TAG_MESSAGE = "message";
    public UserPHP(Context context) {
        this.context = context;
    }

    String result;
    @Override
    protected String doInBackground(String... params) {
        String email=params[0],token=params[1];
        String LINK_EMAIL="email",LINK_TOKEN="token";
        Uri builturi = Uri.parse(new ServerFile().user_email_id).buildUpon().
                appendQueryParameter(LINK_EMAIL,email).
                appendQueryParameter(LINK_TOKEN,token)
                .build();
        Log.v(log_tag,builturi.toString());
        HttpFetch httpFetch=new HttpFetch();
       result= httpFetch.httpResquest(builturi.toString(),"check");
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        JsonString=s;
Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        super.onPostExecute(s);
        showid();
    }

    private void showid() {
        try {

            JSONObject jsonObj = new JSONObject(JsonString);
            int success = jsonObj.getInt(TAG_SUCCESS);

            if (success == 1) {
                String userid = jsonObj.getString(TAG_ID);
                SessionManager session=new SessionManager(context);
                Toast.makeText(context,userid,Toast.LENGTH_LONG).show();
                session.createUserSession(userid);
            } else if (success == 0)
            {
                String message=jsonObj.getString(TAG_MESSAGE);
                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
            }
        }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

