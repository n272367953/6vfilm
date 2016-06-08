package com.nsw.a6vfilm.Dialog;

import android.app.Activity;

import com.nsw.a6vfilm.R;

/**
 * Created by niushuowen on 2016/6/6.
 */
public class TestBulrDialog extends BlurDialog {

    public TestBulrDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreateDialog() {
        setContentView(R.layout.new_app_widget);
    }
}
