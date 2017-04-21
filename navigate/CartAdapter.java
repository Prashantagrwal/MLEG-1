package com.example.dell.navigate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private ArrayList<CartAndroidVersion> android;
    Context context;


    public CartAdapter(Context context,ArrayList<CartAndroidVersion> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder viewHolder, final int i)
    {

        viewHolder.tv_product_name.setText(android.get(i).getAndroid_product_name());
        viewHolder.tv_product_o.setText(android.get(i).getAndroid_o_price());
        viewHolder.tv_product_d.setText(android.get(i).getAndroid_d_price());
        Picasso.with(context).load(new ServerFile().image_php+android.get(i).
                getAndroid_product_code()+".jpg").placeholder(R.drawable.ic_image_black_48dp).into(viewHolder.imageView);
        viewHolder.b_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SessionManager sessionManager=new SessionManager(context);
                new CartAdd(context).
                        execute(sessionManager.getUserDetails().get(SessionManager.KEY_ID),
                                android.get(i).getAndroid_product_code(),"1");
                Intent i =new Intent(context,CartPHPfetch.class);
                i.putExtra("userid",sessionManager.getUserDetails().get(SessionManager.KEY_ID));
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        });

        viewHolder.b_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Contact.class);
                intent.putExtra("ProductName", android.get(i).getAndroid_product_name());
                intent.putExtra("code",android.get(i).getAndroid_product_code());
                intent.putExtra("o_price",android.get(i).getAndroid_o_price());
                intent.putExtra("d_price",android.get(i).getAndroid_d_price());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_product_name,tv_product_o,tv_product_d;
        private ImageView imageView;
        private Button b_remove,b_contact;
        public ViewHolder(View view) {
            super(view);
            tv_product_name = (TextView)view.findViewById(R.id.cart_product_name);
            tv_product_o = (TextView)view.findViewById(R.id.cart_o_price);
            tv_product_d = (TextView)view.findViewById(R.id.cart_d_price);
            imageView=(ImageView)view.findViewById(R.id.cart_image);
            b_remove=(Button)view.findViewById(R.id.b_cart_remove);
            b_contact=(Button)view.findViewById(R.id.b_cart_contact);

            b_contact.setOnClickListener(this);
            b_remove.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.b_cart_remove :

                    break;
                case R.id.b_cart_contact:

                    break;
            }

        }
    }

}
