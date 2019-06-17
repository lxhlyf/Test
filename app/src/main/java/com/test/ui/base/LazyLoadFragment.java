package com.test.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public abstract class LazyLoadFragment extends Fragment {

    private static final String TAG = LazyLoadFragment.class.getSimpleName();

    private boolean isFirstEnter = true;//是否是第一次进入,默认是
    private boolean isReuseView = true ;//是否进行复用，默认复用
    private boolean isFragmentVisible;
    private View rootView;

    /**
     *  会不断被轮循调用
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(rootView == null){
            return;
        }

        if (isFirstEnter && isVisibleToUser){
            onFragmentFirstVisible();
            isFirstEnter = false;
        }

        if (isVisibleToUser){
            isFragmentVisible = isVisibleToUser;
            onFragmentVisibleChange(isFragmentVisible);

            return;
        }

        if (isFragmentVisible){
            isFragmentVisible = false;
            onFragmentVisibleChange(isFragmentVisible);
        }

    }

    /**
     * 当Fragment的可见状态发生改变时回调这个方法。
     * @param isFragmentVisible
     */
    public abstract void onFragmentVisibleChange(boolean isFragmentVisible);

    /**
     *  主要用于第一次进入时加载数据
     */
    public abstract void onFragmentFirstVisible();

    /**
     *  当调用setUserVisibleHint时 rootView还没有被实例化，就会调用onViewCreated
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstEnter) {
                    onFragmentFirstVisible();
                    isFirstEnter = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    /**
     *  view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     */
    public void reUseView(boolean isReuseView){
         this.isReuseView = isReuseView;
    }

    /**重置变量*/
    private void resetVariable(){
        isFirstEnter = true;
        isReuseView = true;
        isFragmentVisible = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        resetVariable();
    }
}
