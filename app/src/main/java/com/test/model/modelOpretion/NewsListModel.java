package com.test.model.modelOpretion;



import com.test.constract.NewsListConstract;
import com.test.ui.base.BaseModel;

import rx.Observable;


/**
 * Created by 简言 on 2019/2/10.
 * 努力吧 ！ 少年 ！
 */

public class NewsListModel extends BaseModel implements NewsListConstract.INewsListModel {

    @Override
    public Observable getNewsList(String channelCode, long lastTime) {

        return getApiService().getNewsList(channelCode, lastTime, (System.currentTimeMillis() / 1000));
    }
}
