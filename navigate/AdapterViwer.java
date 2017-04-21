package com.example.dell.navigate;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

public class AdapterViwer extends PagerAdapter {

    private String[] image_resources = {"SVC01", "SVC02", "SVC04", "SVC05", "SVC06"};
    private Context ctx;
    private LayoutInflater layoutInflater;

    public AdapterViwer(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {

        return (view == (RelativeLayout) o);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View is_item = layoutInflater.inflate(R.layout.image, container, false);
        ImageView imageView = (ImageView) is_item.findViewById(R.id.imageView_slider);
        Picasso.with(ctx).load("http://192.168.1.3/ImageUpload/"+image_resources[position]+".jpg")
                .placeholder(R.drawable.ic_image_black_48dp).into(imageView);
        container.addView(is_item);
        return is_item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
        ;
    }
}