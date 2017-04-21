package com.example.dell.navigate;


import android.util.Log;

public class image_take
{
    String log_tag=image_take.class.getSimpleName();
    public image_take()
    {
    }
    static String image_name[];

    public image_take(String image_name[])
    {
        image_take.image_name = image_name;
        Log.e(log_tag, String.valueOf(image_take.image_name.length));
    }

    public static String[] taken_image()
    {
        return image_name;
    }
}
