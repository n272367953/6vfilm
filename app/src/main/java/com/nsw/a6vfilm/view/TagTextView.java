package com.nsw.a6vfilm.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nsw.a6vfilm.R;

/**
 * Created by niuge on 2016/3/27.
 */
public class TagTextView extends TextView {

    public static final int TAG_HOT = 1;
    public static final int TAG_RECOMMENDS= 2;

    private int mTagFlag;


    public TagTextView(Context context, int tagFlag){
        this(context);
        mTagFlag = tagFlag;
        init();
    }

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setTextSize(12);
        switch (mTagFlag){
            case TAG_HOT:
               setTextColor(0xff0000);
                setCompatBackgroundDrawable(getCompatResource(R.drawable.hot_tag_bg));
                break;
            case TAG_RECOMMENDS:
                setTextColor(0x3F51B5);
                setCompatBackgroundDrawable(getCompatResource(R.drawable.recommend_tag_bg));
                break;
        }
    }

    private Drawable getCompatResource(int id){
        if(Build.VERSION.SDK_INT>=21){
            return getResources().getDrawable(id,null);
        }else{
            return getResources().getDrawable(id);
        }
    }

    private void setCompatBackgroundDrawable(Drawable backgroundDrawable){
        if(Build.VERSION.SDK_INT>=16){
            setBackground(backgroundDrawable);
        }else{
            setBackgroundDrawable(backgroundDrawable);
        }
    }
}
