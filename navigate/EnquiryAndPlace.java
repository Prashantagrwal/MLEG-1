package com.example.dell.navigate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.util.HashMap;


public class EnquiryAndPlace extends AsyncTask<String,String,String>
        {

            Context c; String check;
            public EnquiryAndPlace(Context c)
            {
                this.c=c;
            }

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=ProgressDialog.show(c,null,"Adding",false,false);
            }

            @Override
            protected String doInBackground(String... params) {
                 check=params[0];
                String name=params[1],phone=params[3],email=params[2],subject=params[4];
                String LINK_NAME="Name",LINK_PHONE="Phone",LINK_EMAIL="Email",
                        LINK_SUBJECT="Subject",LINK_QUANTITY="Quantity", LINK_ADDRESS="Address",
                LINK_CHECK="check",LINK_USERID="userid",LINK_CODE="code";

               Uri builturi=null;
                SessionManager sessionManager=new SessionManager(c);
                HashMap<String, String> user = sessionManager.getUserDetails();
                String userid = user.get(SessionManager.KEY_ID);
                if(check.equals("0"))
                {  String code=params[5];
                     builturi = Uri.parse(new ServerFile().enquiry_orders).buildUpon().
                            appendQueryParameter(LINK_CHECK,check)
                             .appendQueryParameter(LINK_USERID,userid)
                             .appendQueryParameter(LINK_NAME,name)
                            .appendQueryParameter(LINK_PHONE,phone)
                            .appendQueryParameter(LINK_EMAIL,email)
                             .appendQueryParameter(LINK_SUBJECT,subject)
                             .appendQueryParameter(LINK_CODE,code).build();

                }
                else if(check.equals("1"))
                {
                    String quantity=params[5],address=params[6];
                    String code=params[7];

                    builturi = Uri.parse(new ServerFile().enquiry_orders).buildUpon().
                            appendQueryParameter(LINK_CHECK,check)
                            .appendQueryParameter(LINK_USERID,userid)
                            .appendQueryParameter(LINK_NAME,name)
                            .appendQueryParameter(LINK_PHONE,phone)
                            .appendQueryParameter(LINK_EMAIL,email)
                            .appendQueryParameter(LINK_SUBJECT,subject)
                            .appendQueryParameter(LINK_CODE,code)
                            .appendQueryParameter(LINK_QUANTITY,quantity)
                            .appendQueryParameter(LINK_ADDRESS,address).build();
                }
                assert builturi != null;
                return new HttpFetch().httpResquest(builturi.toString(), "add");

            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                progressDialog.dismiss();
                showAlert(s);
            }
            private void showAlert(String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setMessage(message).setTitle("Summary")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                         Intent i=new Intent(c,MainActivity.class);
                            c.startActivity(i);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }


        }
