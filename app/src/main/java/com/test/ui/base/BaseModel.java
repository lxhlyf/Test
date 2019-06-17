package com.test.ui.base;


import com.test.http.httpClass.retrofit.ApiService;
import com.test.http.httpClass.retrofit.RetrofitEngine;

/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 */

public class BaseModel {



    protected ApiService getApiService(){

        return  RetrofitEngine.getInstance().getApiService();
    }
}
