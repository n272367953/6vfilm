package com.nsw.a6vfilm.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by niushuowen on 2016/5/5.
 */
public class SixVFilmApplication extends Application {

    private static SixVFilmApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static SixVFilmApplication getInstance(){
       return application;
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
