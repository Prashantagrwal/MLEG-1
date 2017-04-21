package com.example.dell.navigate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OrderPHPfetch extends AppCompatActivity
{
    Context context;
    String myJson;
    private static String TAG_SUCCESS="success";
    private static String TAG_CART="product";
    private static String TAG_NAME="name";
    private static String TAG_CODE="code";
    private static String TAG_CALL="call";
    private static String TAG_DESIGN="design";
    private static String TAG_DELIVERY="delivery";
    private static String TAG_THANKYOU="thankyou";
    private static String TAG_CNAME="CustomerName";
    private static String TAG_CPHONE="phone";
    private static String TAG_CEMAIL="email";
    private static String TAG_CADDRESS="address";
    private static String TAG_PRODUCT_QUANTITY="quantity";
    private static String TAG_O="O_Price";
    private static String TAG_D="D_Price";

    private JSONArray cart = null;
    int success;
   TextView tv;
    private ProgressBar spinner;

    String log_tag = orderAdapter.class.getSimpleName(),userid;
    ProgressDialog progressDialog;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recycle);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name_title) + "</font>"));
        intent=getIntent();
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(intent!=null){
            userid = intent.getStringExtra("userid");
            ids();
            getdata();

        }
    }
    private void ids() {
        tv= (TextView) findViewById(R.id.tv_cart_value);
        spinner= (ProgressBar) findViewById(R.id.progressBar);
    }
    @Override
    public void onStart() {

        super.onStart();
        final CheckConnection checkConnection = new CheckConnection(OrderPHPfetch.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(OrderPHPfetch.this, Internet.class);
            startActivity(intent);


        }
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
             finish();
                //   NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }

    private void getdata() {

        class Cart extends AsyncTask<Void,Void,String > {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

          spinner.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(Void... params) {

                String LINK_USER_ID = "UserEmailId";

                Uri builturi = Uri.parse(new ServerFile().order_status).buildUpon().
                        appendQueryParameter(LINK_USER_ID,userid)
                        .build();
                return new HttpFetch().httpResquest(builturi.toString(), "fetch");

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.v(log_tag,s);
                myJson = s;
                showValues();

                spinner.setVisibility(View.GONE);
            }
        }

        Cart c=new Cart();
        c.execute();
    }

    private void showValues() {
        try {
            JSONObject jsonObj = new JSONObject(myJson);
            success = jsonObj.getInt(TAG_SUCCESS);



            if (success == 1) {

                cart = jsonObj.getJSONArray(TAG_CART);
                int i;

                ArrayList<orderVersion> android_version = new ArrayList<>();
                for (i = 0; i < cart.length(); i++) {
                    JSONObject cart_value = cart.getJSONObject(i);
                    orderVersion androidVersion = new orderVersion();
                    Log.v(log_tag,String.valueOf(i));
                    androidVersion.setProductName(cart_value.getString(TAG_NAME));
                    androidVersion.setProductCode(cart_value.getString(TAG_CODE));
                    androidVersion.setCustomerName(cart_value.getString(TAG_CNAME));
                    androidVersion.setEmail(cart_value.getString(TAG_CEMAIL));
                    androidVersion.setPhone(cart_value.getString(TAG_CPHONE));
                    androidVersion.setAddress(cart_value.getString(TAG_CADDRESS));
                    androidVersion.setQuantity(cart_value.getString(TAG_PRODUCT_QUANTITY));
                    androidVersion.setO_price(cart_value.getString(TAG_O));
                    androidVersion.setD_price(cart_value.getString(TAG_D));
                    androidVersion.setCall(cart_value.getString(TAG_CALL));
                    androidVersion.setDesign(cart_value.getString(TAG_DESIGN));
                    androidVersion.setDelivery(cart_value.getString(TAG_DELIVERY));
                    androidVersion.setTankYou(cart_value.getString(TAG_THANKYOU));
                    android_version.add(androidVersion);
                }

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                assert recyclerView != null;
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setLayoutManager(layoutManager);
                orderAdapter adapter = new orderAdapter(android_version,OrderPHPfetch.this);
                recyclerView.setAdapter(adapter);

            }
            else if (success == 0) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(OrderPHPfetch.this.getString(R.string.no_order));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
