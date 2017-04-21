package com.example.dell.navigate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;

/**
 * Created by DELL on 20/02/2017.
 */
public class Internet extends AppCompatActivity
{Button retry;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_internet) + "</font>"));
    retry = (Button) findViewById(R.id.b_retry);

        retry.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            if(new CheckConnection(Internet.this).checkInternetConenction())
            {
             intent=new Intent(Internet.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else
            {
                intent=new Intent(Internet.this,Internet.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    });

    }

}
