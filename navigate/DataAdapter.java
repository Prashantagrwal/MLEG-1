package com.example.dell.navigate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android;
     Context context;
    SessionManager sessionManager;
    String log_tag=DataAdapter.class.getSimpleName();


    public DataAdapter(Context context,ArrayList<AndroidVersion> android) {
        this.android = android;
        this.context = context;
    }


        @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i)
    {
        Float amt=((1-(Float.valueOf(android.get(i).getAndroid_d_price())/
                Float.valueOf(android.get(i).getAndroid_o_price())))*100);
        int value=Math.round(amt);
        String per=String.valueOf(value);
        viewHolder.tv_product_name.setText(android.get(i).getAndroid_product_name());
        viewHolder.tv_product_o.setText(context.getString(R.string.Rs)+android.get(i).getAndroid_o_price());
        viewHolder.tv_product_o.setPaintFlags(viewHolder.tv_product_o.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.tv_product_d.setText(context.getString(R.string.Rs)+android.get(i).getAndroid_d_price()+" ("+per+"%)");
       Picasso.with(context).load(new ServerFile().image_php+android.get(i).
               getAndroid_product_code()+".jpg").placeholder(R.drawable.ic_image_black_48dp).into(viewHolder.imageView);
    }


    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_product_name,tv_product_o,tv_product_d;
        private ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            tv_product_name = (TextView)view.findViewById(R.id.tv_name_recyler_viewer);
            tv_product_o = (TextView)view.findViewById(R.id.tv_o_price_recyler_viewer);
            tv_product_d = (TextView)view.findViewById(R.id.tv_d_price_recyler_viewer);
            imageView=(ImageView)view.findViewById(R.id.imageView_recycle_viwer);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
             sessionManager = new SessionManager(context);

            if (sessionManager.isLoggedIn())
            {
                Intent intent=new Intent(context,MainActivity123.class);
                intent.putExtra("productCode",android.get(getPosition()).getAndroid_product_code());
                intent.putExtra("name",android.get(getPosition()).getAndroid_product_name());
                intent.putExtra("o_price",android.get(getPosition()).getAndroid_o_price());
                intent.putExtra("d_price",android.get(getPosition()).getAndroid_d_price());
                intent.putExtra("desp",android.get(getPosition()).getAndroid_product_desp());
                intent.putExtra("req",android.get(getPosition()).getAndroid_product_req());
                context.startActivity(intent);

            }
            else
            {
                Toast.makeText(context,"First Sign In",Toast.LENGTH_LONG).show();
            }
        }


    }

}
