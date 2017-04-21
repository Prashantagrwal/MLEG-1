package com.example.dell.navigate;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity123 extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        View.OnClickListener,AsyncResponse {

   int[] mResources = {//R.drawable.sample123, R.drawable.sample123, R.drawable.sample123
    };
    Button b_enquiry, b_cart, b_remove;
    Intent i;
    static  String[] value_image;
    String productCode;
    String name;
    String o_price,desp;
    String d_price;
    SessionManager s;
    String log_tag = MainActivity123.class.getSimpleName();
    ViewPager mViewPager;
    private CustomViewAdapter mAdapter;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    TextView tv_name,tv_o_price,tv_d_price,tv_desp,tv_per;


    public MainActivity123()
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main123);
        //noinspection ConstantConditions
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.app_name_title) + "</font>"));
        s = new SessionManager(MainActivity123.this);
        i = getIntent();
        if (i != null)
        {
            productCode = i.getStringExtra("productCode");
            name = i.getStringExtra("name");
            o_price = i.getStringExtra("o_price");
            d_price = i.getStringExtra("d_price");
            desp=i.getStringExtra("desp");
        }
        b_remove = (Button) findViewById(R.id.b_remove);
        b_cart = (Button) findViewById(R.id.b_add_to_cart);

        CheckCart checkCart= new CheckCart(MainActivity123.this,b_cart,b_remove);
        checkCart.delegate=this;
        checkCart.execute(s.getUserDetails().get(SessionManager.KEY_ID),
                productCode,"2");


    }
    private void ids() {
        b_enquiry = (Button) findViewById(R.id.b_enquiry);
        tv_name=(TextView)findViewById(R.id.text_name);
        tv_o_price=(TextView)findViewById(R.id.text_o_price);
        tv_d_price=(TextView)findViewById(R.id.text_d_price);
        tv_per=(TextView) findViewById(R.id.text_per);
        tv_desp=(TextView)findViewById(R.id.text_desp);

        Float amt=((1-(Float.valueOf(d_price)/
                Float.valueOf(o_price)))*100);
        int value=Math.round(amt);
        String per=String.valueOf(value);
        tv_o_price.setPaintFlags(tv_o_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        tv_name.setText(name);tv_d_price.setText(MainActivity123.this.getString(R.string.Rs)
                +d_price+" ");
        tv_per.setText(per+"% ");
        tv_o_price.setText(o_price);
        tv_desp.setText(desp);
        assert b_enquiry != null;
        b_enquiry.setOnClickListener(this);
        b_remove.setOnClickListener(this);
        b_cart.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        if(value_image!=null) {
            mAdapter = new CustomViewAdapter(this, value_image);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(0);
            mViewPager.addOnPageChangeListener(this);
            setPageViewIndicator();
        }
        else
        {
            Toast.makeText(MainActivity123.this,"not working",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onStart() {

        super.onStart();
        final CheckConnection checkConnection = new CheckConnection(MainActivity123.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(MainActivity123.this, Internet.class);
            startActivity(intent);


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
            case android.R.id.home:
              finish();
                break;
            case R.id.action_call:
                i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:09300012490"));
                startActivity(i);
                break;
            case R.id.action_cart:
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
                break;
        }
        return true;
    }
    private void setPageViewIndicator() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.item_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            final int presentPosition = i;
            dots[presentPosition].setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mViewPager.setCurrentItem(presentPosition);
                    return true;
                }

            });


            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.item_selected));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b_enquiry:
                Intent intent = new Intent(MainActivity123.this, Contact.class);
                intent.putExtra("ProductName", name);
                intent.putExtra("code", productCode);
                intent.putExtra("o_price",o_price);
                intent.putExtra("d_price",d_price);
                startActivity(intent);
                break;
            case R.id.b_add_to_cart:

                HashMap<String, String> user = s.getUserDetails();
                String userid = user.get(SessionManager.KEY_ID);
                new CartAdd(MainActivity123.this).execute(userid, productCode, "0");
                b_remove.setVisibility(View.VISIBLE);
                b_cart.setVisibility(View.GONE);
                break;
            case R.id.b_remove:
                b_cart.setVisibility(View.VISIBLE);
                b_remove.setVisibility(View.GONE);

                new CartAdd(MainActivity123.this).
                        execute(s.getUserDetails().get(SessionManager.KEY_ID),
                                productCode, "1");
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.item_unselected));
        }

        dots[position].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.item_selected));

        if (position + 1 == dotsCount) {

        } }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void processFinish(String[] output)
    {
        value_image=new String[output.length];
        value_image=output;
        Log.e(log_tag,value_image[0]);
        ids();
    }
}

