package com.nsw.a6vfilm.net;

import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.nsw.a6vfilm.application.SixVFilmApplication;

import java.util.Map;

/**
 * Created by niushuowen on 2016/5/5.
 */
public class AppRequest {

    public void sendRequest(String url, Map<String, String> map, AppRequestListener listener) {
        this.sendRequest(url, map, listener, null);
    }

    public void sendRequest(String url, Map<String, String> map, AppRequestListener listener, String tag) {
        SxiVRequest request = new SxiVRequest(Request.Method.POST, url, listener, map);
        RequestQueue requestQueue = SixVFilmApplication.getInstance().getRequestQueue();
        request.setRetryPolicy(new DefaultRetryPolicy(15 * 1000, 1, 1.0f));
        if (!TextUtils.isEmpty(tag)) {
            request.setTag(tag);
        }
        requestQueue.add(request);
    }

}
