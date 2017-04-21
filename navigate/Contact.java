package com.example.dell.navigate;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;


public class Contact extends AppCompatActivity implements View.OnClickListener
{

    private EditText inputName, inputEmail, inputPhone,inputSubject,inputAddress,inputQuantity;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPhone,inputLayoutSubject,
    inputLayoutAddress,inputLayoutQuantity;
    private Button b_enquiry,b_place,b_placed,b_enquiry_add;
    String product_name,product_code,cus_name,phone,email,subject,quantity,address,d_price,o_price;
    Intent i;
    private AdView mAdView;
    String log_tag=Contact.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        assert upArrow != null;
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFFFFF\">" + getString(R.string.title_contact) + "</font>"));
        ids();
        mAdView = (AdView) findViewById(R.id.adView);
        assert mAdView != null;
        mAdView.setVisibility(View.VISIBLE);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("dd55585dd1daa806")
                .build();
        mAdView.loadAd(adRequest);
         }

    private void ids()
    {
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutSubject = (TextInputLayout) findViewById(R.id.input_layout_subject);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputLayoutQuantity = (TextInputLayout) findViewById(R.id.input_layout_quantity);

        inputName = (EditText) findViewById(R.id.hint_name);
        inputEmail = (EditText) findViewById(R.id.hint_email);
        inputPhone = (EditText) findViewById(R.id.hint_phone);
        inputSubject = (EditText) findViewById(R.id.hint_subject);
        inputAddress = (EditText) findViewById(R.id.hint_address);
        inputQuantity = (EditText) findViewById(R.id.hint_quantity);

        b_enquiry = (Button) findViewById(R.id.b_enquiry);
        b_place = (Button) findViewById(R.id.b_place);
        b_placed = (Button) findViewById(R.id.b_placed);
        b_enquiry_add = (Button) findViewById(R.id.button_enq);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputSubject.addTextChangedListener(new MyTextWatcher(inputSubject));
        inputAddress.addTextChangedListener(new MyTextWatcher(inputAddress));
        inputQuantity.addTextChangedListener(new MyTextWatcher(inputQuantity));

        b_enquiry.setOnClickListener(this);
        b_place.setOnClickListener(this);
        b_placed.setOnClickListener(this);
        b_enquiry_add.setOnClickListener(this);
        i=getIntent();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nothing, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                //  NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        SessionManager sessionManager = new SessionManager(Contact.this);
        final CheckConnection checkConnection = new CheckConnection(Contact.this);
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(Contact.this, Internet.class);
            startActivity(intent);
        }

            if (sessionManager.isLoggedIn()) {
            final HashMap<String, String> user = sessionManager.getUserDetails();
            cus_name= user.get(SessionManager.KEY_NAME);
            phone=user.get(SessionManager.KEY_NUMBER);
            email=user.get(SessionManager.KEY_EMAIL);
            address=user.get(SessionManager.KEY_ADDRESS);
            inputName.setText(cus_name);
            inputPhone.setText(phone);
            inputEmail.setText(email);
            inputAddress.setText(address);
            if(i!=null)
            {
                product_name=i.getStringExtra("ProductName");
               product_code=i.getStringExtra("code");
                d_price=i.getStringExtra("d_price");
                o_price=i.getStringExtra("o_price");
                Log.e(log_tag,d_price);
            }
            inputSubject.setText(product_name);
        }
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        String phone=inputPhone.getText().toString().trim();

        if (phone.isEmpty()  ) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhone);
            return false;
        } else {
            if(phone.matches("9[0-9]{9}") || phone.matches("8[" + "0-9]{9}")||phone.matches("7[0-9]{9}"))
            {

                inputLayoutPhone.setErrorEnabled(false);
            }
            else
            {
                inputLayoutPhone.setError(getString(R.string.err_valid_phn));
                requestFocus(inputPhone);
                return false;
            }
            }

        return true;
    }

    private boolean validateAddress()
    {
        if (inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError(getString(R.string.err_msg_address));
            requestFocus(inputAddress);
            return false;
        } else {
            inputLayoutAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateSubject() {
        if (inputSubject.getText().toString().trim().isEmpty()) {
            inputLayoutSubject.setError(getString(R.string.err_msg_subject));
            requestFocus(inputSubject);
            return false;
        } else {
            inputLayoutSubject.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateQuantity() {
        quantity=inputQuantity.getText().toString().trim();
        if (quantity.isEmpty()) {
            inputLayoutQuantity.setError(getString(R.string.err_msg_quantity));
            requestFocus(inputQuantity);
            return false;
        } else {
            inputLayoutQuantity.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

   /* private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.b_enquiry:
                if (validateName()&& validateEmail() && validatePhone()&& validateSubject())
                {
                    new EnquiryAndPlace(this).execute("0",inputName.getText().toString(),
                            inputEmail.getText().toString(),
                            inputPhone.getText().toString(),
                            inputSubject.getText().toString(),
                            product_code);
                    Toast.makeText(Contact.this,"Equiry",Toast.LENGTH_LONG).show();
                }
                else
                {
                    return;
                }


                break;
            case R.id.b_place:
                inputLayoutAddress.setVisibility(View.VISIBLE);
                inputLayoutQuantity.setVisibility(View.VISIBLE);
                b_placed.setVisibility(View.VISIBLE);
                b_place.setVisibility(View.GONE);
                b_enquiry.setVisibility(View.GONE);
                b_enquiry_add.setVisibility(View.VISIBLE);
                break;
            case R.id.button_enq :
                inputLayoutAddress.setVisibility(View.GONE);
                inputLayoutQuantity.setVisibility(View.GONE);
                b_placed.setVisibility(View.GONE);
                b_place.setVisibility(View.VISIBLE);
                b_enquiry.setVisibility(View.VISIBLE);
                b_enquiry_add.setVisibility(View.GONE);
                break;
            case R.id.b_placed:

                if (validateName()&& validateEmail() && validatePhone()&& validateSubject()
                        && validateAddress() && validateQuantity())
                { long amount=Long.valueOf(quantity)*Long.valueOf(d_price);
                    //Log.e(log_tag,String.valueOf(amount));
                    AlertDialog.Builder builder = new AlertDialog.Builder(Contact.this);
                    builder.setMessage("Customer Name:"+inputName.getText().toString()
                            +"\r\nPhone No.:"+inputPhone.getText().toString()
                            +"\r\nEmail:"+inputEmail.getText().toString()
                            +"\r\nDelivery Address:"+inputAddress.getText().toString()
                            +"\r\nProduct:"+inputSubject.getText().toString()
                            +"\r\nQuantity:"+quantity
                            +"\r\nOriginal Price:"+o_price
                            +"\r\nAfter Discount:"+d_price
                            +"\r\nTotal Amount:"+ String.valueOf(amount)
                            +"\r\n\r\nPlace Order?" ).setTitle("Order Summary")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new EnquiryAndPlace(Contact.this).execute("1",inputName.getText().toString(),
                                            inputEmail.getText().toString(),
                                            inputPhone.getText().toString(),
                                            inputSubject.getText().toString(),
                                            inputQuantity.getText().toString(),
                                            inputAddress.getText().toString(),product_code
                                    );
                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.show();
                     }
                else
                {
                    return;
                }
                break;
        }
    }



    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.hint_name:
                    validateName();
                    break;
                case R.id.hint_email:
                    validateEmail();
                    break;
                case R.id.hint_phone:
                    validatePhone();
                    break;
                case R.id.hint_subject:
                    validateSubject();
                    break;
                case R.id.hint_address:
                    validateAddress();
                    break;
                case R.id.hint_quantity:
                    validateQuantity();
                    break;
            }
        }
    }


}
