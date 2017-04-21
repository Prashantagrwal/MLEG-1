package com.example.dell.navigate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GetProduct extends AppCompatActivity
{ Intent intent;
    String category,subcategory,myJSON,log_tag=GetProduct.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PRODUCT_CODE = "Product Code";
    private static final String TAG_PRODUCT_NAME = "Product Name";
    private static final String TAG_ORIGNIAL_PRICE = "Original Price";
    private static final String TAG_DISCOUNT_PRICE= "Discount Price";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_REQURIMENT = "Requriment";
    private static final String TAG_IMAGE = "image";
    private AdView mAdView;

    private String productCode,productName,orgPrice,disPrice,desp,req,image;

    JSONArray product = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
getOverflowMenu();

        //noinspection ConstantConditions
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_product) + "</font>"));

        mAdView = (AdView) findViewById(R.id.adView);
        assert mAdView != null;
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("dd55585dd1daa806")
                .build();
        mAdView.loadAd(adRequest);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        intent=getIntent();
        if(intent!=null)
        {
            category=intent.getStringExtra("Category");
            subcategory=intent.getStringExtra("Subcategory");
            getData(category,subcategory);
        }
    }
    private void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.extra, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;
        SessionManager s=new SessionManager(GetProduct.this);
        switch (id)
        {
            case android.R.id.home:
          finish();
           //     NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_my_cart:
                i =new Intent(this,CartPHPfetch.class);
                i.putExtra("userid",s.getUserDetails().get(SessionManager.KEY_ID));
                startActivity(i);
                break;
            case R.id.action_my_orders:
                i =new Intent(this,OrderPHPfetch.class);
                i.putExtra("userid",s.getUserDetails().get(SessionManager.KEY_ID));
                startActivity(i);
                break;
            case R.id.action_my_account:
                Intent intent =new Intent(this,MyAccount.class);
                startActivity(intent);
                break;        }
        return true;

    }
    @Override
    public void onStart() {

        super.onStart();
        final CheckConnection checkConnection = new CheckConnection(GetProduct.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(GetProduct.this, Internet.class);
            startActivity(intent);


        }
    }
    private void getData(String category, String subcategory)
    {
        class GetDataJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GetProduct.this, "", "loading", false, false);

            }

            @Override
            protected String doInBackground(String... params) {

                String LINK_CATEGORY,LINK_SUB_CATEGORY,cate,subcate;


                cate=params[0];
                subcate=params[1];
                LINK_CATEGORY="category";
                LINK_SUB_CATEGORY="subcategory";

                Uri builturi = Uri.parse(new ServerFile().fetch_product).buildUpon().
                        appendQueryParameter(LINK_CATEGORY,cate).
                        appendQueryParameter(LINK_SUB_CATEGORY,subcate)
                        .build();

                HttpFetch httpAddAndFetch = new HttpFetch();
                String result = httpAddAndFetch.httpResquest(builturi.toString(), "fetch");
                return result;
            }


            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                loading.dismiss();
                    Log.v(log_tag, result);

                showList();
            }
        }

        GetDataJSON g = new GetDataJSON();
        g.execute(category,subcategory);
    }

    private void showList() {
        try {

            JSONObject jsonObj = new JSONObject(myJSON);
            int success = jsonObj.getInt(TAG_SUCCESS);

            if (success == 1) {

                product = jsonObj.getJSONArray(TAG_PRODUCT);
                int i;
                ArrayList<AndroidVersion> android_version = new ArrayList<>();
                for(i=0;i<product.length();i++)
                { JSONObject c = product.getJSONObject(i);

                    AndroidVersion androidVersion = new AndroidVersion();

                    androidVersion.setAndroid_product_code(c.getString(TAG_PRODUCT_CODE));
                    androidVersion.setAndroid_product_name(c.getString(TAG_PRODUCT_NAME));
                    androidVersion.setAndroid_o_price(c.getString(TAG_ORIGNIAL_PRICE));
                    androidVersion.setAndroid_d_price(c.getString(TAG_DISCOUNT_PRICE));
                    androidVersion.setAndroid_product_req(c.getString(TAG_REQURIMENT));
                    androidVersion.setAndroid_product_desp(c.getString(TAG_DESCRIPTION));
                    android_version.add(androidVersion);
                }

                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.cart_recycler_view);
                assert recyclerView != null;
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration  mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        R.drawable.divide);
                recyclerView.addItemDecoration(mDividerItemDecoration);
                DataAdapter adapter = new DataAdapter(GetProduct.this,android_version);
                recyclerView.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
