package com.test.ui.presenter;

import com.google.gson.Gson;
import com.test.constract.NewsListConstract;
import com.test.model.modelOpretion.NewsListModel;
import com.test.model.result.toutiao.News;
import com.test.model.result.toutiao.NewsData;
import com.test.model.response.NewsResponse;
import com.test.ui.base.BasePresenter;
import com.test.utils.ListUtils;
import com.test.utils.SharePreUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/10.
 * 努力吧 ！ 少年 ！
 */

public class NewsListPresenter extends BasePresenter<NewsListConstract.INewsListView, NewsListModel> implements NewsListConstract.INewsListPresenter {

    private long lastTime;

    @Override
    public void getNewsList(String channelCode) {

        lastTime = SharePreUtils.getLong(channelCode, 0);//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0) {
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }

        addSubscription(getModel().getNewsList(channelCode, lastTime), new Subscriber<NewsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getView().onError(e);
            }

            @Override
            public void onNext(NewsResponse newsResponse) {
                lastTime = System.currentTimeMillis() / 1000;
                SharePreUtils.putLong(channelCode, lastTime);

                List<NewsData> newsDatas = newsResponse.data;
                List<News> news = new ArrayList<>();
                if (!ListUtils.isEmpty(newsDatas)) {

                    for (NewsData newsData : newsDatas) {

                        Gson mGson = new Gson();
                        News aNew = mGson.fromJson(newsData.content, News.class);
                        news.add(aNew);
                    }

                }
                getView().onSuccess(news,newsResponse.tips.display_info);
            }
        });
    }
}

