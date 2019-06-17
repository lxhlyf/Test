package com.test.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.test.R;


/**
 * Created by 简言 on 2018/11/28.
 * 努力吧 ！ 少年 ！
 * <p>
 * Description : NavigationBar 基类
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

    private P mParams;
    //头布局
    private View navigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        // PhoneWindow -- > DecorView(FrameLayout) -- > LinearLayout(ContentView(FrameLayout)  -- > 自定义的Layout )

        //处理 Activity
        if (mParams.mViewGroup == null) {

            // android.widget.LinearLayout{c525bef V.E..... ......ID 0,0-720,1230}
            //activityRoot 是contentParent , 其中包含 titleView + ContentView
            ViewGroup activityRoot = (ViewGroup) ((Activity)(mParams.mContext))
                    .findViewById(android.R.id.content);
            //拿到titleView titleVie 是一个 LinearLayout
            mParams.mViewGroup = (ViewGroup) activityRoot.getChildAt(0);

            if (!getParams().isHome){
                mParams.mViewGroup.removeViewAt(0);
            }

            Log.i("isFirst", ""+getParams().isHome);

        }

        //处理 AppCompatActivity
        if (mParams.mViewGroup == null) {

            //  .findViewById(android.R.id.content)
            //获取AppCompatActivity的根布局
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext))
                   .getWindow().getDecorView();

            //拿到LinearLayout
            mParams.mViewGroup = (ViewGroup) activityRoot.getChildAt(0); //Content
        }


        if (mParams.mViewGroup == null) {
            return;
        }

        //1.创建 View
        navigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mViewGroup, false);
        //2.添加
        mParams.mViewGroup.addView(navigationView, 0);

        //3.绑定 View
        applyView();
    }

    public P getParams() {

        return mParams;
    }

    public void setText(int viewId, String mTittle) {

        TextView view = (TextView) getView(viewId);
        if (view != null) {
            view.setText(mTittle);
        }
    }


    public void setRightIcon(int viewId, int mRightIcon) {

        ImageButton view = (ImageButton) getView(viewId);
        if (view != null) {
            view.setImageResource(mRightIcon);
        }
    }

    public void setLeftIcon(int viewId, int mLeftIcon) {

        ImageButton view = (ImageButton) getView(viewId);
        if (view != null) {
            view.setImageResource(mLeftIcon);
        }
    }

    public void setLeftClick(int viewId, View.OnClickListener mLeftClick) {

        View view = getView(viewId);
        if (view != null) {

            view.setOnClickListener(mLeftClick);
        }
    }

    public void setRightClick(int viewId, View.OnClickListener mRightClick) {

        View view = getView(viewId);
        if (view != null) {

            view.setOnClickListener(mRightClick);
        }
    }

    public void setSearchClick(int viewId, View.OnClickListener mRightClick) {

        View view = getView(viewId);
        if (view != null) {

            view.setOnClickListener(mRightClick);
        }
    }

    public String getSearchText(String str) {

        EditText view = getView(R.id.tv_search);
        if (view != null) {

            return String.valueOf(view.getText());
        }
        return "";
    }

    private <T extends View> T getView(int viewId) {

        return (T) navigationView.findViewById(viewId);
    }


    public abstract int bindLayoutId();

    public abstract void applyView();

    // Builder 套路  1. NavigationBar 2. Builder 3. NavigationBarParam
    public static abstract class Builder {
        AbsNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            //创建 P = new
            P = new AbsNavigationParams(context, parent);
        }

        public abstract AbsNavigationBar build();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mViewGroup;
            public boolean isHome = true;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mViewGroup = parent;
            }
        }
    }
}
