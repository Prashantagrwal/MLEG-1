package com.example.dell.navigate;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;


public class CartAdd extends AsyncTask<String,Void,String> {
Context context;


    public CartAdd(Context context) {
        this.context = context;
        }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
    String userid=params[0];
    String productCode=params[1];
    String check=params[2];
        String LINK_USER_ID = "UserEmailId";
        String LINK_PRODUCT_CODE = "productCode";
        String LINK_CHECK="check";

        Uri builturi = Uri.parse(new ServerFile().user_cart_add).buildUpon().
                appendQueryParameter(LINK_USER_ID,userid)
                .appendQueryParameter(LINK_PRODUCT_CODE,productCode)
                .appendQueryParameter(LINK_CHECK,check).build();
        return new HttpFetch().httpResquest(builturi.toString(), "add");

    }

    @Override
    protected void onPostExecute(String s)
    {
        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
    }
}
