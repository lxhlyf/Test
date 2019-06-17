package com.test.app;

import org.litepal.LitePal;

/**
 * Created by 简言 on 2019/2/7.
 * 努力吧 ！ 少年 ！
 */

public class MyApp extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();
        //**************************************相关第三方SDK的初始化等操作*************************************************
        LitePal.initialize(getContext());
    }
}
