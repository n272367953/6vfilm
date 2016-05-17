package com.nsw.a6vfilm.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by niushuowen on 2016/5/17.
 */
public class T {
    public static void showShort(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
