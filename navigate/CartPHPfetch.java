package com.example.dell.navigate;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class CartPHPfetch extends AppCompatActivity {
    Context context;
    String myJson;
    private static String TAG_SUCCESS="success";
    private static String TAG_CART="cart";
    private static String TAG_NAME="name";
    private static String TAG_CODE="product_code";
    private static String TAG_O_PRICE="o_price";
    private static String TAG_D_PRICE="d_price";
    private JSONArray cart = null;
    int success;
   TextView tv;

    String log_tag = CartPHPfetch.class.getSimpleName(),userid;

    Intent intent;
    private ProgressBar spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_recycle);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_cart) + "</font>"));

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      intent=getIntent();
       if(intent!=null){
            userid = intent.getStringExtra("userid");
           ids();
           getdata();

       }
    }
    @Override
    public void onStart() {

        super.onStart();
        final CheckConnection checkConnection = new CheckConnection(CartPHPfetch.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(CartPHPfetch.this, Internet.class);
            startActivity(intent);


        }
    }
    private void ids() {
        spinner= (ProgressBar) findViewById(R.id.progressBar);
        tv= (TextView) findViewById(R.id.tv_cart_value);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.nothing, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
              finish();
                //  NavUtils.navigateUpFromSameTask(this);
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

                Uri builturi = Uri.parse(new ServerFile().user_cart_fetch).buildUpon().
                        appendQueryParameter(LINK_USER_ID,userid)
                        .build();
                return new HttpFetch().httpResquest(builturi.toString(), "fetch");

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

               spinner.setVisibility(View.GONE);
                Log.v(log_tag,s);
                myJson = s;
                showValues();

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

                ArrayList<CartAndroidVersion> android_version = new ArrayList<>();
                for (i = 0; i < cart.length(); i++) {
                    JSONObject cart_value = cart.getJSONObject(i);
                    CartAndroidVersion androidVersion = new CartAndroidVersion();
                    androidVersion.setAndroid_product_name(cart_value.getString(TAG_NAME));
                    androidVersion.setAndroid_product_code(cart_value.getString(TAG_CODE));
                    androidVersion.setAndroid_o_price(cart_value.getString(TAG_O_PRICE));
                    androidVersion.setAndroid_d_price(cart_value.getString(TAG_D_PRICE));
                    android_version.add(androidVersion);
                }

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                assert recyclerView != null;
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
                recyclerView.setLayoutManager(layoutManager);
                CartAdapter adapter = new CartAdapter(CartPHPfetch.this,android_version);
                recyclerView.setAdapter(adapter);

            }
            else if (success == 0) {
                tv.setVisibility(View.VISIBLE);
                tv.setText(CartPHPfetch.this.getString(R.string.no_item));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

