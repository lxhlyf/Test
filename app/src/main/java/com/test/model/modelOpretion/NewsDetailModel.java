package com.test.model.modelOpretion;

import com.test.constract.NewsDetailConstract;
import com.test.http.httpClass.retrofit.RetrofitEngine;
import com.test.ui.base.BaseModel;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */
public class NewsDetailModel extends BaseModel implements NewsDetailConstract.NewsDetailModel {
    @Override
    public Observable getNewsDetail(String url) {

        return getApiService().getNewsDetail(url);
    }

    @Override
    public Observable getComment(String groupId, String itemId, String offset, String  count) {

        return getApiService().getComment(groupId, itemId, offset, count);
    }
}
