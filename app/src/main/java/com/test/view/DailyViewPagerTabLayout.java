package com.test.view;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.utils.UIUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by 简言 on 2019/2/5.
 * 努力吧 ！ 少年 ！
 */

public class DailyViewPagerTabLayout extends TabLayout {

    private Context context;
    private Activity mActivity;

    public DailyViewPagerTabLayout(Context context) {
        this(context, null);
    }

    public DailyViewPagerTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DailyViewPagerTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mActivity != null){
            //int dp10 = UIUtils.dip2px(context,30);
            int screeWidth = UIUtils.getWindowWidth(mActivity);

            LinearLayout mTabStrip = (LinearLayout) this.getChildAt(0);
            try {
                Field mTabs = TabLayout.class.getDeclaredField("mTabs");
                mTabs.setAccessible(true);
                ArrayList<Tab> tabs = (ArrayList<Tab>) mTabs.get(this);
                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    Tab tab = tabs.get(i);
                    Field mView = tab.getClass().getDeclaredField("mView");
                    mView.setAccessible(true);
                    Object tabView = mView.get(tab);
                    Field mTextView = context.getClassLoader().loadClass("android.support.design.widget.TabLayout$TabView").getDeclaredField("mTextView");
                    mTextView.setAccessible(true);
                    TextView textView = (TextView) mTextView.get(tabView);
                    float textWidth = textView.getPaint().measureText(textView.getText().toString());
                    View child = mTabStrip.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) textWidth, LinearLayout.LayoutParams.MATCH_PARENT);
//                 params.leftMargin = dp10;
//                 params.rightMargin = dp10;
                    params.width = screeWidth/2;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }


}
