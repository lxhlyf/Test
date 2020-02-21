package com.test.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 简言 on 2019/2/5.
 * 努力吧 ！ 少年 ！
 */

public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
       this(context, null);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }



}
