package com.example.dell.navigate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DataAdapter2 extends RecyclerView.Adapter<DataAdapter2.ViewHolder> {
    private ArrayList<AndroidVersion2> android;
    Context context;
    String log_tag=DataAdapter2.class.getSimpleName();


    public DataAdapter2(Context context,ArrayList<AndroidVersion2> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item1, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i)
    {
        final Intent[] intent =new Intent[android.size()];

        viewHolder.name11.setText(android.get(i).getCategory());
        viewHolder.tv_product_name.setText(android.get(i).getAndroid_product_name1());
        viewHolder.tv_product_o.setText(context.getString(R.string.Rs)+android.get(i).getAndroid_price1());
         viewHolder.tv_product_name1.setText(android.get(i).getAndroid_product_name2());
        viewHolder.tv_product_o1.setText(context.getString(R.string.Rs)+android.get(i).getAndroid_price2());
        viewHolder.tv_product_name2.setText(android.get(i).getAndroid_product_name3());
        viewHolder.tv_product_o2.setText(context.getString(R.string.Rs)+android.get(i).getAndroid_price3());

      viewHolder.ll1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              intent[0] =new Intent(context,GetProduct.class);
              intent[0].putExtra("Category",android.get(i).getCategory());
              intent[0].putExtra("Subcategory",android.get(i).getAndroid_product_name1());
              context.startActivity(intent[0]);
          }
      });
        viewHolder.ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[1] =new Intent(context,GetProduct.class);
                intent[1].putExtra("Category",android.get(i).getCategory());
                intent[1].putExtra("Subcategory",android.get(i).getAndroid_product_name2());
                context.startActivity(intent[1]);
            }
        });
        viewHolder.ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent[2] =new Intent(context,GetProduct.class);
                intent[2].putExtra("Category",android.get(i).getCategory());
                intent[2].putExtra("Subcategory",android.get(i).getAndroid_product_name3());
                context.startActivity(intent[2]);
            }
        });
     viewHolder.view_all.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent view_all_intent=new Intent(context,GetProduct.class);
             view_all_intent.putExtra("Category",android.get(i).getCategory());
             context.startActivity(view_all_intent);
         }
     });

ServerFile serverFile=new ServerFile();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;int height=displayMetrics.heightPixels;
        Log.e(log_tag,"Width:"+width+" Height:"+height);
        Picasso.with(context).load(serverFile.image_php+"add"+".png").placeholder(R.drawable.ic_image_black_48dp).
                resize((int) (0.8*width), (int) (0.35*width)).
                into(viewHolder.image_main);
        Picasso.with(context).load(serverFile.image_php+"check_value"+".png")
                .placeholder(R.drawable.ic_image_black_48dp).
                resize((int) (0.4*width), (int) (0.5*width)).
                into(viewHolder.imageView);
        Picasso.with(context).load(serverFile.image_php+"SVC04"+".jpg").placeholder(R.drawable.ic_image_black_48dp).into(viewHolder.imageView1);
        Picasso.with(context).load(serverFile.image_php+"SVC04"+".jpg").placeholder(R.drawable.ic_image_black_48dp).into(viewHolder.imageView2);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView tv_product_name,tv_product_o,tv_product_d,tv_product_name1,tv_product_name2,  tv_product_o1,  tv_product_o2,tv_product_d1,tv_product_d2,name11;
        private ImageView imageView,imageView1,imageView2,image_main;
        private RelativeLayout ll1,ll2,ll3;
        private Button view_all;
        public ViewHolder(View view) {
            super(view);
            name11=(TextView)view.findViewById(R.id.name11);
            ll1= (RelativeLayout) view.findViewById(R.id.rl1);
            ll2= (RelativeLayout) view.findViewById(R.id.rl2);
            ll3= (RelativeLayout) view.findViewById(R.id.rl3);
            view_all= (Button) view.findViewById(R.id.view_all);

            tv_product_name = (TextView)view.findViewById(R.id.tv_name);
            tv_product_o = (TextView)view.findViewById(R.id.tv_o_price);
           // tv_product_d = (TextView)view.findViewById(R.id.tv_d_price);
            imageView=(ImageView)view.findViewById(R.id.image2);
            image_main=(ImageView)view.findViewById(R.id.image1);
            tv_product_name1 = (TextView)view.findViewById(R.id.tv_name1);
            tv_product_o1 = (TextView)view.findViewById(R.id.tv_o_price1);
         //   tv_product_d1 = (TextView)view.findViewById(R.id.tv_d_price1);
            imageView1=(ImageView)view.findViewById(R.id.image3);
            tv_product_name2 = (TextView)view.findViewById(R.id.tv_name2);
            tv_product_o2 = (TextView)view.findViewById(R.id.tv_o_price2);
       //     tv_product_d2 = (TextView)view.findViewById(R.id.tv_d_price2);
            imageView2=(ImageView)view.findViewById(R.id.image4);
        }}

    }


