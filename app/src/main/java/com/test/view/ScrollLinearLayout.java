package com.test.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by 简言 on 2019/2/20  11:06.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.test.view
 * Description :
 */
public class ScrollLinearLayout extends LinearLayout {


    public ScrollLinearLayout(Context context) {
        super(context);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int startX;
    private int startY;
    private int deltaY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) getY();
                break;
            case MotionEvent.ACTION_MOVE:
                deltaY = (int) (getY() - startY);
                if (Math.abs(deltaY) > 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                performClick(); //onClick方法将会在该方法中调用
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}