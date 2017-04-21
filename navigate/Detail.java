package com.example.dell.navigate;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;


public class Detail extends AppCompatActivity implements View.OnClickListener {

    Button b_enquiry, b_cart, b_remove;
    Intent i;
    String productCode;
    String name;
    String o_price;
    String d_price;
    int image;
    String log_tag = Detail.class.getSimpleName();
    TextView Product_Name, product_o_price, product_d_price, product_desp, product_req;
    SessionManager s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        //getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_detail) + "</font>"));
        s = new SessionManager(Detail.this);
        i = getIntent();
        if (i != null) {
            productCode = i.getStringExtra("productCode");
            name = i.getStringExtra("name");
            o_price = i.getStringExtra("o_price");
            d_price = i.getStringExtra("d_price");
            Log.v(log_tag, productCode+ " " + name + " " + o_price + " " + d_price);
        }

        ids();
          }

    private void ids() {
        b_enquiry = (Button) findViewById(R.id.b_enquiry);
        b_remove = (Button) findViewById(R.id.b_remove);
        b_cart = (Button) findViewById(R.id.b_add_to_cart);
        assert b_enquiry != null;
        b_enquiry.setOnClickListener(this);
        b_remove.setOnClickListener(this);
        b_cart.setOnClickListener(this);
        new CheckCart(Detail.this,b_cart,b_remove).
                execute(s.getUserDetails().get(SessionManager.KEY_ID),
                        productCode,"2");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.b_enquiry:
                Intent intent = new Intent(Detail.this, Contact.class);
                intent.putExtra("ProductName",name);
                intent.putExtra("code",productCode);
                startActivity(intent);
                break;
            case R.id.b_add_to_cart:

                HashMap<String, String> user = s.getUserDetails();
                String userid = user.get(SessionManager.KEY_ID);
               new CartAdd(Detail.this).execute(userid,productCode,"0");
                b_remove.setVisibility(View.VISIBLE);
                b_cart.setVisibility(View.GONE);
                break;
            case R.id.b_remove:
                b_cart.setVisibility(View.VISIBLE);
                b_remove.setVisibility(View.GONE);

                new CartAdd(Detail.this).
                        execute(s.getUserDetails().get(SessionManager.KEY_ID),
                                productCode,"1");
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detail_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i = null;
         switch (id)
        {
            case R.id.action_call:
                i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:09300012490"));
                startActivity(i);
                break;
            case R.id.action_cart:

                i =new Intent(this,CartPHPfetch.class);
                startActivity(i);
                break;
            case R.id.action_my_orders:
                 i =new Intent(this,OrderPHPfetch.class);
                startActivity(i);
                break;
        }

        return true;
    }
}