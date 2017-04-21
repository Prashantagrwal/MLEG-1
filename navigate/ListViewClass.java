package com.example.dell.navigate;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListViewClass extends AppCompatActivity{
    ListView listView;
   static String[] list;
    ArrayAdapter adapter;
    int title;
    String title_value;
    Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list);
  final Intent intent=getIntent();
        if(intent!=null) {
            list = intent.getStringArrayExtra("list");
            title=intent.getIntExtra("name",R.string.list );
           title_value=getResources().getString(title);
           this.setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +title_value + "</font>"));
            final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            assert upArrow != null;
            upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
         //   getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" +title + "</font>"));

            adapter = new ArrayAdapter<>(ListViewClass.this,
                    R.layout.list_view, list);
            listView= (ListView) findViewById(R.id.my_list_view);
            assert listView != null;
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent1=new Intent(ListViewClass.this,GetProduct.class);
                intent1.putExtra("Category",getResources().getString(title));
                intent1.putExtra("Subcategory",list[position]);
                startActivity(intent1);

            }
        });
        }

    }
    @Override
    public void onStart() {

        super.onStart();
        final CheckConnection checkConnection = new CheckConnection(ListViewClass.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(ListViewClass.this, Internet.class);
            startActivity(intent);
        }
    }
}
