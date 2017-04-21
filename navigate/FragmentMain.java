package com.example.dell.navigate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DELL on 24/02/2017.
 */

public class FragmentMain extends Fragment {

    String myJson;
    private JSONArray data= null;
    String log_tag=FragmentMain.class.getSimpleName();
    int success;
    private static String TAG_SUCCESS="success";
    private static String TAG_Count="count";
    private static String TAG_PRODUCT="product";
    private static String TAG_CATEGORY="category";
    private static String TAG_SUBCATEGORY1="subcategory1";
    private static String TAG_SUBCATEGORY2="subcategory2";
    private static String TAG_SUBCATEGORY3="subcategory3";
    private static String TAG_PRICE1="price1";
    private static String TAG_PRICE2="price2";
    private static String TAG_PRICE3="price3";
    private ProgressBar spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.view_recycle,container,false);
        final CheckConnection checkConnection = new CheckConnection(getActivity());
        if (!checkConnection.checkInternetConenction()) {
            Intent intent = new Intent(getActivity(), Internet.class);
            startActivity(intent);
        }
        else
        getData(view);
        return view;
    }



        private void getData(final View view)
        {
            class Cart extends AsyncTask<Void,Void,String > {
ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
              //      spinner.setVisibility(View.VISIBLE);
                    loading = ProgressDialog.show(getActivity(), "", "loading", false, false);
                }

                @Override
                protected String doInBackground(Void... params) {


                    Uri builturi = Uri.parse(new ServerFile().user_main).buildUpon().build();
                    return new HttpFetch().httpResquest(builturi.toString(), "fetch");

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    myJson = s;
                  //  spinner.setVisibility(View.GONE);
                    showValues(view);
                 loading.dismiss();

                }
            }

            Cart c=new Cart();
            c.execute();

        }

    private void showValues(View view)
    {
        try {
            JSONObject jsonObj = new JSONObject(myJson);
            success = jsonObj.getInt(TAG_SUCCESS);
            if (success == 1) {

                data = jsonObj.getJSONArray(TAG_PRODUCT);
                int i;

                ArrayList<AndroidVersion2> android_version = new ArrayList<>();

                for (i = 0; i < data.length(); i++)
                {
                    JSONObject value = data.getJSONObject(i);
                    AndroidVersion2 androidVersion = new AndroidVersion2();
                    androidVersion.setCategory(value.getString(TAG_CATEGORY));
                    androidVersion.setAndroid_product_name1(value.getString(TAG_SUBCATEGORY1));
                    androidVersion.setAndroid_product_name2(value.getString(TAG_SUBCATEGORY2));
                    androidVersion.setAndroid_product_name3(value.getString(TAG_SUBCATEGORY3));
                    androidVersion.setAndroid_price1(value.getString(TAG_PRICE1));
                    androidVersion.setAndroid_price2(value.getString(TAG_PRICE2));
                    androidVersion.setAndroid_price3(value.getString(TAG_PRICE3));
                    android_version.add(androidVersion);
                }
                RecyclerView recyclerView = (RecyclerView)
                        view.findViewById(R.id.card_recycler_view);
                assert recyclerView != null;
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration  mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        R.drawable.divide);
                recyclerView.addItemDecoration(mDividerItemDecoration);
                DataAdapter2 adapter = new DataAdapter2(getActivity(),android_version);
                recyclerView.setAdapter(adapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    }

