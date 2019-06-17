package com.test.constract;



import com.test.model.result.toutiao.News;
import com.test.ui.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/10.
 * 努力吧 ！ 少年 ！
 */

public class NewsListConstract {

    public interface INewsListView extends BaseView {

        void onError(Throwable e);
        void onSuccess(List<News> data, String display_info);
    }

    public interface INewsListPresenter {

        void getNewsList(String channelCode);
    }

    public interface INewsListModel {

        Observable getNewsList(String channelCode, long lastTime);
    }
}
