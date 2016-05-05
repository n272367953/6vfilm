package com.nsw.a6vfilm.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by niushuowen on 2016/5/5.
 */
public class SxiVRequest extends Request<String> {
    private Map<String, String> map;
    private AppRequestListener mlistener;
    private Response.ErrorListener errorListener;
    public SxiVRequest(int method, String url, AppRequestListener listener, Response.ErrorListener errListener, String tag,Map map) {
        super(method, url, errListener);
        this.mlistener = listener;
        this.errorListener = errListener;
        this.map = map;
    }


    public SxiVRequest(int method, String url, AppRequestListener listener, Map map) {
        this(method, url, listener, null,null, map);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (null != map && map.size() > 0) {
            return map;
        }
        return super.getParams();
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parseStr = null;
        try {
            parseStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(parseStr, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (this.mlistener != null) {
            this.mlistener.onResponse(response);
        }
    }
}
