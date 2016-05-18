package com.nsw.a6vfilm.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by niushuowen on 2016/5/5.
 */
public class SixVFilmApplication extends Application {

    private static SixVFilmApplication application;
    private int screen_width;
    private int screen_height;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Fresco.initialize(this);
    }

    public static SixVFilmApplication getInstance(){
       return application;
    }


    public void getScreenWidth(){
        if(screen_width == 0){
            screen_width = getResources().getDisplayMetrics().widthPixels;
        }
    }

    public void getScreenHeight(){
        if(screen_height == 0){
            screen_height = getResources().getDisplayMetrics().heightPixels;
        }
    }

    private RequestQueue mRequestQueue;


    /**
     * 全局的RequestQueue
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            synchronized (SixVFilmApplication.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(this);
                }
            }
        }
        return mRequestQueue;
    }

}
