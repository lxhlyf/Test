package com.test.ui.base;


/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 */

public class BaseSkinActivity<T extends BasePresenter> extends BaseActivity<T> {


    @Override
    protected void initView() {

    }

    @Override
    public T createPresenter() {
        return (T) getPresenter();
    }

    public BasePresenter getPresenter(){
        return null;
    }

    @Override
    public int provideContentViewId() {
        return 0;
    }
}
