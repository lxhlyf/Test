package com.test.ui.presenter;


import android.util.Log;

import com.test.constract.NewsDetailConstract;
import com.test.data.Constant;
import com.test.http.httpClass.retrofit.SubscriberCallBack;
import com.test.model.modelOpretion.NewsDetailModel;
import com.test.model.response.CommentResponse;
import com.test.model.result.toutiao.NewsDetail;
import com.test.ui.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */

public class NewsDetailPresenter extends BasePresenter<NewsDetailConstract.INewsDetailView, NewsDetailModel> implements NewsDetailConstract.NewsDetailPresenter{


    @Override
    public void getComment(String groupId, String itemId, int pageNow) {
        int offset = (pageNow - 1) * Constant.COMMENT_PAGE_SIZE;
        addSubscription(getModel().getComment(groupId, itemId, offset + "", String.valueOf(Constant.COMMENT_PAGE_SIZE)), new Subscriber<CommentResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.e("", e.getLocalizedMessage());
                getView().onError();
            }

            @Override
            public void onNext(CommentResponse response) {
                getView().onGetCommentSuccess(response);
            }

        });
    }

    @Override
    public void getNewsDetail(String url) {
        addSubscription(getModel().getNewsDetail(url), new SubscriberCallBack<NewsDetail>() {

            @Override
            protected void onSuccess(NewsDetail response) {
                getView().onGetNewsDetailSuccess(response);
            }

            @Override
            protected void onError() {
                getView().onError();
            }
        });
    }
}
