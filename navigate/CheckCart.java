package com.example.dell.navigate;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CheckCart extends AsyncTask<String,String,String> {
    ProgressDialog progressDialog;
    Context context;
    Button add,remove;
    String log_tag=CheckCart.class.getSimpleName();
    public AsyncResponse delegate = null;

    public CheckCart()
    {

    }

    public CheckCart(Context context, Button add, Button remove) {
        this.context = context;
        this.add=add;this.remove=remove;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, null, "loading", false, false);

    }


    @Override
    protected String doInBackground(java.lang.String... params) {
        String userid = params[0];
        String productCode = params[1];
        String check = params[2];
        String LINK_USER_ID = "UserEmailId";
        String LINK_PRODUCT_CODE = "productCode";
        String LINK_CHECK = "check";

        Uri builturi = Uri.parse(new ServerFile().user_cart_add).buildUpon().
                appendQueryParameter(LINK_USER_ID, userid)
                .appendQueryParameter(LINK_PRODUCT_CODE, productCode)
                .appendQueryParameter(LINK_CHECK, check).build();
        return new HttpFetch().httpResquest(builturi.toString(), "add");
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        delegate.processFinish(showValue(s));
    }

    public String[] showValue(String s)
    {
          String TAG_CART_CHECK="check_cart";
            String TAG_IMAGE="images";
           String TAG_COUNT="count";
        String[] get_value = null;
        try {
            JSONObject obj =new JSONObject(s);
            String check=obj.getString(TAG_CART_CHECK);
             int count=obj.getInt(TAG_COUNT);
            JSONArray image_name = null;
            if(check.equals("true"))
            {
                add.setVisibility(View.GONE);
                remove.setVisibility(View.VISIBLE);
            }
            image_name=obj.getJSONArray(TAG_IMAGE);
            int i;
             get_value=new String[count];
            for(i=0;i<count;i++)
            {
                get_value[i]=image_name.getString(i);
                Log.e(log_tag,get_value[i]+"  ");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return get_value;
     }
}