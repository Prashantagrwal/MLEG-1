package com.example.dell.navigate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by DELL on 27/02/2017.
 */
public class SharedToken
{
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MarutiPrefToken";

    public static final String KEY_TOKEN="token";

    public SharedToken(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }
    public  void createToken(String token)
    {
        editor.putString(KEY_TOKEN,token);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_TOKEN,pref.getString(KEY_TOKEN,null));
        // return user
        return user;
    }
}
