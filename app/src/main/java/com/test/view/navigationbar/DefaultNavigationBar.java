package com.test.view.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.test.R;


/**
 * Created by 简言 on 2018/11/28.
 * 努力吧 ！ 少年 ！
 */

public class  DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    //搜索框的监听
    private View.OnClickListener mSearchClick = null;


    public DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super( params);
    }

    @Override
    public int bindLayoutId() {

        return getParams().mBindViewId;
    }

    @Override
    public void applyView() {
        //绑定参数，绑定效果
        if (getParams().mTittle != null) {
            setText(getParams().mTitleViewId, getParams().mTittle);
        }
        setLeftIcon(getParams().mLeftIconViewId, getParams().mLeftIcon);
        setRightIcon(getParams().mRightIconViewId, getParams().mRightIcon);
        setLeftClick(getParams().mLeftIconViewId, getParams().mLeftClick);
        setRightClick(getParams().mRightIconViewId, getParams().mRightClick);

    }

    /**
     * 获取收缩框的文本信息
     */
    public String getSearchText(){

        return getSearchText("");
    }


    /**
     *  设置搜索框的监听事件
     * @param searchListener
     * @return
     */
    public void setSearchClick(View.OnClickListener searchListener){
        if (searchListener != null){
           setSearchClick(R.id.ib_search, searchListener);
        }
    }

    public static class Builder extends AbsNavigationBar.Builder{
        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {

            super(context,null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public AbsNavigationBar build() {

            DefaultNavigationBar instance = new DefaultNavigationBar(P);
            return instance;
        }

        // 在 从MainActivity 向 Builder 里设置所有的效果

        public Builder isNotHomePage(boolean isNotFirst, boolean isHome){
            if (isNotFirst == true){
                P.isHome = isHome;
            }
            return this;
        }

        /**
         *  提供 navigation 的整体布局
         * @param bindViewId
         * @return
         */
         public Builder bindViewId(Object bindViewId){

            if (bindViewId != null){
                P.mBindViewId = (int) bindViewId;
            }
             return this;
         }

        /**
         *   设置右边图片
         * @param rightIcon
         * @return
         */
        public Builder setRightIcon(int rightIconViewId ,Object rightIcon){
            if (rightIcon != null){
                P.mRightIconViewId = rightIconViewId;
                P.mRightIcon = (int) rightIcon;
            }
            return this;
        }

        /**
         *   设置左边图片
         * @param rightIcon
         * @return
         */
        public Builder setLeftIcon(int leftIconViewId, Object rightIcon){
            if (rightIcon != null){
                P.mLeftIconViewId = leftIconViewId;
                P.mLeftIcon = (int) rightIcon;
            }
            return this;
        }

        /**
         *   设置右边图片的点击事件
         * @param rightListener
         * @return
         */
        public Builder setRightClick(View.OnClickListener rightListener){
            if (rightListener != null){
                P.mRightClick = rightListener;
            }
            return this;
        }

        /**
         *   设置左边图片的点击事件
         * @param leftListener
         * @return
         */
        public Builder setLeftClick(View.OnClickListener leftListener){
            if (leftListener != null){
                P.mLeftClick = leftListener;
            }
            return this;
        }


        /**
         *   设置标题
         * @param title
         * @return
         */
        public Builder setTittle(Object titleViewId, String title){
            if(title != null){
                P.mTitleViewId = (int) titleViewId;
                P.mTittle = title;
            }
            return this;
        }


        public static class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationParams{

            // 从 Builder里获得params后 设置所有的效果

            //标题
            public String mTittle;
            //搜索框是否可见
            private boolean mGone = true;


            public int mRightIconViewId;  //显示控件的Id
            public int mRightIcon;  //右边图片的Id

            //左边图片
            public int mLeftIconViewId;
            public int mLeftIcon;

            public int mTitleViewId;  //显示文字空间的Id

            public int mBindViewId;  // navigation布局文件的Id。

            //通用的参数
            //右边图片的监听
            public View.OnClickListener mRightClick;
            //左边图片的监听  默认点击后 退出当前Activity
            public View.OnClickListener mLeftClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity)(mContext)).finish();
                }
            };



            DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }



        }
    }
}
