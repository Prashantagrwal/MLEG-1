package com.example.dell.navigate;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView;
    String log_tag=MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    FragmentMain fragmentMain;

    SessionManager session;
    NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
manager.cancel(11);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);


        session=new SessionManager(this);
        fragmentMain=new FragmentMain();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragmentMain,"Main").commit();

     /*   mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("dd55585dd1daa806")
                .build();
        mAdView.loadAd(adRequest);*/

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    Log.e(log_tag,"Inside OnReciceve");
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    Log.e(log_tag," Not Inside OnReciceve");
                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

      switch (id)
        {
            case R.id.action_about_us:
            Intent intent=new Intent(this,WhoAreWe.class);
                intent.putExtra("check",1);
                    startActivity(intent);
                break;
            case R.id.action_contact_us:
                 intent=new Intent(this,WhoAreWe.class);
                intent.putExtra("check",2);
                startActivity(intent);
                break;
            case R.id.action_share_app:

                String share_data="Hello this is maruti live emotions";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT,share_data);
                startActivity(shareIntent);
                break;
            case R.id.action_rate_us:

                final String appPackageName = getPackageName();
                try {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    Toast.makeText(MainActivity.this,"Not placed in playstore",Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }

                break;
            case R.id.action_my_account:
                Log.v(log_tag,"pressed");
                 intent =new Intent(this,MyAccount.class);
                startActivity(intent);
                break;
            case R.id.action_my_orders:

                if(session.isLoggedIn())
                {
                                 Intent i =new Intent(this,OrderPHPfetch.class);
                    i.putExtra("userid",session.getUserDetails().get(SessionManager.KEY_ID));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(this,"First Sign In",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.action_my_cart:
                if(session.isLoggedIn())
                {

                    Intent i =new Intent(this,CartPHPfetch.class);
                    i.putExtra("userid",session.getUserDetails().get(SessionManager.KEY_ID));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(this,"First Sign In",Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;String[] list;
        if (id == R.id.action_stationery) {
            list= new String[]{"Visiting Cards", "Letter Head", "Bill Book", "Brochure",
            "Book","Leaflet","Menu Card","Personalized Dairy"};
            intent =new Intent(MainActivity.this,ListViewClass.class);
            intent.putExtra("list",list);
            intent.putExtra("name",R.string.action_stationery);
            startActivity(intent);
        } else if (id == R.id.action_coorpate) {
            list= new String[]{"Calender", "Screening Work", "Hanging Leafted/Door Hanging", "Pumplet"};
            intent =new Intent(MainActivity.this,ListViewClass.class);
            intent.putExtra("list",list);
            intent.putExtra("name",R.string.action_coorpate);
            startActivity(intent);

        } else if (id == R.id.action_visting_card)
        {

        } else if (id == R.id.action_logo)
        {

        }
        else if (id == R.id.action_cards) {
            list= new String[]{"Greeting Cards", "Personlized Cards", "Love Cards","Anniversary Cards",
                    "Birthday Cards","Congraution Cards","Thank You Cards","Sorry Cards","Valentine Cards"
                    ,"Diwali Cards","Holi Cards","Rakhi Cards"};
            intent =new Intent(MainActivity.this,ListViewClass.class);
            intent.putExtra("list",list);
            intent.putExtra("name",R.string.action_cards);
            startActivity(intent);

        }
        else if (id == R.id.action_photo)
        {

        }
        else if (id == R.id.action_flex) {
            list= new String[]{"Normal Flex", "Star Flex", "Vinyl","One Way Vision",
                    "Canvas","Wallpaper","SunBoard","GlowShine"};
            intent =new Intent(MainActivity.this,ListViewClass.class);
            intent.putExtra("list",list);
            intent.putExtra("name",R.string.action_flex );
            startActivity(intent);
        }
        else if (id == R.id.action_gift)
        {

        }
        else if (id == R.id.action_Wallpaper)
        {

        }
        else if (id == R.id.action_Wedding) {

        }else if (id == R.id.action_emotions) {

        }else if (id == R.id.action_design) {

        }
        else if (id == R.id.action_words) {

        }
        else if(id==R.id.action_papers)
        {
            list= new String[]{"Textures", "Non-Tearable", "Transparent","All Types Of Stickers",
                    "Lamination"};
            intent =new Intent(MainActivity.this,ListViewClass.class);
            intent.putExtra("list",list);
            intent.putExtra("name",R.string.action_papers);
            startActivity(intent);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
        fragmentMain=new FragmentMain();

        getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragmentMain,"Main").commit();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
        if (mAdView != null) {
            mAdView.pause();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
