package com.nsw.a6vfilm.utils;

import android.content.Context;

/**
 * Created by niushuowen on 2016/5/24.
 */
public class SystemUtils {
    /**
     * 得到当前系统状态栏高度
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context)
    {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
