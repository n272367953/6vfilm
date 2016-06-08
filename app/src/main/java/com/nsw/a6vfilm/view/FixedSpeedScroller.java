/*
 *	Copyright (c) 2015, Red Finance Information Technologies
 *	All rights reserved.
 *  
 *  @author: liujunbo	
 */
package com.nsw.a6vfilm.view;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 固定速率Scroller
 * @author liujunbo
 * @date May 08, 2015	
 */
public class FixedSpeedScroller extends Scroller {
	private int mDuration;

    public FixedSpeedScroller(Context context) {
        super(context);
    }
    
    public void setDuration(int duration){
    	this.mDuration = duration;
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}