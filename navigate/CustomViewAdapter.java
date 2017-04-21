package com.example.dell.navigate;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class CustomViewAdapter extends PagerAdapter {
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private String[] mResources;
    String log_tag=CustomViewAdapter.class.getSimpleName();

    public CustomViewAdapter(Context context,String[] resources) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = resources;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = mLayoutInflater.inflate(R.layout.pager_list,container,false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
      //  imageView.setImageResource(mResources[position]);
        Picasso.with(mContext).load(new ServerFile().image_php+mResources[position]).
                placeholder(R.drawable.ic_image_black_48dp).into(imageView);


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}