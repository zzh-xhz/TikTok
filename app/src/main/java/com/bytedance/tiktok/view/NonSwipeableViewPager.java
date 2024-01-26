package com.bytedance.tiktok.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NonSwipeableViewPager extends ViewPager {
    private boolean isSwipeEnabled = true;

    public void setSlide(boolean slide) {
        isSwipeEnabled = slide;
    }

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeEnabled && super.onInterceptTouchEvent(event);
    }

}
