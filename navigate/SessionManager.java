package com.example.dell.navigate;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MarutiPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NUMBER = "number";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_ID= "user_id";



    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String email){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // Storing email in pref
        editor.putString(KEY_EMAIL, email);
        // commit changes
        editor.commit();
    }
public void createUserSession(String id)
{
    editor.putString(KEY_ID,id);
    editor.commit();
}


    public void createPhoneSession(String number)
    {
        editor.putString(KEY_NUMBER,number);
        editor.commit();
    }
    public void createAddressSession(String address)
    {
        editor.putString(KEY_ADDRESS,address);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        user.put(KEY_NUMBER,pref.getString(KEY_NUMBER,null));

        user.put(KEY_ADDRESS,pref.getString(KEY_ADDRESS,null));

        user.put(KEY_ID,pref.getString(KEY_ID,null));
        // return user
        return user;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
