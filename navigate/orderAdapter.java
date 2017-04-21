package com.example.dell.navigate;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class orderAdapter extends RecyclerView.Adapter<orderAdapter.ViewHolder> {

    private ArrayList<orderVersion> android;
    Context context;
    String log_tag = orderAdapter.class.getSimpleName();

    public orderAdapter(ArrayList<orderVersion> android, Context context) {
        this.android = android;
        this.context = context;
    }

    @Override
    public orderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(orderAdapter.ViewHolder holder, final int position) {
        Log.v(log_tag, android.get(position).getProductName());
        holder.productName.setText(android.get(position).getProductName());



        holder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.check_box,null, false);
                // the alert dialog
                final CheckBox c1,c2,c3,c4;
                c1= (CheckBox) view.findViewById(R.id.cb_contacted);
                c2= (CheckBox) view.findViewById(R.id.cb_design);
                c3= (CheckBox) view.findViewById(R.id.cb_devilery);
                c4= (CheckBox) view.findViewById(R.id.cb_thankyou);
                final String contacted,design,delivery,thankyou,yes="yes";
                contacted=android.get(position).getCall();
                design=android.get(position).getDesign();
                delivery=android.get(position).getDelivery();
                thankyou=android.get(position).getTankYou();
                c1.setClickable(false); c2.setClickable(false); c3.setClickable(false);
                c4.setClickable(false);
                if(contacted.equals(yes))
                {
                    c1.setChecked(true);
                    if(design.equals(yes))
                    {
                        c2.setChecked(true);
                        if(delivery.equals(yes))
                        {
                            c3.setChecked(true);
                            if(thankyou.equals(yes))
                            {
                                c4.setChecked(true);
                            }
                        }
                    }
                }

                AlertDialog.Builder builder=   new AlertDialog.Builder(context);
                builder.setView(view);
                        builder.setTitle("Your Product Status :");

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id)
                            {

                            dialog.cancel();
                            }
                        });
              Dialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

        });

        holder.summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount=String.valueOf(Long.valueOf(android.get(position).getD_price())*
                        Long.valueOf(android.get(position).getQuantity()));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Customer Name:"+android.get(position).getCustomerName()
                +"\r\nPhone No.:"+android.get(position).getPhone()
                +"\r\nEmail:"+android.get(position).getEmail()
                +"\r\nDelivery Address:"+android.get(position).getAddress()
                +"\r\nProduct:"+android.get(position).getProductName()
                +"\r\nQuantity:"+android.get(position).getQuantity()
                +"\r\nOriginal Price:"+android.get(position).getO_price()
                +"\r\nDiscount Price:"+android.get(position).getD_price()
                +"\r\nTotal Amount:"+amount).setTitle("Order Summary")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do nothing
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        Button status,summary;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.oreder_product_name);
            status = (Button) itemView.findViewById(R.id.b_status);
            summary=(Button) itemView.findViewById(R.id.order_summary);
            imageView = (ImageView) itemView.findViewById(R.id.image_order);
        }
    }
}
